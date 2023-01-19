package com.example.collaborator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.collaborator.models.ConstValues
import com.example.collaborator.databinding.ActivitySignupBinding
import com.example.collaborator.models.Expert
import com.example.collaborator.models.User
import com.example.collaborator.viewModel.SignupViewModel
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

class signup : AppCompatActivity() {
    lateinit var binding:ActivitySignupBinding
    val values= ConstValues.values

    lateinit var viewModel: SignupViewModel
    var new=true//this to check if the user is new user

    lateinit var selectedItem:String
    var userRole=""
    var experts = arrayListOf<Expert>()
    var users= arrayListOf<User>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel.getExperts().observe(this){
            experts.clear()
            experts.addAll(it)
        }

        viewModel.getUsers().observe(this){
            users.clear()
            users.addAll(it)
        }

        binding.apply {
            UserRoleSpinner.adapter=ArrayAdapter(this@signup,android.R.layout.simple_spinner_item, values)
            UserRoleSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View,
                                            position: Int,
                                            id: Long) {
                    this@signup.selectedItem = values[position]
                    getselectedItem()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    this@signup.selectedItem = values[1]
                }

            }

            signinBtn.setOnClickListener {
                val signinIntent= Intent(this@signup, MainActivity::class.java)
                startActivity(signinIntent)
                finish()
            }
            // Add to the database
            signUpBtn.setOnClickListener {
                //Get user Data
                val firstNameTxt=firstName.text
                val lastNameTxt=lastName.text
                val emailTxt=emailAddress.text
                val passwordTxt=password.text
                val confirmPasswordTxt=confirmPassword.text
                val educationTxt=education.text
                val experienceTxt=experince.text
                val noOfexperienceTxt=noOfexperince.text
                var gender:String?=""
                //set gender value
                if(female.isChecked){
                    gender="Female"
                }else if(male.isChecked){
                    gender="Male"
                }

                if(userRole=="Expert"){
                    if(firstNameTxt.isNotEmpty()&&lastNameTxt.isNotEmpty()&&gender!=""
                        &&emailTxt.isNotEmpty() &&passwordTxt.isNotEmpty()&&confirmPasswordTxt.isNotEmpty()
                        &&educationTxt.isNotEmpty()&&experienceTxt.isNotEmpty()&&noOfexperienceTxt.isNotEmpty()) {
                        val name = "$firstNameTxt $lastNameTxt"
                        for (expert in experts) {
                            println(" for (expert in experts),${expert.username}")
                            if ((name == expert.username)||(emailTxt.toString() == expert.email)){
                                new = false
                                Toast.makeText(
                                    this@signup,
                                    "This username already exists!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }//end for
                        if (new && checkEmailConstrain(emailTxt.toString()) &&
                            correctPassword(passwordTxt.toString(), confirmPasswordTxt.toString())) {
                            val text=experienceTxt.toString()
                            val newText=text.replace("\n",",\n")
                            //Create new user object then added in the database:
                            val newExpert=Expert(name,emailTxt.toString(),md5Hash(passwordTxt.toString()), gender!!,
                                educationTxt.toString(),newText,noOfexperienceTxt.toString().toInt(),0,0.0)
                            experts.add(newExpert)
                            viewModel.saveExpert(newExpert)
                            Log.d("Data", "signUpProcess: $experts ")
                            Toast.makeText( this@signup ,"The Expert Registered Successfully!", Toast.LENGTH_LONG).show()
                            val LoginIntent= Intent(this@signup, MainActivity::class.java)
                            startActivity(LoginIntent)
                            finish()
                        }

                    } else {
                        Toast.makeText(this@signup, "Please fill all fields", Toast.LENGTH_LONG)
                            .show()
                    }
                }else{
                    if(firstNameTxt.isNotEmpty()&&lastNameTxt.isNotEmpty()&&gender!=""
                        &&emailTxt.isNotEmpty() &&passwordTxt.isNotEmpty()&&confirmPasswordTxt.isNotEmpty()
                        &&educationTxt.isNotEmpty()) {
                        val name = "$firstNameTxt $lastNameTxt"
                        for (user in users) {
                            println(" for (user in users), ${user.username} in $users")
                            if ((name == user.username)||(emailTxt.toString() == user.email)) {
                                new = false
                                Toast.makeText(
                                    this@signup,
                                    "This username already exists!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }//end for

                        if (new && checkEmailConstrain(emailTxt.toString()) &&
                            correctPassword(passwordTxt.toString(), confirmPasswordTxt.toString())) {
                             val newUser=User(name,emailTxt.toString(),md5Hash(passwordTxt.toString()), gender!!,userRole,
                                 educationTxt.toString())
                             viewModel.saveUser(newUser)
                            Toast.makeText(this@signup, "The User Registered Successfully!", Toast.LENGTH_LONG).show()
                             users.add(newUser)
                            val LoginIntent=Intent(this@signup,MainActivity::class.java)
                            startActivity(LoginIntent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@signup, "Please fill all fields", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

//Spinner Item
    private fun getselectedItem(){
        when(selectedItem){
            values[0]-> {
                userRole="Student"
                hideExperienceElements()
            }
            values[1]-> {
                userRole="Fresh graduate"
                hideExperienceElements()
            }
            values[2]-> {
                userRole="Employee"
                hideExperienceElements()
            }
            else -> {
                userRole="Expert"
                showExperienceElements()
            }
        }
    }

    private fun showExperienceElements() {
        binding.apply {
            experince.visibility = View.VISIBLE
            noOfexperince.visibility=View.VISIBLE
        }
    }
    private fun hideExperienceElements(){
        binding.apply {
            experince.visibility=View.GONE
            noOfexperince.visibility=View.GONE
        }
    }

    //========================================================================
    private fun checkEmailConstrain(email: String): Boolean {
        val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile("[a-zA-Z0-9\\+\\._\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")
        if(EMAIL_ADDRESS_PATTERN.matcher(email).matches()) return true
        else
            Toast.makeText(this,"The email must follow the pattern example@organization.extention", Toast.LENGTH_SHORT).show()
        return false
        //=====================================================================

    }//End of checkEmailConstrain
    //========================================================================
    private fun correctPassword(p1:String,p2:String):Boolean{
        return hasLower(p1)&&hasNumber(p1)&&hasUpper(p1)&&match(p1,p2)
    }
    private fun hasUpper(text: String): Boolean{
        for (i in 0..text.length-1){
            if (text[i].isUpperCase())
            {
                return true
            }
        }
        Toast.makeText(this,"The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT).show()
        return false
    }

    private fun hasLower(text: String): Boolean{
        for (i in 0..text.length-1){
            if (text[i].isLowerCase())
            {
                return true
            }
        }
        Toast.makeText(this,"The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT).show()
        return false
    }

    private fun hasNumber(text: String): Boolean{
        for(i in 0..9){
            if(text.contains(i.toString())){
                return true
            }
        }
        Toast.makeText(this,"The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT).show()
        return false
    }

    private fun match(password: String,password2: String):Boolean
    {
        if(password==password2){
            return true
        }
        else
        {
            Toast.makeText(this,"The password must match its confirm", Toast.LENGTH_SHORT).show()

        }
        return false
    }
    //===========================================================
    //===========================================================
    //This function to hash password before save it in database
    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }



}