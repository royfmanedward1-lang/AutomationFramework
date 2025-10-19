package com.example;

public class GetSecondOrFirstString {

    public static void main(String [] args) {

        String str = " Hello World ";
        String[] arr = str.trim().split("\\s+");
        System.out.println("Get a second string in array:" + "\n" + arr[arr.length-2]);

}
}
