package com.promact.chatbiz

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class UserListAdapter(private var activity: Activity, private var items: List<User>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var userId: TextView? = null
        var userName: TextView? = null
        init {
            this.userId = row?.findViewById(R.id.userId)
            this.userName = row?.findViewById(R.id.userName)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.user_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var user = items[position]
        viewHolder.userId?.text = user.id.toString()
        viewHolder.userName?.text = user.name

        return view as View
    }

    override fun getItem(i: Int): User {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}