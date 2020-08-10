package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.recipe_ui_meat.view.*
import kotlinx.android.synthetic.main.recipe_ui_seafood.*

class recipeUISeafood : Fragment() {

    private lateinit var recipeElementList: ArrayList<recipeElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: recipeUIAdapter
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recipe_ui_seafood,container,false)

        view.buttonSearchRecipe.setOnClickListener {

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        recipeElementList = ArrayList()

        var meatList : ArrayList<String> = arrayListOf()
        meatList.add("달걀_유제품")
        meatList.add("돼지고기")
        meatList.add("소고기")
        meatList.add("육류")
        meatList.add("닭고기")
        for (recipeCtgrTitle in meatList) {
            var data = database.getReference("recipeDB").child(recipeCtgrTitle)
            data.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (recipe in p0.children) {
                        for (ingre in recipe.child("ingredient").child("재료").value as ArrayList<String>) {
                            if (ingre.contains(view!!.textRecipeTitle.text.toString())) {
                                var element = recipeElement()
                                element.setFromDB(recipe, recipeCtgrTitle)
                                recipeElementList.add(element)
                                break
                            }
                        }
                    }
                }
            })
        }
        adapter = recipeUIAdapter(recipeElementList, requireContext(), 1){
                recipeElement ->
            (activity as buyerUIMain).showRecipeSpecific(recipeElement)
        }
        RecyclerView.adapter = adapter

    }
}