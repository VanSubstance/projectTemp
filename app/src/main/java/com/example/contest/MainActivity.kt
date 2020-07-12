package com.example.contest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFrag(0)

        button1.setOnClickListener {
            setFrag(0)
        }

        button2.setOnClickListener {
            setFrag(1)
        }

        button3.setOnClickListener {
            setFrag(2)
        }

        button4.setOnClickListener {
            setFrag(3)
        }
    }

    private fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.main_frame,Fragment1()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame,Fragment2()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame,Fragment3()).commit()
            }
            3 -> {
                ft.replace(R.id.main_frame,Fragment4()).commit()
            }
        }


    }
}
