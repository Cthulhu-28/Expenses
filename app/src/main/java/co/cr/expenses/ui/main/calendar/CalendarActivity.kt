package co.cr.expenses.ui.main.calendar

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import androidx.core.content.res.ResourcesCompat
import co.cr.expenses.R
import co.cr.expenses.utils.Utils
import co.cr.expenses.ui.base.BaseActivity
import co.cr.expenses.ui.main.detail.DetailActivity
import co.cr.expenses.utils.FontCrawler

class CalendarActivity: BaseActivity(), CalendarMvpView{

    private lateinit var calendar: CalendarView
    private lateinit var calendarPresenter: CalendarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        calendar = findViewById(R.id.calendar_calendar_act)
        attachCalendarEvents()

        calendarPresenter = CalendarPresenter()
        calendarPresenter.attachView(this)

        val typeFace = ResourcesCompat.getFont(this, R.font.nunito_bold)
        val crawler = FontCrawler(typeFace!!)
        crawler.replaceFonts(calendar)


    }


    override fun showDate() {
        //Toast.makeText(this, "Date shown!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    override fun showSummary() {

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