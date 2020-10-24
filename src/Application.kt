package org.csuf.cpsc411

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.utils.io.readAvailable
import org.csuf.cpsc411.Dao.claim.Claim
import org.csuf.cpsc411.Dao.claim.ClaimDao
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        //Any http message: Call this lambda function if it is get
        get("/ClaimService/getAll") {
            val claimList = ClaimDao().getAll()
            println("The number of claims : ${claimList.size}")
            //JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(claimList)
            call.respondText(respJsonStr, status = HttpStatusCode.OK, contentType = ContentType.Application.Json) //Send text using contentType
        }

        post("/ClaimService/add") {
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead //length of data in body
            val output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output) //For further processing

            println("HTTP message is using POST with method /post ${contType} ${str}")
            call.respondText("The POST request was successfully processed. ",
                status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
    }
}

