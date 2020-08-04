package com.example.contest

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_seller.*
import kotlinx.android.synthetic.main.sign_up_seller.view.*
import java.text.SimpleDateFormat
import java.util.*

class signupSeller : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth : FirebaseAuth?= null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sign_up_seller, container, false)

        val msID = view.seller_id
        val mPasswordText = view.seller_pass
        val mPasswordcheckText = view.seller_passck
        val mName = view.seller_name
        val mPnum = view.seller_Phone_number

        val mPickTimeBt_s = view.picktimebtn_S
        val textView_s = view.time_s

        val mPickTimeBt_e = view.picktimebtn_E
        val textView_e = view.time_E

        view.btn_register_seller.setOnClickListener {
            val DatabaseReference = database.reference
            auth = FirebaseAuth.getInstance()

            if (msID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(requireContext(), "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else if (location.text.equals("시장 이름")) {
                Toast.makeText(requireContext(), "시장을 선택해주세요", Toast.LENGTH_SHORT).show()
            } else if (storeName.text.equals("")) {
                Toast.makeText(requireContext(), "매장 명을 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (time_E.text.equals("개점 시간") || time_s.text.equals("폐점 시간")) {
                Toast.makeText(requireContext(), "개점, 폐점 시간을 정해주세요", Toast.LENGTH_SHORT).show()
            } else if (seller_cat.text.equals("카테고리") || seller_cat.text.equals("")) {
                Toast.makeText(requireContext(), "카테고리를 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                val ID = msID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val marketTitle = location.text.toString()
                val storeTitle = storeName.text.toString()
                val storeCtgr = seller_cat.text.toString()
                val timeOpen = time_s.text.toString()
                val timeClose = time_E.text.toString()
                auth?.createUserWithEmailAndPassword(ID, password)
                        ?.addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // 아이디 생성이 완료되었을 때
                                val user = auth?.getCurrentUser()
                                val uid=user?.uid
                                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(uid.toString()).child("storeName").setValue(storeTitle)
                                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(uid.toString()).child("ctgr").setValue(storeCtgr)
                                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(uid.toString()).child("timeOpen").setValue(timeOpen)
                                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(uid.toString()).child("timeClose").setValue(timeClose)
                                val data = Post_s(name, pnum, role,marketTitle)
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