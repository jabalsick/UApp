package com.diegoblajackis.uapp.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diegoblajackis.uapp.R
import kotlinx.android.synthetic.main.pull_to_refresh_layout.view.*

class ErrorRetrievingDataAdapter : RecyclerView.Adapter<ErrorRetrievingDataAdapter.ErrorViewHolder>() {

    var message: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        return ErrorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pull_to_refresh_layout, parent, false))
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        holder.messageTextView.text = message
    }

    class ErrorViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var messageTextView: TextView = v.messageTextView
    }
}
