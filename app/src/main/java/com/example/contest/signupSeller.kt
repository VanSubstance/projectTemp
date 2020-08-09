package com.example.contest

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.signup_seller.*
import kotlinx.android.synthetic.main.signup_seller.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class signupSeller : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth : FirebaseAuth?= null
    private val TAG = "FirebaseService"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signup_seller, container, false)

        val mID = arguments?.getString("ID")!!
        val mPasswordText = arguments?.getString("pw")!!
        val mName = arguments?.getString("name")!!

        val mPnum = view.textPNum
        val textMarketTitle=view.textMarketTitle
        val staticStoreTitle = view.staicStoreTitle
        val staticSpinnerDate=view.staticSpinnerDate


        val mPickTimeBt_s = view.buttonTimepickOpen
        val textView_s = view.textTimeOpen

        val mPickTimeBt_e = view.buttonTimepickClose
        val textView_e = view.textTimeClose

        var viewAlertList : ArrayList<TextView> = arrayListOf()
        viewAlertList.add(view.textAlertMarket)
        viewAlertList.add(view.textAlertStoreTitle)
        viewAlertList.add(view.textAlertPNum)
        viewAlertList.add(view.textAlertOpenTime)
        viewAlertList.add(view.textAlertCloseTime)
        viewAlertList.add(view.textAlertCtgr)

        view.buttonConfirm.setOnClickListener {
            val DatabaseReference = database.reference
            auth = FirebaseAuth.getInstance()

            if (textMarketTitle.text.equals("시장 이름") || textMarketTitle.text.equals("")) {
                for (alert in viewAlertList) {
                    alert.isVisible = false
                }
                view.textAlertMarket.isVisible = true
                view.textAlertMarket.setText("시장을 선택해주세요!")
            } else if (staicStoreTitle.text.equals("매장명") || staicStoreTitle.text.equals("")) {
                for (alert in viewAlertList) {
                    alert.isVisible = false
                }
                view.textAlertStoreTitle.isVisible = true
                view.textAlertStoreTitle.setText("매장명을 확인해주세요!")
            } else if (mPnum.text.toString().equals("") || mPnum.text.toString().equals("핸드폰 번호") || mPnum.text.toString().length != 11) {
                for (alert in viewAlertList) {
                    alert.isVisible = false
                }
                view.textAlertPNum.isVisible = true
                view.textAlertPNum.setText("핸드폰 번호를 확인해주세요!")
            } else if (textTimeOpen.text.equals("") || textTimeOpen.text.equals("개점 시간")) {
                for (alert in viewAlertList) {
                    alert.isVisible = false
                }
                view.textAlertOpenTime.isVisible = true
                view.textAlertOpenTime.setText("개점 시간을 정해주세요!")
            } else if (textTimeClose.text.equals("폐점 시간") || textTimeClose.text.equals("")) {
                for (alert in viewAlertList) {
                    alert.isVisible = false
                }
                view.textAlertCloseTime.isVisible = true
                view.textAlertCloseTime.setText("폐점 시간을 정해주세요!")
            } else if (staticSpinnerDate.text.equals("카테고리") || staticSpinnerDate.text.equals("")) {
                for (alert in viewAlertList) {
                    alert.isVisible = false
                }
                view.textAlertCtgr.isVisible = true
                view.textAlertCtgr.setText("카테고리를 정해주세요!")
            } else{
                val ID = mID
                val password = mPasswordText
                val name = mName
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val marketTitle = textMarketTitle.text.toString()
                val storeTitle = staticStoreTitle.text.toString()
                val storeCtgr = staticSpinnerDate.text.toString()
                val timeOpen =textView_s.text.toString()
                val timeClose = textView_e.text.toString()

                auth?.createUserWithEmailAndPassword(ID, password)
                        ?.addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // 아이디 생성이 완료되었을 때
                                val user = auth?.getCurrentUser()
                                val uid=user?.uid

                                DatabaseReference.child("marketDB").child(marketTitle).child("store").child(uid.toString()).setValue("")
                                DatabaseReference.child("storeDB").child(uid.toString()).child("ctgr").setValue(storeCtgr)
                                DatabaseReference.child("storeDB").child(uid.toString()).child("timeOpen").setValue(timeOpen)
                                DatabaseReference.child("storeDB").child(uid.toString()).child("timeClose").setValue(timeClose)
                                DatabaseReference.child("storeDB").child(uid.toString()).child("title").setValue(storeTitle)
                                val data = Post_s(password,name, pnum, role,storeTitle,marketTitle)
                                val info = data.toMap_s()
                                pushToken(uid.toString())
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