package com.example;

public class ReverseArrays {


    public static void main(String [] args) {

        int[] arr = {10,20,30,40};

        int arrReverse[] = new int[4];

        for(int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            arrReverse[j] = arr[i];
        }
        System.out.println("Reversed array is: " + java.util.Arrays.toString(arrReverse));
    }
}
