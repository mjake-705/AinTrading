package com.ain.trading.ui.activity.orders

import com.ain.trading.api.OrderlistResponse
import com.ain.trading.api.Orders
import com.ain.trading.customviews.CommonView

interface OrdersView : CommonView{
  fun onOrdersFetched(body: Orders?)
  fun onOrdersFetchedItemFetched(body: OrderlistResponse?)
}