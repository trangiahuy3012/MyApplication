package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.MySharedPreferences
import com.example.myapplication.database.AccountDatabase

class MyApp : Application() {
    lateinit var prefs : MySharedPreferences
    lateinit var db : AccountDatabase

    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreferences()
        prefs.init(this)
        db = AccountDatabase.getDatabase(this)
    }
}