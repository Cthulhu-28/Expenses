package co.cr.expenses.data.base

class DataEvent<T> (
    var onSuccess: (Response<T>) -> Unit,
    var onError: (String) -> Unit
)
