package com.promact.chatbiz


class Message {
    var message: String = ""
    var userName: String = ""
    var date: String = ""
    var time: String = ""

    constructor() {}

    constructor(message: String, userName: String, date: String, time: String) {
        this.message = message
        this.userName = userName
        this.date = date
        this.time = time
    }
}