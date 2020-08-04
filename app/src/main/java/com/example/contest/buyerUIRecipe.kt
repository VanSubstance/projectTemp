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

        view.buttonRecipeMeat.setOnClickListener {
            buttonRecipeMeat.setImageResource(R.drawable.ui_ctgr_meat)
            buttonRecipeFish.setImageResource(R.drawable.ui_ctgr_seafood_off)
            buttonRecipeVegetable.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            buttonRecipeEtc.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(12)
        }
        view.buttonRecipeFish.setOnClickListener {
            buttonRecipeMeat.setImageResource(R.drawable.ui_ctgr_meat_off)
            buttonRecipeFish.setImageResource(R.drawable.ui_ctgr_seafood)
            buttonRecipeVegetable.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            buttonRecipeEtc.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(13)
        }
        view.buttonRecipeVegetable.setOnClickListener {
            buttonRecipeMeat.setImageResource(R.drawable.ui_ctgr_meat_off)
            buttonRecipeFish.setImageResource(R.drawable.ui_ctgr_seafood_off)
            buttonRecipeVegetable.setImageResource(R.drawable.ui_ctgr_vegetable)
            buttonRecipeEtc.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(14)
        }
        view.buttonRecipeEtc.setOnClickListener {
            buttonRecipeMeat.setImageResource(R.drawable.ui_ctgr_meat_off)
            buttonRecipeFish.setImageResource(R.drawable.ui_ctgr_seafood_off)
            buttonRecipeVegetable.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            buttonRecipeEtc.setImageResource(R.drawable.ui_ctgr_etc)
            (activity as buyerUIMain).setBuyerFrag(15)
        }

        return view
    }
}