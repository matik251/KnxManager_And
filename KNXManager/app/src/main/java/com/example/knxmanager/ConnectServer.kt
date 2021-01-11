package com.example.knxmanager

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ConnectServer : AppCompatActivity() {

    lateinit var sharedPrefs : SharedPreferences
    var ipAddress : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_server)
        sharedPrefs = getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
    }


    fun testServerConnection(view: View) {

        var serverIpET = findViewById<EditText>(R.id.editTextTextServerIp)
        var serverPortET = findViewById<EditText>(R.id.editTextTextServerPort)
        var serverResponseT = findViewById<TextView>(R.id.textView3Response)

        var urlString: String = "https://" + serverIpET.text + ":" + serverPortET.text
        var srvResponseString = ""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                srvResponseString = ConnectionTester().testServerConnection(urlString)
            } catch (e: Exception) {
                srvResponseString = e.message.toString()
            }
            withContext(Dispatchers.Main) {
                serverResponseT.text = srvResponseString
                ipAddress = urlString
            }

        }
        serverResponseT.text = srvResponseString
    }

    fun saveAndExit(){
        var spEdit = sharedPrefs.edit()
        var serverIpET = findViewById<EditText>(R.id.editTextTextServerIp)
        var serverPortET = findViewById<EditText>(R.id.editTextTextServerPort)

        spEdit.putString(R.string.preference_ip_address_full.toString(), ipAddress)
        spEdit.putString(R.string.preference_ip_address.toString(), serverIpET.text.toString())
        spEdit.putString(R.string.preference_ip_address_port.toString(), serverPortET.text.toString())

        spEdit.commit()

    }
}
