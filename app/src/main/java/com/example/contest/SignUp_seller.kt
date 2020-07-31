package com.example.contest

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.selectlocation.*
import kotlinx.android.synthetic.main.sign_up_seller.*
import java.text.SimpleDateFormat

class SignUp_seller : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_seller)

        val mEmailText = findViewById<EditText>(R.id.seller_id);
        val mPasswordText = findViewById<EditText>(R.id.seller_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.seller_passck);
        val mName = findViewById<EditText>(R.id.seller_name);
        val mPnum = findViewById<EditText>(R.id.seller_Phone_number)


        val mPickTimeBt_s = findViewById<Button>(R.id.picktimebtn_S)
        val textView_s = findViewById<TextView>(R.id.time_s)

        val mPickTimeBt_e = findViewById<Button>(R.id.picktimebtn_E)
        val textView_e = findViewById<TextView>(R.id.time_E)


        btn_register_seller.setOnClickListener {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val DatabaseReference = database.reference
            if (mEmailText.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                val ID = mEmailText.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val data = Post(ID, password, name, pnum, role)
                val info = data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
                finish()
                overridePendingTransition(0, 0)
            }

        }

        mPickTimeBt_s.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView_s.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mPickTimeBt_e.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView_e.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        btn_category.setOnClickListener {
            showCat()
        }
        picklocation.setOnClickListener {

            var m_List= arrayListOf<locationInfo>(locationInfo("삼성"))
            val l_Adapter=locationAdapter(this,m_List)
            listView.adapter=l_Adapter

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.selectlocation, null)
            val dialogText=dialogView.findViewById<EditText>(R.id.editSearch)

            builder.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->
                        location.text = dialogText.text.toString()
                        /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }
                    .show()

        }
    }
    private fun showCat(){
        lateinit var dialog: AlertDialog
        var arrayCat= arrayOf("정육점","잡화점","채소가게","생선가게")
        // Initialize a boolean array of checked items
        val arrayChecked = booleanArrayOf(false,false,false,false)
        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)
        // Set a title for alert dialog
        builder.setTitle("Choose category")
        builder.setMultiChoiceItems(arrayCat, arrayChecked, {dialog,which,isChecked->
            // Update the clicked item checked status
            arrayChecked[which] = isChecked

            // Get the clicked item
            val Cat = arrayCat[which]

            // Display the clicked item text
            toast("$Cat clicked.")
        })
        builder.setPositiveButton("OK") { _, _ ->
            // Do something when click positive button
            seller_cat.text = ""
            for (i in 0 until arrayCat.size) {
                val checked = arrayChecked[i]
                if (checked) {
                    seller_cat.text = "${seller_cat.text}  ${arrayCat[i]}"
                }
            }
        }
        dialog = builder.create()
        dialog.show()
    }

    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}