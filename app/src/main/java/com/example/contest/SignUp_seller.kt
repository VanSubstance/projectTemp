package com.example.contest

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.util.*
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_seller.*
import kotlinx.android.synthetic.main.signup_main.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class SignUp_seller : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG: String = "SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_seller)

        val mEmailText = findViewById<EditText>(R.id.seller_id);
        val mPasswordText = findViewById<EditText>(R.id.seller_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.seller_passck);
        val mName = findViewById<EditText>(R.id.seller_name);
        val mAddress = findViewById<EditText>(R.id.seller_address)
        val mPnum = findViewById<EditText>(R.id.seller_Phone_number)
        val M_name=findViewById<EditText>(R.id.MarketName)
        val mPickTimeBt_s = findViewById<Button>(R.id.picktimebtn_S)
        val textView_s     = findViewById<TextView>(R.id.time_s)
        val textView_c=findViewById<TextView>(R.id.seller_cat)

        val mPickTimeBt_e = findViewById<Button>(R.id.picktimebtn_E)
        val textView_e     = findViewById<TextView>(R.id.time_E)

        auth = FirebaseAuth.getInstance()

        btn_register_seller.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("users")
            if (mEmailText.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(mEmailText.text.toString(), mPasswordText.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                val email=mEmailText.text.toString()
                                val password=mPasswordText.text.toString()
                                val name=mName.text.toString()
                                val address=mAddress.text.toString()
                                val pnum=mPnum.text.toString()
                                val m_name=M_name.text.toString()
                                val data=Post_seller(email,password,name,address,pnum,m_name,textView_s.text,textView_e.text,seller_cat.text.trim())
                                val info =data.seller_toMap()
                                databaseReference.child("SellerInfo").child(pnum).setValue(info)
                                finish()
                                overridePendingTransition(0, 0)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                        baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                ).show()
                                mEmailText?.setText("")
                                mPasswordText?.setText("")
                                et_id.requestFocus()
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
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mPickTimeBt_e.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView_e.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        btn_category.setOnClickListener{
            showCat()
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