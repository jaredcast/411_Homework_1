package org.csuf.cpsc411.Dao
import java.io.File
//This is what u used for notes
import com.almworks.sqlite4java.SQLiteConnection

class Database constructor (var dbName : String = "") {
    //Single object pattern

    init {
        //Create the db, tables and keeps the db connection
        dbName = "C:\\Users\\jared\\Documents\\Fall 2020\\411\\Android\\hw1\\hw1.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists claim (id, title, date, isSolved)"
        dbConn.exec(sqlStmt)
    } //Automatically gets called

    fun getDbConnection(): SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    companion object { //As soon as you define this class, compiler will create an object for this class
        private var dbObj: Database? = null

        fun getInstance(): Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}

fun main() {

}