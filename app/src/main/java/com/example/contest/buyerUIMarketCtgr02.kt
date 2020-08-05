package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_02.*
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_02.view.*

class buyerUIMarketCtgr02 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market_ctgr_02, container, false)
        view.buttonMeat.setOnClickListener {
            // 해당 시장 오늘 상품중 육류만 골라서 보여준다.
            // conditionData 정보를 조건에 맞춰서 여기서 변경해줘야함
            imageMeat.setImageResource(R.drawable.ui_ctgr_meat)
            imageSeafood.setImageResource(R.drawable.ui_ctgr_seafood_off)
            imageVegetable.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            imageEtc.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(25)
        }
        view.buttonFish.setOnClickListener {
            // 해당 시장 오늘 상품중 해산물만 골라서 보여준다.
            // conditionData 정보를 조건에 맞춰서 여기서 변경해줘야함
            imageMeat.setImageResource(R.drawable.ui_ctgr_meat_off)
            imageSeafood.setImageResource(R.drawable.ui_ctgr_seafood)
            imageVegetable.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            imageEtc.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(25)
        }
        view.buttonVegetable.setOnClickListener {
            // 해당 시장 오늘 상품중 채소만 골라서 보여준다.
            // conditionData 정보를 조건에 맞춰서 여기서 변경해줘야함
            imageMeat.setImageResource(R.drawable.ui_ctgr_meat_off)
            imageSeafood.setImageResource(R.drawable.ui_ctgr_seafood_off)
            imageVegetable.setImageResource(R.drawable.ui_ctgr_vegetable)
            imageEtc.setImageResource(R.drawable.ui_ctgr_etc_off)
            (activity as buyerUIMain).setBuyerFrag(25)
        }
        view.buttonEtc.setOnClickListener {
            // 해당 시장 오늘 상품중 기타만 골라서 보여준다.
            // conditionData 정보를 조건에 맞춰서 여기서 변경해줘야함
            imageMeat.setImageResource(R.drawable.ui_ctgr_meat_off)
            imageSeafood.setImageResource(R.drawable.ui_ctgr_seafood_off)
            imageVegetable.setImageResource(R.drawable.ui_ctgr_vegetable_off)
            imageEtc.setImageResource(R.drawable.ui_ctgr_etc)
            (activity as buyerUIMain).setBuyerFrag(25)
        }
        return view
    }
}