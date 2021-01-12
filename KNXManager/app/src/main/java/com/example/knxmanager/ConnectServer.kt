package com.example.knxmanager

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
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


    lateinit var serverIpET : EditText
    lateinit var serverPortET : EditText
    lateinit var serverResponseT : TextView

    lateinit var conProgBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_server)
        sharedPrefs = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

        serverIpET = findViewById<EditText>(R.id.editTextTextServerIp)
        serverPortET = findViewById<EditText>(R.id.editTextTextServerPort)
        serverResponseT = findViewById<TextView>(R.id.textView3Response)
        conProgBar = findViewById<ProgressBar>(R.id.connectionProgressBar)

        var temp = sharedPrefs.getString(PREFERENCE_IP_ADDRESS, "")
        serverIpET.setText(temp)
        temp = sharedPrefs.getString(PREFERENCE_IP_ADDRESS_PORT, "")
        serverPortET.setText(temp)

    }


    fun testServerConnection(view: View) {
        conProgBar.setVisibility(View.VISIBLE)

        var urlString: String = "https://" + serverIpET.text + ":" + serverPortET.text
        var srvResponseString = ""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                srvResponseString = ConnectionTesterService().testServerConnection(urlString)
            } catch (e: Exception) {
                srvResponseString = e.message.toString()
            }
            withContext(Dispatchers.Main) {
                serverResponseT.text = srvResponseString
                ipAddress = urlString
                conProgBar.setVisibility(View.GONE)
            }
        }
        serverResponseT.text = srvResponseString
    }

    fun saveAndExit(view: View){
        //TODO dodac sprawdzanie czy 200
        var spEdit = sharedPrefs.edit()

        spEdit.putString(PREFERENCE_IP_ADDRESS_FULL, ipAddress)
        spEdit.putString(PREFERENCE_IP_ADDRESS, serverIpET.text.toString())
        spEdit.putString(PREFERENCE_IP_ADDRESS_PORT, serverPortET.text.toString())
        spEdit.putInt(PREFERENCE_SERWER_STATE, 1)

        spEdit.commit()
        super.onBackPressed();
    }
}
