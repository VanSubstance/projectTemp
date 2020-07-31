package com.example.contest

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_seller.*
import java.text.SimpleDateFormat

class SignUp_seller : AppCompatActivity() {
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
        setContentView(R.layout.sign_up_seller)

        val mEmailText = findViewById<EditText>(R.id.seller_id);
        val mPasswordText = findViewById<EditText>(R.id.seller_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.seller_passck);
        val mName = findViewById<EditText>(R.id.seller_name);
        val mPnum = findViewById<EditText>(R.id.seller_Phone_number)


        val mPickTimeBt_s = findViewById<Button>(R.id.picktimebtn_S)
        val textView_s     = findViewById<TextView>(R.id.time_s)

        val mPickTimeBt_e = findViewById<Button>(R.id.picktimebtn_E)
        val textView_e     = findViewById<TextView>(R.id.time_E)


        btn_register_seller.setOnClickListener {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val DatabaseReference=database.reference
            if (mEmailText.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                val ID=mEmailText.text.toString()
                val password=mPasswordText.text.toString()
                val name=mName.text.toString()
                val pnum=mPnum.text.toString()
                val role:String="seller"
                val data=Post(ID,password,name,pnum,role)
                val info =data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
                finish()
                overridePendingTransition(0, 0)
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
        picklocation.setOnClickListener{
            val builder=AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.sellectlocation, null)
            editSearch = findViewById<View>(R.id.editSearch) as EditText
            listView = findViewById<View>(R.id.listView) as ListView
            var location=findViewById<TextView>(R.id.location)

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
            builder.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->
                        location.text = editSearch!!.text.toString()
                        /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }
                    .show()

        }

    }


    fun settingList() {
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