package com.crypto.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crypto.R;
import com.crypto.dialog.TransparentProgressDialog;
import com.crypto.api.GetDataService;
import com.crypto.api.RetrofitClientInstance;
import com.crypto.model.CryptoResponse;
import com.crypto.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    TextView symbolNameTxt, bidPriceTxt, highestPriceTxt,BuyTxt,sellTxt, lowPricesTxt, assetTxt;
    String symbolStr, baseAsset, bidPriceStr, lowPriceStr, highPriceStr, quoteAssetStr;
ImageView backImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            symbolStr = getIntent().getExtras().getString("symbol");
            baseAsset = getIntent().getExtras().getString("baseAsset");
            bidPriceStr = getIntent().getExtras().getString("bidPrice");
            lowPriceStr = getIntent().getExtras().getString("lowPrice");
            highPriceStr = getIntent().getExtras().getString("highPrice");
            quoteAssetStr = getIntent().getExtras().getString("quoteAsset");
            Log.e("symbolData ", "" + symbolStr);
            Log.e("baseAsset ", "" + baseAsset);
        }

        sellTxt = findViewById(R.id.sellTxt);
        BuyTxt = findViewById(R.id.BuyTxt);
        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assetTxt = findViewById(R.id.assetTxt);
        symbolNameTxt = findViewById(R.id.symbolNameTxt);
        bidPriceTxt = findViewById(R.id.bidPriceTxt);
        highestPriceTxt = findViewById(R.id.highestPriceTxt);
        lowPricesTxt = findViewById(R.id.lowPricesTxt);
        symbolNameTxt.setText(baseAsset);
        highestPriceTxt.setText(highPriceStr + " " + quoteAssetStr);
        bidPriceTxt.setText(bidPriceStr);
        lowPricesTxt.setText(lowPriceStr + " " + quoteAssetStr);
        assetTxt.setText(quoteAssetStr);
        callAPI();

        sellTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "SELL",Toast.LENGTH_SHORT).show();
            }
        });
        BuyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "BUY",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callAPI() {
        if (CommonUtils.isInternetOn(DetailActivity.this)) {
            callDetailsAPI(symbolStr);
        } else {
            Toast.makeText(DetailActivity.this, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }
    private void callDetailsAPI(String symbolStr) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("symbol", symbolStr);

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(DetailActivity.this);
        pd.show();
        Call<CryptoResponse> call = service.getDetails( hashMap);
        call.enqueue(new Callback<CryptoResponse>() {
            @Override
            public void onResponse(Call<CryptoResponse> call, Response<CryptoResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;


                    symbolNameTxt.setText(response.body().getBaseAsset());
                    highestPriceTxt.setText(response.body().getHighPrice());
                    bidPriceTxt.setText(response.body().getBidPrice());
                    lowPricesTxt.setText(response.body().getLowPrice());

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<CryptoResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}