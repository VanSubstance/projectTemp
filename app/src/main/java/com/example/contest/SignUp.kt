package com.example.contest


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup_main.*


class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG: String = "SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_main)
        val mEmailText = findViewById<EditText>(R.id.et_id);
        val mPasswordText = findViewById<EditText>(R.id.et_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.et_passck);
        val mName = findViewById<EditText>(R.id.et_name);
        val mAddress = findViewById<EditText>(R.id.et_name)
        val mPnum = findViewById<EditText>(R.id.et_Phone_number)

        auth = FirebaseAuth.getInstance()
        btn_register.setOnClickListener {
            val database:FirebaseDatabase= FirebaseDatabase.getInstance()
            val DatabaseReference=database.reference
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
                                val Character="소비자"
                                val data=Post(email,password,name,address,pnum,Character)
                                val info =data.toMap()
                                DatabaseReference.child("BuyerInfo").child(pnum).setValue(info)
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
    }
}


