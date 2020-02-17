package co.cr.expenses.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.cr.expenses.R
import kotlinx.android.synthetic.main.fragment_record.view.*

class RecordFragment: Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.listRecords
        adapter = DetailAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun updateList(){
        adapter.notifyDataSetChanged()
    }

}