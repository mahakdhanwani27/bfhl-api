package com.bajaj.bfhl.model;

import java.util.List;

public class ResponseData {
    private boolean is_success = true;
    private String user_id = "mahak_dhanwani";
    private String email = "mahakdhanwani27@gmail.com";
    private String roll_number = "0827CI221085";
    private List<String> numbers;
    private List<String> alphabets;

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<String> getAlphabets() {
        return alphabets;
    }

    public void setAlphabets(List<String> alphabets) {
        this.alphabets = alphabets;
    }
}