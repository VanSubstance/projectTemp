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
    var soldDate : String = ""

    // 등록시 사용하는 함수
    fun setInfo(title: String, price : Int, quantity : Int, productId : String) {
        this.productId = productId
        this.title = title
        this.price = price
        this.quantity = quantity
        this.sellerId = userInfo.id
        buyerId = "undefined"
    }

    // 판매시
    @RequiresApi(Build.VERSION_CODES.O)
    fun sold() {
        buyerId = userInfo.id
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title
            ,"price" to price.toString()
            ,"quantity" to quantity.toString()
            ,"seller" to sellerId
            ,"buyer" to buyerId
            ,"soldDate" to soldDate
        )
    }

    fun setFromDb(product : DataSnapshot) {
        productId = product.key.toString()
        title = product.child("title").value.toString()
        price = Integer.parseInt(product.child("price").value as String)
        quantity = Integer.parseInt(product.child("quantity").value as String)
        sellerId = product.child("seller").value.toString()
        buyerId = product.child("buyer").value.toString()
        soldDate = product.child("soldDate").value.toString()
    }
}