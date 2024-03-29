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
// OnBoarding Fragment_1
public class OnBoardingFragment2 extends Fragment {
    TextView skip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding2,container,false);
        skip = root.findViewById(R.id.skip);
        skip.setOnClickListener(v -> {
           startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
        return root;
    }
    //End of Code
}
