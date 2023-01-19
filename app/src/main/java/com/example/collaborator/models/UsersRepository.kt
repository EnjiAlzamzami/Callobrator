package com.example.collaborator.models


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class UsersRepository {
    val db = Firebase.database
    private var myRef: DatabaseReference
    val expertLiveData: MutableLiveData<List<Expert>> = MutableLiveData()
    val questionsLiveData: MutableLiveData<List<Questions>> = MutableLiveData()
    val experquestionLiveData: MutableLiveData<List<Questions>> = MutableLiveData()
    val userLiveData: MutableLiveData<List<User>> = MutableLiveData()
    //https://collaboratee-8561c-default-rtdb.firebaseio.com/User/-NLvkRDDiGnJgRJ8CB5-

    init {
        myRef = db.getReferenceFromUrl("https://collaboratee-8561c-default-rtdb.firebaseio.com/")
            //        UserRef=db.getReferenceFromUrl("User/-NLvkRDDiGnJgRJ8CB5-")

    }

//Users Functions:
    //Add to expert to Expert table by using RealtimeDatabase
    fun saveExpert(newExpert: Expert) {
        var pushed =myRef.child("Expert").child(newExpert.username.toString())
        pushed.setValue(newExpert)
        Log.d("Saved??", "SavedExpert!")
        getExperts()
    }
    //Retrieve experts data
    fun getExperts(): LiveData<List<Expert>> {
        // Read from the database
        myRef.child("Expert").get().addOnSuccessListener {
            val value = it.children.map {dataSnapshot -> dataSnapshot.getValue(Expert::class.java)!! }
            Log.d("Data", "Value is: " + value)
            expertLiveData.postValue(value)
        }
        return expertLiveData
    }
    //Add user
    fun saveUser(user:User)
    {
        var pushed=myRef.child("User").child(user.username.toString())
        pushed.setValue(user)
        Log.d("Saved??", "SavedUser!")
        getUsers()

    }
    //Retrieve users data
    fun getUsers(): LiveData<List<User>> {

        myRef.child("User").get().addOnSuccessListener {
            val value = it.children.map {dataSnapshot -> dataSnapshot.getValue(User::class.java)!! }
            Log.d("Data", "Value is: " + value)
            userLiveData.postValue(value)
        }
        return userLiveData
    }
    //========================================

//Questions Functions
    //Retrieve Questions into list
    fun getQuestions(): LiveData<List<Questions>> {
        // Read from the database
        myRef.child("Question").get().addOnSuccessListener {
            val value = it.children.map{dataSnapshot -> dataSnapshot.getValue(Questions::class.java)!!}
            Log.d("Data", "Value is: " + value)
            questionsLiveData.postValue(value)
        }
        Log.d("TAG", "getQuestions: $questionsLiveData ")
        return questionsLiveData
    }
    //Add Question to Question table by using RealtimeDatabase
    fun saveQuestion(question:Questions){
        var pushed=myRef.child("Question").push()
        pushed.setValue(Questions(pushed.key,question.Question,question.answer,question.isanswer,question.UserId,question.ExpertId))
        Log.d("Saved??", "SavedQuestion!")
        getQuestions()
    }

    fun updateAnswer(question:Questions,newAnswer:String){
        myRef.child("Question")
            .child(question.question_id!!).child("answer").setValue(newAnswer)
        myRef.child("Question").child(question.question_id!!).child("isanswer").setValue(true)
        getQuestions()
    }
    fun updateRateIn(expert: String) {
        myRef.child("Expert").child(expert!!).child("rate")
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentRate = dataSnapshot.getValue(Long::class.java) ?: 0
                    myRef.child("Expert").child(expert!!).child("rate").setValue(currentRate + 1)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase Error", error.toString())
                }
            })
    }

    fun updateRatede(expert: String){
        myRef.child("Expert").child(expert!!).child("rate").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentRate = dataSnapshot.getValue(Long::class.java) ?: 0
                myRef.child("Expert").child(expert!!).child("rate").setValue(currentRate - 1)

            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", error.toString())
            }
        })


    }




}