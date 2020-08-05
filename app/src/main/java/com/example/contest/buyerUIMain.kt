package com.example.contest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.buyer_ui_main.*
import kotlinx.android.synthetic.main.product_buyer_put.view.*

class buyerUIMain : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val TAG = "FirebaseService"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_ui_main)
        val DatabaseReference = database.reference
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    DatabaseReference.child("tokenDB").child(userInfo.id).setValue(token)
                })
        // 구매자 화면 전환
        setBuyerFrag(11)

        buttonBuyerRecipe.setOnClickListener {
            setBuyerFrag(11)
        }

        buttonBuyerToday.setOnClickListener {
            setBuyerFrag(21)
        }

        buttonBuyerInfo.setOnClickListener {
            setBuyerFrag(31)
        }
        buttonBuyerBasket.setOnClickListener {
            setBuyerFrag(41)
        }
    }

    fun setBuyerFrag(fragNum: Int) {
        val ft = supportFragmentManager.beginTransaction()
        when (fragNum) {
            // 레시피
            11 -> {
                ft.replace(R.id.main_frame, buyerUIRecipe()).commit()
            }
            // 지도
            21 -> {
                ft.replace(R.id.main_frame, buyerUIToday()).commit()
            }
            // 정보
            31 -> {
                ft.replace(R.id.main_frame, buyerUIInfo()).commit()
            }
            // 장바구니
            41 -> {
                ft.replace(R.id.main_frame, buyerUIBasket()).commit()
            }
            // 시장 선택 시
            22 -> {
                ft.replace(R.id.main_frame, buyerUIMarketCtgr01()).commit()
            }
            // 재료 카테고리 선택 시
            23 -> {
                ft.replace(R.id.main_frame02, buyerUIMarketCtgr02()).commit()
            }
            // 판매자 완제품
            24 -> {
                var frag = buyerUIMarket()
                var bundle = Bundle(1)
                bundle.putInt("usage", 2)
                frag.setArguments(bundle)
                ft.replace(R.id.main_frame02, frag).commit()
            }
            // 판매자 재료
            25 -> {
                var frag = buyerUIMarket()
                var bundle = Bundle(1)
                bundle.putInt("usage", 3)
                frag.setArguments(bundle)
                ft.replace(R.id.main_frame03, frag).commit()
            }
            // 정보 수정
            32 -> {
                ft.replace(R.id.main_frame, buyerUIInfoModify()).commit()
            }
            // 레시피 육류
            12 -> {
                ft.replace(R.id.recipe_frame, recipeUIMeat()).commit()
            }
            // 레시피 해산물
            13 -> {
                ft.replace(R.id.recipe_frame, recipeUISeafood()).commit()
            }
            // 레시피 채소
            14 -> {
                ft.replace(R.id.recipe_frame, recipeUIVegetable()).commit()
            }
            // 레시피 기타
            15 -> {
                ft.replace(R.id.recipe_frame, recipeUIEtc()).commit()
            }
            // 레시피 페이지
            16 -> {
                ft.replace(R.id.recipe_frame, recipeUISpecific()).commit()
            }
        }


    }

    fun recyclerViewFun(product: productElement) {
        when (currentProductElement.function) {
            "plus" -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_buyer_put, null)

                val alertDialog = AlertDialog.Builder(this).create()

                view.textQuanLeft.text = product.quanLeft.toString()
                view.buttonConfirm.setOnClickListener {
                    if (view.textAcquire.text.toString().toInt() > view.textQuanLeft.text.toString()
                            .toInt()
                    ) {
                        view.staticWarning.isVisible = true
                    } else {
                        product.quanSold += view.textAcquire.text.toString().toInt()
                        product.quanLeft -= view.textAcquire.text.toString().toInt()
                        product.buyerId[userInfo.id] = view.textAcquire.text.toString().toInt()
                        var data = FirebaseDatabase.getInstance().getReference("productTodayDB")
                        data.child(product.productId).setValue(product.toMap())
                    }
                    productBasket.productList.add(currentProductElement.currentProductElement)

                    // currentProduct 초기화
                    currentProductElement.currentProductElement = productElement()
                    currentProductElement.function = ""

                    // 시장이 완제품 시장인지 재료 시장인지
                    when (currentCondition.ctgr01) {
                        "complete" -> {
                            setBuyerFrag(24)
                        }
                        "raw" -> {
                            setBuyerFrag(25)
                        }
                    }
                    alertDialog.dismiss()
                }
                view.buttonCancel.setOnClickListener {
                    alertDialog.cancel()
                }

                alertDialog.setView(view)
                alertDialog.show()
            }
            "minus" -> {
                // 뚝딱뚝딱 다시 되돌려주기
                product.quanLeft += product.buyerId[userInfo.id].toString().toInt()
                product.quanSold -= product.buyerId[userInfo.id].toString().toInt()
                product.buyerId.remove(userInfo.id)
                var data = FirebaseDatabase.getInstance().getReference("productTodayDB")
                data.child(product.productId).setValue(product.toMap())
                productBasket.productList.remove(currentProductElement.currentProductElement)
                // currentProduct 초기화
                currentProductElement.currentProductElement = productElement()
                currentProductElement.function = ""

                setBuyerFrag(41)
            }
            "purchase" -> {
                // 구매기능
            }
        }
    }

    /**
    // 상품의 세부사항을 보여주는 함수 -> 폐기
    fun showProductSpecific(productElement: productElement, usage : Int) {
    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    when (usage) {
    // 구매자 장바구니

    1 -> {
    val view = inflater.inflate(R.layout.product_buyer_basket_specific, null)
    // basket_specific 에 productElement의 값 넣어주기
    view.textProductTitle.text = productElement.title
    view.imageProduct.setImageResource(R.drawable.test_02)
    view.textPrice.text = productElement.price.toString()
    view.textServing.text = productElement.serve.toString()
    view.textQuan.text = productElement.quanTotal.toString()

    val alertDialog = AlertDialog.Builder(this).create()

    // 장바구니 빼기 버튼 기능 구현

    view.buttonCancel.setOnClickListener {
    productBasket.productList.remove(productElement)
    alertDialog.dismiss()
    setBuyerFrag(41)
    }

    alertDialog.setView(view)
    alertDialog.show()
    }
    // 구매자 완제품
    2 -> {
    val view = inflater.inflate(R.layout.product_buyer_market_specific, null)

    view.textProductTitle.text = productElement.title
    view.imageProduct.setImageResource(R.drawable.test_02)
    view.textPrice.text = productElement.price.toString()
    view.textServing.text = productElement.serve.toString()
    view.textQuan.text = productElement.quanLeft.toString()
    view.textQuanTotal.text = productElement.quanTotal.toString()

    val alertDialog = AlertDialog.Builder(this).create()

    // 장바구니 담기 버튼
    view.buttonBasket.setOnClickListener {
    val view2 = inflater.inflate(R.layout.product_buyer_put, null)

    val alertDialog2 = AlertDialog.Builder(this).create()

    productBasket.productList.add(productElement)
    setBuyerFrag(24)

    // 장바구니 확인 버튼
    view2.buttonConfirm.setOnClickListener {
    alertDialog2.dismiss()
    }

    // 장바구니 취소 버튼
    view2.buttonCancel.setOnClickListener {
    alertDialog2.cancel()
    }

    alertDialog2.setView(view2)
    alertDialog2.show()


    }
    alertDialog.setView(view)
    alertDialog.show()

    }
    // 구매자 재료
    3 -> {
    val view = inflater.inflate(R.layout.product_buyer_market_specific, null)

    view.textProductTitle.text = productElement.title
    view.textPrice.text = productElement.price.toString()
    view.textQuan.text = productElement.quanLeft.toString()
    view.textServing.text = productElement.serve.toString()
    view.textQuanTotal.text = productElement.quanTotal.toString()
    view.imageProduct.setImageResource(R.drawable.test_02)


    val alertDialog = AlertDialog.Builder(this).setTitle("상품 정보").create()
    // 구매 버튼

    // 장바구니 버튼
    view.buttonBasket.setOnClickListener {
    val view2 = inflater.inflate(R.layout.product_buyer_put, null)

    val alertDialog2 = AlertDialog.Builder(this).create()

    productBasket.productList.add(productElement)
    setBuyerFrag(25)

    // 장바구니 확인 버튼
    view2.buttonConfirm.setOnClickListener {
    alertDialog2.dismiss()
    }

    // 장바구니 취소 버튼
    view2.buttonCancel.setOnClickListener {
    alertDialog2.cancel()
    }

    alertDialog2.setView(view2)
    alertDialog2.show()


    }
    alertDialog.setView(view)
    alertDialog.show()

    }
    }
    }
     */

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
        if (supportFragmentManager.backStackEntryCount == 0) {
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
