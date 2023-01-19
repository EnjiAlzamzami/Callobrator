package com.example.collaborator.views

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.collaborator.R
import com.example.collaborator.databinding.ActivityHomeExpertBinding
import com.example.collaborator.models.Expert
import com.example.collaborator.models.Questions
import com.example.collaborator.viewModel.SignupViewModel
import com.example.collaborator.views.Adapters.QuestionsAdapter
import com.example.collaborator.views.MainActivity.userData.currentexpert
import com.google.android.material.bottomnavigation.BottomNavigationView

class homeExpert : AppCompatActivity(),QuestionsAdapter.onClickListener {
    private lateinit var binding: ActivityHomeExpertBinding
    lateinit var viewModel:SignupViewModel
    var expertQuestionsList = listOf<Questions>()

    lateinit var adapter : QuestionsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeExpertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter=QuestionsAdapter(this)
        binding.questionsRV.adapter=adapter




        viewModel=ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel.getQuestions().observe(this,{
            expertQuestionsList=it
            getExpertQuestions(currentexpert!!)
        })

          binding.bottomNavigation.setSelectedItemId(R.id.homeid)
            bottomNavigation()


    }

  private fun bottomNavigation() {
        // Perform item selected listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeid -> return@OnNavigationItemSelectedListener true

                R.id.historyid -> {
                    val Intent = Intent(applicationContext, history::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }


                R.id.profileid -> {
                    val addIntent = Intent(applicationContext, profile::class.java)
                    startActivity(addIntent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }
    override fun AnswerBtn(question: Questions) {
        AnswerDialog(question)
    }

    fun getExpertQuestions(expert:Expert){
        var expertsArraylist = arrayListOf<Questions>()
        for(expertQuestion in expertQuestionsList){
            if(expertQuestion.ExpertId==expert.username){
                if(expertQuestion.isanswer==false){
                    expertsArraylist.add(expertQuestion)
                    Log.d("TAG", "onCreate: $expertsArraylist")
                }
            }
        }
        adapter.updateList(expertsArraylist)


    }

    //delete dialog
    fun AnswerDialog(question: Questions){
        val dialogBuilder = AlertDialog.Builder(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val answerTxt = EditText(this)
        answerTxt.hint = "Enter Your Answer:"
        layout.addView(answerTxt)

        dialogBuilder.setPositiveButton("SEND",{
                dialog, id ->
            viewModel.updateQuestion(question,answerTxt.text.toString())
        })
            .setNegativeButton("Cancel",{
                    dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle(question.UserId)
        alert.setMessage(question.Question+"??")
        alert.setView(layout)
        alert.show()
    }
}