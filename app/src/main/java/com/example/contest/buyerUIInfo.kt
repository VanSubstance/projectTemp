package com.example.contest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.buyer_ui_info.view.*

class buyerUIInfo : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val storage = FirebaseStorage.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.buyer_ui_info, container, false)
        var dataImage = storage.getReference("userImageDB")
        val imageSize: Long = 1024 * 1024 * 10
        var bitmap: Bitmap? = null
        dataImage.child(userInfo.id + ".png").getBytes(imageSize).addOnSuccessListener {
            bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.imageUser.setImageBitmap(bitmap)
        }
        if (bitmap == null) {
            dataImage.child("default.png").getBytes(imageSize).addOnSuccessListener {
                bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                view.imageUser.setImageBitmap(bitmap)
            }
        }



        var data = database.getReference("userDB").child(userInfo.id)
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                view.outputName.setText(p0.child("Name").value.toString())
                view.outputPhoneNumber.setText(p0.child("pNum").value.toString())
                view.outputNickName.setText(p0.child("nickName").value.toString())
                if (p0.child("ctgr").child("정육점").value.toString().equals("true")) {
                    view.outputPreferButcher.isVisible = true
                } else {
                    view.outputPreferButcher.isVisible = false
                }
                if (p0.child("ctgr").child("생선가게").value.toString().equals("true")) {
                    view.outputPreferFishShop.isVisible = true
                } else {
                    view.outputPreferFishShop.isVisible = false
                }
                if (p0.child("ctgr").child("채소가게").value.toString().equals("true")) {
                    view.outputPreferGreengrocer.isVisible = true
                } else {
                    view.outputPreferGreengrocer.isVisible = false
                }
                if (p0.child("ctgr").child("잡화점").value.toString().equals("true")) {
                    view.outputPreferGenearl.isVisible = true
                } else {
                    view.outputPreferGenearl.isVisible = false
                }
                if (p0.child("ctgr").child("완제품").value.toString().equals("true")) {
                    view.outputPreferComplete.isVisible = true
                } else {
                    view.outputPreferComplete.isVisible = false
                }
            }

        })

        view.buttonModify.setOnClickListener {
            if (!view.staticComfimPw.isVisible) {
                // 수정 버튼 처음 눌렀을 때
                view.staticComfimPw.isVisible = true
                view.inputConfirmPw.isVisible = true
            } else {
                if (view.inputConfirmPw.text.toString().equals(userInfo.pw)) {
                    (activity as buyerUIMain).setBuyerFrag(32)
                } else {
                    view.staticComfimPw.setText("비밀번호 확인 : 비밀번호가 틀립니다!")
                    view.staticComfimPw.setTextColor(Color.parseColor("#ff0000"))
                }
            }
        }
        return view
    }
}