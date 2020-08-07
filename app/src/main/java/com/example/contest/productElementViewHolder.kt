package com.example.contest

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    val productQuanTotal = elementView.findViewById<TextView>(R.id.textQuanTotal)
    val productQuanLeft = elementView.findViewById<TextView>(R.id.textQuanLeft)
    val productQuanSold = elementView.findViewById<TextView>(R.id.textQuanSold)
    val productSalary = elementView.findViewById<TextView>(R.id.textSalary)
    val productQuanBasket = elementView.findViewById<TextView>(R.id.textQuanBasket)
    val productServing = elementView.findViewById<TextView>(R.id.textServing)
    val textCloseTime = elementView.findViewById<TextView>(R.id.textCloseTime)
    val imageCtgr = elementView.findViewById<ImageView>(R.id.imageCtgr)
    val textSellerId = elementView.findViewById<TextView>(R.id.textSellerId)
    var textBuyerId = elementView.findViewById<TextView>(R.id.textBuyerId)
    var staticBuyerMark = elementView.findViewById<TextView>(R.id.staticBuyerMark)
    var textBuyerQuan = elementView.findViewById<TextView>(R.id.textBuyerQuan)
    var staticBuyerQuanUnit = elementView.findViewById<TextView>(R.id.staticBuyerQuanUnit)
    val usage : Int = usage
    val elementView = elementView
    val productClick = productClick
    var CountDownTimer : CountDownTimer? = null
    var layoutProductSpecific = elementView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.layoutProductSpecific)

    private val mStorageRef = FirebaseStorage.getInstance().getReference("productImageDB")
    val data = FirebaseDatabase.getInstance().getReference("storeDB")
    var data2 = FirebaseDatabase.getInstance().getReference("userDB")

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind (productElements : productElement, context : Context) {
        when (productElements.ctgr) {
            "완제품" -> {
                imageCtgr.setImageResource(R.drawable.ui_ctgr_complete)
            }
            "정육점" -> {
                imageCtgr.setImageResource(R.drawable.ui_ctgr_meat)
            }
            "생선가게" -> {
                imageCtgr.setImageResource(R.drawable.ui_ctgr_seafood)
            }
            "잡화점" -> {
                imageCtgr.setImageResource(R.drawable.ui_ctgr_etc)
            }
            "채소가게" -> {
                imageCtgr.setImageResource(R.drawable.ui_ctgr_vegetable)
            }
            else -> {
                imageCtgr.setImageResource(R.drawable.ui_ctgr_etc)
            }
        }
        productTitle.text = productElements.title
        productPrice.text = productElements.price.toString()
        productServing.text = productElements.serve.toString()
        val imagePath = mStorageRef.child(productElements.productId + ".png")
        val imageSize: Long = 1024 * 1024 * 10
        imagePath.getBytes(imageSize).addOnSuccessListener {
            val imageBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            productImage.setImageBitmap(imageBitmap)
            productImage.setScaleType(ImageView.ScaleType.CENTER_CROP)
        }

        when (usage) {
            // 판매자 이력
            2 -> {
                productQuanTotal.text = productElements.quanTotal.toString()
                productQuanSold.text = productElements.quanSold.toString()
                productSalary.text = (productElements.quanSold * productElements.price).toString()
                for (buyer in productElements.buyerId) {
                    var nick : String = ""
                    data2.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            nick = p0.child(buyer.key).child("nick").value.toString()
                            textBuyerId.text = textBuyerId.text.toString() + nick + "\n"
                            staticBuyerMark.text = staticBuyerMark.text.toString() + ":\n"
                            textBuyerQuan.text = textBuyerQuan.text.toString() + buyer.value + "\n"
                            staticBuyerQuanUnit.text = staticBuyerQuanUnit.text.toString() + "개\n"
                        }
                    })
                }
                elementView.setOnClickListener {
                    if (layoutProductSpecific.isGone) {
                        layoutProductSpecific.isGone = false
                    } else {
                        layoutProductSpecific.isGone = true
                    }
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
                // 구매한 소비자들 이름 따라락
                for (buyer in productElements.buyerId) {
                    var nick : String = ""
                    data2.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            nick = p0.child(buyer.key).child("nick").value.toString()
                            textBuyerId.text = textBuyerId.text.toString() + nick + "\n"
                            staticBuyerMark.text = staticBuyerMark.text.toString() + ":\n"
                            textBuyerQuan.text = textBuyerQuan.text.toString() + buyer.value + "\n"
                            staticBuyerQuanUnit.text = staticBuyerQuanUnit.text.toString() + "개\n"
                        }
                    })
                }
                productQuanTotal.text = productElements.quanTotal.toString()
                productQuanLeft.text = productElements.quanLeft.toString()
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
                productQuanLeft.text = productElements.quanLeft.toString()
                data.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        textSellerId.text = p0.child(productElements.sellerId).child("title").value.toString()
                    }
                })
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
                productQuanLeft.text = productElements.quanLeft.toString()
                data.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        textSellerId.text = p0.child(productElements.sellerId).child("title").value.toString()
                    }
                })
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
                productQuanBasket.text = productElements.buyerId[userInfo.id].toString()
                data.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        textSellerId.text = p0.child(productElements.sellerId).child("title").value.toString()
                    }
                })
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