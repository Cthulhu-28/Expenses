package co.cr.expenses.ui.base

interface Presenter<V: MvpView>{

    fun attachView(mvpView: V)

    fun detachView()
}