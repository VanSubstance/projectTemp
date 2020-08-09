package com.example.contest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.signup_buyer.*
import kotlinx.android.synthetic.main.signup_buyer.view.*


class signupBuyer : Fragment(){
    private val TAG = "FirebaseService"
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth : FirebaseAuth?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signup_buyer, container, false)

        //*************************************************************************************//
        //sign up sellect로 옮기는 바람에 오류가 뜨는중

        val mID = arguments?.getString("ID")!!
        val mPasswordText = arguments?.getString("pw")!!
        val mName = arguments?.getString("name")!!

        val mPnum = view.textPNum
        val mnick=view.buyer_nick


        view.buttonConfirm.setOnClickListener {
            val DatabaseReference = database.reference
            val auth=FirebaseAuth.getInstance()
            if (mPnum.text.toString().length != 11) {
                Toast.makeText(requireContext(), "전화번호 11자리를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (mnick.text.toString().length == 0) {
                mnick.setText(mName)
            } else {
                val pnum = mPnum.text.toString()
                val nick=mnick.text.toString()
                val role: String = "buyer"
                auth?.createUserWithEmailAndPassword(mID, mPasswordText)
                        ?.addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // 아이디 생성이 완료되었을 때
                                val user = auth?.getCurrentUser()
                                val uid=user?.uid
                                val data = Post(mPasswordText, mName, pnum, role, nick)
                                val info = data.toMap()
                                DatabaseReference.child("userDB").child(uid.toString()).setValue(info)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("정육점").setValue(view.checkCtgrMeat.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("생선가게").setValue(view.checkCtgrFish.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("채소가게").setValue(view.checkCtgrVegetable.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("잡화점").setValue(view.checkCtgrGeneral.isChecked)
                                DatabaseReference.child("userDB").child(uid.toString()).child("ctgr").child("완제품").setValue(view.checkCtgrEtc.isChecked)
                                pushToken(uid.toString())
                                Toast.makeText(requireContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                                (activity as signup_sellect).finish()

                            } else { // 아이디 생성이 실패했을 경우
                                Toast.makeText(requireContext(), "이미 가입된 이메일이거나 잘못된 이메일입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }

        view.buttonFindMarket.setOnClickListener {
            if(!view.mapFrame.isVisible) {
                view.mapFrame.isVisible = true
            }
        }

        view.mapFrame.setOnClickListener(View.OnClickListener {
            if(mapFrame.isVisible) {
                mapFrame.isVisible = false
            }
        })

        view.buttonCertifyNick.setOnClickListener{
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val DatabaseReference = database.reference
            DatabaseReference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
                override fun onDataChange(p0: DataSnapshot) {
                    for(db_name in p0.children){
                        if(db_name.toString()=="userDB"){
                            for(i in p0.child("usedDB").children){
                                if (i.child("nick").value==mnick.text.toString()){
                                    Toast.makeText(requireContext(),"이미 있는 닉네임입니다.",Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(requireContext(),"사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show()
                                }
                            }
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


    fun pushToken(ID:String){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val DatabaseReference = database.reference
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    DatabaseReference.child("userDB").child(ID).child("token").setValue(token)
                })
    }
}
