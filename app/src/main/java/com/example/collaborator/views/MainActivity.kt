package com.example.collaborator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.collaborator.databinding.ActivityMainBinding
import com.example.collaborator.models.Expert
import com.example.collaborator.models.User
import com.example.collaborator.viewModel.SignupViewModel
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var viewModel: SignupViewModel

    var experts = arrayListOf<Expert>()
    var users= arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel.getExperts().observe(this){
            experts.clear()
            experts.addAll(it)
        }

        viewModel.getUsers().observe(this){
            users.clear()
            users.addAll(it)
        }

        binding.apply {
            registerTv.setOnClickListener {
                val signupIntent= Intent(this@MainActivity, signup::class.java)
                startActivity(signupIntent)
                finish()
            }

            //Authentication User
            signInBtn.setOnClickListener {
                val userEmail=userEmail.text
                val userPassword=userPassword.text
                if(userEmail.isNotEmpty()&&userPassword.isNotEmpty()){
                    for(user in users){
                        println(" for (user in users), ${user.username} in $users")
                        if (userEmail.toString() == user.email) {
                            checkUserLogin(userPassword.toString(),user)
                            userEmail.clear()
                            userPassword.clear()
                        }
                    }
                    for(expert in experts){
                        println(" for (expert in experts),${expert.username} in $experts")
                        if (userEmail.toString() == expert.email){
                            checkExpertLogin(userPassword.toString(),expert)
                            userEmail.clear()
                            userPassword.clear()
                        }
                    }

                }

            }
        }
    }

    //===========================================================
    //This function to hash password before compare it with one in database
    private fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

    fun checkExpertLogin(password:String,expert:Expert){
        if(md5Hash(password)==expert.password!!){
            currentexpert=expert
            Log.d("MainActivity", "checkExpertLogin: $currentexpert ")
            val HomeExpertIntent=Intent(this@MainActivity,homeExpert::class.java)
            startActivity(HomeExpertIntent)
            finish()
            Toast.makeText(this@MainActivity,"Welcome ${expert.username}",Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(this@MainActivity,"Please make sure from your password",Toast.LENGTH_SHORT).show()
        }
    }fun checkUserLogin(password:String,user:User){
        if(md5Hash(password)==user.password!!){
            currentuser=user
            Log.d("MainActivity", "checkUserLogin: $currentuser")
            val HomeUserIntent=Intent(this@MainActivity,homeuser::class.java)
            startActivity(HomeUserIntent)
            finish()
        }else{
            Toast.makeText(this@MainActivity,"Please make sure from your password",Toast.LENGTH_SHORT).show()
        }
    }
    companion object userData{
        var currentuser: User? =null
        var currentexpert: Expert? =null
    }
}