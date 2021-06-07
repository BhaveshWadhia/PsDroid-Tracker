package com.example.psdroid.ui.home;
//Import Class
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
//Where are you recycler adapter
public class WRY_RecyclerViewAdapter extends RecyclerView.Adapter<WRY_RecyclerViewAdapter.ViewHolder> {
    //Declaration
    static final String TAG = "RecyclerViewAdapter";
    private final ArrayList<String> name_array;
    private ArrayList<String> phone_array = new ArrayList<>();
    private final Context context;
    private final clickInterface mClickInterfaceListener;

    // Constructor for RecyclerViewAdapter
    public WRY_RecyclerViewAdapter(ArrayList<String> name_array, ArrayList<String> phone_array, Context context, clickInterface onclickListener) {
        this.name_array = name_array;
        this.phone_array = phone_array;
        this.context = context;
        this.mClickInterfaceListener = onclickListener;
    }

    //onCreate View Holder Constructors
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_content,parent,false);
        return new ViewHolder(view,mClickInterfaceListener);
    }

    // Constructor View Holder where all the layout elements are declared
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView con_name, con_phone;
        clickInterface onclickListener;
        public ViewHolder(@NonNull View itemView, clickInterface onclickListener) {
            super(itemView);
            con_name = itemView.findViewById(R.id.email_title);
            con_phone = itemView.findViewById(R.id.username);
            this.onclickListener = onclickListener;
            itemView.setOnClickListener(view -> onclickListener.onClicked(getAdapterPosition()));
        }
    }

    //Binding Function
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder function is called.");
        holder.con_name.setText(name_array.get(position));
        holder.con_phone.setText(phone_array.get(position));
    }
    //Counter
    @Override
    public int getItemCount() {
        // Tells the viewHolder know the number of items to be displayed
        return name_array.size();
    }

    //Interface for clickLister of RecyclerView
    public  interface clickInterface {
        void onClicked(int pos);
    }
    //End of Code
}

