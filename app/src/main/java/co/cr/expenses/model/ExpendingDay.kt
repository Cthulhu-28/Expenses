package co.cr.expenses.model

import co.cr.expenses.utils.Utils
import com.google.firebase.database.Exclude
import java.util.*

class ExpendingDay(){
    var summary = Summary()
    @get:Exclude
    var time: Int = 0
    var expenditures = hashMapOf<Int, Expenditure>()
    var income = hashMapOf<Int, Detail>()

    fun asDate(): Date{
        return Utils.buildDateFromTimestamp(time)
    }
}