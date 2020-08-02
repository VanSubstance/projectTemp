package com.example.contest

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_seller.*
import kotlinx.android.synthetic.main.sign_up_seller.view.*
import java.text.SimpleDateFormat
import java.util.*

class signupSeller : Fragment() {

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
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val DatabaseReference = database.reference
            if (msID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(requireContext(), "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                val ID = msID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val data = Post(ID, password, name, pnum, role)
                val info = data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
            }
        }
        view.validateButton_seller.setOnClickListener{
            val database :FirebaseDatabase= FirebaseDatabase.getInstance()
            val myref: DatabaseReference =database.getReference("userDB")
            myref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val child = p0.children.iterator();
                    while(child.hasNext()){
                        if(msID.text.toString().equals(child.next().key)) {
                            Toast.makeText(requireContext(), "존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(requireContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            })
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