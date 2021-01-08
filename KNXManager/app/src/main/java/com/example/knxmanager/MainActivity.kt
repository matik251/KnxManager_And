package com.example.knxmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openConnectServer(view: View) {
        val connectServerActivity = Intent(this, ConnectServer::class.java)
        startActivity(connectServerActivity)
    }

    fun openProcessViewer(view: View) {
        val processesViewerActivity = Intent(this, ProcessesViewer::class.java)
        startActivity(processesViewerActivity)
    }

    fun openDataViewer(view: View) {
        val dataViewerActivity = Intent(this, DataViewer::class.java)
        startActivity(dataViewerActivity)
    }

}