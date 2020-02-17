package co.cr.expenses.utils

import java.util.*

fun Date.timestamp(): Int{
    val calendar = Calendar.getInstance()
    calendar.time = this
    Calendar.JANUARY
    val day = calendar.get(Calendar.DATE)
    var month = calendar.get(Calendar.MONTH)
    var year = calendar.get(Calendar.YEAR)
    month = (month + 9) / 12
    year = (year - month/10)
    return 365*year + year/4 - year/100 + year/400 + (month*306 + 5)/10 + (day - 1)
}

object Utils{
    fun buildDateFromTimestamp(timestamp: Int): Date{
        var year = (((10000L*timestamp.toLong()) + 14780L)/3652425L).toInt()
        var day = timestamp - (365*year + year/4 - year/100 + year/400)
        if (day < 0){
            year -= 1
            day = timestamp - (365*year + year/4 - year/100 + year/400)
        }
        val mi = (100*day + 52)/3060
        val month = (mi + 2) % 12 + 1
        year += (mi + 2) / 12
        day = day - (mi*306 + 5)/10 + 1
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        return calendar.time
    }

    fun buildDate(day: Int, month: Int, year: Int): Date{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        return calendar.time
    }
    /*
        123456 - > "12:34:56"
     */
    fun buildStringTimeFromInt(time: Int, pad: Int): String{
        if (time <= 0){
            return "00:".repeat(pad-1) + "00"
        }
        if(time < 100 && pad == 1){
            return time.toString().padStart(2,'0')
        }
        return "${buildStringTimeFromInt(time/100, pad - 1)}:${(time % 100).toString().padStart(2,'0')}"
    }
}
