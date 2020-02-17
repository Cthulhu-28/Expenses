package co.cr.expenses.ui.main.detail

import co.cr.expenses.data.base.DataEvent
import co.cr.expenses.data.base.DataManager
import co.cr.expenses.model.Detail
import co.cr.expenses.model.ExpendingDay
import co.cr.expenses.model.Expenditure
import co.cr.expenses.ui.base.BasePresenter
import java.lang.RuntimeException
import java.util.*

class DetailPresenter (val dataManager: DataManager): BasePresenter<DetailMvpView>(){

    private var date: Date? = null
    private lateinit var day: ExpendingDay

    fun loadSummary(date: Date){
        this.date = date
        dataManager.getDaySummary(
            date,
            DataEvent(
                onSuccess = {
                    mvpView?.onUpdateSummary(it.data)
                },
                onError = {
                    mvpView?.onSummaryFailed()
                }
            )
        )
    }

    fun addIncome(income: Detail){
        if(date == null)
            throw DateNotSetException("The date is null")
        else{
            dataManager.findExpendingDay(
                date!!,
                DataEvent(
                    onSuccess = {
                        day = it.data
                        dataManager.addIncome(day, income,
                            DataEvent(
                                onSuccess = {
                                    mvpView?.onIncomeAdded()
                                },
                                onError = {
                                    mvpView?.onIncomeFailed()
                                }
                            )
                        )
                    },
                    onError = {
                        mvpView?.onIncomeFailed()
                    }
                )
            )
        }
    }

    fun addExpenditure(expenditure: Expenditure){
        if(date == null)
            throw DateNotSetException("The date is null")
        else{
            dataManager.findExpendingDay(
                date!!,
                DataEvent(
                    onSuccess = {
                        day = it.data
                        dataManager.addExpenditure(day, expenditure,
                            DataEvent(
                                onSuccess = {
                                    mvpView?.onExpenditureAdded()
                                },
                                onError = {
                                    mvpView?.onExpenditureFailed()
                                }
                            )
                        )
                    },
                    onError = {
                        mvpView?.onIncomeFailed()
                    }
                )
            )
        }
    }

    inner class DateNotSetException(exception: String): RuntimeException(exception)

}