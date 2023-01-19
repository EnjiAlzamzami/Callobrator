package com.example.collaborator.models

import com.google.firebase.database.Exclude

data class User(
                var username:String?=null,
                var email:String?=null,
                var password:String?=null,
                var gender:String?=null,
                var userRole:String?=null,
                var Education:String?=null)
