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
import com.google.firebase.database.FirebaseDatabase
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
        view.textName.setText(userInfo.name)
        view.textPNum.setText(userInfo.pNum)
        view.textNickName.setText(userInfo.nick)
        for (ctgr in userInfo.ctgrForBuyer) {
            when (ctgr) {
                "정육점" -> {
                    view.staticCtgrMeat.isVisible = true
                }
                "생선가게" -> {
                    view.staticCtgrFish.isVisible = true
                }
                "채소가게" -> {
                    view.staticCtgrVegetable.isVisible = true
                }
                "잡화점" -> {
                    view.staticCtgrGeneral.isVisible = true
                }
                "완제품" -> {
                    view.staticCtgrComplete.isVisible = true
                }
            }
        }

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
