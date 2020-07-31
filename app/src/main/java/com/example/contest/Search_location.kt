package com.example.contest


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*


class Search_location : AppCompatActivity() {
    private var list // 데이터를 넣은 리스트변수
            : MutableList<String>? = null
    private var listView // 검색을 보여줄 리스트변수
            : ListView? = null
    private var editSearch // 검색어를 입력할 Input 창
            : EditText? = null
    private var adapter // 리스트뷰에 연결할 아답터
            : SearchAdapter? = null
    private var arraylist: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editSearch = findViewById<View>(R.id.editSearch) as EditText
        listView = findViewById<View>(R.id.listView) as ListView

        // 리스트를 생성한다.
        list = ArrayList()

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList()

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = ArrayList()
        arraylist!!.addAll(list as ArrayList<String>)

        // 리스트에 연동될 아답터를 생성한다.
        adapter = SearchAdapter(list as ArrayList<String>, this)

        // 리스트뷰에 아답터를 연결한다.
        listView!!.adapter = adapter

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                val text = editSearch!!.text.toString()
                search(text)
            }
        })
    }

    // 검색을 수행하는 메소드
    fun search(charText: String) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list!!.clear()

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length == 0) {
            list!!.addAll(arraylist!!)
        } else {
            // 리스트의 모든 데이터를 검색한다.
            for (i in arraylist!!.indices) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist!![i].toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list!!.add(arraylist!![i])
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter!!.notifyDataSetChanged()
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private fun settingList() {
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myref: DatabaseReference =database.getReference("MarketInfo")
        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val child = p0.children.iterator();
                for(Data in p0.children){
                    val loc=Data.child("전통시장명").value.toString()
                    list!!.add(loc)

                }
                }
            }
        )
    }
}