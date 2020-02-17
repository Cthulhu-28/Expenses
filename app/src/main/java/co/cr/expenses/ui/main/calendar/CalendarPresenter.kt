package co.cr.expenses.ui.main.calendar

import co.cr.expenses.ui.base.BasePresenter
import java.util.*

class CalendarPresenter: BasePresenter<CalendarMvpView>(){

    fun loadSummary(date: Date){
        mvpView?.showSummary()
    }

    fun loadDateSummary(date: Date){
        mvpView?.showDate()
    }
}