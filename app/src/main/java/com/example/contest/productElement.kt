package com.example.contest

class productElement() {
    var productId : String = ""
    var sellerId : String = ""
    var title : String = ""
    var price : Int = 0
    var quantity : Int = 0
    var ctgr : Array<String> = Array(5, {i -> ""})
    var buyerId : String = ""
    var soldTime : Int = 0

    // 등록시
    private fun setInfo(title: String, price : Int, quantity : Int, ctgr : Array<String>) {
        this.title = title
        this.price = price
        this.quantity = quantity
        this.ctgr = ctgr
        this.sellerId = userInfo.id
        // productId, 즉 상품 코드도 선언해 줄 것
    }

    // 판매시
    private fun soldBy(buyerId : String) {
        this.buyerId = buyerId
        // soldTime, 즉 팔린 시간도 선언해 줄 것
    }

    // 조회시
    private fun callInfo(productId : String) {
        // 데베 접속 -> productId 에 해당하는 상품 정보를 불러와서 할당
    }



}