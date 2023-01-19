package com.example.collaborator.models

import com.google.firebase.database.Exclude

data class Expert(
                  var username:String?=null,
                  var email:String?=null,
                  var password:String?=null,
                  var gender:String?=null,
                  var education:String?=null,
                  var experience:String?=null,
                  var noOfExperience:Int?=0,
                  var Rate:Int=0,
                  var materialIncome:Double=0.0)