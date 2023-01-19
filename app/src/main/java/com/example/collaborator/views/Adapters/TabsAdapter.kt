package com.example.collaborator.views.Adapters

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import com.example.collaborator.views.homeuser
import com.example.collaborator.views.profile
import com.example.collaborator.views.searchpage

class TabsAdapter(private val myContext: Context, internal var totalTabs: Int) : Activity() {

    // this is for fragment tabs
    fun getItem(position: Int): Activity? {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return homeuser()
            }
            1 -> {
                return searchpage()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                return profile()
            }
            else -> return null
        }
    }

    // this counts total number of tabs
    fun getCount(): Int {
        return totalTabs
    }
}