package com.example.asvladimirov.randomuser.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asvladimirov.randomuser.Model.User;
import com.example.asvladimirov.randomuser.MyInterface.OnUserSelectedListener;
import com.example.asvladimirov.randomuser.R;
import com.squareup.picasso.Picasso;

public class UserViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageUser;
    public TextView titleNameUser;
    public TextView firstNameUser;
    public TextView lastNameUser;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        imageUser = itemView.findViewById(R.id.imageUser);
        titleNameUser = itemView.findViewById(R.id.titleNameUser);
        firstNameUser = itemView.findViewById(R.id.firstNameUser);
        lastNameUser = itemView.findViewById(R.id.lastNameUser);
    }

    void bind(User users, final OnUserSelectedListener listener, int i){
        String title = users.getName().getTitle();
        String firstName = users.getName().getFirstName();
        String lastName = users.getName().getLastName();
        if(users != null){
            Picasso.get()
                    .load(users.getPicture().getMedium())
                    .transform(new CircularTransformation())
                    .resize(100, 100)
                    .into(imageUser);


            title = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();
            firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
            lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

            titleNameUser.setText(title);
            firstNameUser.setText(firstName);
            lastNameUser.setText(lastName);
        } else {
            firstNameUser.setText("Loading...");
        }

        final String firstNameFinal = firstName;
        final String lastNameFinal = lastName;
        final String picture = users != null ? users.getPicture().getLarge() : null;
        final String gender = users != null ? users.getGender() : null;
        final String DOB = users != null ? users.getDob().getDate() : null;
        final String age = users != null ? users.getDob().getAge() : null;
        final String phone = users != null ? users.getPhone() : null;
        final String cell = users != null ? users.getCell() : null;
        final String email = users != null ? users.getEmail() : null;
        final String postcode = users != null ? users.getLocation().getPostcode() : null;
        final String state = users != null ? users.getLocation().getState() : null;
        final String city = users != null ? users.getLocation().getCity() : null;
        final String street = users != null ? users.getLocation().getStreet() : null;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onUserSelectedListened(picture, firstNameFinal, lastNameFinal, gender, DOB, age, phone, cell, email, postcode, state, city, street);
                }
            }
        });
    }
}
