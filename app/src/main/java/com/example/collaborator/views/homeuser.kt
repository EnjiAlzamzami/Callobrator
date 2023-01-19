package com.example.collaborator.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.collaborator.R
import com.example.collaborator.databinding.ActivityHomeuserBinding
import com.example.collaborator.models.Questions
import com.example.collaborator.models.User
import com.example.collaborator.viewModel.SignupViewModel
import com.example.collaborator.views.Adapters.AnswersAdapter
import com.example.collaborator.views.MainActivity.userData.currentuser
import com.google.android.material.bottomnavigation.BottomNavigationView


class homeuser : AppCompatActivity(),AnswersAdapter.onClick {
    private lateinit var binding: ActivityHomeuserBinding

    lateinit var viewModel: SignupViewModel
    var userQuestionsAnswersList = listOf<Questions>()

    lateinit var adapter : AnswersAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityHomeuserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter=AnswersAdapter(this)
        binding.answersRV.adapter=adapter


        viewModel= ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel.getQuestions().observe(this,{
            userQuestionsAnswersList=it
            getUserQAnswers(currentuser!!)
        })

        binding.bottomNavigation.setSelectedItemId(R.id.homeid)
        bottomNavigation()



    }

    private fun bottomNavigation() {
        // Perform item selected listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeid -> return@OnNavigationItemSelectedListener true

                R.id.searchid -> {
                    val Intent = Intent(applicationContext, searchpage::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }


                R.id.profileid -> {
                    val Intent = Intent(applicationContext, profile::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }

    private fun getUserQAnswers(currentuser: User) {
        var usersQArraylist = arrayListOf<Questions>()
        for(UsersQAnswers in userQuestionsAnswersList){
            if(UsersQAnswers.UserId==currentuser.username){
                if(UsersQAnswers.isanswer==true){
                    usersQArraylist.add(UsersQAnswers)
                    Log.d("TAG", "onCreate: $usersQArraylist")
                }
            }
        }
        adapter.updateList(usersQArraylist)

    }

    override fun RateClick(expertname: String) {
       //update Rate
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Rating")
        builder.setMessage("How do you rate $expertname?!")

        builder.setPositiveButton("Good") { dialog, which ->
            // Perform action for "Yes" button
            viewModel.updateRateIn(expertname)
        }

        builder.setNegativeButton("Not Good") { dialog, which ->
            // Perform action for "No" button
            viewModel.updateRateDe(expertname)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}