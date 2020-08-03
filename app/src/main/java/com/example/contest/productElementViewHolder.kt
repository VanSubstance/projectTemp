package com.example.contest

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime


class productElementViewHolder(elementView : View, usage : Int, productClick: (productElement) -> Unit) : RecyclerView.ViewHolder(elementView) {
    val productImage = elementView.findViewById<ImageView>(R.id.productImage)
    val productTitle = elementView.findViewById<TextView>(R.id.productTitle)
    val productPrice = elementView.findViewById<TextView>(R.id.productPrice)
    val productQuan = elementView.findViewById<TextView>(R.id.productQuan)
    val textCloseTime = elementView.findViewById<TextView>(R.id.textCloseTime)
    val usage : Int = usage
    val elementView = elementView
    val productClick = productClick
    var CountDownTimer : CountDownTimer? = null

    private val mStorageRef = FirebaseStorage.getInstance().getReference("productImageDB")

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind (productElements : productElement, context : Context) {
        productTitle.text = productElements.title
        productPrice.text = productElements.price.toString()
        productQuan.text = productElements.serve.toString()
        val imagePath = mStorageRef.child(productElements.productId + ".png")
        val imageSize: Long = 1024 * 1024 * 10
        imagePath.getBytes(imageSize).addOnSuccessListener {
            val imageBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            productImage.setImageBitmap(imageBitmap)
        }

        when (usage) {
            // 판매자 이력
            2 -> {
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