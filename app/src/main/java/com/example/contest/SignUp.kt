package com.example.contest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.signup_main.*
import kotlin.collections.HashMap as KotlinCollectionsHashMap


// [START blog_user_class]
/*
@IgnoreExtraProperties
data class Seller(
        var userID: String? = "",
        var passworID: String? = "",
        var userName: String?="",
        var address: String?="",
        var phone_number: Int?
)

 */

class SubActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val TAG:String="CreateAccount"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_main)
        database = Firebase.database.reference
        auth=FirebaseAuth.getInstance()

        val email=findViewById<EditText>(R.id.et_id)
        val password=findViewById<EditText>(R.id.et_pass)
        val passwordAk=findViewById<EditText>(R.id.et_passck)
        val Uname=findViewById<EditText>(R.id.et_name)
        val address=findViewById<EditText>(R.id.et_address)
        val Phone_n=findViewById<EditText>(R.id.et_Phone_number)

        btn_register.setOnClickListener {
            //Log.d(TAG, "Data: " + email.text + password.text)
            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(password.text.toString()!=passwordAk.text.toString()){
                Toast.makeText(this, "비번이랑 비번확인이랑 달라용", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                val map= kotlin.collections.HashMap<String, Any>()
                                map["email"] = email.toString()
                                map["password"] = password.toString()
                                map["user_name"] = Uname.toString()
                                map["address"] = address.toString()
                                map["phone_number"] = Phone_n.toString()
                                database.child(email.toString()).setValue(map)

                                val intent = Intent(this@SubActivity, MainActivity::class.java)
                                startActivity(intent);
                                finish()
                                Toast.makeText(this, "회원가입 완료했어", Toast.LENGTH_SHORT).show()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                        baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                ).show()

                                email?.setText("")
                                password?.setText("")
                                email.requestFocus()
                            }
                        }
            }
        }



    }
}


