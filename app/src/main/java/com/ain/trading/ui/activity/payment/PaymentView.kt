package com.ain.trading.ui.activity.payment

import com.ain.trading.api.PaymentResponse
import com.ain.trading.api.PaymentTypeResponse
import com.ain.trading.customviews.CommonView

interface PaymentView:CommonView {
   fun onPaySuccesfully(body: PaymentResponse?)
    fun onPaymentTypeFetched(body: PaymentTypeResponse?)

}