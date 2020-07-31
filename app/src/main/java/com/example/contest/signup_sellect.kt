package com.example.contest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.signup_sellect.*

class signup_sellect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_sellect)

        signup_buyer.setOnClickListener{
            val SignUp_buyer= Intent(this,SignUp_buyer::class.java)
            startActivity(SignUp_buyer)
        }
        signup_seller.setOnClickListener{
            val SignUp_seller=Intent(this,SignUp_seller::class.java)
            startActivity(SignUp_seller)
        }
    }
}