package com.example.collaborator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.collaborator.R
import com.example.collaborator.databinding.ActivitySearchpageBinding
import com.example.collaborator.models.Expert
import com.example.collaborator.viewModel.SignupViewModel
import com.example.collaborator.views.Adapters.SearchAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class searchpage : AppCompatActivity(), SearchAdapter.onClickListener {
    lateinit var binding: ActivitySearchpageBinding
    var expertList = listOf<Expert>()
    lateinit var adapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= SearchAdapter(this)
        binding.searchRV.adapter=adapter

        binding.bottomNavigation.setSelectedItemId(R.id.searchid)
        bottomNavigation()

        val viewmodel = ViewModelProvider(this).get(SignupViewModel::class.java)
        viewmodel.getExperts().observe(this){
            expertList = it
            Log.d("SearchPage", "$expertList ")
        }

        binding.apply {
            //Rate
            userSearch.addTextChangedListener{
                var searchText = it.toString()
                val filteredExpertList = expertList.filter {
                    it.experience!!.contains(searchText)
                }
                Log.d("SearchPage", "Filter: $filteredExpertList ")
                adapter.updateAdapterList(filteredExpertList)

            }
        }
    }

    override fun onClick(expert: Expert) {
        val searchResultIntent= Intent(this@searchpage,search_result::class.java)
        selectedExpert=expert
        Log.d("SelectedExpert", "$selectedExpert")
        startActivity(searchResultIntent)
        //finish()
    }



    private fun bottomNavigation() {
        // Perform item selected listener
        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeid ->{
                    val Intent = Intent(applicationContext, homeuser::class.java)
                    startActivity(Intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true

                }

                R.id.searchid -> {
                     return@OnNavigationItemSelectedListener true
                }



                R.id.profileid -> {
                    val Intent = Intent(applicationContext, profile::class.java)
                    startActivity(Intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }
    companion object{
        var selectedExpert:Expert?=null
    }
}