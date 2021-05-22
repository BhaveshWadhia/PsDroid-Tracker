package com.example.psdroid.ui.add_users;
//Import Class
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
// Constacts Shared Preference
public class Contacts_SharedPref {
  private static final String LIST1_KEY = "list1_key";
  private static final String LIST2_KEY = "list2_key";

    // Data STORING of Name
    public static void store_nameInList(Context context, ArrayList<String> arrayList1) {
        Gson gson = new Gson();   //GSON object to store Contact details
        String jsonString = gson.toJson(arrayList1);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST1_KEY, jsonString);
        editor.apply();
    }
    //Data RETRIEVING of Name
    public static ArrayList<String> retrieve_nameFromList(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST1_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> returnedList = gson.fromJson(jsonString,type);
        if (returnedList == null)
        {
            returnedList = new ArrayList<>();
        }
         return returnedList;
    }
    // Data STORING of Phone Number
    public static void store_phoneInList(Context context, ArrayList<String> arrayList2) {
        Gson gson = new Gson();   //GSON object to store Contact details
        String jsonString = gson.toJson(arrayList2);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST2_KEY, jsonString);
        editor.apply();
    }
    //Data RETRIEVING of Name
    public static ArrayList<String> retrieve_phoneFromList(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST2_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> returnedList = gson.fromJson(jsonString,type);
        if (returnedList == null)
        {
            returnedList = new ArrayList<>();
        }
        return returnedList;
    }
}
