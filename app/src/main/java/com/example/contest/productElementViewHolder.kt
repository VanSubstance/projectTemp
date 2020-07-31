package com.example.contest

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class productElementViewHolder(elementView : View, usage : Int, productClick: (productElement) -> Unit) : RecyclerView.ViewHolder(elementView) {
    val productImage = elementView.findViewById<ImageView>(R.id.productImage)
    val productTitle = elementView.findViewById<TextView>(R.id.productTitle)
    val productPrice = elementView.findViewById<TextView>(R.id.productPrice)
    val productQuan = elementView.findViewById<TextView>(R.id.productQuan)
    val usage : Int = usage
    val elementView = elementView
    val productClick = productClick


    @RequiresApi(Build.VERSION_CODES.O)
    fun bind (productElements : productElement, context : Context) {
        productImage.setImageResource(productElements.image)
        productTitle.text = productElements.title
        productPrice.text = productElements.price.toString()
        productQuan.text = productElements.quantity.toString()

        when (usage) {
            // 판매자 이력
            2 -> {
                elementView.findViewById<TextView>(R.id.productSoldDate).text = productElements.soldDate.toString()
                elementView.setOnClickListener {
                    productClick(productElements)
                }
            }
            // 판매자 날짜에 따른 이력
            22 -> {
                elementView.findViewById<TextView>(R.id.textDate).text = LocalDateTime.now().toString()
                elementView.setOnClickListener {
                    productClick(productElements)
                }
            }
            // 판매자 오늘 상품
            3 -> {
                elementView.setOnClickListener {
                    productClick(productElements)
                }
            }
            // 소비자 재료
            4 -> {
                elementView.setOnClickListener {
                    productClick(productElements)
                }
            }
            // 소비자 완제품
            42 -> {
                elementView.setOnClickListener {
                    productClick(productElements)
                }
            }
            // 소비자 장바구니
            5 -> {
                elementView.setOnClickListener {
                    productClick(productElements)
                }
            }


        }

    }
}