package com.example;

public class FindDuplicates {


    public static void main(String [] args) {

        String str = "programming";

        for(int i = 0; i < str.length(); i++){
            boolean isDuplicate = false;

            for(int j = 0; j < i; j++)
                if(str.charAt(i) == str.charAt(j)) {
                    isDuplicate = true;
                    break;
                }
            if(isDuplicate) {
                System.out.println("Duplicate character found: " + str.charAt(i));
            }
        }








    }
    
}
