package com.company;

import javax.swing.*;

public class Palindrome
{
    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            String s = args[i];
            System.out.print("Является ли слово " +s+ " палиндром? - ");
            System.out.println(isPalindrome(s));
        }
    }
    public static String reverseString(String s)
    {
        int len = s.length();
        String s1 = "";
        for (int i = len-1; i >= 0; i--)
            s1 += s.charAt(i);
        return s1;
    }
    public static boolean isPalindrome(String s)
    {
        String s1 = reverseString(s);
        return (s.equals(s1));
    }
}
