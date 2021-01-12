package com.example.knxmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DataViewer : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var telegramsAdapter: KnxTelegramsAdapter

    lateinit var telegramList : List<KnxTelegram>
    lateinit var telegramsService: KnxTelegramsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_view)

        recyclerView = findViewById(R.id.dgRecyclerview)
        telegramsService = KnxTelegramsService("")
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        telegramsAdapter = KnxTelegramsAdapter(this, getList())
        recyclerView.adapter = telegramsAdapter
    }

    private fun getList(): List<KnxTelegram> {
        telegramList = telegramsService.getKnxTelegrams()
        return telegramList
    }


}

data class KnxTelegram(
        var Tid: Long,
        var TimestampS:String,
        var Timestamp: Date,
        var Service: String,
        var FrameFormat: String,
        var RawData: String,
        var RawDataLength: Int?,
        var FileName: String)
