package com.example.asvladimirov.randomuser;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.asvladimirov.randomuser.Model.RequestFailure;
import com.example.asvladimirov.randomuser.Model.User;
import com.example.asvladimirov.randomuser.Model.UserList;
import com.example.asvladimirov.randomuser.MyInterface.RandomUserApi;
import com.example.asvladimirov.randomuser.MyInterface.Retryable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class UserDataSource extends PageKeyedDataSource<Integer, User> {

    final int results = 20;

    private RandomUserApi randomUserApi;
    private final MutableLiveData<RequestFailure> requestFailureLiveData;

    public UserDataSource(RandomUserApi randomUserApi) {
        this.randomUserApi = randomUserApi;
        this.requestFailureLiveData = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, User> callback) {
        final int page = 1;

        Call<UserList> call = randomUserApi.getUser(page, results);

        Callback<UserList> requestCallback = new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();

                if(userList == null){
                    onFailure(call, new HttpException(response));
                    return;
                }

                callback.onResult(
                        userList.getUsersArrayList(),
                        0,
                        999,
                        null,
                        page + 1
                );
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadInitial(params, callback);
                    }
                };

                handleError(retryable, t);
            }
        };

        call.enqueue(requestCallback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, User> callback) {
        final int page = params.key;

        Call<UserList> call = randomUserApi.getUser(page, results);

        final Callback<UserList> requestCallback = new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();

                if(userList == null){
                    onFailure(call, new HttpException(response));
                    return;
                }

                callback.onResult(
                        userList.getUsersArrayList(),
                        page +1
                );
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadAfter(params, callback);
                    }
                };

                handleError(retryable, t);
            }
        };

        call.enqueue(requestCallback);
    }

    public LiveData<RequestFailure> getRequestFailureLiveData() {
        return requestFailureLiveData;
    }

    private void handleError(Retryable retryable, Throwable t) {
        requestFailureLiveData.postValue(new RequestFailure(retryable, t.getMessage()));
    }
}
