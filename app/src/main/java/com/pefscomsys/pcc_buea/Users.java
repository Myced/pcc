package com.pefscomsys.pcc_buea;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Users {

    protected String name;
    protected String userId;
    protected String email;
    protected String phone_number;
    protected String password;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String username, String userId, String phone_number, String email, String password) {
        this.name = username;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("userId", userId);
        result.put("email", email);
        result.put("phone_number", phone_number);

        return result;
    }

}
