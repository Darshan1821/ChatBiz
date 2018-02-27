package com.promact.chatbiz.chatbiz

import com.google.gson.Gson
import com.promact.chatbiz.User
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ChatService {

    private val baseURL = "https://chat.promactinfo.com/api"

    data class UserData(val responseCode: Int, val responseMessage: String)
    data class UserList(val responseCode: Int, val responseMessage: List<User>)

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
                val questions: List<User> = Gson().fromJson(response.toString(Charsets.UTF_8), Array<User>::class.java).toList()
                UserList(chatConnection.responseCode, questions)
            }
            else -> UserList(chatConnection.responseCode, emptyList())
        }
    }
}