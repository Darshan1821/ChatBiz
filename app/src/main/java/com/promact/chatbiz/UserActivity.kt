package com.promact.chatbiz

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.promact.chatbiz.chatbiz.ChatService
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private var userId: Int = 0
    private lateinit var dialog: AlertDialog
    private lateinit var usersList: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val userBundle = intent.extras

        if(userBundle != null){
            title = "Welcome " + userBundle.getString("name") + ","
            userId = userBundle.getInt("id")
            getAllUsers(userBundle.getString("token"))
        }

        userList.setOnItemClickListener {
            adapterView, view, position, id ->

            var chatIntent = Intent(applicationContext, ChatActivity::class.java)
            chatIntent.putExtra("senderId", userBundle.getInt("id"))
            chatIntent.putExtra("senderName", userBundle.getString("name"))
            chatIntent.putExtra("receiverId", usersList[position].id)
            chatIntent.putExtra("receiverName", usersList[position].name)
            chatIntent.putExtra("token", userBundle.getString("token"))
            startActivity(chatIntent)
        }
    }

    private fun getAllUsers(token: String){
        Thread(Runnable {
            val users = ChatService().getAllUsers(token)

            this@UserActivity.runOnUiThread {
                dialog = Utils().createDialog(this,layoutInflater,"Loading Users...")
                dialog.show()
            }

            when (users.responseCode) {
                200 -> {
                    this@UserActivity.runOnUiThread {
                        usersList = users.responseMessage
                        userList.adapter = UserListAdapter(this, usersList)
                        dialog.dismiss()
                    }
                }
                else -> {
                    dialog.dismiss()
                    this@UserActivity.runOnUiThread {
                        Toast.makeText(applicationContext, "Fetching of users failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }).start()
    }
}
