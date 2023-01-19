package com.example.collaborator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.collaborator.R
import com.example.collaborator.databinding.ActivityQuestionBinding
import com.example.collaborator.models.Questions
import com.example.collaborator.viewModel.SignupViewModel
import com.example.collaborator.views.MainActivity.userData.currentuser
import com.example.collaborator.views.searchpage.Companion.selectedExpert

class AskActivity : AppCompatActivity() {
    lateinit var binding:ActivityQuestionBinding
    lateinit var viewModel: SignupViewModel
    var questions = arrayListOf<Questions>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel.getQuestions().observe(this){
            questions.clear()
            questions.addAll(it)
        }

        binding.apply {
            Log.d("AskActivity", "$selectedExpert ")
            expertname.text=selectedExpert!!.username
            if(selectedExpert?.gender=="Female"){
                Glide.with(this@AskActivity).load(R.drawable.expertfemale).into(ExpertImage)
            }else{
                Glide.with(this@AskActivity).load(R.drawable.expertmale).into(ExpertImage)
            }
            if(currentuser !=null){
                val userQuestion=message.text
                sendBtn.setOnClickListener {
                    if(userQuestion.isNotEmpty()){
                        viewModel.saveQuestion(Questions("",userQuestion.toString(),"",false,
                            currentuser!!.username, selectedExpert!!.username))
                        userQuestion.clear()
                        userMsg.visibility=View.VISIBLE
                        userMsg.text=userQuestion.toString()
                        val homeuserIntent= Intent(this@AskActivity,homeuser::class.java)
                        startActivity(homeuserIntent)
                    }else{
                        Toast.makeText(this@AskActivity,"Please Write your Question!",Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }


    }
}