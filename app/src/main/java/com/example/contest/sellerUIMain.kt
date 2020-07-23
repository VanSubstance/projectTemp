package com.example.contest

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.seller_ui_main.*
import kotlinx.android.synthetic.main.product_seller_home_specific.*
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*
import kotlinx.android.synthetic.main.seller_ui_info_modify.*
import kotlinx.android.synthetic.main.seller_ui_main.textTime
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class sellerUIMain : AppCompatActivity() {

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
            31 -> {
                ft.replace(R.id.main_frame,sellerUIToday()).commit()
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
        when (usage) {
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
                        productElement.title = view.inputTitle.text.toString()
                        productElement.price = Integer.parseInt(view.inputPrice.text.toString())
                        productElement.quantity = Integer.parseInt(view.inputQuan.text.toString())
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
                    sampledb.productList.remove(productElement)
                    setSellerFrag(11)
                }
            }
        }

    }


}
