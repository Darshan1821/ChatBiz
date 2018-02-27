package com.promact.chatbiz

class User {
    var id: Int = 0
    var name: String = ""
    var token: String = ""

    constructor() {}

    constructor(id: Int, name: String, token: String) {
        this.id = id
        this.name = name
        this.token = token
    }
}