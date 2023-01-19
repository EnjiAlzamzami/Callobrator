package com.example.collaborator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.collaborator.R
import com.example.collaborator.databinding.ActivityProfileBinding
import com.example.collaborator.models.Expert
import com.example.collaborator.views.MainActivity.userData.currentexpert
import com.example.collaborator.views.MainActivity.userData.currentuser
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            if(currentexpert !=null){
                if(currentexpert!!.gender=="Female"){
                    Glide.with(this@profile).load(R.drawable.expertfemale).into(userImg)
                }else{
                    Glide.with(this@profile).load(R.drawable.expertmale).into(userImg)
                }

                userRole.text= "Expert"
                showExperienceElements()
                username.setText(currentexpert!!.username)
                username.isFocusable=false
                emailAddress.setText(currentexpert!!.email)
                emailAddress.isFocusable=false
                if(currentexpert!!.gender=="Female"){
                     female.isChecked=true
                    female.isClickable=false
                    male.isClickable=false
                }else{
                   male.isChecked=true
                    male.isClickable=false
                    female.isClickable= false
                }
                education.setText(currentexpert!!.education)
                education.isFocusable=false
                experince.setText(currentexpert!!.experience)
                experince.isFocusable=false
                noOfexperince.setText("${currentexpert!!.noOfExperience}")
                noOfexperince.isFocusable=false

             //   binding.bottomNavigation.visibility=View.VISIBLE
                binding.bottomNavigationExpert.setSelectedItemId(R.id.profileid)
                ExpertbottomNavigation()
            }else if(currentuser !=null){

                if(currentuser!!.gender=="Female"){
                    Glide.with(this@profile).load(R.drawable.userfemale).into(userImg)
                }else{
                    Glide.with(this@profile).load(R.drawable.usermale).into(userImg)
                }

                userRole.text= currentuser!!.userRole
                hideExperienceElements()
                username.setText(currentuser!!.username)
                emailAddress.setText(currentuser!!.email)
                if(currentuser!!.gender=="Female"){
                    female.isChecked=true
                    female.isClickable=false
                    male.isClickable=false
                }else{
                    male.isChecked=true
                    male.isClickable=false
                    female.isClickable= false
                }
                education.setText(currentuser!!.Education)
                binding.bottomNavigation.setSelectedItemId(R.id.profileid)
                bottomNavigation()
            }
        }
        binding.logoutBtn.setOnClickListener {
            currentuser=null
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
            finish()
        }
    }

    private fun showExperienceElements() {
        binding.apply {
            experince.visibility = View.VISIBLE
            noOfexperince.visibility= View.VISIBLE
            binding.bottomNavigationExpert.visibility=View.VISIBLE
            binding.bottomNavigation.visibility=View.GONE
        }
    }
    private fun hideExperienceElements(){
        binding.apply {
            experince.visibility= View.GONE
            noOfexperince.visibility= View.GONE
            binding.bottomNavigation.visibility=View.VISIBLE
            binding.bottomNavigationExpert.visibility=View.GONE


        }
    }

    private fun bottomNavigation() {
        // Perform item selected listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeid -> {
                    val Intent = Intent(applicationContext, homeuser::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }


                R.id.searchid -> {
                    val Intent = Intent(applicationContext, searchpage::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profileid -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }
    private fun ExpertbottomNavigation() {
        //binding.bottomNavigation.visibility=View.GONE
        // Perform item selected listener
        binding.bottomNavigationExpert.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeid -> {
                    val Intent = Intent(applicationContext, homeExpert::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }


                R.id.historyid -> {
                    val Intent = Intent(applicationContext, history::class.java)
                    startActivity(Intent)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profileid -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }

}