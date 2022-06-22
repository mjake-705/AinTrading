package com.ain.trading.customviews

interface CommonView {
    abstract fun onError(error : String?)

    abstract fun showProgress()

    abstract fun hideProgress()
}