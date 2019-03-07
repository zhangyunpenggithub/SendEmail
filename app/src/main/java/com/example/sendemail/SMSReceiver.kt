package com.example.sendemail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.telephony.gsm.SmsMessage
import com.example.sendemail.mail.MailUtil
import java.text.SimpleDateFormat




class SMSReceiver : BroadcastReceiver() {

    private val action = "android.provider.Telephony.SMS_RECEIVED"
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onReceive(context: Context, intent: Intent) {


        if (intent.action == action) {
            val messages = getMessagesFromIntent(intent)
            val emailContent:StringBuilder = java.lang.StringBuilder("原始号码：")
            var firstIn = true
            var originatingAddress: String
            var displayOriginatingAddress: String
            var time: String
            for (message in messages) {
                if (firstIn){
                    originatingAddress = message?.originatingAddress ?: "未获取到原始号码"
                    displayOriginatingAddress = message?.displayOriginatingAddress ?: "未获取到展示号码"
                    time =  simpleDateFormat.format(message?.timestampMillis) ?: "未获取到短信时间"
                    emailContent.append(originatingAddress + "\n")
                    emailContent.append("展示号码：")
                    emailContent.append(displayOriginatingAddress + "\n")
                    emailContent.append("收到短信的时间：")
                    emailContent.append(time + "\n")
                    firstIn = false
                }
                emailContent.append(message?.displayMessageBody)

            }
            try {
                val address = PreferenceManager.getDefaultSharedPreferences(context).getString(MainActivity.SP_ADDRESS, "")
                MailUtil.send(address, emailContent.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun getMessagesFromIntent(intent: Intent): Array<SmsMessage?> {
        val messages = intent.getSerializableExtra("pdus") as Array<Any>
        val pduObjs = arrayOfNulls<ByteArray>(messages.size)
        for (i in messages.indices) {
            pduObjs[i] = messages[i] as ByteArray
        }
        val pdus = arrayOfNulls<ByteArray>(pduObjs.size)
        val pduCount = pdus.size
        val msgs = arrayOfNulls<SmsMessage>(pduCount)
        for (i in 0 until pduCount) {
            pdus[i] = pduObjs[i]
            msgs[i] = SmsMessage.createFromPdu(pdus[i])
        }
        return msgs
    }


}