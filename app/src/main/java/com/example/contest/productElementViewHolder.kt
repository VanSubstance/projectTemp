package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class productElementViewHolder(elementView : View) : RecyclerView.ViewHolder(elementView) {
    val productImage = elementView.findViewById<ImageView>(R.id.productImage)
    val productTitle = elementView.findViewById<TextView>(R.id.productTitle)
    val productPrice = elementView.findViewById<TextView>(R.id.productPrice)
    val productQuan = elementView.findViewById<TextView>(R.id.productQuan)
    val productSoldDate = elementView.findViewById<TextView>(R.id.productSoldDate)
    val buttonModify = elementView.findViewById<Button>(R.id.buttonProductModify)
    val buttonDelete = elementView.findViewById<Button>(R.id.buttonProductDelete)
    val buttonPurchase = elementView.findViewById<Button>(R.id.buttonPurchase)


    fun bind (productElements : productElement, context : Context) {

        productImage.setImageResource(productElements.image)
        productTitle.text = productElements.title
        productPrice.text = productElements.price.toString()
        productQuan.text = productElements.quantity.toString()

        // 판매자 ui history 일 경우
        if (productSoldDate != null) {
            productSoldDate.text = productElements.soldDate.toString()
        }

        // 판매자 ui home 일 경우
        if (buttonModify != null) {
            buttonModify.setOnClickListener {
                Toast.makeText(it.context,"수정 화면이 열려야 함", Toast.LENGTH_SHORT).show()

            }
            buttonDelete.setOnClickListener {
                Toast.makeText(it.context,"삭제 되어야 함", Toast.LENGTH_SHORT).show()
            }

        }

        // 소비자 ui today 일 경우
        if (buttonPurchase != null) {
            buttonPurchase.setOnClickListener {
                Toast.makeText(it.context, "구매 확인 창 떠야 함", Toast.LENGTH_SHORT).show()
            }
        }

    }
}