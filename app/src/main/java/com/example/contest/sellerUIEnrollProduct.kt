package com.example.contest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*
import kotlinx.android.synthetic.main.seller_ui_enroll_product.view.*
import java.time.LocalDate

class sellerUIEnrollProduct : Fragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_enroll_product, container, false)

        auth = FirebaseAuth.getInstance()
        val data = database.getReference("productTodayDB")
        // 사진 변경 버튼
        view.buttonChangeImage.setOnClickListener {
            val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 1)
        }
        view.buttonEnroll.setOnClickListener {
            // inputTitle: 상품 이름 인풋, inputPrice: 가격 인풋, inputQuan: 수량 인풋
            // 여기서 데이터베이스에 저장
            // 저장 양식은 테이블에 맞춰서: 상품 이름, 가격, 수량, 날짜, 판매자 ID, etc.
            if (view.inputTitle.text.isEmpty() || view.inputPrice.text.isEmpty() || view.inputQuan.text.isEmpty()) {
                Toast.makeText(requireContext(), "제대로 입력해야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                var title = view.inputTitle.text.toString()
                var price = Integer.parseInt(view.inputPrice.text.toString())
                var quan = Integer.parseInt(view.inputQuan.text.toString())
                var newProduct : productElement = productElement()
                //var image = view.imageProduct as Uri
                var productId : String = LocalDate.now().toString() + userInfo.id + title
                newProduct.setInfo(title, price, quan, productId/**, image*/)
                data.child(productId).setValue(newProduct.toMap())
                (activity as sellerUIMain).setSellerFrag(11)
            }
        }

        view.buttonCancel.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(11)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                var imageUrl = data?.data
                imageProduct.setImageURI(imageUrl)
            }
        }
    }
}