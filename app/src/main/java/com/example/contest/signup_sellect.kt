package com.example.contest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.signup_seller.*
import kotlinx.android.synthetic.main.signup_sellect.*
import kotlinx.android.synthetic.main.signup_seller_ctgr.view.*
import kotlinx.android.synthetic.main.signup_seller_market.view.*

class signup_sellect : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_sellect)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val mID = textId
        val mPasswordText = textPw
        val mPasswordcheckText = textPwCheck
        val mName = textName

        val ft = supportFragmentManager.beginTransaction()

        buttonSignupBuyer.setOnClickListener{
            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else if (mName.text.toString().length == 0) {
                Toast.makeText(this, "이름을 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val ID = mID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val frag = signupBuyer()
                var bundle = Bundle(3)
                bundle.putString("ID", ID)
                bundle.putString("pw", password)
                bundle.putString("name", name)
                frag.setArguments(bundle)
                main_frame.isVisible = true
                layoutRoleSelection.isVisible = false
                ft.replace(R.id.main_frame, frag).commit()
            }
        }
        buttonSignupSeller.setOnClickListener{
            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else if (mName.text.toString().length == 0) {
                Toast.makeText(this, "이름을 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val ID = mID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val frag = signupSeller()
                var bundle = Bundle(3)
                bundle.putString("ID", ID)
                bundle.putString("pw", password)
                bundle.putString("name", name)
                frag.setArguments(bundle)
                main_frame.isVisible = true
                layoutRoleSelection.isVisible = false
                ft.replace(R.id.main_frame, frag).commit()
            }
        }
    }

    fun showCtgr() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.signup_seller_ctgr, null)

        val alertDialog = AlertDialog.Builder(this)
            .create()

        var checkBoxes : ArrayList<CheckBox> = arrayListOf()
        checkBoxes.add(view.checkCtgrMeat)
        checkBoxes.add(view.checkCtgrFish)
        checkBoxes.add(view.checkCtgrVegetable)
        checkBoxes.add(view.checkCtgrGeneral)
        checkBoxes.add(view.checkCtgrEtc)
        var settledCtgr : String = ""

        for (checkBox in checkBoxes) {
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                // 하나만 고를 수 있게끔
                if (isChecked) {
                    settledCtgr = checkBox.text.toString()
                    for (box in checkBoxes) {
                        if (box != checkBox) {
                            box.isChecked = false
                        }
                    }
                }
                // 기타 체크박스 체크했을때만 보이게
                if (checkBoxes[4].isChecked) {
                    view.staticEtc.isVisible = false
                    view.textEtc.isVisible = true
                } else {
                    view.staticEtc.isVisible = true
                    view.textEtc.isVisible = false
                }
            }
        }
        view.buttonConfirm.setOnClickListener {
            if (checkBoxes[4].isChecked) {
                settledCtgr = view.textEtc.text.toString()
            }
            staticSpinnerDate.setText(settledCtgr)
            alertDialog.dismiss()
        }
        view.buttonCancel.setOnClickListener {
            alertDialog.cancel()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }

    fun showMarket() {
        var marketList : ArrayList<marketElement> = arrayListOf()
        var data = database.getReference("marketDB")
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                val alertDialog = AlertDialog.Builder(this@signup_sellect)
                    .create()

                for (market in p0.children) {
                    var marketEl = marketElement()
                    marketEl.title = market.child("marketTitle").value.toString()
                    marketEl.addr = market.child("address").value.toString()
                    marketList.add(marketEl)
                }

                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.signup_seller_market, null)
                alertDialog.setView(view)
                alertDialog.show()

                val adapter = signupSellerMarketAdapter()
                val listMarket = view.listMarket
                listMarket.adapter = adapter
                for (marketEl in marketList) {
                    adapter.addItem(marketEl)
                }
                listMarket.onItemClickListener = object : AdapterView.OnItemClickListener {
                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        var item = parent?.getItemAtPosition(position) as marketElement
                        textMarketTitle.setText(item.title)
                        textMarketAddress.setText(item.addr)
                        alertDialog.cancel()
                    }
                }

                view.buttonSearchMarket.setOnClickListener {
                    adapter.dropItem()
                    for (market in marketList) {
                        var keyword = view.textMarketTitle.text.toString()
                        if (keyword == "" || market.title.contains(view.textMarketTitle.text.toString())) {
                            adapter.addItem(market)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    listMarket.adapter = adapter
                }


            }
        })

    }
    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}

class marketElement {
    var title : String = ""
    var addr : String = ""
}