package co.cr.expenses.ui.base

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.*
import co.cr.expenses.R

class ConfirmationDialog(mContext: Context, private val prompt: String, private var event: DialogConfirmationEvent): Dialog(mContext) {

    private var btnClose: ImageButton
    private var btnAccept: Button
    private var btnReject: Button
    private var tvPrompt: TextView

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_confirmation)

        btnClose = findViewById(R.id.btn_close_dialog_confirmation)
        btnClose.setOnClickListener {
            dismiss()
            event.onReject()
        }
        btnAccept = findViewById(R.id.btn_confirm_dialog_add_expenditure)
        btnAccept.setOnClickListener{
            dismiss()
            event.onAccept()
        }

        btnReject = findViewById(R.id.btn_reject_dialog_add_expenditure)
        btnReject.setOnClickListener {
            dismiss()
            event.onReject()
        }

        tvPrompt = findViewById(R.id.tv_prompt_dialog_confirmation)
        tvPrompt.text = prompt

        setOnCancelListener {
            event.onReject()
            dismiss()
        }
    }

    interface DialogConfirmationEvent{

        fun onAccept()

        fun onReject()
    }
}
