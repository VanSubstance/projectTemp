package com.example.contest

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.seller_ui_info_modify.*
import kotlinx.android.synthetic.main.seller_ui_info_modify.view.*

class sellerUIInfoModify : Fragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val storage = FirebaseStorage.getInstance()
    var imageUrl: Uri? = null
    var pw: String = ""

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_info_modify, container, false)

        // 사진
        var dataImage = storage.getReference("userImageDB")
        val imageSize: Long = 1024 * 1024 * 10
        var bitmap: Bitmap? = null
        dataImage.child(userInfo.id + ".png").getBytes(imageSize).addOnSuccessListener {
            bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.imageUser.setImageBitmap(bitmap)
            view.imageUser.setScaleType(ImageView.ScaleType.CENTER_CROP)
        }
        if (bitmap == null) {
            dataImage.child("default.png").getBytes(imageSize).addOnSuccessListener {
                bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                view.imageUser.setImageBitmap(bitmap)
                view.imageUser.setScaleType(ImageView.ScaleType.CENTER_CROP)
            }
        }

        // 원래 정보 기입
        var data = database.getReference("userDB").child(userInfo.id)

        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                textName.setText(p0.child("Name").value.toString())
                textStoreTitle.setText(p0.child("storeTitle").value.toString())
                textPNum.setText(p0.child("pNum").value.toString())
                pw = p0.child("pw").value.toString()
            }
        })

        view.buttonChangeImage.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
        }

        view.buttonConfirm.setOnClickListener {
            if (!textPwChange.text.toString().isEmpty()) {
                data.child("pw").setValue(textPwChange.text.toString())
            }
            data.child("Name").setValue(textName.text.toString())
            data.child("storeTitle").setValue(textStoreTitle.text.toString())
            data.child("pNum").setValue(textPNum.text.toString())
            if (imageUrl != null) {
                // 사진 바꿈
                dataImage.child(userInfo.id + ".png").putFile(imageUrl!!)
                (activity as sellerUIMain).setSellerFrag(41)
            } else {
                Toast.makeText(requireContext(), "사진을 등록해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        view.buttonCancel.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(41)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                imageUrl = data?.data
                imageUser.setImageURI(imageUrl)
                imageUser.setScaleType(ImageView.ScaleType.CENTER_CROP)
            }
        }
    }
}
