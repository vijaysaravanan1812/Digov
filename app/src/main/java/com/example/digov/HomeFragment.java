package com.example.digov;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class HomeFragment extends Fragment {
    @Nullable

    Button SignOut;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View HomeView = inflater.inflate(R.layout.fragment_home , container , false);

        SignOut = (Button)HomeView.findViewById(R.id.SignOutBtn);
        mAuth = FirebaseAuth.getInstance();

        SignOut.setOnClickListener(v->{
            mAuth.signOut();
            startActivity(new Intent( getActivity(), Login_activity.class) );

        });

        return  HomeView;

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user  = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent( getActivity(), Login_activity.class) );
        }
    }
}
