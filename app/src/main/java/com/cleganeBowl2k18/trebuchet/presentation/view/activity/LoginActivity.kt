package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.Manifest.permission.READ_CONTACTS
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.LoginPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity(), LoaderCallbacks<Cursor>, LoginView {

    private var prefs: SharedPreferences? = null
    private var mCurrentUser: User? = null

    @Inject
    lateinit var mPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)

        Glide.with(this)
                .load(R.drawable.cloud_login_4k)
                .into(mBackgroundImage)

        Glide.with(this)
                .load(R.drawable.billshare_logo_x05)
                .into(mLogoImage)

        // Set up the login form.
        populateAutoComplete()
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }

        if (prefs!!.getBoolean(Constants.LOGGED_IN, false)) {
            startActivity(MainIntent())
        }
    }

    override fun onResume() {
        super.onResume()
        prefs = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
        if (prefs!!.getBoolean(Constants.LOGGED_IN, false)) {
            finish()
        }
    }

    private fun populateAutoComplete() {
        if (!mayRequestContacts()) {
            return
        }

        loaderManager.initLoader(0, null, this)
    }

    private fun mayRequestContacts(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok,
                            { requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS) })
        } else {
            requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS)
        }
        return false
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete()
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
//            mAuthTask = UserLoginTask(emailStr, passwordStr)
//            mAuthTask!!.execute(null as Void?)
            mPresenter.login(emailStr, passwordStr)
        }
    }

    override fun loginSuccess(user: User) {
        val editor = prefs!!.edit()
        editor.putBoolean(Constants.LOGGED_IN, true)
        editor.putLong(Constants.CURRENT_USER_ID, user.externalId)
        editor.apply()
        showProgress(false)
        startActivity(MainIntent())
        return
    }

    override fun loginError(message: String) {
        password.error = getString(R.string.error_incorrect_password)
        password.requestFocus()
        showProgress(false)
    }

    override fun createAccountError(message: String) {
        mEmail.error = getString(R.string.error_email_in_use)
        mEmail.requestFocus()
        showProgress(false)
    }

    override fun showError(message: String) {
        // TODO: snackbar
    }

    private fun isEmailValid(email: String): Boolean {
        // TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        // TODO: Replace this with your own logic
        return password.length > 2
    }

    @BindView(R.id.login_progress_hider)
    lateinit var mProgressHider: FrameLayout

    @BindView(R.id.background_image)
    lateinit var mBackgroundImage: ImageView

    @BindView(R.id.show_create_account)
    lateinit var mShowCreateAccount: LinearLayout

    @BindView(R.id.hide_create_account)
    lateinit var mHideCreateAccount: LinearLayout

    @BindView(R.id.email)
    lateinit var mEmail: EditText

    @BindView(R.id.password)
    lateinit var mPassword: EditText

    @BindView(R.id.password_confirm)
    lateinit var mPasswordConfirm: EditText

    @BindView(R.id.first_name)
    lateinit var mFirstName: EditText

    @BindView(R.id.last_name)
    lateinit var mLastName: EditText

    @BindView(R.id.logo_image)
    lateinit var mLogoImage: ImageView

    @OnClick(R.id.create_account_button)
    fun showAccountCreate() {
        mShowCreateAccount.visibility = View.VISIBLE
        mHideCreateAccount.visibility = View.GONE
    }

    @OnClick(R.id.back_to_login)
    fun hideAccountCreate() {
        mShowCreateAccount.visibility = View.GONE
        mHideCreateAccount.visibility = View.VISIBLE
    }

    @OnClick(R.id.action_create_account)
    fun attemptCreateAccount() {
        // reset errors
        mEmail.error = null
        mPassword.error = null
        mPasswordConfirm.error = null
        mFirstName.error = null
        mLastName.error = null

        // store values
        val emailStr = mEmail.text.toString()
        val passwordStr = mPassword.text.toString()
        val confirmedStr = mPasswordConfirm.text.toString()
        val fNameStr = mFirstName.text.toString()
        val lNameStr = mLastName.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        // Check for a valid password
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            mPassword.error = getString(R.string.error_invalid_password)
            focusView = mPassword
            cancel = true
        }

        // Check for a valid confirmed password
        if (!TextUtils.isEmpty(confirmedStr) && !isPasswordValid(confirmedStr)) {
            mPasswordConfirm.error = getString(R.string.error_invalid_password)
            focusView = mPasswordConfirm
            cancel = true
        }

        // Check that passwords match
        if (confirmedStr != passwordStr) {
            mPasswordConfirm.error = getString(R.string.error_password_match)
            focusView = mPasswordConfirm
            cancel = true
        }

        // Validate Names
        if (TextUtils.isEmpty(fNameStr)) {
            mFirstName.error = getString(R.string.error_field_required)
            focusView = mFirstName
            cancel = true
        }

        if (TextUtils.isEmpty(lNameStr)) {
            mLastName.error = getString(R.string.error_field_required)
            focusView = mLastName
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
//            mAuthTask = UserLoginTask(emailStr, passwordStr)
//            mAuthTask!!.execute(null as Void?)
            mPresenter.createAccount(emailStr, passwordStr, fNameStr, lNameStr)
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            mProgressHider.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressHider.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE),

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
    }

    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }

        addEmailsToAutoComplete(emails)
    }

    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {

    }

    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(this@LoginActivity,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)

        email.setAdapter(adapter)
    }

    object ProfileQuery {
        val PROJECTION = arrayOf(
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}
