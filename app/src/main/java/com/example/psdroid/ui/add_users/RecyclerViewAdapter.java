package com.example.psdroid.ui.add_users;
// Import Class
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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // Declaration
    public static  final String TAG = "RecyclerViewAdapter";
    private final ArrayList<String> name_array;
    private final ArrayList<String> phone_array;
    private final Context context;

    // Constructor for RecyclerViewAdapter
    public RecyclerViewAdapter(ArrayList<String> name_array, ArrayList<String> phone_array, Context context) {
        this.name_array = name_array;
        this.phone_array = phone_array;
        this.context = context;
    }

    // Constructor View Holder where all the layout elements are declared
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView con_name, con_phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            con_name = itemView.findViewById(R.id.email_title);
            con_phone = itemView.findViewById(R.id.username);
        }
    }

    // onCreate View Holder Constructors
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_content,parent,false);
        return new ViewHolder(view);
    }

    // Binding Function
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder function is called.");
        holder.con_name.setText(name_array.get(position));
        holder.con_phone.setText(phone_array.get(position));
    }

    // Counter
    @Override
    public int getItemCount() {
        // Tells the viewHolder know the number of items to be displayed
       return name_array.size();
    }
    //End of Code
}

