package com.example.collaborator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.collaborator.models.Expert
import com.example.collaborator.models.Questions
import com.example.collaborator.models.User
import com.example.collaborator.models.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(application: Application): AndroidViewModel(application)  {
//    private var users: LiveData<List<User>>
//    private  var experts: LiveData<List<Expert>>
    var repository:UsersRepository

    init {

       repository = UsersRepository()


    }


    fun getExperts(): LiveData<List<Expert>> {
        return repository.getExperts()
    }

    fun saveExpert(newExpert:Expert) {
        return repository.saveExpert(newExpert)
    }

    fun saveUser(newUser:User) {
        return repository.saveUser(newUser)
    }

    fun getUsers(): LiveData<List<User>> {
        return repository.getUsers()
    }

    fun saveQuestion(newQuestions: Questions) {
        return repository.saveQuestion(newQuestions)
    }


    fun getQuestions(): LiveData<List<Questions>> {
        return repository.getQuestions()
    }

    fun updateQuestion(question: Questions,newAnswer:String) {
        return repository.updateAnswer(question,newAnswer)
    }
    fun updateRateIn(expert: String) {
        return repository.updateRateIn(expert)
    }
    fun updateRateDe(expert: String) {
        return repository.updateRatede(expert)
    }


}