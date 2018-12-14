package com.example.asvladimirov.randomuser;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.asvladimirov.randomuser.Adapter.UserListAdapter;
import com.example.asvladimirov.randomuser.Executor.MainThreadExecutor;
import com.example.asvladimirov.randomuser.Model.RequestFailure;
import com.example.asvladimirov.randomuser.Model.User;
import com.example.asvladimirov.randomuser.MyInterface.OnUserSelectedListener;
import com.example.asvladimirov.randomuser.MyInterface.RandomUserApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private UserListAdapter adapter;
    private MainThreadExecutor executor;
    private RandomUserApi randomUserApi;
    public static Retrofit retrofit;

    private String BASE_URL = "https://randomuser.me/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = new MainThreadExecutor();

        if(isOnline()){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }

        randomUserApi = retrofit.create(RandomUserApi.class);

        setupRecyclerView();
        setupSwipeRefresh();
        setupDataSource();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout= findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupDataSource();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupDataSource() {
        UserDataSource dataSource = new UserDataSource(randomUserApi);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(40)
                .setEnablePlaceholders(true)
                .build();

        PagedList<User> users = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(executor)
                .setNotifyExecutor(executor)
                .build();

        adapter.submitList(users);

        dataSource.getRequestFailureLiveData().observe(this, new Observer<RequestFailure>() {
            @Override
            public void onChanged(@Nullable final RequestFailure requestFailure) {
                if (requestFailure == null) return;

                Snackbar.make(findViewById(android.R.id.content), requestFailure.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestFailure.getRetryable().retry();
                            }
                        }).show();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new UserListAdapter();

        adapter.setOnUserSelectedListener(new OnUserSelectedListener() {
            @Override
            public void onUserSelectedListened(String picture, String firstName, String lastName, String gender, String DOB, String age, String phone, String cell, String email, String postcode, String state, String city, String street) {
                Intent intent = new Intent(MainActivity.this, UserSelectedActivity.class);
                intent.putExtra("IMAGE", picture);
                intent.putExtra("FIRST_NAME", firstName);
                intent.putExtra("LAST_NAME", lastName);
                intent.putExtra("GENDER", gender);
                intent.putExtra("DATE_OF_BD", DOB);
                intent.putExtra("AGE", age);
                intent.putExtra("PHONE", cell);
                intent.putExtra("EMAIL", email);
                intent.putExtra("POSTCODE", postcode);
                intent.putExtra("STATE", state);
                intent.putExtra("CITY", city);
                intent.putExtra("STREET", street);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);
    }
}
