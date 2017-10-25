package com.cleganeBowl2k18.trebuchet.presentation.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.cleganeBowl2k18.trebuchet.R
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.presentation.common.ui.VerticalSpacingItemDecoration
import com.cleganeBowl2k18.trebuchet.presentation.common.view.BaseFragment
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerActivityComponent
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.CreateGroupIntent
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.GroupListAdapter
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.GroupPresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.GroupView
import java.util.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class GroupFragment : BaseFragment(), GroupView, GroupListAdapter.OnGroupItemClickListener {

    interface OnGroupSelectedListener {
        fun onGroupSelected(position: Int)
    }

    val VERTICAL_SPACING: Int = 30

    // REPLACE WITH GROUP EMPTY LIST
    @BindView(R.id.group_list)
    lateinit var mGroupListRV: RecyclerView

    @BindView(R.id.empty_group_list)
    lateinit var mGroupListEmptyView: TextView

    @BindView(R.id.progressbar_group)
    lateinit var mProgressBar: ContentLoadingProgressBar

    @Inject
    lateinit var mPresenter: GroupPresenter

    lateinit var mGroupListAdapter: GroupListAdapter

    private val mAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            onGroupListChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            onGroupListChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            onGroupListChanged()
        }
    }

    private var mColumnCount = 1
    private var mListener: OnGroupSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_group_list, container, false)

        ButterKnife.bind(this, view)

        setupRecyclerView()

        return view
    }

    override fun onGroupItemClick(group: Group) {
        //TODO: do something
    }

    override fun onEditGroupItemClick(group: Group) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun showGroups(groups: List<Group>) {
        mGroupListAdapter.groups = groups
    }

    override fun showGroups() {
        mGroupListAdapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        mGroupListEmptyView.visibility = View.GONE
        mGroupListRV.visibility = View.GONE
        mProgressBar.show()
    }

    override fun hideProgress() {
        mGroupListEmptyView.visibility = View.VISIBLE
        mGroupListRV.visibility = View.VISIBLE
        mProgressBar.hide()
    }

    override fun showError(message: String) {

    }

    private fun setupRecyclerView() {
        mGroupListAdapter = GroupListAdapter(ArrayList<Group>(0), this)

        mGroupListRV.itemAnimator = DefaultItemAnimator()
        mGroupListRV.addItemDecoration(VerticalSpacingItemDecoration(VERTICAL_SPACING))
        mGroupListRV.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        mGroupListRV.setHasFixedSize(true)
        mGroupListRV.adapter = mGroupListAdapter

        mGroupListAdapter.registerAdapterDataObserver(mAdapterDataObserver)
    }

    private fun onGroupListChanged() {
        if (mGroupListAdapter.itemCount == 0) {
            showEmptyView()
        } else {
            showListView()
        }
    }

    fun showEmptyView() {
        mGroupListEmptyView.visibility = View.VISIBLE
        mGroupListRV.visibility = View.GONE
    }

    fun showListView() {
        mGroupListEmptyView.visibility = View.GONE
        mGroupListRV.visibility = View.VISIBLE
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnGroupSelectedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnGroupSelectedListener")
        }

        DaggerActivityComponent.builder()
                .applicationComponent(mApplicationComponent)
                .build()
                .inject(this)

        mPresenter.setView(this)
        // TODO: use current userId
        mPresenter.fetchGroupsByUserId(1)
    }

    override fun onDetach() {
        super.onDetach()
        mPresenter.onDestroy()
        mGroupListAdapter.unregisterAdapterDataObserver(mAdapterDataObserver)
        mListener = null
    }

    @OnClick(R.id.create_group_fab)
    fun createNewGroup() {
        startActivityForResult(getActivity().CreateGroupIntent(), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mPresenter.fetchGroupsByUserId(1) // TODO: replace with current_user from prefs
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(group: Group)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): GroupFragment {
            val fragment = GroupFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
