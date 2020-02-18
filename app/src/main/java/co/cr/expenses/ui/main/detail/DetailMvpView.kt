package co.cr.expenses.ui.main.detail

import co.cr.expenses.model.Detail
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import co.cr.expenses.ui.base.MvpView

interface DetailMvpView: MvpView{

    fun onIncomeAdded()

    fun onExpenditureAdded()

    fun onIncomeFailed()

    fun onExpenditureFailed()

    fun onListIncome(list: List<Detail>)

    fun onListExpenditures(list: List<Detail>)

    fun onUpdateSummary(summary: Summary)

    fun onSummaryFailed()

    fun onExpenditureDeleted()

    fun onIncomeDeleted()

    fun onExpenditureDeleteFailed()

    fun onIncomeDeleteFailed()
}