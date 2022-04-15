package com.crypto.api;

import android.view.View;

public interface Onclick {
    void onItemClicks(View view, int position, int i, String symbol, String baseAsset, String bidPrice, String highPrice, String lowPrice, String quoteAsset );

}
