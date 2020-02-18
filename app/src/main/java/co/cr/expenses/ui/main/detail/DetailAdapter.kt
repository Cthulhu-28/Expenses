package co.cr.expenses.ui.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.cr.expenses.R
import co.cr.expenses.model.Detail
import co.cr.expenses.ui.base.ConfirmationDialog
import co.cr.expenses.utils.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailAdapter(var deleteEvent: DeleteEvent): RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    private var details: MutableList<Detail> = mutableListOf()

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = details[position]

        var top = View.VISIBLE
        var bottom = View.VISIBLE
        if(position == 0){
            top = View.INVISIBLE
        }
        if(position == itemCount-1){
            bottom = View.INVISIBLE
        }
        holder.topBar.visibility = top
        holder.bottomBar.visibility = bottom

        holder.tvTime.text = Utils.buildStringTimeFromInt(detail.time, 3)
        holder.tvAmount.text = detail.amount.toString()
        holder.tvDetails.text = detail.description
        Glide
            .with(holder.icon.context)
            .load(detail.generateDetailIcon())
            .into(holder.icon)

        holder.container.setOnLongClickListener {
            val dialog = ConfirmationDialog(
                it.context,
                it.context.getString(R.string.questions_delete_item),
                object: ConfirmationDialog.DialogConfirmationEvent{
                    override fun onAccept() {
                        deleteEvent.onDelete (detail){
                            details.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }

                    override fun onReject() {

                    }
                }
            ).show()
            true
        }
    }

    fun updateList(list: MutableList<Detail>){
        this.details = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView){
        var tvAmount: TextView = mView.tv_amount_item_detail
        var tvTime: TextView = mView.tv_time_item_detail
        var tvDetails: TextView = mView.tv_details_item_detail
        var topBar: View = mView.top_bar_item_detail
        var bottomBar: View = mView.bottom_bar_item_detail
        var icon: ImageView = mView.img_icon_item_detail
        var container: View = mView.container_item_detail
    }
}