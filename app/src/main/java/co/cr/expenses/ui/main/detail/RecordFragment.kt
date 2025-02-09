package co.cr.expenses.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.cr.expenses.R
import co.cr.expenses.model.Detail
import kotlinx.android.synthetic.main.fragment_record.view.*

class RecordFragment(val deleteEvent: DeleteEvent): Fragment(){

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
        adapter = DetailAdapter(deleteEvent)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun updateList(list: MutableList<Detail>){
        adapter.updateList(list)
    }

}