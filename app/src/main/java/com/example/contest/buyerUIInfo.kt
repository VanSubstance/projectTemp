package com.example.contest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_info, container, false)
        var dataImage = storage.getReference("userImageDB")
        val imageSize: Long = 1024 * 1024 * 10
        var bitmap : Bitmap? = null
        dataImage.child(userInfo.id + ".png").getBytes(imageSize).addOnSuccessListener {
            if (it == null) {
                dataImage.child("default.png").getBytes(imageSize).addOnSuccessListener {
                    bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                }
            } else {
                bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
            view.imageUser.setImageBitmap(bitmap)
        }

        var data = database.getReference("userDB").child(userInfo.id)
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                view.outputName.setText(p0.child("Name").value.toString())
                view.outputPhoneNumber.setText(p0.child("pNum").value.toString())
                view.outputNickName.setText(p0.child("nickName").value.toString())
                // 카테고리도 해줘야댐
            }

        })

        view.buttonModify.setOnClickListener {
            (activity as buyerUIMain).setBuyerFrag(32)
        }
        return view
    }
}