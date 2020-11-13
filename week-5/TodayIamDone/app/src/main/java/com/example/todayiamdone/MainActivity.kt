package com.example.todayiamdone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 환경설정 값 불러오기
        // TODO: 테마 적용하기

        setContentView(R.layout.activity_main)

        Handler().postDelayed( {
            val intent = Intent(this, TodoActivity::class.java)

            startActivity(intent)

            finish()
        }, 3000)
    }
}
