package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_buyer.*
import kotlinx.android.synthetic.main.sign_up_buyer.view.*

class signupBuyer : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sign_up_buyer, container, false)

        val mID = view.et_id
        val mPasswordText = view.et_pass
        val mPasswordcheckText = view.et_passck
        val mName = view.et_name
        val mPnum = view.et_Phone_number

        view.btn_register.setOnClickListener {
            val DatabaseReference = database.reference

            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(requireContext(), "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                val ID = mID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "buyer"
                val data = Post(ID, password, name, pnum, role)
                val info = data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
                (activity as signup_sellect).finish()
            }
        }
        view.validateButton.setOnClickListener {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val myref: DatabaseReference = database.getReference("userDB")
            myref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var err = 0
                    for (account in p0.children) {
                        if(mID.text.toString().equals(account.key.toString())) {
                            err = 1
                            Toast.makeText(requireContext(), "존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (err == 0) {
                        Toast.makeText(requireContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }
                    err = 0
                }
            })
        }
        return view
    }
}