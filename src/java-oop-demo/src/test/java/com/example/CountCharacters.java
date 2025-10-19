package com.example;

public class CountCharacters {


    public static void main(String [] args) {

        String str= "Selleminm";
        int count = str.length() - str.replace("l", "").length();

        System.out.println("Count of 'l' in the string: " + count);

    } 
    
}
