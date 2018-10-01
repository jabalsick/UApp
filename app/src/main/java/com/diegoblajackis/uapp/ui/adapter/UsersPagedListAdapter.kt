package com.diegoblajackis.uapp.ui.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.diegoblajackis.uapp.R
import com.diegoblajackis.uapp.model.User
import com.diegoblajackis.uapp.ui.GlideApp
import kotlinx.android.synthetic.main.activity_main_loading_item.view.*


class UsersPagedListAdapter(private val context: Context) : PagedListAdapter<User, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private val layoutInflater = LayoutInflater.from(context)!!
    private var loading = false

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areContentsTheSame(oldItem: User, newItem: User) =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: User, newItem: User) =
                    oldItem.firstname == newItem.firstname &&
                            oldItem.lastname == newItem.lastname &&
                            oldItem.email == newItem.email &&
                            oldItem.thumbnail == newItem.thumbnail &&
                            oldItem.picture == newItem.picture
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.activity_main_thumb_item -> bindViewHolderThumbItem(holder, position)
            R.layout.activity_main_loading_item -> (holder as ViewHolderLoadingItem).bind(loading)
        }
    }

    private fun bindViewHolderThumbItem(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)!!
        val viewHolderThumbItem = holder as ViewHolderThumbItem

        GlideApp.with(context).load(user.picture).into(viewHolderThumbItem.photoImageView)
        viewHolderThumbItem.nameTextView.text = context.getString(R.string.username,user.firstname, user.lastname)
        viewHolderThumbItem.dataTextView.text = context.getString(R.string.userdata,user.age, user.state)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (loading && position == itemCount - 1) return R.layout.activity_main_loading_item
        return R.layout.activity_main_thumb_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {
                R.layout.activity_main_thumb_item ->
                    ViewHolderThumbItem(layoutInflater.inflate(R.layout.activity_main_thumb_item, parent, false))
                R.layout.activity_main_loading_item ->
                    ViewHolderLoadingItem(layoutInflater.inflate(R.layout.activity_main_loading_item, parent, false))
                else -> throw IllegalArgumentException("unknown view type $viewType")
            }

    fun isLoading(isLoading: Boolean) {
        loading = isLoading
        if (isLoading) notifyItemInserted(super.getItemCount())
        else notifyItemRemoved(super.getItemCount())
    }

    class ViewHolderThumbItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoImageView: ImageView = itemView.findViewById(R.id.thumbImageView)
        var nameTextView: TextView = itemView.findViewById(R.id.name)
        var dataTextView: TextView = itemView.findViewById(R.id.age_city)

    }

    class ViewHolderLoadingItem(view: View) : RecyclerView.ViewHolder(view) {
        private val progressBar = view.progressBar!!

        fun bind(isLoading: Boolean) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}