package co.cr.expenses.model

import androidx.annotation.DrawableRes
import co.cr.expenses.R

class Expenditure: Detail(){

    var icon: Int = 0

    override fun generateDetailIcon(): Int {
        return ICONS[icon]
    }

    companion object{
        @JvmStatic
        @DrawableRes
        private val ICONS = listOf(
            R.drawable.ic_restaurant_black_24dp,
            R.drawable.ic_directions_bus_black_24dp,
            R.drawable.ic_local_taxi_black_24dp,
            R.drawable.ic_casino_black_24dp)
    }
}