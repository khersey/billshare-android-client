package com.cleganeBowl2k18.trebuchet.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.models.Pet
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.PetsListAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.HomePresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.HomeView
import java.util.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeView, PetsListAdapter.OnPetItemClickListener,
        PetsListAdapter.OnItemDeletedListener, SwipeRefreshLayout.OnRefreshListener {

    val VERTICAL_SPACING: Int = 30

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.swipe_refresh)
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.pets_list)
    lateinit var mPetsListRV: RecyclerView

    @BindView(R.id.progressbar_group)
    lateinit var mProgressBar: ContentLoadingProgressBar

    @BindView(R.id.empty_pets_list)
    lateinit var mPetsListEmptyView: View

    @Inject
    lateinit var mPresenter: HomePresenter

    lateinit var mPetsListAdapter: PetsListAdapter

    private var mSyncPetsSnackbar: Snackbar? = null

    private val mAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            onPetsListChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onPetsListChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onPetsListChanged()
        }
    }

    companion object IntentFactory {
        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

//        DaggerActivityComponent.builder()
//                .applicationComponent(mApplicationComponent)
//                .build()
//                .inject(this)

        mPresenter.setView(this)
        setSupportActionBar(mToolbar)

        setupRecyclerView()
        setupSwipeRefresh()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.fetchPets()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
        mPetsListAdapter.unregisterAdapterDataObserver(mAdapterDataObserver)
    }

    private fun setupRecyclerView() {
        mPetsListAdapter = PetsListAdapter(ArrayList<Pet>(0), this, this)

        mPetsListRV.itemAnimator = DefaultItemAnimator()
        mPetsListRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mPetsListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mPetsListRV.setHasFixedSize(true)
        mPetsListRV.adapter = mPetsListAdapter

        mPetsListAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    private fun setupSwipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        )
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun showPets(pets: List<Pet>) {
        mPetsListAdapter.pets = pets
    }

    override fun showPet(pet: Pet) {
    }

    override fun showEditPet(pet: Pet) {
    }

    override fun showPets() {
        mPetsListAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        Snackbar.make(mPetsListRV, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onPetItemClick(pet: Pet) {
        showPet(pet)
    }

    override fun onEditPetItemClick(pet: Pet) {
        showEditPet(pet)
    }

    override fun onPetItemDeleted(pet: Pet) {
        mPresenter.deletePet(pet)
    }

    private fun onPetsListChanged() {
        if (mPetsListAdapter.itemCount == 0) {
            showEmptyView()
        } else {
            showListView()
        }
    }

    @OnClick(R.id.add_pet_fab)
    fun onAddButtonClicked() {
    }

    override fun onRefresh() {
        mPresenter.fetchPets()
    }

    fun showEmptyView() {
        mPetsListEmptyView.visibility = View.VISIBLE
        mPetsListRV.visibility = View.GONE
    }

    fun showListView() {
        mPetsListEmptyView.visibility = View.GONE
        mPetsListRV.visibility = View.VISIBLE
    }

    override fun showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing) {
            mPetsListEmptyView.visibility = View.GONE
            mPetsListRV.visibility = View.GONE
            mProgressBar.show()
        } else {
            mSyncPetsSnackbar = Snackbar.make(mToolbar, R.string.synching_pets_message, Snackbar.LENGTH_INDEFINITE)
            mSyncPetsSnackbar!!.show()
        }
    }

    override fun hideProgress() {
        if (mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = false
        }
        if (mSyncPetsSnackbar != null && mSyncPetsSnackbar!!.isShownOrQueued) {
            mSyncPetsSnackbar!!.dismiss()
            mSyncPetsSnackbar = null
        }
        mProgressBar.hide()
    }
}