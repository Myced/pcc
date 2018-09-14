package com.pefscomsys.pcc_buea;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Book {
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String display_name;
    private String category;
    private String url;

    public Book(){

    }

    public Book(String display_name, String category, String url) {
       this.setUrl(url);
       this.setDisplay_name(display_name);
       this.setCategory(category);
    }


    public String getDisplay_name() {
        return display_name;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("display_name", display_name);
        result.put("category", category);
        result.put("url", url);

        return result;
    }

}
