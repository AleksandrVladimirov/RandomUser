package com.example.asvladimirov.randomuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asvladimirov.randomuser.Adapter.CircularTransformation;
import com.squareup.picasso.Picasso;

public class UserSelectedActivity extends AppCompatActivity {

    private ImageView imageUserFull;
    private TextView firstName, lastName, gender, dob, age, phone, email, street, city, state, postcode;
    private ImageButton call, emailButton;

    private String FirstName, LastName, Gender, DOB, Age, Phone, Email, Street, City, State, Postcode, Image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selected);

        imageUserFull = findViewById(R.id.imageUserFull);
        firstName = findViewById(R.id.firstUserName);
        lastName = findViewById(R.id.lastUserName);
        gender = findViewById(R.id.userGender);
        dob = findViewById(R.id.userDOB);
        age = findViewById(R.id.userAge);
        phone = findViewById(R.id.userPhone);
        email = findViewById(R.id.userEmail);
        street = findViewById(R.id.userStreet);
        city = findViewById(R.id.userCity);
        state = findViewById(R.id.userState);
        postcode = findViewById(R.id.userPostcode);
        call = findViewById(R.id.callButton);
        emailButton = findViewById(R.id.emailButton);

        setValue();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Phone));
                if (ActivityCompat.checkSelfPermission(UserSelectedActivity.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + Email));
                startActivity(emailIntent);
            }
        });
    }

    private void setValue() {
        Image = getIntent().getStringExtra("IMAGE");
        FirstName = getIntent().getStringExtra("FIRST_NAME");
        LastName = getIntent().getStringExtra("LAST_NAME");
        Gender = getIntent().getStringExtra("GENDER");
        DOB = getIntent().getStringExtra("DATE_OF_BD");
        Age = getIntent().getStringExtra("AGE");
        Phone = getIntent().getStringExtra("PHONE");
        Email = getIntent().getStringExtra("EMAIL");
        Postcode = getIntent().getStringExtra("POSTCODE");
        State = getIntent().getStringExtra("STATE");
        City = getIntent().getStringExtra("CITY");
        Street = getIntent().getStringExtra("STREET");

        Street = Street.substring(0,1).toUpperCase() + Street.substring(1).toLowerCase();
        City = City.substring(0,1).toUpperCase() + City.substring(1).toLowerCase();
        State = State.substring(0,1).toUpperCase() + State.substring(1).toLowerCase();

        Picasso.get()
                .load(Image)
                .transform(new CircularTransformation())
                .resize(250, 250)
                .into(imageUserFull);

        firstName.setText(FirstName);
        lastName.setText(LastName);
        gender.setText(Gender);
        dob.setText(DOB.substring(0,10));
        age.setText("(" +Age + " years old)");
        phone.setText(Phone);
        email.setText(Email);
        street.setText(Street);
        city.setText(City);
        state.setText(State);
        postcode.setText(Postcode);
    }
}
