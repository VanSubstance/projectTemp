package com.example.contest

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.signup_seller.*
import kotlinx.android.synthetic.main.signup_seller.view.*
import java.text.SimpleDateFormat
import java.util.*

class signupSeller : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signup_seller, container, false)

        val msID = view.textId
        val mPasswordText = view.textPw
        val mPasswordcheckText = view.textPwCheck
        val mName = view.textName
        val mPnum = view.textPNum

        val mPickTimeBt_s = view.buttonTimepickOpen
        val textView_s = view.textTimeOpen

        val mPickTimeBt_e = view.buttonTimepickClose
        val textView_e = view.textTimeClose

        view.buttonConfirm.setOnClickListener {
            val DatabaseReference = database.reference
            if (msID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(requireContext(), "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else if (textMarketTitle.text.equals("시장 이름")) {
                Toast.makeText(requireContext(), "시장을 선택해주세요", Toast.LENGTH_SHORT).show()
            } else if (textStoreTitle.text.equals("")) {
                Toast.makeText(requireContext(), "매장 명을 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (textTimeClose.text.equals("개점 시간") || textTimeOpen.text.equals("폐점 시간")) {
                Toast.makeText(requireContext(), "개점, 폐점 시간을 정해주세요", Toast.LENGTH_SHORT).show()
            } else if (textCtgr.text.equals("카테고리") || textCtgr.text.equals("")) {
                Toast.makeText(requireContext(), "카테고리를 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                val ID = msID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val marketTitle = textMarketTitle.text.toString()
                val storeTitle = textStoreTitle.text.toString()
                val storeCtgr = textCtgr.text.toString()
                val timeOpen = textTimeOpen.text.toString()
                val timeClose = textTimeClose.text.toString()
                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(storeTitle).child("seller").setValue(ID)
                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(storeTitle).child("textStoreTitle").setValue(storeTitle)
                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(storeTitle).child("ctgr").setValue(storeCtgr)
                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(storeTitle).child("timeOpen").setValue(timeOpen)
                DatabaseReference.child("marketInfo").child(marketTitle).child("store").child(storeTitle).child("timeClose").setValue(timeClose)
                val data = Post(ID, password, name, pnum, role)
                val info = data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
                DatabaseReference.child("userDB").child(ID).child("marketTitle").setValue(marketTitle)
                DatabaseReference.child("userDB").child(ID).child("store").child("seller").setValue(ID)
                DatabaseReference.child("userDB").child(ID).child("store").child("textStoreTitle").setValue(storeTitle)
                DatabaseReference.child("userDB").child(ID).child("store").child("ctgr").setValue(storeCtgr)
                DatabaseReference.child("userDB").child(ID).child("store").child("timeOpen").setValue(timeOpen)
                DatabaseReference.child("userDB").child(ID).child("store").child("timeClose").setValue(timeClose)
                Toast.makeText(requireContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                (activity as signup_sellect).finish()
            }
        }
        view.buttonCertifyId.setOnClickListener{
            if (msID.text.toString().equals("")) {
                Toast.makeText(requireContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                val database :FirebaseDatabase= FirebaseDatabase.getInstance()
                val myref: DatabaseReference =database.getReference("userDB")
                myref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        var err = 0
                        for (account in p0.children) {
                            if(msID.text.toString().equals(account.key.toString())) {
                                err = 1
                                Toast.makeText(requireContext(), "존재하는 아이디 입니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (err == 0) {
                            Toast.makeText(requireContext(), "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                        }
                        err = 0
                    }
                })
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