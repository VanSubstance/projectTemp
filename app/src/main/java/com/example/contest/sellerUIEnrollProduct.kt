package com.example.contest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*
import kotlinx.android.synthetic.main.seller_ui_enroll_product.view.*
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*


class sellerUIEnrollProduct : Fragment() {
    private val mStorageRef = FirebaseStorage.getInstance()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var imageUrl : Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_enroll_product, container, false)
        val data = database.getReference("productTodayDB")
        val imageData = mStorageRef.getReference("productImageDB")
        // 사진 변경 버튼
        view.buttonChangeImage.setOnClickListener {
            val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
        }
        view.checkCtgrComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.checkCtgrRaw.isChecked = false
            } else {
                view.checkCtgrRaw.isChecked = true
            }
        }
        view.checkCtgrRaw.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.checkCtgrComplete.isChecked = false
            } else {
                view.checkCtgrComplete.isChecked = true
            }
        }
        view.buttonEnroll.setOnClickListener {
            val DatabaseReference = database.reference
            if (!view.checkCtgrComplete.isChecked && !view.checkCtgrRaw.isChecked) {
                Toast.makeText(requireContext(), "제품 카테고리를 골라주세요", Toast.LENGTH_SHORT).show()
            } else if (view.textProductTitle.text.isEmpty() || view.textPrice.text.isEmpty() || view.textQuan.text.isEmpty() || view.textServing.text.isEmpty()) {
                Toast.makeText(requireContext(), "제대로 입력해야 합니다", Toast.LENGTH_SHORT).show()
            } else if (imageUrl == null) {
                Toast.makeText(requireContext(), "사진을 등록해주세요", Toast.LENGTH_SHORT).show()
            } else {
                var title = view.textProductTitle.text.toString()
                var price = Integer.parseInt(view.textPrice.text.toString())
                var serve = Integer.parseInt(view.textServing.text.toString())
                var quan = Integer.parseInt(view.textQuan.text.toString())
                var newProduct : productElement = productElement()
                var productId = SimpleDateFormat("yyyyMMdd").format(Date()) + userInfo.id + title
                var imageTitle = productId + ".png"
                imageData.child(imageTitle).putFile(imageUrl!!)
                if (view.checkCtgrComplete.isChecked) {
                    newProduct.setInfo(title, price, serve, productId, quan, "완제품", userInfo.timeClose)
                } else {
                    newProduct.setInfo(title, price, serve, productId, quan, userInfo.ctgrForSeller, userInfo.timeClose)
                }
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
                imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP)
            }
        }
    }

    private fun resize(
        context: Context,
        uri: Uri,
        resize: Int
    ): Bitmap? {
        var resizeBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        try {
            BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            ) // 1번
            var width = options.outWidth
            var height = options.outHeight
            var samplesize = 1
            while (true) { //2번
                if (width / 2 < resize || height / 2 < resize) break
                width /= 2
                height /= 2
                samplesize *= 2
            }
            options.inSampleSize = samplesize
            val bitmap = BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            ) //3번
            resizeBitmap = bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return resizeBitmap
    }

}