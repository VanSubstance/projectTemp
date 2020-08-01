package com.example.contest

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_seller.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.View


class SignUp_seller : AppCompatActivity() {
    private val TAG_TEXT = "text"

    var dialogItemList: List<Map<String, Any>>? = null

    var text = arrayOf("포도", "체리", "수박")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_seller)
        val msID = findViewById<EditText>(R.id.seller_id);
        val mPasswordText = findViewById<EditText>(R.id.seller_pass);
        val mPasswordcheckText = findViewById<EditText>(R.id.seller_passck);
        val mName = findViewById<EditText>(R.id.seller_name);
        val mPnum = findViewById<EditText>(R.id.seller_Phone_number)


        val mPickTimeBt_s = findViewById<Button>(R.id.picktimebtn_S)
        val textView_s = findViewById<TextView>(R.id.time_s)

        val mPickTimeBt_e = findViewById<Button>(R.id.picktimebtn_E)
        val textView_e = findViewById<TextView>(R.id.time_E)


        btn_register_seller.setOnClickListener {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val DatabaseReference = database.reference
            if (msID.text.toString().length == 0 || mPasswordText.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (mPasswordcheckText.text.toString() != mPasswordText.text.toString()) {
                Toast.makeText(this, "password가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } else {
                val ID = msID.text.toString()
                val password = mPasswordText.text.toString()
                val name = mName.text.toString()
                val pnum = mPnum.text.toString()
                val role: String = "seller"
                val data = Post(ID, password, name, pnum, role)
                val info = data.toMap()
                DatabaseReference.child("userDB").child(ID).setValue(info)
                finish()
                overridePendingTransition(0, 0)
            }

        }
        validateButton_seller.setOnClickListener{
            val database :FirebaseDatabase= FirebaseDatabase.getInstance()
            val myref:DatabaseReference=database.getReference("userDB")
            myref.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val child = p0.children.iterator();
                    while(child.hasNext()){
                        if(msID.text.toString().equals(child.next().key)) {
                            Toast.makeText(getApplicationContext(), "존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            })
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

        mPickTimeBt_e.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView_e.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
        btn_category.setOnClickListener {
            showCat()
        }
        picklocation.setOnClickListener{
            

            val builder =AlertDialog.Builder(this)
            val dialogView =layoutInflater.inflate(R.layout.select_location,null)
            val dialogText =dialogView.findViewById<EditText>(R.id.editTextFilter)
            builder.setView(dialogView)
                    .setPositiveButton("확인"){dialogInterface, i ->
                        location.text=dialogText.text.toString()
                    }
                    .setNegativeButton("취소"){dialogInterface, i ->
                    }
                    .show()
        }
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