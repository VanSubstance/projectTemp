package com.example.contest

import kotlinx.android.synthetic.main.seller_ui_history_date.*

class sellerUIHistoryDate {
    var date : String = ""
    var productList : ArrayList<productElement> = ArrayList()

    fun setData(date: String, productList: ArrayList<productElement>) {
        this.date = date
        this.productList = productList
    }

}