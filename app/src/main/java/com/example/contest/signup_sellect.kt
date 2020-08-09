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
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.signup_sellect.*
import kotlinx.android.synthetic.main.signup_seller.*
import kotlinx.android.synthetic.main.signup_seller_ctgr.view.*
import kotlinx.android.synthetic.main.signup_seller_market.view.*

class signup_sellect : AppCompatActivity() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ft = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_sellect)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val mID = textId
        val mPasswordText = textPw
        val mPasswordcheckText = textPwCheck
        val mName = textName


        buttonSignupBuyer.setOnClickListener{
            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                textAlert.isVisible = true
                textAlert.setText("※ 이메일 또는 비밀번호를 반드시 입력하세요!")
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                textAlert.isVisible = true
                textAlert.setText("※ 비밀번호가 일치하지 않습니다!")
            } else if (mName.text.toString().length == 0) {
                textAlert.isVisible = true
                textAlert.setText("※ 이름을 확인해주세요!")
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
                overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
            }
        }

        buttonSignupSeller.setOnClickListener{
            if (mID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                textAlert.isVisible = true
                textAlert.setText("※ 이메일 또는 비밀번호를 반드시 입력하세요!")
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                textAlert.isVisible = true
                textAlert.setText("※ 비밀번호가 일치하지 않습니다!")
            } else if (mName.text.toString().length == 0) {
                textAlert.isVisible = true
                textAlert.setText("※ 이름을 확인해주세요!")
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
                ft.setCustomAnimations(R.anim.slide_in_right_to_left,R.anim.slide_out_right_to_left)
            }
        }
    }

    // 판매자 카테고리 보여주기
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

    // 판매자 시장 리스트 보여주기
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

                val adapter = signupMarketAdapter()
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

    // 구매자 시장 지도 보여주기
    fun showMap() {
        //ft.replace(바꿀 프레임, signupBuyerMap()).commit()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left_to_right,R.anim.slide_out_left_to_right)
    }


}

class marketElement {
    var title : String = ""
    var addr : String = ""
}