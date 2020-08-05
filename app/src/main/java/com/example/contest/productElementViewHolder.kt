package com.example.contest

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.product_buyer_basket.view.*
import kotlinx.android.synthetic.main.product_buyer_market.view.*
import kotlinx.android.synthetic.main.product_buyer_market.view.buttonPurchase
import kotlinx.android.synthetic.main.product_seller_home.view.*
import java.time.LocalDateTime


class productElementViewHolder(elementView : View, usage : Int, productClick: (productElement) -> Unit) : RecyclerView.ViewHolder(elementView) {
    val productImage = elementView.findViewById<ImageView>(R.id.imageProduct)
    val productTitle = elementView.findViewById<TextView>(R.id.textProductTitle)
    val productPrice = elementView.findViewById<TextView>(R.id.textPrice)
    val productQuan = elementView.findViewById<TextView>(R.id.textQuan)
    val textCloseTime = elementView.findViewById<TextView>(R.id.textCloseTime)
    val usage : Int = usage
    val elementView = elementView
    val productClick = productClick
    var CountDownTimer : CountDownTimer? = null
    var layoutProductSpecific = elementView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.layoutProductSpecific)

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
                elementView.findViewById<TextView>(R.id.staticDate).text = LocalDateTime.now().toString()
                elementView.setOnClickListener {
                    if (layoutProductSpecific.isGone) {
                        layoutProductSpecific.isGone = false
                    } else {
                        layoutProductSpecific.isGone = true
                    }
                }
            }
            // 판매자 오늘 상품
            3 -> {
                elementView.buttonModify.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "modify"
                    productClick(productElements)
                }
                elementView.buttonDelete.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "delete"
                    productClick(productElements)
                }
                elementView.setOnClickListener {
                    if (layoutProductSpecific.isGone) {
                        layoutProductSpecific.isGone = false
                    } else {
                        layoutProductSpecific.isGone = true
                    }
                }
            }
            // 소비자 재료
            41 -> {
                elementView.buttonPlus.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "plus"
                    currentCondition.ctgr01 = "raw"
                    productClick(productElements)
                }
                elementView.buttonPurchase.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "purchase"
                    currentCondition.ctgr01 = "raw"
                    productClick(productElements)
                }
                elementView.setOnClickListener {
                    if (layoutProductSpecific.isGone) {
                        layoutProductSpecific.isGone = false
                    } else {
                        layoutProductSpecific.isGone = true
                    }
                }
            }
            // 소비자 완제품
            42 -> {
                elementView.buttonPlus.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "plus"
                    currentCondition.ctgr01 = "complete"
                    productClick(productElements)
                }
                elementView.buttonPurchase.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "purchase"
                    currentCondition.ctgr01 = "complete"
                    productClick(productElements)
                }
                elementView.setOnClickListener {
                    if (layoutProductSpecific.isGone) {
                        layoutProductSpecific.isGone = false
                    } else {
                        layoutProductSpecific.isGone = true
                    }
                }
            }
            // 소비자 장바구니
            5 -> {
                elementView.buttonMinus.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "minus"
                    productClick(productElements)
                }
                elementView.buttonPurchase.setOnClickListener {
                    currentProductElement.currentProductElement = productElements
                    currentProductElement.function = "purchase"
                    productClick(productElements)
                }
                elementView.setOnClickListener {
                    if (layoutProductSpecific.isGone) {
                        layoutProductSpecific.isGone = false
                    } else {
                        layoutProductSpecific.isGone = true
                    }
                }
            }


        }

    }
}