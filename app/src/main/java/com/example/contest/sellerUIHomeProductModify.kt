package com.example.contest

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.seller_ui_modify_product.*
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.*
import kotlinx.android.synthetic.main.seller_ui_modify_product.view.imageProduct
import java.text.SimpleDateFormat
import java.util.*

class sellerUIHomeProductModify : Fragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val mStorageRef = FirebaseStorage.getInstance()
    var imageUrl: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.seller_ui_modify_product, null)
        var productElement = currentProductElement.currentProductElement
        val data = database.getReference("productTodayDB")
        var imagePath = mStorageRef.getReference("productImageDB")
        var imageData = imagePath.child(productElement.productId + ".png")
        val imageSize: Long = 1024 * 1024 * 10
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        var byteArr: ByteArray = byteArrayOf()

        view.textProductTitle.setText(productElement.title)
        view.textPrice.setText(productElement.price.toString())
        view.textQuan.setText(productElement.serve.toString())
        imageData.getBytes(imageSize).addOnSuccessListener {
            byteArr = it
            val imageBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.imageProduct.setImageBitmap(imageBitmap)
        }

        view.buttonChangeImage.setOnClickListener {
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
        }
        view.buttonConfirm.setOnClickListener {
            var modifiedProduct = productElement()
            modifiedProduct.setInfo(
                view.textProductTitle.text.toString()
                ,
                Integer.parseInt(view.textPrice.text.toString())
                ,
                Integer.parseInt(view.textQuan.text.toString())
                ,
                SimpleDateFormat("yyyyMMdd").format(Date()) + userInfo.id + view.textProductTitle.text.toString()
                ,
                textQuan.text.toString().toInt()
                ,
                userInfo.ctgrForSeller
                ,
                userInfo.timeClose
            )
            var imageNew = imagePath.child(modifiedProduct.productId + ".png")

            imageData.delete()
            if (imageUrl == null) {
                // 사진 안바꿈
                imageNew.putBytes(byteArr)
            } else {
                // 사진 바꿈
                imageNew.putFile(imageUrl!!)
            }
            data.child(productElement.productId).removeValue()
            data.child(modifiedProduct.productId).setValue(modifiedProduct.toMap())
            
            // currentProduct 초기화
            currentProductElement.currentProductElement = productElement()
            currentProductElement.function = ""

            (activity as sellerUIMain).setSellerFrag(11)
        }
        view.buttonCancel.setOnClickListener {
            // currentProduct 초기화
            currentProductElement.currentProductElement = productElement()
            currentProductElement.function = ""

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