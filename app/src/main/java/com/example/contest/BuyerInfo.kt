package com.example.contest
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.Exclude


@IgnoreExtraProperties
data class Post(
        var pw:String?="",
        var Name: String? = "",
        var pNum: String? = "",
        var role: String?="",
        var nick: String?="",
        var token: String?=""
) {

    // [START post_to_map]
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "pw" to pw,
                "Name" to Name,
                "pNum" to pNum,
                "role" to role,
                "nick" to nick,
                "token" to token
        )
    }
    // [END post_to_map]
}