package com.promact.chatbiz

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.promact.chatbiz.chatbiz.ChatService
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatUserBundle: Bundle
    private lateinit var senderName: String
    private lateinit var receiverName: String
    private var senderId: Int = 0
    private var receiverId: Int = 0
    private lateinit var token: String
    private lateinit var messages: List<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatUserBundle = intent.extras

        if(chatUserBundle != null){
            title = chatUserBundle.getString("receiverName")
            senderName = chatUserBundle.getString("senderName")
            receiverName = chatUserBundle.getString("receiverName")
            senderId = chatUserBundle.getInt("senderId")
            receiverId = chatUserBundle.getInt("receiverId")
            token = chatUserBundle.getString("token")

            Log.d("senderId", senderId.toString())
            Log.d("recId", receiverId.toString())
            Log.d("token", token)

            getAllMessages()
        }

        sendMessage.setOnClickListener {

            Thread(Runnable {
                val messageStatus = ChatService().sendMessage(token, messageText.text.toString(), receiverId)

                when(messageStatus){
                    200 -> {
                        this@ChatActivity.runOnUiThread {
                            messageText.text.clear()
                            getAllMessages()
                            Utils().hideKeyboard(getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager, currentFocus.windowToken)
                            Toast.makeText(applicationContext, "Messages Sent Successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                    else -> {
                        this@ChatActivity.runOnUiThread {
                            messageText.text.clear()
                            Toast.makeText(applicationContext, "Messages Sending Failed", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }).start()
        }
    }

    private fun getAllMessages() {

        Thread(Runnable {
            val messageList = ChatService().getAllMessages(token, receiverId)

            when(messageList.responseCode){

                200 -> {
                    this@ChatActivity.runOnUiThread {
                        messages = getFormattedMessages(messageList.responseMessage)
                        messagesList.adapter = MessageListAdapter(this,messages)
                        messagesList.setSelection(messages.size-1)
                    }
                }
                else -> {
                    this@ChatActivity.runOnUiThread {
                        Toast.makeText(applicationContext, "Messages Loading Failed", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }).start()
    }

    private fun getFormattedMessages(messageList: List<Messages>): List<Message> {

        var messages: ArrayList<Message> = arrayListOf()
        var userName: String

        for (message in messageList){

            val timeStamp = message.createdDateTime
            userName = if(message.toUserId == receiverId){
                senderName
            }else{
                receiverName
            }
            messages.add(Message(message.message, userName,
                                    timeStamp.substring(0,timeStamp.indexOf("T")),
                                    timeStamp.substring(timeStamp.indexOf("T")+1,timeStamp.indexOf("."))))
        }
        return messages
    }
}
