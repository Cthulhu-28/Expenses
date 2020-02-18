package co.cr.expenses.ui.main.detail;

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import co.cr.expenses.R
import co.cr.expenses.model.Detail

class AddIncomeDialog(mContext: Context, private var event: DialogConfirmationEvent): Dialog(mContext) {

    private var btnClose: ImageButton
    private var btnSave: Button
    private var txtAmount: EditText
    private var txtDetails: EditText

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_income)

        txtAmount = findViewById(R.id.txt_amount_dialog_add_income)
        txtDetails = findViewById(R.id.txt_details_dialog_add_income)

        btnClose = findViewById(R.id.btn_close_dialog_add_income)
        btnClose.setOnClickListener {
            dismiss()
            event.onClose()
        }
        btnSave = findViewById(R.id.btn_save_dialog_add_income)
        btnSave.setOnClickListener{
            if(txtAmount.text.isNotEmpty() && txtDetails.text.isNotEmpty()){
                dismiss()
                val amount = txtAmount.text.toString().toDouble()
                val details = txtDetails.text.toString()
                val detail = Detail()
                detail.amount =  amount
                detail.description = details
                event.onSave(detail)
            }
        }
    }

    interface DialogConfirmationEvent{

        fun onSave(detail: Detail)

        fun onClose()
    }
}
