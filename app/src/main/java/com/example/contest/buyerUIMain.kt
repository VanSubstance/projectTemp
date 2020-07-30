package com.example.contest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.buyer_ui_main.*
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.*
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.buttonPurchase
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.textPrice
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.textQuan
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.textTitle
import kotlinx.android.synthetic.main.product_buyer_market_specific.view.*
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
            // 레시피
            11 -> {
                ft.replace(R.id.main_frame,buyerUIRecipe()).commit()
            }
            // 지도
            21 -> {
                ft.replace(R.id.main_frame,buyerUIToday()).commit()
            }
            // 정보
            31 -> {
                ft.replace(R.id.main_frame,buyerUIInfo()).commit()
            }
            // 장바구니
            41 -> {
                ft.replace(R.id.main_frame,buyerUIBasket()).commit()
            }
            // 시장 선택 시
            22 -> {
                ft.replace(R.id.main_frame,buyerUIMarketCtgr01()).commit()
            }
            // 재료 카테고리 선택 시
            23 -> {
                ft.replace(R.id.main_frame02,buyerUIMarketCtgr02()).commit()
            }
            // 판매자 완제품
            24 -> {
                var frag = buyerUIMarket()
                var bundle = Bundle(1)
                bundle.putInt("usage", 2)
                frag.setArguments(bundle)
                ft.replace(R.id.main_frame02,frag).commit()
            }
            // 판매자 재료
            25 -> {
                var frag = buyerUIMarket()
                var bundle = Bundle(1)
                bundle.putInt("usage", 3)
                frag.setArguments(bundle)
                ft.replace(R.id.main_frame03,frag).commit()
            }
            // 정보 수정
            32 -> {
                ft.replace(R.id.main_frame,buyerUIInfoModify()).commit()
            }
            // 레시피 육류
            12 -> {
                ft.replace(R.id.recipe_frame,recipeUIMeat()).commit()
            }
            // 레시피 해산물
            13 -> {
                ft.replace(R.id.recipe_frame,recipeUISeafood()).commit()
            }
            // 레시피 채소
            14 -> {
                ft.replace(R.id.recipe_frame,recipeUIVegetable()).commit()
            }
            // 레시피 기타
            15 -> {
                ft.replace(R.id.recipe_frame,recipeUIEtc()).commit()
            }
            // 레시피 페이지
            16 -> {
                ft.replace(R.id.recipe_frame,recipeUISpecific()).commit()
            }
        }


    }

    // 상품의 세부사항을 보여주는 함수
    fun showProductSpecific(productElement: productElement, usage : Int) {
        when (usage) {
            // 구매자 장바구니
            1 -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_buyer_basket_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                // 구매 버튼
                view.buttonPurchase.setOnClickListener{

                }
                view.buttonCancel.setOnClickListener {
                    conditionData.productList.add(productElement)
                    instantData.productList.remove(productElement)
                    alertDialog.dismiss()
                    setBuyerFrag(41)
                }
                alertDialog.setView(view)
                alertDialog.show()
            }
            // 구매자 완제품
            2 -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_buyer_market_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                // 구매 버튼
                view.buttonPurchase.setOnClickListener{

                }
                // 장바구니 버튼
                view.buttonBasket.setOnClickListener {
                    conditionData.productList.remove(productElement)
                    instantData.productList.add(productElement)
                    alertDialog.dismiss()
                    setBuyerFrag(24)
                }
                alertDialog.setView(view)
                alertDialog.show()
            }
            // 구매자 재료
            3 -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_buyer_market_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                // 구매 버튼
                view.buttonPurchase.setOnClickListener{

                }
                // 장바구니 버튼
                view.buttonBasket.setOnClickListener {
                    conditionData.productList.remove(productElement)
                    instantData.productList.add(productElement)
                    alertDialog.dismiss()
                    setBuyerFrag(25)
                }
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
