package com.diegoblajackis.uapp.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.diegoblajackis.uapp.R
import com.diegoblajackis.uapp.model.User
import com.diegoblajackis.uapp.ui.fragments.DetailUserFragment
import com.diegoblajackis.uapp.ui.fragments.UserListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), UserListFragment.OnFragmentInteractionListener {

    override fun openDetails(user: User?) {
        replaceFragment(DetailUserFragment.newInstance(user), container.id, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(UserListFragment(), container.id, false)
    }

    private fun replaceFragment(fragment: Fragment, layoutId: Int, addToStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (addToStack) {
            transaction.addToBackStack(fragment.javaClass.getName())
        }
        transaction.replace(layoutId, fragment)
        transaction.commit()
    }


}
