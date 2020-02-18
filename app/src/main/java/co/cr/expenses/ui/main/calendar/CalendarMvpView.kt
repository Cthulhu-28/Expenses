package co.cr.expenses.ui.main.calendar

import co.cr.expenses.model.Summary
import co.cr.expenses.ui.base.MvpView

interface CalendarMvpView: MvpView{

    fun showSummary(summary: Summary)

    fun showDate(summary: Summary)
}