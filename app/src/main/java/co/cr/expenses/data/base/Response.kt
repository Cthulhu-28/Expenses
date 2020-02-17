package co.cr.expenses.data.base

class Response<T>(var data: T){
    var successful: Boolean = true
}