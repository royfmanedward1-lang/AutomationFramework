package com.example;

public class LargestNumber {


    public static void main(String [] args) {

        int[ ]arr = {10,3,5,6,7};

        int largest = arr[0];

        for(int ar : arr) {

            if(ar > largest) {
                largest = ar;
            }
        }
        System.out.println(largest);
    }
    
}
