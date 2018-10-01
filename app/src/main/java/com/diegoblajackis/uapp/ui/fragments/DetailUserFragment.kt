package com.diegoblajackis.uapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.request.RequestOptions
import com.diegoblajackis.uapp.R
import com.diegoblajackis.uapp.model.User
import com.diegoblajackis.uapp.ui.GlideApp
import kotlinx.android.synthetic.main.fragment_detail_user.*


class DetailUserFragment : Fragment() {

    private var mUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mUser = arguments!!.getParcelable(ARG_USER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        hearts.animateHearts()
    }

    private fun initData() {
        name.text = getString(R.string.username, mUser?.firstname, mUser?.lastname)
        data.text = getString(R.string.userAge, mUser?.age)
        GlideApp.with(this).load(mUser?.picture).apply(RequestOptions.circleCropTransform()).into(photo)
        GlideApp.with(this).load(mUser?.picture).into(photoback)

        email.text = mUser?.email
        phone.text = if (mUser?.phone != null) mUser?.phone else getString(R.string.noPhone)
        street.text = mUser?.street
        city.text = mUser?.city
        state.text = mUser?.state
    }

    companion object {
        private val ARG_USER = "user"
        fun newInstance(user: User?): DetailUserFragment {
            val fragment = DetailUserFragment()
            val args = Bundle()
            args.putParcelable(ARG_USER, user)
            fragment.arguments = args
            return fragment
        }
    }
}
