package com.promact.chatbiz

class Messages {
    var id: Int = 0
    var message: String = ""
    var fromUserId: Int = 0
    var toUserId: Int = 0
    var createdDateTime: String = ""

    constructor() {}

    constructor(id: Int, message: String, fromUserId: Int, toUserId: Int, createdDateTime: String) {
        this.id = id
        this.message = message
        this.fromUserId = fromUserId
        this.toUserId = toUserId
        this.createdDateTime = createdDateTime
    }
}