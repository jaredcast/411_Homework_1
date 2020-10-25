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
            for (claim in claimList) {
                println("$claim")
            }
            //JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(claimList) //Convert to json, print out
            call.respondText(respJsonStr, status = HttpStatusCode.OK, contentType = ContentType.Application.Json) //Send text using contentType
        }

        post("/ClaimService/add") {
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead //length of data in body
            val output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output) //For further processing, save the string


            /*val id = UUID.randomUUID()
            val title = call.request.queryParameters["title"]
            val date = call.request.queryParameters["date"]
            val isSolved = false
            val claimObj = Claim(id, title, date, isSolved)
            val dao = ClaimDao().addClaim(claimObj)*/

            //Take the title and date from json string, convert to json, make new claim, add to claim list
            val gsonSt = Gson().fromJson(str, Claim::class.java) //Convert to gson string from a json
            val claimObj = Claim(UUID.randomUUID(), gsonSt.title, gsonSt.date, isSolved = false) //Make a new claim object, isSolved will default to being false.
            val dao = ClaimDao().addClaim(claimObj) //adding to database
            val response = String.format("\nUUID %s \nTitle %s \nDate %s \nisSolved %s", claimObj.id, claimObj.title, claimObj.date, claimObj.isSolved) //Print this to postman and the terminal.

            //JSON serialization/deserialization
            // GSON (Google Library)
            println("HTTP message is using POST with method /post ${contType} ${str}") //Print out success http message
            println(response) //Print the response from above
            call.respondText("The POST request was successfully processed. " + response,
                status= HttpStatusCode.OK, contentType = ContentType.Text.Plain) //Print to postman
        }
    }
}

