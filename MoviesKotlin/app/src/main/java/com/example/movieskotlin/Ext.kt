package com.example.movieskotlin

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showToast(message:Any ){
  Toast.makeText(this,"$message",Toast.LENGTH_LONG).show()
}