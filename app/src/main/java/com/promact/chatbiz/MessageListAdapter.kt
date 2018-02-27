package com.promact.chatbiz

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MessageListAdapter(private var activity: Activity, private var items: List<Message>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var userName: TextView? = null
        var messageData: TextView? = null
        var dateStamp: TextView? = null
        var timeStamp: TextView? = null
        init {
            this.userName = row?.findViewById(R.id.userName)
            this.messageData = row?.findViewById(R.id.messageData)
            this.dateStamp = row?.findViewById(R.id.dateStamp)
            this.timeStamp = row?.findViewById(R.id.timeStamp)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.message_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var message = items[position]
        viewHolder.userName?.text = message.userName
        viewHolder.messageData?.text = message.message
        viewHolder.dateStamp?.text = message.date
        viewHolder.timeStamp?.text = message.time

        return view as View
    }

    override fun getItem(i: Int): Message {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}