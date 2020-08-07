package com.example.contest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.buyer_ui_info.view.*
import kotlinx.android.synthetic.main.seller_ui_info.view.*
import kotlinx.android.synthetic.main.seller_ui_info.view.buttonConfirm
import kotlinx.android.synthetic.main.seller_ui_info.view.imageUser
import kotlinx.android.synthetic.main.seller_ui_info.view.staticComfimPw
import kotlinx.android.synthetic.main.seller_ui_info.view.staticCtgrComplete
import kotlinx.android.synthetic.main.seller_ui_info.view.staticCtgrFish
import kotlinx.android.synthetic.main.seller_ui_info.view.staticCtgrGeneral
import kotlinx.android.synthetic.main.seller_ui_info.view.staticCtgrMeat
import kotlinx.android.synthetic.main.seller_ui_info.view.staticCtgrVegetable
import kotlinx.android.synthetic.main.seller_ui_info.view.textConfirmPw
import kotlinx.android.synthetic.main.seller_ui_info.view.textName
import kotlinx.android.synthetic.main.seller_ui_info.view.textPNum

class sellerUIInfo : Fragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val storage = FirebaseStorage.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.seller_ui_info, container, false)
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
                // 개인정보들
                view.textName.setText(p0.child("Name").value.toString())
                view.textPNum.setText(p0.child("pNum").value.toString())
                view.textStoreTitle.setText(p0.child("storeTitle").value.toString())
                if (p0.child("ctgr").child("정육점").value.toString().equals("true")) {
                    view.staticCtgrMeat.isVisible = true
                } else {
                    view.staticCtgrMeat.isVisible = false
                }
                if (p0.child("ctgr").child("생선가게").value.toString().equals("true")) {
                    view.staticCtgrFish.isVisible = true
                } else {
                    view.staticCtgrFish.isVisible = false
                }
                if (p0.child("ctgr").child("채소가게").value.toString().equals("true")) {
                    view.staticCtgrVegetable.isVisible = true
                } else {
                    view.staticCtgrVegetable.isVisible = false
                }
                if (p0.child("ctgr").child("잡화점").value.toString().equals("true")) {
                    view.staticCtgrGeneral.isVisible = true
                } else {
                    view.staticCtgrGeneral.isVisible = false
                }
                if (p0.child("ctgr").child("완제품").value.toString().equals("true")) {
                    view.staticCtgrComplete.isVisible = true
                } else {
                    view.staticCtgrComplete.isVisible = false
                }
            }

        })

        view.buttonConfirm.setOnClickListener {
            if (!view.staticComfimPw.isVisible) {
                // 수정 버튼 처음 눌렀을 때
                view.staticComfimPw.isVisible = true
                view.textConfirmPw.isVisible = true
            } else {
                if (view.textConfirmPw.text.toString().equals(userInfo.pw)) {
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