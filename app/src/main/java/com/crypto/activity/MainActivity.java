package com.crypto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crypto.utils.CommonUtils;
import com.crypto.adapter.CryptoAdapter;
import com.crypto.api.Onclick;
import com.crypto.R;
import com.crypto.dialog.TransparentProgressDialog;
import com.crypto.api.GetDataService;
import com.crypto.api.RetrofitClientInstance;
import com.crypto.model.CryptoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCryptoRecyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    CryptoAdapter cryptoAdapter;
    ImageView backBtn;
    Onclick itemClick;
    List<CryptoResponse> lst_product;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvCryptoRecyclerView = findViewById(R.id.rv_crypto);
        callProductAPI();
        itemClick = new Onclick() {
            @Override
            public void onItemClicks(View view, int position, int i, String symbol, String baseAsset, String bidPrice, String highPrice, String lowPrice, String quoteAsset) {
                Toast.makeText(getApplicationContext(), symbol, Toast.LENGTH_SHORT).show();
                if (i==10){
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("symbol", symbol);
                    intent.putExtra("baseAsset", baseAsset);
                    intent.putExtra("bidPrice", bidPrice);
                    intent.putExtra("highPrice", highPrice);
                    intent.putExtra("lowPrice", lowPrice);
                    intent.putExtra("quoteAsset", quoteAsset);
                    startActivity(intent);

                }
            }
        };

        swipeContainer = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                callProductAPI();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void callProductAPI() {
        if (CommonUtils.isInternetOn(MainActivity.this)) {
            callProductListApi();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void callProductListApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(MainActivity.this);
        pd.show();
        Call<List<CryptoResponse>> call = service.cryptoList();
        call.enqueue(new Callback<List<CryptoResponse>>() {
            @Override
            public void onResponse(Call<List<CryptoResponse>> call, Response<List<CryptoResponse>> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                     lst_product = (ArrayList<CryptoResponse>) response.body();
                    if (lst_product == null) {
                        lst_product = new ArrayList<>();
                    }
                    RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvCryptoRecyclerView.setLayoutManager(RecyclerViewLayoutManager);
                    cryptoAdapter = new CryptoAdapter((ArrayList<CryptoResponse>) lst_product, getApplicationContext(), itemClick);
                    HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvCryptoRecyclerView.setLayoutManager(HorizontalLayout);
                    rvCryptoRecyclerView.setAdapter(cryptoAdapter);
                    swipeContainer.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<CryptoResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
            });

       }
}