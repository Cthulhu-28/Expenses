package co.cr.expenses.ui.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.cr.expenses.R
import co.cr.expenses.model.Detail
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailAdapter: RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    private var details: List<Detail> = mutableListOf()

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var top = View.VISIBLE
        var bottom = View.VISIBLE
        if(position == 0){
            top = View.INVISIBLE
        }else if(position == itemCount-1){
            bottom = View.INVISIBLE
        }
        holder.top_bar.visibility = top
        holder.bottom_bar.visibility = bottom
    }

    fun updateList(list: List<Detail>){
        this.details = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView){
        var tv_amount = mView.tv_amount_item_detail
        var tv_time = mView.tv_amount_item_detail
        var top_bar = mView.top_bar_item_detail
        var bottom_bar = mView.bottom_bar_item_detail
    }
}