package com.example.contest

class recipeElement() {
    var title : String = "레시피 이름 테스트"
    var ctgr : String = "카테고리 테스트"
    var ctgrBig : String = ""
    var level : Int = R.drawable.recipe_level_3
    var image : Int = R.drawable.test_02

    constructor( name : String ) : this() {
        title = name
    }

}