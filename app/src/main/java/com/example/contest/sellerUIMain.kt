package com.example.contest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.seller_ui_main.*
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*
import kotlinx.android.synthetic.main.seller_ui_main.textTime
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.*
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.productImage
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class sellerUIMain : AppCompatActivity() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var imageUrl : Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_ui_main)

        Thread(Runnable {
            while (!Thread.interrupted()) try {
                Thread.sleep(1000)
                runOnUiThread { textTime.setText(getCurrentTime()) }
            } catch (e: InterruptedException) {
            }
        }).start()

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

    fun getCurrentTime(): String? {
        val time = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date(time))
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
        val mStorageRef = FirebaseStorage.getInstance().getReference("productImageDB")
        val data = database.getReference("productTodayDB")
        val imagePath = mStorageRef.child(productElement.productId + ".png")
        val imageSize: Long = 1024 * 1024 * 10

        when (usage) {
            // 판매자 오늘 상품
            1 -> {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.product_seller_home_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()
                imagePath.getBytes(imageSize).addOnSuccessListener {
                    val imageBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    view.productImage.setImageBitmap(imageBitmap)
                }
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
                    imagePath.getBytes(imageSize).addOnSuccessListener {
                        val imageBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                        view.productImage.setImageBitmap(imageBitmap)
                    }
                    view.buttonChangeImage.setOnClickListener {
                        //사진 변경 버튼
                        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.setType("image/*")
                        startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
                    }
                    view.buttonConfirm.setOnClickListener {
                        // 확인 버튼
                        var imageTitle = productElement.productId + ".png"
                        mStorageRef.child(imageTitle).putFile(imageUrl!!)
                        var modifiedProduct = productElement
                        modifiedProduct.setInfo(view.inputTitle.text.toString()
                            , Integer.parseInt(view.inputPrice.text.toString())
                            , Integer.parseInt(view.inputQuan.text.toString())
                            , productElement.productId)
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
                    imagePath.delete()
                    setSellerFrag(11)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                imageUrl = data?.data
                productImage.setImageURI(imageUrl)
            }
        }
    }


}
