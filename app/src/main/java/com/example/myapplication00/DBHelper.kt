package com.example.projectapp

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.example.projectapp.DBHelper.Companion.TABLE_NAME
import java.time.LocalDate
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


//context 정보는 멤버로 많이 사용하므로 반드시 생성자에 있어야 하고 나머지는 클래스 내부에 Static 멤버로 만들면 된다.

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "project.db"
        val DB_VERSION = 1
        val TABLE_NAME = "daily"
        val DATE = "date"
        val DAILY_GOAL = "daily_goal"
        val WEEKLY_GOAL = "weekly_goal"
        val CHECKED = "checked"
        val TITLE = "title"
        val LOCATION = "LOCATION"
        val START_TIME = "start_time"
        val END_TIME = "end_time"
        val ISALLDAY = "isallday"
        val ALARM = "alarm"
        val MEMO = "memo"
        val SOJU = "soju"
        val BEER = "beer"
        val MAKGEOLLI = "makgeolli"
        val WINE = "wine"
        val DIARY = "diary"
        val SELF_EXAMINATION = "self_examination"
        val TIP = "tip"
    }

    //DB가 생성될 때 실행되는 함수
    override fun onCreate(db: SQLiteDatabase?) {
        //table 생성 sql문 생성
        val create_table = "create table if not exists $TABLE_NAME(" +
                "$DATE text primary key," +
                "$DAILY_GOAL integer," +
                "$WEEKLY_GOAL integer, " +
                "$CHECKED boolean, " +
                "$TITLE text, " +
                "$LOCATION text, " +
                "$START_TIME text, " +
                "$END_TIME text, " +
                "$ISALLDAY boolean, " +
                "$ALARM text, " +
                "$MEMO text, " +
                "$SOJU integer, " +
                "$BEER integer, " +
                "$MAKGEOLLI integer, " +
                "$WINE integer, " +
                "$DIARY text, " +
                "$SELF_EXAMINATION text, " +
                "$TIP text);"

        //생성한 sql문을 DB 객체를 이용해 전송
        db!!.execSQL(create_table)
    }

    //데이터베이스의 버전이 바뀐 경우 호출하는 함수
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"

        db!!.execSQL(drop_table)
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findAlcohol(date: String) : Int {
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var num = 0

        if(flag){
            cursor.moveToFirst()
            num = cursor.getInt(11) + cursor.getInt(12)+cursor.getInt(13)+cursor.getInt(14)
        }
        cursor.close()
        db.close()
        return num
    }

    fun getAllAlcohol() : ArrayList<Int> {
        var strsql="select * from $TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val result = arrayListOf<Int>(0,0,0,0)
        if(cursor != null && cursor.count >0) {
            cursor.moveToFirst()
            do {
                result[0] = result[0] + cursor.getInt(11)
                result[1] = result[1] + cursor.getInt(12)
                result[2] = result[2] + cursor.getInt(13)
                result[3] = result[3] + cursor.getInt(14)
            } while (cursor.moveToNext())
            //작업 완료 후 db, cursor를 닫아주는 함수 호출
        }
        cursor.close()
        db.close()
        return  result
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAlcohol(array : ArrayList<Int>) : Boolean{
        val date = LocalDate.now().toString()
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(SOJU ,array[0])
            values.put(BEER, array[1])
            values.put(MAKGEOLLI, array[2])
            values.put(WINE, array[3])
            db.update(TABLE_NAME, values, "$DATE = ?", arrayOf(date))
        }
        //작업 완료 후 db, cursor를 닫아주는 함수 호출
        cursor.close()
        db.close()
        return flag
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert():Boolean{
        val now = LocalDate.now()
        var yesterday = now.minusDays(1)
        val values = ContentValues()
        values.put(DATE, now.toString())
        values.put(DAILY_GOAL, findDailyGoal(yesterday.toString()))
        values.put(WEEKLY_GOAL, findWeeklyGoal(yesterday.toString()))
        val db = writableDatabase
        if(db.insert(TABLE_NAME, null, values)>0){
            db.close()
            return true
        }
        else{
            db.close()
            return false
        }
    }

    fun updateGoal(date : String, daily_goal: Int, weekly_goal: Int):Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(DAILY_GOAL ,daily_goal)
            values.put(WEEKLY_GOAL, weekly_goal)
            db.update(TABLE_NAME, values, "$DATE = ?", arrayOf(date))
        }
        //작업 완료 후 db, cursor를 닫아주는 함수 호출
        cursor.close()
        db.close()
        return flag
    }

    fun findDailyGoal(date:String) : String{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        lateinit var daily : String
        if(flag){
            cursor.moveToFirst()
            daily = cursor.getString(1)
        }
        cursor.close()
        db.close()
        return daily;
    }

    fun findWeeklyGoal(date:String) : String{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        lateinit var weekly : String;
        if(flag){
            cursor.moveToFirst()
            weekly = cursor.getString(2)
        }
        cursor.close()
        db.close()
        return weekly
    }

    fun checkData(date:String):Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        cursor.close()
        db.close()
        return flag
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlcohol(cmonth: Int): ArrayList<Int> {
        var dayAlchol = ArrayList<Int>()
        lateinit var date: LocalDate
        var days = 0
        val now = LocalDate.now().toString()
        var parse = now.split("-")
        when (cmonth) {
            1 -> {
                date = LocalDate.of(parse[0].toInt(), 1, 1)
                days = 31
            }
            2 -> {
                date = LocalDate.of(parse[0].toInt(), 2, 1)
                days = 30
            }
            3 -> {
                date = LocalDate.of(parse[0].toInt(), 3, 1)
                days = 31
            }
            4 -> {
                date = LocalDate.of(parse[0].toInt(), 4, 1)
                days = 30
            }
            5 -> {
                date = LocalDate.of(parse[0].toInt(), 5, 1)
                days = 31
            }
            6 -> {
                date = LocalDate.of(parse[0].toInt(), 6, 1)
                days = 30
            }
            7 -> {
                date = LocalDate.of(parse[0].toInt(), 7, 1)
                days = 31
            }
            8 -> {
                date = LocalDate.of(parse[0].toInt(), 8, 1)
                days = 31
            }
            9 -> {
                date = LocalDate.of(parse[0].toInt(), 9, 1)
                days = 30
            }
            10 -> {
                date = LocalDate.of(parse[0].toInt(), 10, 1)
                days = 31
            }
            11 -> {
                date = LocalDate.of(parse[0].toInt(), 11, 1)
                days = 30
            }
            12 -> {
                date = LocalDate.of(parse[0].toInt(), 12, 1)
                days = 31
            }
        }


        for (i in 0..days - 1) {
            var changeDate = date.plusDays(i.toLong()).toString()
            dayAlchol.add(findAlcohol(changeDate)) // 날마다 종류별 1개씩 마신 양 가져오기
            //해야 할 작업 작성부분

        }

        return dayAlchol

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDailyGoal(cmonth: Int): ArrayList<Int> {
        var daily_goal = ArrayList<Int>()
        lateinit var date: LocalDate
        var days = 0
        val now = LocalDate.now().toString()
        var parse = now.split("-")
        when (cmonth) {
            1 -> {
                date = LocalDate.of(parse[0].toInt(), 1, 1)
                days = 31
            }
            2 -> {
                date = LocalDate.of(parse[0].toInt(), 2, 1)
                days = 30
            }
            3 -> {
                date = LocalDate.of(parse[0].toInt(), 3, 1)
                days = 31
            }
            4 -> {
                date = LocalDate.of(parse[0].toInt(), 4, 1)
                days = 30
            }
            5 -> {
                date = LocalDate.of(parse[0].toInt(), 5, 1)
                days = 31
            }
            6 -> {
                date = LocalDate.of(parse[0].toInt(), 6, 1)
                days = 30
            }
            7 -> {
                date = LocalDate.of(parse[0].toInt(), 7, 1)
                days = 31
            }
            8 -> {
                date = LocalDate.of(parse[0].toInt(), 8, 1)
                days = 31
            }
            9 -> {
                date = LocalDate.of(parse[0].toInt(), 9, 1)
                days = 30
            }
            10 -> {
                date = LocalDate.of(parse[0].toInt(), 10, 1)
                days = 31
            }
            11 -> {
                date = LocalDate.of(parse[0].toInt(), 11, 1)
                days = 30
            }
            12 -> {
                date = LocalDate.of(parse[0].toInt(), 12, 1)
                days = 31
            }
        }


        for (i in 0..days - 1) {
            var changeDate = date.plusDays(i.toLong()).toString()
            daily_goal.add(findDailyGoal(changeDate).toInt()) // 날마다 종류별 1개씩 마신 양 가져오기
            //해야 할 작업 작성부분

        }

        return daily_goal

    }



}



