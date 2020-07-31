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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*
import kotlinx.android.synthetic.main.seller_ui_enroll_product.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class sellerUIEnrollProduct : Fragment() {
    private val mStorageRef = FirebaseStorage.getInstance()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth
    var imageUrl : Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_enroll_product, container, false)

        auth = FirebaseAuth.getInstance()
        val data = database.getReference("productTodayDB")
        val imageData = mStorageRef.getReference("productImageDB")
        // 사진 변경 버튼
        view.buttonChangeImage.setOnClickListener {
            val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 1)
        }
        view.buttonEnroll.setOnClickListener {
            if (view.inputTitle.text.isEmpty() || view.inputPrice.text.isEmpty() || view.inputQuan.text.isEmpty()) {
                Toast.makeText(requireContext(), "제대로 입력해야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                var title = view.inputTitle.text.toString()
                var price = Integer.parseInt(view.inputPrice.text.toString())
                var quan = Integer.parseInt(view.inputQuan.text.toString())
                var newProduct : productElement = productElement()
                var productId = SimpleDateFormat("yyyyMMdd").format(Date()) + userInfo.id + title
                var imageTitle = productId + ".png"

                imageData.child(imageTitle).putFile(imageUrl!!)
                newProduct.setInfo(title, price, quan, productId)
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
                imageUrl = data?.data
                imageProduct.setImageURI(imageUrl)
            }
        }
    }
}