package com.sg.doggenetics;

import java.util.Random;
import java.util.Scanner;

public class DogGenetics {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        String dogName;
        int yellowLab;
        int pug;
        int poodle;
        int greyHound;
        int bullDog;
        int sum;
        
        
        System.out.println("What is your dogs name?");
        dogName = sc.next();

        yellowLab = rand.nextInt(45) + 1;
        pug = rand.nextInt(25) + 1;
        poodle = rand.nextInt(25) + 1;
        greyHound = rand.nextInt(20) + 1;
                     
        bullDog = yellowLab + pug + poodle + greyHound;
                
        if (bullDog == 100) {
            bullDog = 0;
        }
        if (bullDog < 100) {
            bullDog = (100 - bullDog);
        }

        
        System.out.println("Well then, I have this highly reliable report on " + dogName + " prestigious background right here.");
        System.out.println(" ");
        System.out.println(dogName + " is:");
        System.out.println(" ");
        System.out.println(yellowLab +"% Yellow Lab");
        System.out.println(pug + "% Pug");
        System.out.println(poodle + "% Poodle");
        System.out.println(greyHound + "% Greyhound");
        System.out.println(bullDog + "% Bulldog");
        System.out.println(" ");
        System.out.println("Wow, that is quite the dog!!");
        
     
                     
    }
}
