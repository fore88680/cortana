/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.summativesums;

import java.util.Scanner;

/**
 *
 * @author fore8
 */
public class SummativeSums {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        
        
        int arrayLength;
        int sum = 0;
        
        
        System.out.println("Howmany numbers are in the array");
        arrayLength = sc.nextInt();
        int a[] = new int[arrayLength];
        
        System.out.println("Enter all the elements:");
   
        for (int i = 0; i < arrayLength; i++) {
        a[i] = sc.nextInt();
        sum = sum + a[i];
        
        }
           System.out.println("Array Sum: " + sum);
            
        
        

    }
}
