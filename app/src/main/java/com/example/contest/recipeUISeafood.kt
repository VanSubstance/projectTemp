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
import kotlinx.android.synthetic.main.recipe_ui_meat.*
import kotlinx.android.synthetic.main.recipe_ui_meat.view.*
import kotlinx.android.synthetic.main.recipe_ui_seafood.*
import kotlinx.android.synthetic.main.recipe_ui_seafood.RecyclerView

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

        var ctgrList: ArrayList<String> = arrayListOf()
        ctgrList.add("건어물")
        ctgrList.add("해물")

        database.getReference("recipeDB")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(p0: DataSnapshot) {
                    for (recipeCtgr in p0.children) {
                        // 큰 카테고리에 포함 될 경우
                        if (ctgrList.any{it.equals(recipeCtgr.key.toString())}) {
                            var recipeList: ArrayList<recipeElement> = arrayListOf()
                            for (recipe in recipeCtgr.children) {
                                for (ingre in recipe.child("ingredient")
                                    .child("재료").value as ArrayList<String>) {
                                    if (ingre.contains(view!!.textRecipeTitle.text.toString())) {
                                        var element = recipeElement()
                                        element.setFromDB(recipe, recipeCtgr.key.toString())
                                        recipeList.add(element)
                                        break
                                    }
                                }
                            }
                            addList(recipeList, recipeElementList)
                        }
                    }
                    adapter = recipeUIAdapter(recipeElementList, requireContext(), 1) { recipeElement ->
                        (activity as buyerUIMain).showRecipeSpecific(recipeElement)
                    }
                    RecyclerView.adapter = adapter
                }
            })
    }

    fun addList(src: ArrayList<recipeElement>, dst: ArrayList<recipeElement>) {
        dst.addAll(src)
    }
}