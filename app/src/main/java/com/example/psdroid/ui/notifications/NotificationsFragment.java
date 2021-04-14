package com.example.psdroid.ui.notifications;
//Import class
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.psdroid.R;

//Notification Fragment
public class NotificationsFragment extends Fragment {
    public NotificationsFragment() {
        //Constructor
    }
    //Inflate view & Enable menus for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); //Enable options menu for this fragment
        return inflater.inflate(
                R.layout.fragment_notifications, container, false);
    }

    //Create the Notification Toolbar Menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu_1, @NonNull MenuInflater inflater_1) {
        menu_1.clear();
        inflater_1.inflate(R.menu.notificaitons_menu, menu_1);
        super.onCreateOptionsMenu(menu_1, inflater_1);
    }
}