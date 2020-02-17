package co.cr.expenses.ui.main.detail;

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import co.cr.expenses.R
import co.cr.expenses.model.Expenditure

class AddExpenditureDialog(mContext: Context, private var event: DialogConfirmationEvent): Dialog(mContext) {

    private var btnClose: ImageButton
    private var btnSave: Button
    private var txtAmount: EditText
    private var txtDetails: EditText
    private var radioGroup: RadioGroup
    private var rbOptions: HashMap<Int, Int>

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_expenditure)

        txtAmount = findViewById(R.id.txt_amount_dialog_add_expenditure)
        txtDetails = findViewById(R.id.txt_details_dialog_add_expenditure)
        radioGroup = findViewById(R.id.radio_group_dialog_add_expenditure)
        rbOptions = hashMapOf(
            R.id.rb_food_dialog_add_expenditure to 0,
            R.id.rb_bus_dialog_add_expenditure to 1,
            R.id.rb_taxi_dialog_add_expenditure to 2,
            R.id.rb_other_dialog_add_expenditure to 3)

        btnClose = findViewById(R.id.btn_close_dialog_add_expenditure)
        btnClose.setOnClickListener {
            dismiss()
            event.onClose()
        }
        btnSave = findViewById(R.id.btn_save_dialog_add_expenditure)
        btnSave.setOnClickListener{
            if(txtAmount.text.isNotEmpty() && txtDetails.text.isNotEmpty()){
                dismiss()
                val icon = rbOptions[radioGroup.checkedRadioButtonId]
                val amount = txtAmount.text.toString().toDouble()
                val details = txtDetails.text.toString()
                val expenditure = Expenditure()
                expenditure.icon = if (icon == null) 3 else icon
                expenditure.amount =  amount
                expenditure.description = details
                event.onSave(expenditure)
            }
        }
    }

    interface DialogConfirmationEvent{

        fun onSave(expenditure: Expenditure)

        fun onClose()
    }
}
