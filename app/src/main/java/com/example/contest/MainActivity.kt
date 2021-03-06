package com.example.contest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val TAG = "FirebaseService"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread(Runnable {
            while (!Thread.interrupted()) try {
                Thread.sleep(1000)
                runOnUiThread {
                    if (SimpleDateFormat("HH:mm:ss").format(Date()) == "00:00:00") {
                        pushProducts()
                    }
                }
            } catch (e: InterruptedException) {
            }
        }).start()

        buttonLogin.setOnClickListener {
            loginUserId(textUserID.text.toString(), textUserPW.text.toString())

        }
        buttonSignUp.setOnClickListener {
            val SignUp_user = Intent(this, signup_sellect::class.java)
            startActivity(SignUp_user)

            // 액티비티 전환 애니메이션 설정
            overridePendingTransition(
                R.anim.slide_in_right_to_left,
                R.anim.slide_out_right_to_left
            );

        }


    }

    fun loginUserId(email: String, password: String) {
        val DatabaseReference = database.reference

        val sellerUI = Intent(this, sellerUIMain::class.java)
        val buyerUI = Intent(this, buyerUIMain::class.java)
        if (textUserID.text.toString().equals("") ) {
            alertEmail.isVisible = true
            alertPW.isVisible = false
            alertError.isVisible = false
        } else if (textUserPW.text.toString().equals("")) {
            alertEmail.isVisible = false
            alertPW.isVisible = true
            alertError.isVisible = false
        } else {
            auth = FirebaseAuth.getInstance()
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) { // 로그인 성공 시 이벤트 발생
                        DatabaseReference.addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                if (p0.child("userDB").child(auth?.uid.toString())
                                        .child("role").value == "seller"
                                ) {
                                    userInfo.id = auth?.uid.toString()
                                    userInfo.pw = password
                                    userInfo.name = p0.child("userDB").child(auth?.uid.toString())
                                        .child("Name").value as String
                                    userInfo.role = p0.child("userDB").child(auth?.uid.toString())
                                        .child("role").value as String
                                    userInfo.pNum = p0.child("userDB").child(auth?.uid.toString())
                                        .child("pNum").value as String
                                    userInfo.storeTitle = p0.child("userDB").child(auth?.uid.toString())
                                        .child("storeTitle").value as String
                                    userInfo.ctgrForSeller =
                                        p0.child("storeDB").child(auth?.uid.toString())
                                            .child("ctgr").value.toString()
                                    userInfo.timeOpen =
                                        p0.child("storeDB").child(auth?.uid.toString())
                                            .child("timeOpen").value.toString()
                                    userInfo.timeClose =
                                        p0.child("storeDB").child(auth?.uid.toString())
                                            .child("timeClose").value.toString()
                                    userInfo.marketTitle= p0.child("userDB").child(auth?.uid.toString()).child("marketTitle").value.toString()
                                    startActivity(sellerUI)
                                } else {
                                    userInfo.id = auth?.uid.toString()
                                    userInfo.pw = password
                                    userInfo.name = p0.child("userDB").child(auth?.uid.toString())
                                        .child("Name").value as String
                                    userInfo.nick = p0.child("userDB").child(auth?.uid.toString())
                                        .child("nick").value as String
                                    userInfo.role = p0.child("userDB").child(auth?.uid.toString())
                                        .child("role").value as String
                                    userInfo.pNum = p0.child("userDB").child(auth?.uid.toString())
                                        .child("pNum").value as String
                                    userInfo.marketTitle = p0.child("userDB").child(auth?.uid.toString())
                                        .child("관심시장").value as String
                                    if (p0.child("userDB").child(auth?.uid.toString())
                                        .child("ctgr").child("생선가게").value as Boolean) {
                                        userInfo.ctgrForBuyer.add("생선가게")
                                    }
                                    if (p0.child("userDB").child(auth?.uid.toString())
                                            .child("ctgr").child("정육점").value as Boolean) {
                                        userInfo.ctgrForBuyer.add("정육점")
                                    }
                                    if (p0.child("userDB").child(auth?.uid.toString())
                                            .child("ctgr").child("채소가게").value as Boolean) {
                                        userInfo.ctgrForBuyer.add("채소가게")
                                    }
                                    if (p0.child("userDB").child(auth?.uid.toString())
                                            .child("ctgr").child("잡화점").value as Boolean) {
                                        userInfo.ctgrForBuyer.add("잡화점")
                                    }
                                    if (p0.child("userDB").child(auth?.uid.toString())
                                            .child("ctgr").child("완제품").value as Boolean) {
                                        userInfo.ctgrForBuyer.add("완제품")
                                    }
                                    startActivity(buyerUI)
                                }
                            }
                        })
                    } else {
                        alertEmail.isVisible = false
                        alertPW.isVisible = false
                        alertError.isVisible = true
                    }
                }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}


// 이거 한 이유 설명좀 !! 
private fun resetDB() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var data = database.getReference("marketDB")
    data.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onDataChange(p0: DataSnapshot) {
            for (market in p0.children) {
                var title = market.child("marketTitle").value.toString()
                var addr = market.child("address").value.toString()
                var lat = market.child("latitude").value.toString()
                var lon = market.child("longitude").value.toString()
                data.child(title).child("marketTitle").setValue(title)
                data.child(title).child("address").setValue(addr)
                data.child(title).child("latitude").setValue(lat)
                data.child(title).child("longitude").setValue(lon)
                data.child(market.key.toString()).removeValue()
            }
        }

    })
}

private fun pushProducts() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val historyDB = database.getReference("productDB")
    var todayDB = database.getReference("productTodayDB")
    todayDB.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onDataChange(p0: DataSnapshot) {
            for (element in p0.children) {
                var product = productElement()
                product.setFromDb(element)
                product.soldDate = SimpleDateFormat("yyyyMMdd").format(Date()).toString()
                historyDB.child(SimpleDateFormat("yyyyMMdd").format(Date()).toString())
                    .child(product.productId).child(userInfo.id).setValue(product.toMap())
                todayDB.child(element.key.toString()).removeValue()
            }
        }
    })
}

// 앱 실행부터 종료때까지 유저의 정보를 저장해두는 오브젝트
// 자바의 static 개념으로 보면 됨
object userInfo {
    var id: String = ""
    var pw: String = ""
    var name : String = ""
    var nick: String = ""
    var pNum: String = ""
    var role: String = ""
    var storeTitle : String = ""
    var ctgrForSeller: String = ""
    var ctgrForBuyer : ArrayList<String> = arrayListOf()
    var timeOpen = ""
    var timeClose = ""
    var marketTitle:String=""
}

object backPressedTime {
    var flag: Long = 0
}

object currentCondition {
    // 시장
    var marketTitle: String = ""

    // 완제품 or 재료
    var ctgr01: String = ""

    // 육류, 해산물, 등등
    var ctgr02: String = ""
}

object currentProductElement {
    var currentProductElement = productElement()
    var function = ""
}
