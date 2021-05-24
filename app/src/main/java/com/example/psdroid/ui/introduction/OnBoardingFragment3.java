package com.example.psdroid.ui.introduction;
//Import Class
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.psdroid.R;
import com.example.psdroid.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
// OnBoarding Fragment_1
public class OnBoardingFragment3 extends Fragment {
    FloatingActionButton fab;
    TextView skip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding3,container,false);
        skip = root.findViewById(R.id.skip);
        skip.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        return root;
    }
}
