package org.csuf.cpsc411.Dao.claim

import org.csuf.cpsc411.Dao.Dao
import org.csuf.cpsc411.Dao.Database
import java.util.*

class ClaimDao : Dao() {
    fun addClaim(cObj: Claim) {
        //Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        //Prepare sql statement
        sqlStmt = "Insert into claim (id, title, date, isSolved) values " +
                "('${cObj.id}', '${cObj.title}', '${cObj.date}', '${cObj.isSolved}')"

        // Submit sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim>
    {
        //Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        //Prepare sql statement
        sqlStmt = "select id, title, date, isSolved from claim"

        // Submit sql statement
        var claimsList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)
        while (st!!.step()) {
            val id = st.columnString(0) //", you will use a Text column to store the string representation of UUID. "
            val title = st.columnString(1) //These are strings
            val date = st.columnString(2)
            val isSolved = st.columnNull(3)
            claimsList.add(Claim(UUID.fromString(id), title, date, isSolved))
        }
        return claimsList
    }
}