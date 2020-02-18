package co.cr.expenses.ui.main.detail

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import co.cr.expenses.R
import co.cr.expenses.data.FirebaseDataManager
import co.cr.expenses.model.Detail
import co.cr.expenses.model.Expenditure
import co.cr.expenses.model.Summary
import co.cr.expenses.ui.base.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.util.*

class DetailActivity: BaseActivity(), DetailMvpView{

    private lateinit var tabAdapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var detailPresenter: DetailPresenter
    private lateinit var tvAmount: TextView
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var deleteExpenditureCallback: ()-> Unit
    private lateinit var deleteIncomeCallback: ()-> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabs)
        tvAmount = findViewById(R.id.tv_amount_activity_detail)
        btnAdd = findViewById(R.id.btn_add_act_detail)
        btnAdd.setOnClickListener {
            onAdd()
        }
        initTabLayout()

        detailPresenter = DetailPresenter(FirebaseDataManager())
        detailPresenter.attachView(this)
        val date = intent.getSerializableExtra(DATE_DETAIL_ACTIVITY) as Date
        detailPresenter.loadSummary(date)
        detailPresenter.loadExpenditures()
        detailPresenter.loadIncome()
    }

    private fun initTabLayout(){
        tabAdapter = TabAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        tabAdapter.addFragment(RecordFragment(onDeleteExpenditure), this.getString(R.string.titles_detail_activity_expenditures))
        tabAdapter.addFragment(RecordFragment(onDeleteIncome), this.getString(R.string.titles_detail_activity_income))
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                detailPresenter.loadSummary(false)
            }

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
        })
    }

    private fun onAdd(){
        val dialog = when(viewPager.currentItem){
            0->AddExpenditureDialog(
                mContext = this,
                event = object: AddExpenditureDialog.DialogConfirmationEvent{
                    override fun onClose() {

                    }

                    override fun onSave(expenditure: Expenditure) {
                        detailPresenter.addExpenditure(expenditure)
                    }
                })
            else->AddIncomeDialog(
                mContext = this,
                event = object: AddIncomeDialog.DialogConfirmationEvent{
                    override fun onClose() {

                    }

                    override fun onSave(detail: Detail) {
                        detailPresenter.addIncome(detail)
                    }
                })
        }
        dialog.show()
    }

    private val onDeleteExpenditure: DeleteEvent = object: DeleteEvent{

        override fun onDelete(detail: Detail, onDeleteConfirmed: () -> Unit) {
            deleteExpenditureCallback = onDeleteConfirmed
            detailPresenter.deleteExpenditure(detail as Expenditure)
        }
    }

    private val onDeleteIncome: DeleteEvent = object: DeleteEvent{

        override fun onDelete(detail: Detail, onDeleteConfirmed: () -> Unit) {
            deleteIncomeCallback = onDeleteConfirmed
        }
    }

    override fun onIncomeAdded() {
        detailPresenter.loadIncome()
        detailPresenter.loadSummary()
    }

    override fun onExpenditureAdded() {
        detailPresenter.loadExpenditures()
        detailPresenter.loadSummary()
    }

    override fun onIncomeFailed() {
        Toast.makeText(this, "I :(", Toast.LENGTH_SHORT).show()
    }

    override fun onExpenditureFailed() {
        Toast.makeText(this, "E :(", Toast.LENGTH_SHORT).show()
    }

    override fun onListIncome(list: List<Detail>) {
        tabAdapter.updateFragment(1, list.toMutableList())
    }

    override fun onListExpenditures(list: List<Detail>) {
        tabAdapter.updateFragment(0, list.toMutableList())
    }

    override fun onUpdateSummary(summary: Summary) {
        when(viewPager.currentItem){
            0->{
                tvAmount.text = summary.totalSpent.toString()
            }
            1->{
                tvAmount.text = summary.totalIncome.toString()
            }
        }
    }

    override fun onSummaryFailed() {

    }

    override fun onExpenditureDeleteFailed() {

    }

    override fun onIncomeDeleteFailed() {

    }

    override fun onExpenditureDeleted() {
        deleteExpenditureCallback()
        detailPresenter.loadSummary()
    }

    override fun onIncomeDeleted() {
        deleteIncomeCallback()
        detailPresenter.loadSummary()
    }

    companion object{
        const val DATE_DETAIL_ACTIVITY: String = "DATE_DETAIL_ACTIVITY"
    }

    override fun onDestroy() {
        detailPresenter.detachView()
        super.onDestroy()
    }
}