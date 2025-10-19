package com.example;

public class MissingNumber {


    public static void main(String [] args) {

        int[] arr = {1,2,4,5,6};
        int  sum= 0;

        for(int i=0;i<arr.length;i++)
           sum=sum+arr[i];
        System.out.println(sum);

        int p=0;
        for(int j=1;j<=6;j++)
            p=p+j;
        System.out.println(p - sum);
    }

       



    
}
