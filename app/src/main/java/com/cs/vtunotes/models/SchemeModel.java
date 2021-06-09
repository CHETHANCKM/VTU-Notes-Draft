package com.cs.vtunotes.models;

public class SchemeModel {
    String scheme_year;

    SchemeModel(){

    }

    public SchemeModel(String scheme_year) {
        this.scheme_year = scheme_year;
    }

    public String getScheme_year() {
        return scheme_year;
    }

    public void setScheme_year(String scheme_year) {
        this.scheme_year = scheme_year;
    }
}
