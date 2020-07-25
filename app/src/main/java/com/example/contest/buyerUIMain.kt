package com.example.contest

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_main.*
import kotlinx.android.synthetic.main.product_buyer_today_specific.view.*
import kotlinx.android.synthetic.main.product_buyer_today_specific.view.textTitle
import kotlinx.android.synthetic.main.recipe_ui_specific.view.*
import java.time.LocalDate

class buyerUIMain : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_ui_main)

        val currentTime = LocalDate.now()
        textTime.setText(currentTime.toString())

        // 구매자 화면 전환
        setBuyerFrag(11)

        buyerHome.setOnClickListener {
            setBuyerFrag(11)
        }

        buyerToday.setOnClickListener {
            setBuyerFrag(21)
        }

        buyerInfo.setOnClickListener {
            setBuyerFrag(31)
        }

        buyerMarket.setOnClickListener {
            setBuyerFrag(22)
        }
        buyerBasket.setOnClickListener {
            setBuyerFrag(41)
        }
    }

    fun setBuyerFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            11 -> {
                ft.replace(R.id.main_frame,buyerUIRecipe()).commit()
            }
            21 -> {
                ft.replace(R.id.main_frame,buyerUIToday()).commit()
            }
            31 -> {
                ft.replace(R.id.main_frame,buyerUIInfo()).commit()
            }
            41 -> {
                ft.replace(R.id.main_frame,buyerUIBasket()).commit()
            }
            22 -> {
                ft.replace(R.id.main_frame,buyerUITodayCtgr01()).commit()
            }
            23 -> {
                ft.replace(R.id.main_frame02,buyerUITodayCtgr02()).commit()
            }
            24 -> {
                ft.replace(R.id.main_frame02,buyerUIMarket()).commit()
            }
            25 -> {
                ft.replace(R.id.main_frame03,buyerUIMarket()).commit()
            }
            32 -> {
                ft.replace(R.id.main_frame,buyerUIInfoModify()).commit()
            }
            12 -> {
                ft.replace(R.id.recipe_frame,recipeUIMeat()).commit()
            }
            13 -> {
                ft.replace(R.id.recipe_frame,recipeUISeafood()).commit()
            }
            14 -> {
                ft.replace(R.id.recipe_frame,recipeUIVegetable()).commit()
            }
            15 -> {
                ft.replace(R.id.recipe_frame,recipeUIEtc()).commit()
            }
            16 -> {
                ft.replace(R.id.recipe_frame,recipeUISpecific()).commit()
            }
        }


    }

    // 상품의 세부사항을 보여주는 함수
    fun showProductSpecific(productElement: productElement, usage : Int) {
        when (usage) {
            1 -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_buyer_today_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()


                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                alertDialog.setView(view)
                alertDialog.show()
            }
        }
    }

    fun showRecipeSpecific(recipeElement: recipeElement) {
        // return 타입: recipeUISpecific
        // 값을 전달받은 fragment
        var frag = recipeUISpecific()
        var bundle = Bundle(2)
        bundle.putString("title", recipeElement.title)
        bundle.putString("ctgrBig", recipeElement.ctgrBig)
        frag.setArguments(bundle)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.recipe_frame, frag).commit()
    }

}
