package com.example.knxmanager.Model


/* Kotlin data/model classes that map the JSON response, we could also add Moshi
 * annotations to help the compiler with the mappings on a production app */

data class ServerHello(
    val id: Int,
    val homeText: String,
    val time : String)

data class KnxTelegram(
    var Tid: Long?,
    var TimestampS: String?,
    var Timestamp: String?,
    var Service: String?,
    var FrameFormat: String?,
    var RawData: String?,
    var RawDataLength: Int?,
    var FileName: String?)


data class KnxProcess(var ProcessId : Int){
    var ProcessName : String?
    var ProcessIp : String?
    var ProcessType : String?

    var ProcessedFile : String?
    var AcutalTelegramNr : Int?
    var TotalTelegramNr : Int?

    init{
        ProcessId = 0
        ProcessName = ""
        ProcessIp = ""
        ProcessType = ""
        ProcessedFile = ""
        AcutalTelegramNr = 0
        TotalTelegramNr = 0
    }
}