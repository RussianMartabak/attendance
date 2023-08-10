package com.martabak.phincon

import com.google.firebase.auth.FirebaseUser


data class ActiveUser(var user: FirebaseUser) : java.io.Serializable {

}