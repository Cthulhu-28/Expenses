package co.cr.expenses.data.base

import co.cr.expenses.model.Detail
import co.cr.expenses.model.ExpendingDay
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import java.util.Date

interface DataManager{

    fun findExpendingDay(date: Date, event: DataEvent<ExpendingDay>)

    fun addIncome(day: ExpendingDay, income: Detail, event: DataEvent<ExpendingDay>)

    fun addExpenditure(day: ExpendingDay, expenditure: Expenditure, event: DataEvent<ExpendingDay>)

    fun getDaySummary(date: Date, event: DataEvent<Summary>)
}