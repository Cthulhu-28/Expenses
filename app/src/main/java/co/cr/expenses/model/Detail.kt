package co.cr.expenses.model

import androidx.annotation.DrawableRes
import co.cr.expenses.R
import com.google.firebase.database.Exclude
import java.text.SimpleDateFormat
import java.util.*

open class Detail{
    @get:Exclude
    var time: Int = 0

    constructor(){
        val calendar = Calendar.getInstance()
        time = format.format(calendar.time).toInt()
    }

    constructor(amount: Double, description: String, time: Int): this(amount, description){
       this.time = time
    }

    constructor(amount: Double, description: String): this(){
        this.amount = amount
        this.description = description
    }

    @DrawableRes
    open fun generateDetailIcon(): Int{
        return R.drawable.ic_casino_black_24dp
    }


    var amount: Double = 0.0
    var description: String = ""


    companion object{
        @SuppressWarnings
        val format = SimpleDateFormat("HHmmss")
    }
}