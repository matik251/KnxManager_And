package com.example.knxmanager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.knxmanager.Model.PREFERENCE_FILE_KEY
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS_FULL
import com.example.knxmanager.Services.ConnectionTesterService
import kotlinx.coroutines.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    lateinit var sharedPrefs: SharedPreferences
    lateinit var mainProgressBar: ProgressBar
    var ServerIpAddress: String = ""
    var connectionTesterService = ConnectionTesterService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        mainProgressBar = findViewById(R.id.progressBar)
        checkConnection()
    }

    override fun onResume() {
        super.onResume()
        var temp = sharedPrefs.getString(PREFERENCE_IP_ADDRESS_FULL, "")
        var ipAddressTB = findViewById<TextView>(R.id.textView2)
        try {
            ServerIpAddress = temp.toString()
            temp = Resources.getSystem().getString(R.string.text_connect_state).toString() + " " + temp.toString()
        } catch (e: Resources.NotFoundException) {
        }
        ipAddressTB.setText(temp)
        checkConnection()
    }

    fun openConnectServer(view: View) {
        val intent = Intent(view.context, ConnectServer::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        view.context.startActivity(intent)
    }

    fun openProcessViewer(view: View) {
        val intent = Intent(view.context, ProcessesViewer::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        intent.putExtra(PREFERENCE_IP_ADDRESS_FULL, sharedPrefs.getString(PREFERENCE_IP_ADDRESS_FULL, ""))
        view.context.startActivity(intent)
    }

    fun openDataViewer(view: View) {
        val intent = Intent(view.context, DataViewer::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        intent.putExtra(PREFERENCE_IP_ADDRESS_FULL, sharedPrefs.getString(PREFERENCE_IP_ADDRESS_FULL, ""))
        view.context.startActivity(intent)
    }

    fun checkConnection() {
        var connection = 0
        CoroutineScope(Dispatchers.IO).launch {
            try {
                connection = connectionTesterService.isServerAvaiable(ServerIpAddress)
            } catch (e: Exception) {
            }
            withContext(Dispatchers.Main) {
                if(connection == 1){
                    mainProgressBar.setVisibility(View.GONE)
                }else{
                    mainProgressBar.setVisibility(View.VISIBLE)
                }
            }
        }
    }

}