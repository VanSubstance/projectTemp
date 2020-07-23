package com.example.contest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.signup_main.*


class SignUp : AppCompatActivity() {
    // [START post_class]

    @IgnoreExtraProperties
    data class Post(
            var email: String? = "",
            var password: String? = "",
            var name: String? = "",
            var address: String? = "",
            var pnum:String ?="",
            var starCount: Int = 0,
            var stars: MutableMap<String, Boolean> = HashMap()
    ) {

        // [START post_to_map]
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                    "email" to email,
                    "password" to password,
                    "name" to name,
                    "address" to address,
                    "pnum" to pnum,
                    "starCount" to starCount,
                    "stars" to stars
            )
        }
        // [END post_to_map]
    }

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val TAG:String="SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_main)

        val database:FirebaseDatabase= FirebaseDatabase.getInstance()
        val myref:DatabaseReference=database.getReference("UserInfo")
        val mEmailText =findViewById<EditText>(R.id.et_id);
        val mPasswordText = findViewById<EditText>(R.id.et_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.et_passck);
        val mName = findViewById<EditText>(R.id.et_name);
        val mAddress=findViewById<EditText>(R.id.et_name)
        val mPnum=findViewById<EditText>(R.id.et_Phone_number)

        auth = FirebaseAuth.getInstance()

        btn_register_seller.setOnClickListener{
            val intent= Intent(this,signUp_seller::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener{
            if (mEmailText.text.toString().length == 0 || mPasswordText.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(mPasswordcheckText.text.toString()!=mPasswordText.text.toString()){
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(mEmailText.text.toString(), mPasswordText.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                val info= mapOf("email" to mEmailText.text.toString(),"password" to mPasswordText.text.toString(), "name" to mName.text.toString(),"address" to mAddress.text.toString(),"Pnum" to mPnum.text.toString())
                                val ID=mEmailText.text.toString()
                                myref.push().setValue(info)
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
        validateButton.setOnClickListener{

        }

    }
    private fun writeNewPost(email: String, password:String, name: String, address: String, pnum:String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = database.child("posts").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        val post = Post(email, password, name, address,pnum)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
                "/posts/$key" to postValues,
                "/user-posts/$email/$key" to postValues
        )

        database.updateChildren(childUpdates)
    }

}


