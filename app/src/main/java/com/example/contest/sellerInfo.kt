package com.example.contest
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.Exclude


@IgnoreExtraProperties
data class Post_s(
        var pw : String?="",
        var Name: String? = "",
        var pNum: String? = "",
        var role: String?="",
        var market_n: String?="",
        var market_t:String?="",
        var token:String?=""
) {

    // [START post_to_map]
    @Exclude
    fun toMap_s(): Map<String, Any?> {
        return mapOf(
                "pw" to pw,
                "Name" to Name,
                "pNum" to pNum,
                "role" to role,
                "storeTitle" to market_n,
                "marketTitle" to market_t,
                "token" to token
        )
    }
    // [END post_to_map]
}