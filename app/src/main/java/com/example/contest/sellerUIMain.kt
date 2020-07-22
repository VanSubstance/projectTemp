package com.example.contest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.seller_ui_main.*
import kotlinx.android.synthetic.main.product_seller_home_specific.*
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*
import kotlinx.android.synthetic.main.seller_ui_info_modify.*
import kotlinx.android.synthetic.main.seller_ui_main.textTime
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.*
import java.text.SimpleDateFormat
import java.util.*

class sellerUIMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_ui_main)

        val currentTime = Calendar.getInstance().time
        var timeFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(currentTime)
        textTime.setText(timeFormat)


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
            13 -> {
                ft.replace(R.id.main_frame,sellerUIModifyProduct()).commit()
            }
            42 -> {
                ft.replace(R.id.main_frame,sellerUIInfoModify()).commit()
            }
        }
    }

    fun showProductSpecific(productElement: productElement) {
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
            alertDialog.dismiss()
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("상품 수정 정보")
                .create()
            val view = inflater.inflate(R.layout.seller_ui_modify_product, null)
            view.inputTitle.setText(productElement.title)
            view.inputPrice.setText(productElement.price.toString())
            view.inputQuan.setText(productElement.quantity.toString())
            view.buttonConfirm.setOnClickListener {
                productElement.title = view.inputTitle.text.toString()
                productElement.price = Integer.parseInt(view.inputPrice.text.toString())
                productElement.quantity = Integer.parseInt(view.inputQuan.text.toString())
                alertDialog.dismiss()
                setSellerFrag(11)
            }
            view.buttonCancel.setOnClickListener {
                alertDialog.cancel()
                setSellerFrag(11)
            }
            alertDialog.setView(view)
            alertDialog.show()
        }
    }


}
