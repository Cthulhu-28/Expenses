package co.cr.expenses.ui.main.detail

import co.cr.expenses.data.base.DataEvent
import co.cr.expenses.data.base.DataManager
import co.cr.expenses.model.Detail
import co.cr.expenses.model.ExpendingDay
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import co.cr.expenses.ui.base.BasePresenter
import java.lang.RuntimeException
import java.util.*

class DetailPresenter (val dataManager: DataManager): BasePresenter<DetailMvpView>(){

    private var date: Date? = null
    private lateinit var day: ExpendingDay
    private var summary: Summary? = null

    fun loadSummary(reload: Boolean = true) = loadSummary(date!!, reload)

    fun loadSummary(date: Date, reload: Boolean = true){
        this.date = date
        if(summary == null) {
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
        }else{
            mvpView?.onUpdateSummary(summary!!)
        }
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

    fun deleteExpenditure(expenditure: Expenditure){
        checkDate()
        dataManager.deleteExpenditure(date!!, expenditure,
            DataEvent(
                onSuccess = {
                    mvpView?.onExpenditureDeleted()
                },
                onError = {
                    mvpView?.onExpenditureFailed()
                }
            )
        )
    }

    fun deleteIncome(detail: Detail){
        checkDate()
        dataManager.deleteIncome(date!!, detail,
            DataEvent(
                onSuccess = {
                    mvpView?.onIncomeDeleted()
                },
                onError = {
                    mvpView?.onIncomeFailed()
                }
            )
        )
    }

    fun loadExpenditures(){
        checkDate()
        dataManager.getExpenditures(date!!,
            DataEvent(
                onSuccess = {
                    mvpView?.onListExpenditures(it.data)
                },
                onError = {

                }
            )
        )
    }

    fun loadIncome(){
        checkDate()
        dataManager.getIncome(date!!,
            DataEvent(
                onSuccess = {
                    mvpView?.onListIncome(it.data)
                },
                onError = {

                }
            )
        )
    }

    private fun checkDate(){
        if (date == null)
            throw DateNotSetException("The date is null")
    }

    inner class DateNotSetException(exception: String): RuntimeException(exception)

    override fun detachView() {
        super.detachView()
        dataManager.destroy()
    }

}