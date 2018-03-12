package com.sdsmdg.rohit.sms

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by rohit on 12/3/18.
 */


class MyAdapter(private val ctx: Context, private val messages: ArrayList<List<String>>, private val itemClick: (List<String>) -> Unit) : RecyclerView.Adapter<MyAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_card, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindMessage(messages[position])
    }

    override fun getItemCount() = messages.count()

    inner class Holder(itemView: View, private var itemClick: (List<String>) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val msgTV = itemView.findViewById<TextView>(R.id.msgTV)
        private val title = itemView.findViewById<TextView>(R.id.title)
        fun bindMessage(msg: List<String>) {
            title.text = msg[0]
            msgTV.text = msg[1]
            itemView.setOnClickListener { itemClick(msg) }
        }
    }
}