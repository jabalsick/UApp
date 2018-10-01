package com.diegoblajackis.uapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.diegoblajackis.uapp.R
import com.diegoblajackis.uapp.data.net.Resource
import com.diegoblajackis.uapp.model.User
import com.diegoblajackis.uapp.ui.adapter.ErrorRetrievingDataAdapter
import com.diegoblajackis.uapp.ui.adapter.UsersPagedListAdapter
import com.diegoblajackis.uapp.ui.viewModels.UsersViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var usersViewModel: UsersViewModel
    private var usersPagedListAdapter: UsersPagedListAdapter? = null
    private lateinit var snapHelper: LinearSnapHelper
    private var errorRetrievingDataAdapter = ErrorRetrievingDataAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val usersObserver = Observer<Resource<Any>> { resource ->
        when (resource?.status) {
            Resource.Companion.Status.LOADING -> processLoading(resource)
            Resource.Companion.Status.SUCCESS -> processSuccess(resource)
            Resource.Companion.Status.ERROR -> processError(resource)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    fun onButtonPressed(user: User?) {
        if (mListener != null) {
            mListener!!.openDetails(user)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun setupView() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        userRecyclerView.layoutManager = linearLayoutManager
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(userRecyclerView)
        userRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> revealUser()
                    RecyclerView.SCROLL_STATE_SETTLING, RecyclerView.SCROLL_STATE_DRAGGING -> detailButton.hide()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx > 0 && detailButton.getVisibility() == View.VISIBLE) {
                    detailButton.hide();
                } else if (dx < 0 && detailButton.getVisibility() != View.VISIBLE) {
                    revealUser()
                }
            }
        })

        detailButton.setOnClickListener(){
            onButtonPressed(usersPagedListAdapter?.getCurrentList()?.get(linearLayoutManager.findFirstCompletelyVisibleItemPosition()))
        }
    }

    private fun revealUser() {
        detailButton.show();
    }

    private fun setupViewModel() {
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        usersViewModel.getUsers().observe(this, usersObserver)
    }

    private fun processLoading(resource: Resource<Any>) {
        if (resource.data is PagedList<*>) {
            usersPagedListAdapter = UsersPagedListAdapter(requireContext())
            if (resource.data.isNotEmpty() || (resource.data.isEmpty() && userRecyclerView.adapter == null)) {
                userRecyclerView.adapter = usersPagedListAdapter
            }
            val items = resource.data as PagedList<User>
            usersPagedListAdapter?.submitList(items)
        }
        usersPagedListAdapter
                ?.isLoading(true)
    }

    private fun processSuccess(resource: Resource<Any>) {
        usersPagedListAdapter?.isLoading(false)
    }

    private fun processError(resource: Resource<Any>) {
        val error = getString(R.string.error_server)!!

        if (userRecyclerView.adapter == errorRetrievingDataAdapter)
            return

        usersPagedListAdapter
                ?.run {
                    isLoading(false)
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
                ?: run {
                    errorRetrievingDataAdapter.message = error
                    userRecyclerView.adapter = errorRetrievingDataAdapter
                }
    }

    interface OnFragmentInteractionListener {
        fun openDetails(user: User?)
    }

    companion object {
        fun newInstance(): UserListFragment {
            val fragment = UserListFragment()
            return fragment
        }
    }


}