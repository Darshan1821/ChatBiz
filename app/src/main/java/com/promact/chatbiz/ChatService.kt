package com.promact.chatbiz.chatbiz

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ChatService {

    private val baseURL = "https://chat.promactinfo.com/api"

    data class UserData(val responseCode: Int, val responseMessage: String)

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

}