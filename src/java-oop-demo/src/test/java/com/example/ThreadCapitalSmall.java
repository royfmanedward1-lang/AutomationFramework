package com.example;

public class ThreadCapitalSmall {

    public static void main(String[] args) {

        String str = "Hello World";
        char[] ch = str.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(char c : ch) {

            if(Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
                System.out.println(sb);
            }
            else if (Character.isLowerCase(c)) {
                sb.append(Character.toUpperCase(c));
                System.out.println(sb);
            }
            else {
                sb.append(c);
            }
            System.out.println(sb);
        }
    }
}
