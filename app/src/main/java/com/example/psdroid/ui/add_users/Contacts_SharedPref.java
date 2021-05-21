package com.example.psdroid.ui.add_users;
//Import Class

public class Contacts_SharedPref {
  /*  private static final String LIST_KEY = "list_key";

    // Data STORING
    public static void storeInList(Context context, ArrayList<String> arrayList1) {
        Gson gson = new Gson();   //GSON object to store Contact details
        String jsonString = gson.toJson(arrayList1);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }
    //Data RETRIEVING
    public static ArrayList<String> retrieveFromList(Context context,ArrayList<String> arrayList){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> returnedList = gson.fromJson(jsonString,type);
        return returnedList;
    }*/
}
