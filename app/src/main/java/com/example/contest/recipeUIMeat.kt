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

class recipeUIMeat : Fragment() {

    private var recipeElementList: ArrayList<recipeElement> = arrayListOf()
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: recipeUIAdapter
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_ui_meat, container, false)

        view.buttonSearchRecipe.setOnClickListener {

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        var ctgrList: ArrayList<String> = arrayListOf()
        ctgrList.add("달걀_유제품")
        ctgrList.add("돼지고기")
        ctgrList.add("소고기")
        ctgrList.add("육류")
        ctgrList.add("닭고기")


        /**
        readData(object : MyCallback {
        override fun onCallback(value: ArrayList<recipeElement>?) {
        recipeElementList.addAll(value!!)

        adapter = recipeUIAdapter(recipeElementList, requireContext(), 1) { recipeElement ->
        (activity as buyerUIMain).showRecipeSpecific(recipeElement)
        }
        RecyclerView.adapter = adapter
        }
        }, "달걀_유제품")
         */
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


        /**
        for (recipeCtgrTitle in ctgrList) {
            database.getReference("recipeDB").child(recipeCtgrTitle)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        var recipeList: ArrayList<recipeElement> = arrayListOf()
                        for (recipe in p0.children) {
                            for (ingre in recipe.child("ingredient")
                                .child("재료").value as ArrayList<String>) {
                                if (ingre.contains(view!!.textRecipeTitle.text.toString())) {
                                    var element = recipeElement()
                                    element.setFromDB(recipe, recipeCtgrTitle)
                                    recipeList.add(element)
                                    break
                                }
                            }
                        }
                        addList(recipeList, recipeElementList)
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        }
        adapter = recipeUIAdapter(recipeElementList, requireContext(), 1) { recipeElement ->
        (activity as buyerUIMain).showRecipeSpecific(recipeElement)
        }
        RecyclerView.adapter = adapter
         */
    }

    fun addList(src: ArrayList<recipeElement>, dst: ArrayList<recipeElement>) {
        dst.addAll(src)
    }

    /**
    fun readData(myCallback: MyCallback, recipeCtgrTitle: String) {
    database.getReference("recipeDB").child(recipeCtgrTitle)
    .addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(p0: DataSnapshot) {
    var recipeList: ArrayList<recipeElement> = arrayListOf()
    for (recipe in p0.children) {
    for (ingre in recipe.child("ingredient")
    .child("재료").value as ArrayList<String>) {
    if (ingre.contains(view!!.textRecipeTitle.text.toString())) {
    var element = recipeElement()
    element.setFromDB(recipe, recipeCtgrTitle)
    recipeList.add(element)
    break
    }
    }
    }
    myCallback.onCallback(recipeList)
    }

    override fun onCancelled(databaseError: DatabaseError) {}
    })
    }
     */
}