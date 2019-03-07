package com.example.sendemail.sms


class MessageItem : java.io.Serializable {


    var id: Int = 0

    var type: Int = 0

    var protocol: Int = 0

    var phone: String? = null

    var body: String? = null


    companion object {

        private const val serialVersionUID = 1L
    }

}