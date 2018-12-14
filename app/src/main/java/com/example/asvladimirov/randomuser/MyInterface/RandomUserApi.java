package com.example.asvladimirov.randomuser.MyInterface;

import com.example.asvladimirov.randomuser.Model.UserList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApi {

    @GET("api/?seed=abc")
    Call<UserList> getUser (@Query("page") int page,
                            @Query("results") int results);

}
