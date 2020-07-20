package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.seller_ui_main.*
import java.text.SimpleDateFormat
import java.util.*

class productElementViewHolder(elementView : View) : RecyclerView.ViewHolder(elementView) {
    val productImage = elementView.findViewById<ImageView>(R.id.productImage)
    val productTitle = elementView.findViewById<TextView>(R.id.productTitle)
    val productPrice = elementView.findViewById<TextView>(R.id.productPrice)
    val productQuan = elementView.findViewById<TextView>(R.id.productQuan)
    val productSoldDate = elementView.findViewById<TextView>(R.id.productSoldDate)
    val buttonModify = elementView.findViewById<Button>(R.id.buttonProductModify)
    val buttonDelete = elementView.findViewById<Button>(R.id.buttonProductDelete)
    val buttonPurchase = elementView.findViewById<Button>(R.id.buttonPurchase)
    val textDate = elementView.findViewById<TextView>(R.id.textDate)


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

        // 판매자 ui history Date 일 경우
        if (textDate != null) {
            val currentTime = Calendar.getInstance().time
            var timeFormat = SimpleDateFormat("yyyy-mm-dd", Locale.KOREA).format(currentTime)
            textDate.setText(timeFormat)
        }

    }
}