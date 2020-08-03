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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.buyer_ui_info_modify.*
import kotlinx.android.synthetic.main.buyer_ui_info_modify.view.*
import kotlinx.android.synthetic.main.buyer_ui_info_modify.view.buttonCancel
import kotlinx.android.synthetic.main.buyer_ui_info_modify.view.imageUser

class buyerUIInfoModify : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val storage = FirebaseStorage.getInstance()
    var imageUrl : Uri? = null
    var pw : String = ""


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_info_modify, container, false)
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
                inputName.setText(p0.child("Name").value.toString())
                inputNickName.setText(p0.child("nickName").value.toString())
                inputPhoneNumber.setText(p0.child("pNum").value.toString())
                checkButcher.isChecked = p0.child("ctgr").child("정육점").value as Boolean
                checkFishShop.isChecked = p0.child("ctgr").child("생선가게").value as Boolean
                checkGenearl.isChecked = p0.child("ctgr").child("잡화점").value as Boolean
                checkGreengrocer.isChecked = p0.child("ctgr").child("채소가게").value as Boolean
                checkComplete.isChecked = p0.child("ctgr").child("완제품").value as Boolean
                pw = p0.child("pw").value.toString()
            }
        })

        view.changeImageUser.setOnClickListener {
            val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
        }

        view.buttonConfirm.setOnClickListener {
            if (!pw.equals(inputCurrentPassword.text.toString())) {
                Toast.makeText(requireContext(), "현재 비밀번호를 잘못 입력했습니다", Toast.LENGTH_SHORT).show()
            } else {
                data.child("Name").setValue(inputName.text.toString())
                data.child("nickName").setValue(inputNickName.text.toString())
                data.child("pNum").setValue(inputPhoneNumber.text.toString())
                data.child("pw").setValue(inputChangePassword.text.toString())
                data.child("ctgr").child("정육점").setValue(checkButcher.isChecked)
                data.child("ctgr").child("생선가게").setValue(checkFishShop.isChecked)
                data.child("ctgr").child("잡화점").setValue(checkGenearl.isChecked)
                data.child("ctgr").child("채소가게").setValue(checkGreengrocer.isChecked)
                data.child("ctgr").child("완제품").setValue(checkComplete.isChecked)
                if (imageUrl != null) {
                    // 사진 바꿈
                    dataImage.child(userInfo.id + ".png").putFile(imageUrl!!)
                }
                (activity as buyerUIMain).setBuyerFrag(31)
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
            }
        }
    }
}