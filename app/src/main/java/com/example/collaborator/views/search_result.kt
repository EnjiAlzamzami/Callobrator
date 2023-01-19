package com.example.collaborator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.collaborator.R
import com.example.collaborator.databinding.ActivitySearchResultBinding
import com.example.collaborator.models.Expert
import com.example.collaborator.views.Adapters.QualificationAdapter

class search_result : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    var expert: Expert?=null
    lateinit var adapter: QualificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        expert= searchpage.selectedExpert
        Log.d("TAG", "${expert!!.username},${expert!!.gender}")


        binding.apply {

            if(expert!!.gender=="Female"){
                ExpertImage.setImageResource(R.drawable.expertfemale)
            }else{
                ExpertImage.setImageResource(R.drawable.expertmale)
            }
            expertNameTv.text=expert!!.username
            noOfExpertExperience.text="${expert!!.noOfExperience} Years"

            expertEducationBtn.setOnClickListener {
                adapter= QualificationAdapter("Education")
                binding.expertQualifications.adapter=adapter
                val educationList= expert!!.education!!.split(" ").toList()
                adapter.updateList(educationList,"Education")
            }
            expertExperienceBtn.setOnClickListener {
                adapter= QualificationAdapter("Experience")
                binding.expertQualifications.adapter=adapter
                val experienceList= expert!!.experience!!.split(",").toList()
                adapter.updateList(experienceList,"Experience")

            }
            askExpertBtn.setOnClickListener {
                val AskActivityIntent= Intent(this@search_result,AskActivity::class.java)
                startActivity(AskActivityIntent)
                finish()
            }
            backBtn.setOnClickListener {
                val searchActivityIntent= Intent(this@search_result,searchpage::class.java)
                startActivity(searchActivityIntent)
                finish()
            }
        }






    }
}