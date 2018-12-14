package com.example.asvladimirov.randomuser.Adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asvladimirov.randomuser.Model.User;
import com.example.asvladimirov.randomuser.MyInterface.OnUserSelectedListener;
import com.example.asvladimirov.randomuser.R;

public class UserListAdapter extends PagedListAdapter<User, UserViewHolder> {

    private OnUserSelectedListener listener;

    public UserListAdapter() {
        super(User.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.bind(getItem(i), listener, i);
    }

    public void setOnUserSelectedListener(OnUserSelectedListener listener){
        this.listener = listener;
    }
}
