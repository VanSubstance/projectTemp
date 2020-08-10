package com.example.contest

import android.net.Uri
import com.google.firebase.database.DataSnapshot

class recipeElement() {
    var title : String = ""
    var titleUrl : String = ""
    var ingredient : ArrayList<String> = arrayListOf()
    var ctgr : String = ""
    var recipe : ArrayList<String> = arrayListOf()
    var recipeUrl : ArrayList<String> = arrayListOf()

    constructor( name : String ) : this() {
        title = name
    }

    fun setFromDB(recipe : DataSnapshot, ctgrFromDB : String) {
        ctgr = ctgrFromDB
        title = recipe.child("title").value.toString()
        titleUrl = recipe.child("titleImage").value.toString()
        for (stage in recipe.child("recipe").value as ArrayList<String>) {
            this.recipe.add(stage)
        }
        if (recipe.child("recipeImage").exists()) {
            if (recipe.child("recipeImage").value is HashMap<*, *>) {
                for (stage in (recipe.child("recipeImage").value as HashMap<String, String>).values) {
                    this.recipeUrl.add(stage)
                }
            } else {
                for (stage in recipe.child("recipeImage").value as ArrayList<String>) {
                    this.recipeUrl.add(stage)
                }
            }
        }
        for (ingre in recipe.child("ingredient").child("재료").value as ArrayList<String>) {
            ingredient.add(ingre)
        }
    }

}