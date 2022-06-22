package com.ain.trading.ui.activity.search

import com.ain.trading.api.SearchResponse
import com.ain.trading.customviews.CommonView

interface SearchView: CommonView {
    fun onSearchDataFetched(body: SearchResponse?)
}