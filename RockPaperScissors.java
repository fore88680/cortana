/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rockpaperscissors;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author fore8
 */
public class RockPaperScissors {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int numRounds;
        int tie = 0;
        int cpuWin = 0;
        int userWin = 0;
        int rock = 1;
        int paper = 2;
        int scissors = 3;
        int uRps;
        int cRps;
        String yah = "Yes";
        String nah = "No";
        String playAgain;

        System.out.println("How many rounds would you like to play?");
        numRounds = sc.nextInt();

        while (numRounds >= 1 && numRounds <= 10) {
            System.out.println("Select 1 for Rock, 2 for Paper, and 3 for Scissors");
            uRps = sc.nextInt();
            cRps = rand.nextInt(3) + 1;

            if (cRps == rock) {
                if (uRps == rock) {
                    System.out.println("Rock vs. Rock!!! I'll be rock.... Im already rock!! ITS A TIE!!!");
                    tie++;
                    numRounds--;
                } else if (uRps == paper) {
                    System.out.println("Rock vs. Paper!!! plz nerf rock... YOU WIN!!!");
                    userWin++;
                    numRounds--;
                } else if (uRps == scissors) {
                    System.out.println("Rock vs. Scissors!! get gud scrub... YOU LOSE!!!");
                    cpuWin++;
                    numRounds--;
                }

            } else if (cRps == paper) {
                if (uRps == paper) {
                    System.out.println("Paper vs. Paper!!! I'll be paper... Im already paper!! ITS A TIE!!!");
                    tie++;
                    numRounds--;
                } else if (uRps == rock) {
                    System.out.println("Paper vs. Rock!!! get wrapped... YOU LOSE!!!");
                    cpuWin++;
                    numRounds--;
                } else if (uRps == scissors) {
                    System.out.println("Paper vs. Scissors!!! ouch thats not nice... YOU WIN!!!");
                    userWin++;
                    numRounds--;
                }

            } else if (cRps == scissors) {
                if (uRps == scissors) {
                    System.out.println("Scissors vs Scissors...... gigity...... ITS A TIE!!!");
                    tie++;
                    numRounds--;
                } else if (uRps == rock) {
                    System.out.println("Scissors vs Rock!!!    lag you cheated..fine... YOU WIN!!");
                    userWin++;
                    numRounds--;
                } else if (uRps == paper) {
                    System.out.println("Scissors vs Paper!!! I cut you... YOU LOSE!!!");
                    cpuWin++;
                    numRounds--;
                }

            } else if (numRounds == 0) {
                System.out.println("You won " + userWin + " times.  I, Mr. Java won " + cpuWin + " times.  We tied " + tie + "times.");           
            }if (userWin > cpuWin) {
                System.out.println("You win, but I let you win cause Im nice");
            } else if (userWin < cpuWin) {
                System.out.println("You lost, gg no re");
            } else if (userWin == cpuWin) {
                System.out.println("ITS A TIE");
            }

            System.out.println("Would you like to play agian 'Yes' or 'No'");
            playAgain = sc.nextLine();

            if (playAgain.equals("Yes")) {
            } else if (playAgain.equals("No")) {
                System.out.println("k, bye.");
            }
        }
    }
}

