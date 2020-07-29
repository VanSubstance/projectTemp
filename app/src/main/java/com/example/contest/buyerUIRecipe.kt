package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_recipe.*
import kotlinx.android.synthetic.main.buyer_ui_recipe.view.*

class buyerUIRecipe : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_recipe, container, false)

        view.buttonRecipe1.setOnClickListener {
            buttonRecipe1.setImageResource(R.drawable.ui_ctgr_meat)
            buttonRecipe2.setImageResource(R.drawable.ui_ctgr_seafood_off)
            buttonRecipe3.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            buttonRecipe4.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(12)
        }
        view.buttonRecipe2.setOnClickListener {
            buttonRecipe1.setImageResource(R.drawable.ui_ctgr_meat_off)
            buttonRecipe2.setImageResource(R.drawable.ui_ctgr_seafood)
            buttonRecipe3.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            buttonRecipe4.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(13)
        }
        view.buttonRecipe3.setOnClickListener {
            buttonRecipe1.setImageResource(R.drawable.ui_ctgr_meat_off)
            buttonRecipe2.setImageResource(R.drawable.ui_ctgr_seafood_off)
            buttonRecipe3.setImageResource(R.drawable.ui_ctgr_vegetable)
            buttonRecipe4.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(14)
        }
        view.buttonRecipe4.setOnClickListener {
            buttonRecipe1.setImageResource(R.drawable.ui_ctgr_meat_off)
            buttonRecipe2.setImageResource(R.drawable.ui_ctgr_seafood_off)
            buttonRecipe3.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            buttonRecipe4.setImageResource(R.drawable.ui_ctgr_etc)
            (activity as buyerUIMain).setBuyerFrag(15)
        }

        return view
    }
}