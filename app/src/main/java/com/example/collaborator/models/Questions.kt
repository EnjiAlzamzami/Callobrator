package com.example.collaborator.models


data class Questions( var question_id:String?="",
                     var Question:String?="",
                     var answer:String?="",
                     var isanswer:Boolean?= false ,
                     var UserId:String?="",
                     var ExpertId:String?="")
