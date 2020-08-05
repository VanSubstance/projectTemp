package com.example.contest

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.seller_ui_info.*
import kotlinx.android.synthetic.main.signup_seller.*
import kotlinx.android.synthetic.main.signup_seller.staicStoreTitle
import kotlinx.android.synthetic.main.signup_seller.view.*
import java.text.SimpleDateFormat
import java.util.*

class signupSeller : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth : FirebaseAuth?= null
    private val TAG = "FirebaseService"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signup_seller, container, false)

        val msID = view.textId
        val mPasswordText = view.textPw
        val mPasswordcheckText = view.textPwCheck
        val mName = view.staticName
        val mPnum = view.textPNum
        val textMarketTitle=view.textMarketTitle
        val staticStoreTitle = view.staicStoreTitle
        val staticSpinnerDate=view.staticSpinnerDate


        val mPickTimeBt_s = view.buttonTimepickOpen
        val textView_s = view.textTimeOpen

        val mPickTimeBt_e = view.buttonTimepickClose
        val textView_e = view.textTimeClose

        view.buttonConfirm.setOnClickListener {
            val DatabaseReference = database.reference
            auth = FirebaseAuth.getInstance()

            if (msID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(requireContext(), "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else if (textMarketTitle.text.equals("시장 이름") || textMarketTitle.text.equals("")) {
                Toast.makeText(requireContext(), "시장을 선택해주세요", Toast.LENGTH_SHORT).show()
            } else if (staicStoreTitle.text.equals("")) {
                Toast.makeText(requireContext(), "매장 명을 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (textTimeClose.text.equals("개점 시간") || textTimeOpen.text.equals("폐점 시간") || textTimeClose.text.equals("") || textTimeOpen.text.equals("")) {
                Toast.makeText(requireContext(), "개점, 폐점 시간을 정해주세요", Toast.LENGTH_SHORT).show()
            } else if (staticSpinnerDate.text.equals("카테고리") || staticSpinnerDate.text.equals("")) {
                Toast.makeText(requireContext(), "카테고리를 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                val ID = msID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val marketTitle = textMarketTitle.text.toString()
                val storeTitle = staticStoreTitle.text.toString()
                val storeCtgr = staticSpinnerDate.text.toString()
                val timeOpen =textView_s.text.toString()
                val timeClose = textView_e.text.toString()
                var token_r:String=""

                auth?.createUserWithEmailAndPassword(ID, password)
                        ?.addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // 아이디 생성이 완료되었을 때
                                val user = auth?.getCurrentUser()
                                val uid=user?.uid
                                FirebaseInstanceId.getInstance().instanceId
                                        .addOnCompleteListener(OnCompleteListener { task ->
                                            if(!task.isSuccessful){
                                                Log.w(TAG, "getInstanceId failed", task.exception)
                                                return@OnCompleteListener
                                            }
                                            val token=task.result?.token
                                            token_r=token.toString()})
                                
                                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(uid.toString()).setValue(""   )
                                DatabaseReference.child("storeDB").child(uid.toString()).child("ctgr").setValue(storeCtgr)
                                DatabaseReference.child("storeDB").child(uid.toString()).child("timeOpen").setValue(timeOpen)
                                DatabaseReference.child("storeDB").child(uid.toString()).child("timeClose").setValue(timeClose)
                                DatabaseReference.child("storeDB").child(uid.toString()).child("title").setValue(storeTitle)
                                val data = Post_s(password,name, pnum, role,storeTitle,marketTitle,token_r)
                                val info = data.toMap_s()
                                DatabaseReference.child("userDB").child(uid.toString()).setValue(info)
                                Toast.makeText(requireContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                                (activity as signup_sellect).finish()

                            } else { // 아이디 생성이 실패했을 경우
                                Toast.makeText(requireContext(), "이미 가입된 이메일이거나 잘못된 이메일입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }

        mPickTimeBt_s.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView_s.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mPickTimeBt_e.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView_e.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        view.buttonCtgr.setOnClickListener {
            (activity as signup_sellect).showCtgr()
        }

        view.buttonSelectMarket.setOnClickListener {
            (activity as signup_sellect).showMarket()
        }

        return view
    }
}