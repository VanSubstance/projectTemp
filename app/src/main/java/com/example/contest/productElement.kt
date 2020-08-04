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
    var productId: String = ""
    var sellerId: String = ""
    var title: String = ""
    var price: Int = 0
    var serve: Int = 0
    var quanTotal = 0
    var quanLeft = 0
    var quanSold = 0
    var ctgr: String = ""
    var buyerId: String = "undefined"
    var soldDate: String = ""
    var soldTime = ""

    // 등록시 사용하는 함수
    fun setInfo(
        title: String,
        price: Int,
        serve: Int,
        productId: String,
        quanTotal: Int,
        ctgr: String,
        soldTime : String
    ) {
        this.productId = productId
        this.title = title
        this.price = price
        this.serve = serve
        this.sellerId = userInfo.id
        buyerId = "undefined"
        this.soldDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        this.quanTotal = quanTotal
        this.ctgr = ctgr
        this.soldTime = soldTime
    }

    // 판매시
    @RequiresApi(Build.VERSION_CODES.O)
    fun sold() {
        buyerId = userInfo.id
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title
            , "price" to price
            , "serve" to serve
            , "seller" to sellerId
            , "buyer" to buyerId
            , "soldDate" to soldDate
            , "ctgr" to ctgr
            , "quanTotal" to quanTotal
            , "quanLeft" to quanLeft
            , "quanSold" to quanSold
            , "soldTime" to soldTime
        )
    }

    fun setFromDb(product: DataSnapshot) {
        productId = product.key.toString()
        title = product.child("title").value.toString()
        price = product.child("price").value.toString().toInt()
        serve = product.child("serve").value.toString().toInt()
        sellerId = product.child("seller").value.toString()
        buyerId = product.child("buyer").value.toString()
        soldDate = product.child("soldDate").value.toString()
        quanTotal = product.child("quanTotal").value.toString().toInt()
        quanLeft = product.child("quanLeft").value.toString().toInt()
        quanSold = product.child("quanSold").value.toString().toInt()
        ctgr = product.child("ctgr").value.toString()
        soldTime = product.child("soldTime").value.toString()
    }
}