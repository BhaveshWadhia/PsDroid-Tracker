package com.example.psdroid.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.psdroid.R;
import java.util.ArrayList;

public class WRY_RecyclerViewAdapter extends RecyclerView.Adapter<WRY_RecyclerViewAdapter.ViewHolder> {
    public static  final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> name_array,phone_array = new ArrayList<>();
    private Context context;
    private clickInterface clickInterfaceListener;

    // Constructor for RecyclerViewAdapter
    public WRY_RecyclerViewAdapter(ArrayList<String> name_array, ArrayList<String> phone_array, Context context, clickInterface clickInterfaceListener) {
        this.name_array = name_array;
        this.phone_array = phone_array;
        this.context = context;
        this.clickInterfaceListener = clickInterfaceListener;
    }

    //onCreate View Holder Constructors
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_content,parent,false);
        return new ViewHolder(view,clickInterfaceListener);
    }

    // Constructor View Holder where all the layout elements are declared
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView con_name, con_phone;
        clickInterface clickInterfaceListener;
        public ViewHolder(@NonNull View itemView, clickInterface clickInterfaceListener) {
            super(itemView);
            con_name = itemView.findViewById(R.id.contact_name);
            con_phone = itemView.findViewById(R.id.contact_phone);
            itemView.setOnClickListener(this);
        }
        //Click Listener for the interface
        @Override
        public void onClick(View view) {
            clickInterfaceListener.onClicked(getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder function is called.");
        holder.con_name.setText(name_array.get(position));
        holder.con_phone.setText(phone_array.get(position));
    }

    @Override
    public int getItemCount() {
        // Tells the viewHolder know the number of items to be displayed
        return name_array.size();
    }

    //Interface for clickLister of RecyclerView
    public  interface clickInterface {
        public void onClicked(int pos);
    }
}

