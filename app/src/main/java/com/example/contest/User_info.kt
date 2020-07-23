
import com.google.firebase.database.Exclude

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class FirebasePost {
    var Email: String? = null
    var Name: String? = null
    var Address: String? = null
    var Pnum: String? = null
    var Password: String? = null

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    constructor(Email: String?, Password: String?, Name: String?, Address: String?,Pnum:String?) {
        this.Email = Email
        this.Password = Password
        this.Name = Name
        this.Address = Address
        this.Pnum=Pnum
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        val result: HashMap<String, Any?> = HashMap()
        result["Email"] = Email
        result["Password"] = Password
        result["Name"] = Name
        result["Address"] = Address
        result["Pnum"]= Pnum
        return result
    }
}