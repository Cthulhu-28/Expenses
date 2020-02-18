package co.cr.expenses.ui.main.calendar

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import co.cr.expenses.R
import co.cr.expenses.data.FirebaseDataManager
import co.cr.expenses.model.Summary
import co.cr.expenses.utils.Utils
import co.cr.expenses.ui.base.BaseActivity
import co.cr.expenses.ui.main.detail.DetailActivity
import co.cr.expenses.utils.FontCrawler
import java.util.*

class CalendarActivity: BaseActivity(), CalendarMvpView{

    private lateinit var calendar: CalendarView
    private lateinit var tvTotal: TextView
    private lateinit var tvIncome: TextView
    private lateinit var tvSpent: TextView
    private lateinit var calendarPresenter: CalendarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        calendar = findViewById(R.id.calendar_calendar_act)
        tvIncome = findViewById(R.id.tv_income)
        tvSpent = findViewById(R.id.tv_spent)
        tvTotal = findViewById(R.id.tv_total)
        attachCalendarEvents()

        calendarPresenter = CalendarPresenter(FirebaseDataManager())
        calendarPresenter.attachView(this)

//        val typeFace = ResourcesCompat.getFont(this, R.font.nunito_bold)
//        val crawler = FontCrawler(typeFace!!)
//        crawler.replaceFonts(calendar)

        calendarPresenter.loadDateSummary(Calendar.getInstance().time)
        calendarPresenter.loadSummary()

    }

    override fun onResume() {
        super.onResume()
        calendarPresenter.loadSummary()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this.calendar.date
        calendarPresenter.loadDateSummary(calendar.time)
    }

    override fun showDate(summary: Summary) {
        tvSpent.text = summary.totalSpent.toString()
        tvIncome.text = summary.totalIncome.toString()
    }

    override fun showSummary(summary: Summary) {
        tvTotal.text = (summary.totalIncome - summary.totalSpent).toString()
    }

    private fun attachCalendarEvents(){
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //calendarPresenter.loadDateSummary(Utils.buildDate(dayOfMonth, month, year))
            val intent = Intent(this, DetailActivity::class.java)
            val date = Utils.buildDate(dayOfMonth, month, year)
            intent.putExtra(DetailActivity.DATE_DETAIL_ACTIVITY, date)
            startActivity(intent)
        }
    }
}