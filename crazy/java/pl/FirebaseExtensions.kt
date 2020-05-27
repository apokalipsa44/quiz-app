package pl.cgwisdom.quizzandrcgw

import com.google.firebase.auth.FirebaseUser
import pl.cgwisdom.quizzandrcgw.profile.UserItem

fun FirebaseUser.toUserItem(): UserItem {
    return UserItem().apply {
        uid = this@toUserItem.uid
        url = this@toUserItem.photoUrl.toString()
        name = this@toUserItem.displayName!!
    }
}