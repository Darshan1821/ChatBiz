package com.promact.chatbiz.chatbiz

import com.google.gson.Gson
import com.promact.chatbiz.Message
import com.promact.chatbiz.Messages
import com.promact.chatbiz.User
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ChatService {

    private val baseURL = "https://chat.promactinfo.com/api"

    data class UserData(val responseCode: Int, val responseMessage: String)
    data class UserList(val responseCode: Int, val responseMessage: List<User>)
    data class UserMessages(val responseCode: Int,val responseMessage: List<Messages>)

    fun loginUser(user: String): UserData {

        val chatAPIURL = URL(baseURL + "/user/login")
        val chatConnection = chatAPIURL.openConnection() as HttpURLConnection
        chatConnection.doOutput = true
        chatConnection.setRequestProperty("Content-Type", "application/json")

        val userObject = JSONObject()
        userObject.put("name", user)
        val content = userObject.toString()

        val output = chatConnection.outputStream
        output.write(content.toByteArray())

        return when (chatConnection.responseCode) {
            200 -> {
                val response = chatConnection.inputStream.readBytes()
                UserData(chatConnection.responseCode, response.toString(Charsets.UTF_8))
            }
            else -> UserData(chatConnection.responseCode, chatConnection.responseMessage)
        }
    }

    fun getAllUsers(token: String): UserList {

        val chatAPIURL = URL(baseURL + "/user")
        val chatConnection = chatAPIURL.openConnection() as HttpURLConnection
        chatConnection.doInput = true
        chatConnection.setRequestProperty("Authorization", token)

        return when (chatConnection.responseCode) {
            200 -> {
                val response = chatConnection.inputStream.readBytes()
                val users: List<User> = Gson().fromJson(response.toString(Charsets.UTF_8), Array<User>::class.java).toList()
                UserList(chatConnection.responseCode, users)
            }
            else -> UserList(chatConnection.responseCode, emptyList())
        }
    }

    fun sendMessage(token: String, message: String, toUserId: Int): Int {

        val chatAPIURL = URL(baseURL + "/chat")
        val chatConnection = chatAPIURL.openConnection() as HttpURLConnection
        chatConnection.doOutput = true
        chatConnection.setRequestProperty("Content-Type", "application/json")
        chatConnection.setRequestProperty("Authorization", token)

        val messageObject = JSONObject()
        messageObject.put("message", message)
        messageObject.put("toUserId",toUserId)

        val content = messageObject.toString()

        val output = chatConnection.outputStream
        output.write(content.toByteArray())

        return chatConnection.responseCode
    }

    fun getAllMessages(token: String, userId: Int): UserMessages {

        val chatAPIURL = URL(baseURL+"/chat/"+userId)
        val chatConnection = chatAPIURL.openConnection() as HttpURLConnection
        chatConnection.doInput = true
        chatConnection.setRequestProperty("Authorization", token)
        chatConnection.setRequestProperty("Content-Type", "application/json")

        return when (chatConnection.responseCode) {
            200 -> {
                val response = chatConnection.inputStream.readBytes()
                val messages: List<Messages> = Gson().fromJson(response.toString(Charsets.UTF_8), Array<Messages>::class.java).toList()
                UserMessages(chatConnection.responseCode, messages)
            }
            else -> UserMessages(chatConnection.responseCode, emptyList())
        }
    }
}