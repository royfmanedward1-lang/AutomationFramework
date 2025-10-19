package com.example;
public class ReverseString {


    public static void main(String [] args) {

        String reverseString = "Selenium";
        String result = "";

        for(int i=reverseString.length()-1; i>=0; i--) {
            result = result + reverseString.charAt(i);
        }

        System.out.println(result);
    }

}
