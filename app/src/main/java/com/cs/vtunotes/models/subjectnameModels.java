package com.cs.vtunotes.models;

public class subjectnameModels
{
    String subject_code, subject_name, scheme;

    subjectnameModels(){

    }

    public subjectnameModels(String subject_code, String subject_name, String scheme) {
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.scheme = scheme;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
