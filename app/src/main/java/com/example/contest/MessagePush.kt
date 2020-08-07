package com.example.contest


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.squareup.okhttp.*
import java.io.IOException

class MessagePush() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val JSON = MediaType.parse("application/json; charset=utf-8")//Post전송 JSON Type
    val url = "https://fcm.googleapis.com/fcm/send" //FCM HTTP를 호출하는 URL
    val serverKey =
        "AAAAv115-6g:APA91bHF9UX5qTiV2JmhM5oJrjq8NL6VuYciFPmV3cvyc3Z9qUJtRNE-y3E5aOMqn6e0prefmXEg2riitvF22PMHZywxcQtotCSEMZJEGPyXFwEN9642neblUBtlk492JHeTCG8CIhcO"
    //Firebase에서 복사한 서버키
    var okHttpClient: OkHttpClient
    var gson: Gson
    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun sendMessage(destinationToken: String, title: String, message: String) {
        val Data = FirebaseDatabase.getInstance().getReference("userDB")
        Data.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for(id in p0.children){
                    if(destinationToken==id.child("token").value){
                        var pushDTO=pushDTO()
                        pushDTO.to=id.toString()
                        pushDTO.notification?.title=title
                        pushDTO.notification?.body=message
                        var body =RequestBody.create(JSON,gson?.toJson(pushDTO))
                        var request =Request
                            .Builder()
                            .addHeader("Content-Type","application/json")
                            .addHeader("Authorization","key="+serverKey)
                            .url(url)
                            .post(body)
                            .build()
                        okHttpClient?.newCall(request)?.enqueue(object : Callback {//푸시 전송
                        override fun onFailure(request: Request?, e: IOException?) {
                        }
                            override fun onResponse(response: Response?) {
                                println(response?.body()?.string())
                            }
                        })
                    }
                }
            }
        })
    }
}