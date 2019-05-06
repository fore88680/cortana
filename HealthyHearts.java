/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.healthyhearts;

import java.util.Scanner;

/**
 *
 * @author fore8
 */
public class HealthyHearts {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int userAge;
        int maxHeartRate;
        double a;
        double b;
                
        System.out.println("What is your age?");
        userAge = sc.nextInt();
        
        maxHeartRate = 220 - userAge;
        
        System.out.println("Your maximum heart rate should be " + maxHeartRate + " beats per minute.");
        
        a = maxHeartRate * .5;
        b = maxHeartRate * .85;
        
        int lowRange = (int) Math.round(a);
        int highRange = (int) Math.round(b);
        
        System.out.println("target HR Zone is " + lowRange + " - " + highRange + " beats per minute");
        
        
        
    }
}
