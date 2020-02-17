package co.cr.expenses.ui.main.calendar

import co.cr.expenses.ui.base.MvpView

interface CalendarMvpView: MvpView{

    fun showSummary()

    fun showDate()
}