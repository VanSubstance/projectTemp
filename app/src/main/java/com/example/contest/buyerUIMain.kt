package com.example.contest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.buyer_ui_main.*
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.*
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.buttonPurchase
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.textPrice
import kotlinx.android.synthetic.main.product_buyer_basket_specific.view.textTitle
import kotlinx.android.synthetic.main.product_buyer_market.view.*
import kotlinx.android.synthetic.main.product_buyer_market_specific.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlinx.android.synthetic.main.product_buyer_market_specific.view.textServing as textServing1

class buyerUIMain : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_ui_main)

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
                view.textServing.text = productElement.quantity.toString()

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                // 구매 버튼
                view.buttonPurchase.setOnClickListener{

                }
                // 취소 버튼
                view.buttonCancel.setOnClickListener {
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
                view.textServing.text = productElement.quantity.toString()

                // 타이머
                var openTime = "20:00" + ":00"
                var openTimeList = openTime.split(":")
                var openTimeSec = (openTimeList[0].toInt() * 60 + openTimeList[1].toInt()) * 60 + openTimeList[2].toInt()
                var closeTime = "21:00" + ":00"
                var closeTimeList = closeTime.split(":")
                var closeTimeSec = (closeTimeList[0].toInt() * 60 + closeTimeList[1].toInt()) * 60 + closeTimeList[2].toInt()
                timer(period = 1000) {
                    var currentTime = SimpleDateFormat("HH:mm:ss").format(Date())
                    var currentTimeList = currentTime.split(":")
                    var currentTimeSec = (currentTimeList[0].toInt() * 60 + currentTimeList[1].toInt()) * 60 + currentTimeList[2].toInt()

                    if (currentTimeSec > openTimeSec) {
                        view.textCloseTime.setText("준비중")
                    } else {
                        var leftTimeSec = closeTimeSec - currentTimeSec
                        var leftTimeList : ArrayList<Int> = arrayListOf()
                        leftTimeList[0] = leftTimeSec / 60 / 60
                        leftTimeList[1] = leftTimeSec / 60 % 60
                        leftTimeList[2] = leftTimeSec % 60
                        var leftTime = leftTimeList[0].toString() + ":" + leftTimeList[1].toString() + ":" + leftTimeList[2].toString()
                        view.textCloseTime.setText(leftTime)
                    }
                }

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                // 구매 버튼
                view.buttonPurchase.setOnClickListener{

                }
                // 장바구니 버튼
                view.buttonBasket.setOnClickListener {
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
                view.textServing.text = productElement.quantity.toString()

                // 타이머
                var openTime = "20:00" + ":00"
                var openTimeList = openTime.split(":")
                var openTimeSec = (openTimeList[0].toInt() * 60 + openTimeList[1].toInt()) * 60 + openTimeList[2].toInt()
                var closeTime = "21:00" + ":00"
                var closeTimeList = closeTime.split(":")
                var closeTimeSec = (closeTimeList[0].toInt() * 60 + closeTimeList[1].toInt()) * 60 + closeTimeList[2].toInt()
                timer(period = 1000) {
                    var currentTime = SimpleDateFormat("HH:mm:ss").format(Date())
                    var currentTimeList = currentTime.split(":")
                    var currentTimeSec = (currentTimeList[0].toInt() * 60 + currentTimeList[1].toInt()) * 60 + currentTimeList[2].toInt()

                    if (currentTimeSec > openTimeSec) {
                        view.textCloseTime.setText("준비중")
                    } else {
                        var leftTimeSec = closeTimeSec - currentTimeSec
                        var leftTimeList : ArrayList<Int> = arrayListOf()
                        leftTimeList[0] = leftTimeSec / 60 / 60
                        leftTimeList[1] = leftTimeSec / 60 % 60
                        leftTimeList[2] = leftTimeSec % 60
                        var leftTime = leftTimeList[0].toString() + ":" + leftTimeList[1].toString() + ":" + leftTimeList[2].toString()
                        view.textCloseTime.setText(leftTime)
                    }
                }

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("상품 정보")
                    .create()
                // 구매 버튼
                view.buttonPurchase.setOnClickListener{

                }
                // 장바구니 버튼
                view.buttonBasket.setOnClickListener {
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
        bundle.putString("ctgr", recipeElement.ctgr)

        frag.setArguments(bundle)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.recipe_frame, frag).commit()
    }
    // 뒤로가기 테스트
    private final var FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0) {
            var tempTime = System.currentTimeMillis()
            var intervalTime = tempTime - backPressedTime
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finishAffinity()
                System.exit(0)
                super.onBackPressed()
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
        super.onBackPressed()
    }

}
