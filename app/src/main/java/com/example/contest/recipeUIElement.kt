package com.example.contest

class recipeUIElement() {
    var title : String = "레시피 이름 테스트"
    var ctgr : String = "카테고리 테스트"
    var level : String = "난이도 테스트"
    var image : Int = R.drawable.test_02

    constructor( name : String ) : this() {
        title = name
    }

}