package com.example.knxmanager

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
        sharedPrefs = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

        var serverIpET = findViewById<EditText>(R.id.editTextTextServerIp)
        var serverPortET = findViewById<EditText>(R.id.editTextTextServerPort)
        var temp = sharedPrefs.getString(PREFERENCE_FILE_KEY, "")
        serverIpET.setText(temp)
        temp = sharedPrefs.getString(PREFERENCE_IP_ADDRESS_PORT, "")
        serverPortET.setText(temp)
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

    fun saveAndExit(view: View){
        //TODO dodac sprawdzanie czy 200
        var spEdit = sharedPrefs.edit()
        var serverIpET = findViewById<EditText>(R.id.editTextTextServerIp)
        var serverPortET = findViewById<EditText>(R.id.editTextTextServerPort)

        spEdit.putString(PREFERENCE_IP_ADDRESS_FULL, ipAddress)
        spEdit.putString(PREFERENCE_IP_ADDRESS, serverIpET.text.toString())
        spEdit.putString(PREFERENCE_IP_ADDRESS_PORT, serverPortET.text.toString())
        spEdit.putInt(PREFERENCE_SERWER_STATE, 1)

        spEdit.commit()
        super.onBackPressed();
    }
}
