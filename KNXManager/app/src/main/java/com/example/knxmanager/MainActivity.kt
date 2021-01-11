package com.example.knxmanager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var sharedPrefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = getSharedPreferences( PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        var temp = sharedPrefs.getString(PREFERENCE_IP_ADDRESS_FULL, "")
        var ipAddressTB = findViewById<TextView>(R.id.textView2)
        try{
            temp = Resources.getSystem().getString(R.string.text_connect_state).toString() + " " + temp.toString()
        }catch (e:Resources.NotFoundException){
        }
        ipAddressTB.setText(temp)
    }

    fun openConnectServer(view: View) {
        val intent = Intent(view.context, ConnectServer::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        view.context.startActivity(intent)
    }

    fun openProcessViewer(view: View) {
        val intent = Intent(view.context, ProcessesViewer::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        view.context.startActivity(intent)
    }

    fun openDataViewer(view: View) {
        val intent = Intent(view.context, DataViewer::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        view.context.startActivity(intent)
    }

}