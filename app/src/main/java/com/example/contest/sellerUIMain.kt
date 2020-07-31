package com.example.contest

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.seller_ui_main.*
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*
import kotlinx.android.synthetic.main.seller_ui_main.textTime
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.*
import java.time.LocalDate

class sellerUIMain : AppCompatActivity() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_ui_main)

        val currentTime = LocalDate.now()
        textTime.setText(currentTime.toString())

        //판매자 화면 전환
        setSellerFrag(11)

        sellerHome.setOnClickListener {
            setSellerFrag(11)
        }

        SellerHistory.setOnClickListener {
            setSellerFrag(21)
        }

        sellerInfo.setOnClickListener {
            setSellerFrag(41)
        }
    }

    fun setSellerFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            11 -> {
                ft.replace(R.id.main_frame,sellerUIHome()).commit()
            }
            21 -> {
                ft.replace(R.id.main_frame,sellerUIHistory()).commit()
            }
            41 -> {
                ft.replace(R.id.main_frame,sellerUIInfo()).commit()
            }
            12 -> {
                ft.replace(R.id.main_frame,sellerUIEnrollProduct()).commit()
            }
            42 -> {
                ft.replace(R.id.main_frame,sellerUIInfoModify()).commit()
            }
        }
    }

    // 상품의 세부사항을 보여주는 함수
    fun showProductSpecific(productElement: productElement, usage : Int) {
        val data = database.getReference("productTodayDB")

        when (usage) {
            // 판매자 오늘 상품
            1 -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_seller_home_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()


                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                alertDialog.setView(view)
                alertDialog.show()

                view.buttonModify.setOnClickListener {
                    // 상품 수정 버튼
                    alertDialog.dismiss()
                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle("상품 수정 정보")
                        .create()
                    val view = inflater.inflate(R.layout.seller_ui_modify_product, null)
                    view.inputTitle.setText(productElement.title)
                    view.inputPrice.setText(productElement.price.toString())
                    view.inputQuan.setText(productElement.quantity.toString())
                    view.buttonConfirm.setOnClickListener {
                        // 확인 버튼
                        var modifiedProduct = productElement
                        modifiedProduct.setInfo(view.inputTitle.text.toString()
                            , Integer.parseInt(view.inputPrice.text.toString())
                            , Integer.parseInt(view.inputQuan.text.toString())
                            , productElement.productId
                            /**, productElement.image*/)
                        data.child(modifiedProduct.productId).setValue(modifiedProduct.toMap())
                        alertDialog.dismiss()
                        setSellerFrag(11)
                    }
                    view.buttonCancel.setOnClickListener {
                        // 취소 버튼
                        alertDialog.cancel()
                        setSellerFrag(11)
                    }
                    alertDialog.setView(view)
                    alertDialog.show()
                }
                view.buttonDelete.setOnClickListener {
                    alertDialog.dismiss()
                    data.child(productElement.productId).removeValue()
                    setSellerFrag(11)
                }
            }
        }

    }


}
