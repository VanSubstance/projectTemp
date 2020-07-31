package com.example.contest

import android.net.Uri
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class productElement() {
    // 상품코드의 기준을 세워야 함; ex) 20200712-001-0001
    var productId : String = ""
    var sellerId : String = ""
    var title : String = ""
    var price : Int = 0
    // 양의 기준을 정해야 함; ex) 3인분, etc.
    var quantity : Int = 0
    var ctgr : Array<String> = Array(5, {i -> ""})
    var buyerId : String = "undefined"
    var soldTime : String = ""
    var soldDate : String = ""
    //var image : Uri = Uri.EMPTY

    constructor(title: String) : this() {
        this.title = title
        this.price = 1000
        this.quantity = 3
    }
    // 등록시 사용하는 함수
    fun setInfo(title: String, price : Int, quantity : Int, productId : String/**, image : Uri*/) {
        this.productId = productId
        this.title = title
        this.price = price
        this.quantity = quantity
        this.sellerId = userInfo.id
        buyerId = "undefined"
        //this.image = image
        // image 받아줘야댐
    }

    // 판매시
    @RequiresApi(Build.VERSION_CODES.O)
    fun sold() {
        buyerId = userInfo.id
        soldDate = LocalDate.now().toString()
        // soldTime, 즉 팔린 시간도 선언해 줄 것
        // soldDate도 선언
    }

    // 못팔고 시간 종료 -> 폐기 확정일 때
    @RequiresApi(Build.VERSION_CODES.O)
    fun terminated() {
        soldDate = LocalDate.now().toString()
        soldTime = "3:00:00"
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title
            ,"price" to price.toString()
            ,"quantity" to quantity.toString()
            ,"seller" to sellerId
            ,"buyer" to buyerId
            //,"image" to image.toString()
        )
    }

    fun setFromDb(product : DataSnapshot) {
        productId = product.key.toString()
        title = product.child("title").value.toString()
        price = Integer.parseInt(product.child("price").value as String)
        quantity = Integer.parseInt(product.child("quantity").value as String)
        sellerId = product.child("seller").value.toString()
        buyerId = product.child("buyer").value.toString()
        //image = product.child("image").value as Uri
    }
}