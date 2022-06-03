package com.example.mygit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import java.io.FileOutputStream
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    lateinit var myDBHelper:DBHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDB()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initDB(){
        val dbfile = getDatabasePath("project.db")
        //데이터베이스 폴더가 존재하지 않는 경우 실행하는 함수
        if(!dbfile.parentFile.exists()){
            dbfile.parentFile.mkdir()
        }

        myDBHelper = DBHelper(this)
        val date = LocalDate.now().toString()
        myDBHelper.findGoal(date)
    }

}