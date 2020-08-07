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
import kotlinx.android.synthetic.main.buyer_ui_info.view.*
import kotlinx.android.synthetic.main.buyer_ui_info_modify.*
import kotlinx.android.synthetic.main.buyer_ui_info_modify.view.*
import kotlinx.android.synthetic.main.buyer_ui_info_modify.view.buttonConfirm
import kotlinx.android.synthetic.main.buyer_ui_info_modify.view.imageUser
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*

class buyerUIInfoModify : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val storage = FirebaseStorage.getInstance()
    var imageUrl: Uri? = null
    var pw: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.buyer_ui_info_modify, container, false)
        
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
        var data = database.getReference("userDB").child(userInfo.id)
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                textName.setText(p0.child("Name").value.toString())
                textNickName.setText(p0.child("nick").value.toString())
                textPNum.setText(p0.child("pNum").value.toString())
                checkCtgrMeat.isChecked = p0.child("ctgr").child("정육점").value as Boolean
                checkCtgrFish.isChecked = p0.child("ctgr").child("생선가게").value as Boolean
                checkCtgrGeneral.isChecked = p0.child("ctgr").child("잡화점").value as Boolean
                checkCtgrVegetable.isChecked = p0.child("ctgr").child("채소가게").value as Boolean
                checkCtgrEtc.isChecked = p0.child("ctgr").child("완제품").value as Boolean
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
            data.child("nickName").setValue(textNickName.text.toString())
            data.child("pNum").setValue(textPNum.text.toString())
            data.child("ctgr").child("정육점").setValue(checkCtgrMeat.isChecked)
            data.child("ctgr").child("생선가게").setValue(checkCtgrFish.isChecked)
            data.child("ctgr").child("잡화점").setValue(checkCtgrGeneral.isChecked)
            data.child("ctgr").child("채소가게").setValue(checkCtgrVegetable.isChecked)
            data.child("ctgr").child("완제품").setValue(checkCtgrEtc.isChecked)
            if (imageUrl != null) {
                // 사진 바꿈
                dataImage.child(userInfo.id + ".png").putFile(imageUrl!!)
                (activity as buyerUIMain).setBuyerFrag(31)
            } else {
                Toast.makeText(requireContext(), "사진을 등록해주세요", Toast.LENGTH_SHORT).show()
            }


        }

        view.buttonCancel.setOnClickListener {
            (activity as buyerUIMain).setBuyerFrag(31)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                imageUrl = data?.data
                imageUser.setImageURI(imageUrl)
                imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP)
            }
        }
    }
}