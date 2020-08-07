package com.example.contest


import android.util.Log

import com.google.firebase.database.FirebaseDatabase
import com.squareup.okhttp.*
import org.json.JSONObject
import java.io.IOException

import java.lang.Exception


class MessagePush() {
    fun sendMessage(destinationToken: String, title: String, message: String) {
        try{
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val JSON = MediaType.parse("application/json; charset=utf-8")
            val url = "https://fcm.googleapis.com/fcm/send"
            val serverKey =
                "AAAAv115-6g:APA91bHF9UX5qTiV2JmhM5oJrjq8NL6VuYciFPmV3cvyc3Z9qUJtRNE-y3E5aOMqn6e0prefmXEg2riitvF22PMHZywxcQtotCSEMZJEGPyXFwEN9642neblUBtlk492JHeTCG8CIhcO"

            var client: OkHttpClient? = OkHttpClient()
            var json = JSONObject()
            var dataJson = JSONObject()
            dataJson.put("body",message)
            dataJson.put("title",title)
            json.put("notification",dataJson)
            json.put("to",destinationToken)
            val body=RequestBody.create(JSON,json.toString())
            val request=Request.Builder()
                .header("Authorization","key="+serverKey)
                .url(url)
                .post(body)
                .build()
            val response= client?.newCall(request)?.execute()
            client?.newCall(request)?.enqueue(object :Callback{
                override fun onFailure(request: Request?, e: IOException?) {
                }
                override fun onResponse(response: Response?) {
                    val finalrespons=response?.body().toString()
                }
            })
        }catch (e:Exception){
            Log.d("error",e.toString())
        }
    }
}