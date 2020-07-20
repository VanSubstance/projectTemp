package com.example.contest

import android.widget.ImageView
import java.text.SimpleDateFormat
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
    var buyerId : String = ""
    var soldTime : String = ""
    var soldDate : String = ""
    var image : Int = R.drawable.test_02

    constructor(title: String) : this() {
        this.title = title
        this.price = 1000
        this.quantity = 3
        this.soldDate = SimpleDateFormat("yyyy-mm-dd", Locale.KOREA).format(Calendar.getInstance().time)
    }
    // 등록시
    fun setInfo(title: String, price : Int, quantity : Int, date : String) {
        this.title = title
        this.price = price
        this.quantity = quantity
        this.sellerId = userInfo.id
        soldDate = date
        // productId, 즉 상품 코드도 선언해 줄 것
    }


    // 판매시
    private fun soldBy(buyerId : String) {
        this.buyerId = buyerId
        // soldTime, 즉 팔린 시간도 선언해 줄 것
        // soldDate도 선언
    }

    // 조회시
    private fun callInfo(productId : String, ctgr : String) {
        // 데베 접속 -> productId 에 해당하는 상품 정보를 불러와서 할당
    }



}