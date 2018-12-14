package com.example.asvladimirov.randomuser.Model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("gender")
    private String gender;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("cell")
    private String cell;
    @SerializedName("nat")
    private String nat;
    @SerializedName("name")
    private Name name;
    @SerializedName("location")
    private Location location;
    @SerializedName("dob")
    private DOB dob;
    @SerializedName("picture")
    private Picture picture;

    public User() {
    }

    public User(String gender, String email, String phone, String cell, String nat, Name name, Location location, DOB dob, Picture picture) {
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
        this.nat = nat;
        this.name = name;
        this.location = location;
        this.dob = dob;
        this.picture = picture;
    }

    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {

        @Override
        public boolean areItemsTheSame(@NonNull User user, @NonNull User t1) {
            return user.getPhone().equals(t1.getPhone());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User user, @NonNull User t1) {
            return true;
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DOB getDob() {
        return dob;
    }

    public void setDob(DOB dob) {
        this.dob = dob;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
