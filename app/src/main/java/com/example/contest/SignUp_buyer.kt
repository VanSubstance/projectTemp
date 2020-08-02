package com.example.contest

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_buyer.*


class SignUp_buyer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_buyer)

        val mID = findViewById<EditText>(R.id.et_id);
        val mPasswordText = findViewById<EditText>(R.id.et_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.et_passck);
        val mName = findViewById<EditText>(R.id.et_name);
        val mPnum = findViewById<EditText>(R.id.et_Phone_number)

        btn_register.setOnClickListener {
            val database:FirebaseDatabase= FirebaseDatabase.getInstance()
            val DatabaseReference=database.reference

            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                val ID=mID.text.toString()
                val password=mPasswordText.text.toString()
                val name=mName.text.toString()
                val pnum=mPnum.text.toString()
                val role:String="buyer"
                val data=Post(ID,password,name,pnum,role)
                val info =data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
                finish()
                overridePendingTransition(0, 0)
            }
        }
        validateButton.setOnClickListener{
            val database :FirebaseDatabase= FirebaseDatabase.getInstance()
                    val myref:DatabaseReference=database.getReference("userDB")
                    myref.addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val child = p0.children.iterator();
                            while(child.hasNext()){
                                if(mID.text.toString().equals(child.next().key)) {
                                    Toast.makeText(getApplicationContext(), "존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();

                                }
                            }
                }
            })
        }
            }
        }




