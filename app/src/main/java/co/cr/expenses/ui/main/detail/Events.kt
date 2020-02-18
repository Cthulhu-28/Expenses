package co.cr.expenses.ui.main.detail

import co.cr.expenses.model.Detail

interface DeleteEvent{
    fun onDelete(detail: Detail, onDeleteConfirmed: ()->Unit)
}