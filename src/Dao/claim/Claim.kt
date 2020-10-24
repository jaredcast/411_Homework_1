package org.csuf.cpsc411.Dao.claim

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

data class Claim (var id: UUID = UUID.randomUUID(), var title:String?, var date:String?, var isSolved:Boolean = false)

fun main() {
    /*val test1 = Claim(UUID.randomUUID(), "Hit and run", "2020-01-01", false)
    val jsonStr = Gson().toJson(test1) //toJson - makes to json
    println("The converted JSON string : ${jsonStr}")*/
}