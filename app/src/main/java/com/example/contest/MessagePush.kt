package com.example.contest


import android.app.Notification
import android.os.AsyncTask
import android.util.Log
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.json.JSONObject



class MessagePush {
    fun sendNotification(
        regToken: String?,
        title: String?,
        messsage: String?
    ) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                try {
                    val JSON = MediaType.parse("application/json; charset=utf-8")
                    val serverKey =
                        "AAAAv115-6g:APA91bHF9UX5qTiV2JmhM5oJrjq8NL6VuYciFPmV3cvyc3Z9qUJtRNE-y3E5aOMqn6e0prefmXEg2riitvF22PMHZywxcQtotCSEMZJEGPyXFwEN9642neblUBtlk492JHeTCG8CIhcO"
                    val client = OkHttpClient()
                    val json = JSONObject()
                    val dataJson = JSONObject()
                    dataJson.put("body", messsage)
                    dataJson.put("title", title)
                    json.put("notification", dataJson)
                    json.put("to", regToken)
                    val body =
                        RequestBody.create(JSON, json.toString())
                    val request=Request.Builder()
                        .header("Authorization", "key=" + serverKey)
                        .url("https://fcm.googleapis.com/fcm/send")
                        .post(body)
                        .build()
                    val response = client.newCall(request).execute()
                    val finalResponse = response.body().string()
                } catch (e: Exception) {
                    Log.d("error", e.toString() + "")
                }
                return null
            }
        }.execute()
    }
}