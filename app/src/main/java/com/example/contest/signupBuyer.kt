package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.signup_buyer.view.*

class signupBuyer : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth : FirebaseAuth?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signup_buyer, container, false)

        val mID = view.textId
        val mPasswordText = view.textPw
        val mPasswordcheckText = view.textPwCheck
        val mName = view.staticName
        val mPnum = view.textPNum
        val mnick=view.buyer_nick

        view.buttonConfirm.setOnClickListener {
            val DatabaseReference = database.reference

            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(requireContext(), "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                auth = FirebaseAuth.getInstance()
                val ID = mID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val nick=mnick.text.toString()
                val role: String = "buyer"
                auth?.createUserWithEmailAndPassword(ID, password)
                        ?.addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // 아이디 생성이 완료되었을 때
                                val user = auth?.getCurrentUser()
                                val uid=user?.uid
                                val data = Post(name, pnum, role,nick)
                                val info = data.toMap()
                                DatabaseReference.child("userDB").child(uid.toString()).setValue(info)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("정육점").setValue(view.checkCtgrMeat.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("생선가게").setValue(view.checkCtgrFish.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("채소가게").setValue(view.checkCtgrVegetable.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("잡화점").setValue(view.checkCtgrGeneral.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("완제품").setValue(view.checkCtgrEtc.isChecked)

                                Toast.makeText(requireContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                                (activity as signup_sellect).finish()

                            } else { // 아이디 생성이 실패했을 경우
                                Toast.makeText(requireContext(), "이미 가입된 이메일이거나 잘못된 이메일입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
        view.buttonCertifyId.setOnClickListener{
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val data = database.getReference("userDB")
            data.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(i in p0.children){
                        if (i.child("nick").value==mnick.text.toString()){
                            Toast.makeText(requireContext(),"이미 있는 닉네임입니다.",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(requireContext(),"사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        return view
    }
}