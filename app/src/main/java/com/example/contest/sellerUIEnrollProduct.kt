package com.example.contest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*
import kotlinx.android.synthetic.main.seller_ui_enroll_product.view.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class sellerUIEnrollProduct : Fragment() {
    var auth : FirebaseAuth?= null
    private val mStorageRef = FirebaseStorage.getInstance()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var imageUrl : Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_enroll_product, container, false)
        val data = database.getReference("productTodayDB")
        val imageData = mStorageRef.getReference("productImageDB")
        // 사진 변경 버튼
        view.buttonChangeImage.setOnClickListener {
            val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
        }
        view.checkCtgrComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.checkCtgrRaw.isChecked = false
            } else {
                view.checkCtgrRaw.isChecked = true
            }
        }
        view.checkCtgrRaw.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.checkCtgrComplete.isChecked = false
            } else {
                view.checkCtgrComplete.isChecked = true
            }
        }
        view.buttonEnroll.setOnClickListener {
            val DatabaseReference = database.reference
            if (!view.checkCtgrComplete.isChecked && !view.checkCtgrRaw.isChecked) {
                Toast.makeText(requireContext(), "제품 카테고리를 골라주세요", Toast.LENGTH_SHORT).show()
            } else if (view.textProductTitle.text.isEmpty() || view.textPrice.text.isEmpty() || view.textQuan.text.isEmpty() || view.textServing.text.isEmpty()) {
                Toast.makeText(requireContext(), "제대로 입력해야 합니다", Toast.LENGTH_SHORT).show()
            } else if (imageUrl == null) {
                Toast.makeText(requireContext(), "사진을 등록해주세요", Toast.LENGTH_SHORT).show()
            } else {
                var title = view.textProductTitle.text.toString()
                var price = Integer.parseInt(view.textPrice.text.toString())
                var serve = Integer.parseInt(view.textServing.text.toString())
                var quan = Integer.parseInt(view.textQuan.text.toString())
                var newProduct : productElement = productElement()
                var productId = SimpleDateFormat("yyyyMMdd").format(Date()) + userInfo.id + title
                var imageTitle = productId + ".png"


                imageData.child(imageTitle).putFile(imageUrl!!)
                if (view.checkCtgrComplete.isChecked) {
                    newProduct.setInfo(title, price, serve, productId, quan, "완제품", userInfo.timeClose)

                    DatabaseReference.addValueEventListener(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for(id in p0.child("userDB").children){
                                if(id.child("ctgr").child("완제품").value==true){
                                    sendMessage(id.toString(),"새로운 상품","완제품 새로운 상품이 등록되었습니다")
                                }
                           }
                        }
                    })
                } else {
                    newProduct.setInfo(title, price, serve, productId, quan, userInfo.ctgrForSeller, userInfo.timeClose)

                }
                data.child(productId).setValue(newProduct.toMap())


                (activity as sellerUIMain).setSellerFrag(11)
            }
        }

        view.buttonCancel.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(11)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                imageUrl = data?.data
                imageProduct.setImageURI(imageUrl)
            }
        }
    }
    fun sendMessage(member:String,title:String,message:String){
        val Data = FirebaseDatabase.getInstance().getReference("TokenDB")

        val JSON=MediaType.parse("application/json; charset=utf-8")
        val url="https://fcm.googleapis.com/fcm/send"
        val serverkey="AAAAv115-6g:APA91bHF9UX5qTiV2JmhM5oJrjq8NL6VuYciFPmV3cvyc3Z9qUJtRNE-y3E5aOMqn6e0prefmXEg2riitvF22PMHZywxcQtotCSEMZJEGPyXFwEN9642neblUBtlk492JHeTCG8CIhcO"
        var okHttpClient:OkHttpClient=OkHttpClient()
        var gson:Gson=Gson()
        Data.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                    for(id in p0.children){
                        if(member==id.toString()){
                            var pushDTO=pushDTO()
                            pushDTO.to=id.toString()
                            pushDTO.notification?.title=title
                            pushDTO.notification?.body=message
                            var body =RequestBody.create(JSON,gson?.toJson(pushDTO))
                            var request =Request
                                    .Builder()
                                    .addHeader("Content-Type","application/json")
                                    .addHeader("Authorization","key="+serverkey)
                                    .url(url)
                                    .post(body)
                                    .build()
                            okHttpClient?.newCall(request)?.enqueue(object : Callback {//푸시 전송
                                override fun onFailure(call: Call?, e: IOException?) {
                            }
                                override fun onResponse(call: Call?, response: Response?) {
                                    Toast.makeText(requireContext(), response?.body().toString(), Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                }
        })
    }
}