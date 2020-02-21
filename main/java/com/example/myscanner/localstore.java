package com.example.myscanner;

import android.content.Context;
import android.content.SharedPreferences;

// store data locally on the phone using sharedpreference

class localstore {
    private static final String Sh_Pr_Name = "user Details";
    private static SharedPreferences localStoreDatabase;   // use shared preference to store

    localstore(Context context){
        localStoreDatabase = context.getSharedPreferences(Sh_Pr_Name,0);
    }

    // add details to the database
    void localStore(User user){
        SharedPreferences.Editor spEditor = localStoreDatabase.edit();
        spEditor.putString("fullName", user.fullname);
        spEditor.putString("Username", user.username);
        spEditor.putString("Password", user.password);
        spEditor.putInt("Student_id", user.student_id);
        spEditor.apply();  //scave changes
    }

    // to retrieve data of logged in user
    public User getLoggedIn (){
        String fullname = localStoreDatabase.getString("fullName", "");
        String username = localStoreDatabase.getString("Username", "");
        String password= localStoreDatabase.getString("Password", "");
        int student_id = localStoreDatabase.getInt("Student_id", 0);

        // give new users these attributes
        User storedUser = new User(fullname, username, password, student_id);
        return storedUser;
    }

    static boolean getUserLoggedIn (){
        return localStoreDatabase.getBoolean("loggeddIn", false);
    }

    //if a user is logged in set to true otherwise false
    public void setloggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = localStoreDatabase.edit();
        spEditor.putBoolean("loggeddIn", loggedIn);
        spEditor.commit();
    }

    // to clear users details
    public void clearUserData(){
        SharedPreferences.Editor spEditor = localStoreDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}
