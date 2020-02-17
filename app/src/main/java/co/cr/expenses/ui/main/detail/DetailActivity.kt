package co.cr.expenses.ui.main.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.cr.expenses.R
import co.cr.expenses.data.FirebaseDataManager
import co.cr.expenses.data.base.DataEvent
import co.cr.expenses.model.Detail
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import co.cr.expenses.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.core.Context
import java.util.*

class DetailActivity: BaseActivity(), DetailMvpView{


    private lateinit var tabAdapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabs)
        initTabLayout()

        detailPresenter = DetailPresenter(FirebaseDataManager())
        detailPresenter.attachView(this)
        val date = intent.getSerializableExtra(DATE_DETAIL_ACTIVITY) as Date
        detailPresenter.loadSummary(date)
    }

    private fun initTabLayout(){
        tabAdapter = TabAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        tabAdapter.addFragment(RecordFragment(), this.getString(R.string.titles_detail_activity_expenditures))
        tabAdapter.addFragment(RecordFragment(), this.getString(R.string.titles_detail_activity_income))
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun onAdd(view: View){
        val dialog = AddExpenditureDialog(
            mContext = this,
            event = object: AddExpenditureDialog.DialogConfirmationEvent{
                override fun onClose() {

                }

                override fun onSave(expenditure: Expenditure) {
                    detailPresenter.addExpenditure(expenditure)
                }
            }
        )
        dialog.show()
    }


    override fun onIncomeAdded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onExpenditureAdded() {
        Toast.makeText(this, ":)", Toast.LENGTH_SHORT).show()
    }

    override fun onIncomeFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onExpenditureFailed() {
        Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show()
    }

    override fun onListIncome(list: List<Detail>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListExpenditures(list: List<Detail>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdateSummary(summary: Summary) {

    }

    override fun onSummaryFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object{
        val DATE_DETAIL_ACTIVITY = "DATE_DETAIL_ACTIVITY"
    }
}