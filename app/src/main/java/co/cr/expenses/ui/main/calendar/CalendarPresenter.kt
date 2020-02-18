package co.cr.expenses.ui.main.calendar

import co.cr.expenses.data.base.DataEvent
import co.cr.expenses.data.base.DataManager
import co.cr.expenses.ui.base.BasePresenter
import java.util.*

class CalendarPresenter(private val dataManager: DataManager): BasePresenter<CalendarMvpView>(){

    fun loadSummary(){
        dataManager.getSummary(
            DataEvent(
                onSuccess = {
                    mvpView?.showDate(it.data)
                },
                onError = {

                }
            )
        )
    }

    fun loadDateSummary(date: Date){
        dataManager.getDaySummary(date,
            DataEvent(
                onSuccess = {
                    mvpView?.showSummary(it.data)
                },
                onError = {

                }
            ), false
        )
    }
}