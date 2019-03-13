package com.kazzi;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fetchPhoneNumber {

    Context context;
    String phone_no;
    private FirebaseAuth mAuth;


    public fetchPhoneNumber(Context context) {
        this.context = context;
    }

    public String getPhone_no() {
        return phone_no.replaceAll("\\s+","");
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void mynumber(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        setPhone_no(user.getPhoneNumber());
    }
}
