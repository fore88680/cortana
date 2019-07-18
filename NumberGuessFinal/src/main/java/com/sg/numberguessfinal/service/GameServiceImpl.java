/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.numberguessfinal.service;

import com.sg.numberguessfinal.dao.GameDaoImpl;
import com.sg.numberguessfinal.dao.RoundDaoImpl;
import com.sg.numberguessfinal.model.Game;
import com.sg.numberguessfinal.model.Round;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author fore8
 */
@Component
public class GameServiceImpl {

    GameDaoImpl gameDao;

    RoundDaoImpl roundDao;

    @Autowired
    public GameServiceImpl(GameDaoImpl gameDao, RoundDaoImpl roundDao) {

        this.gameDao = gameDao;
        this.roundDao = roundDao;

    }

    public void deleteGame(int gameId) {
    }

    public Game getGameById(int gameId) {
        return gameDao.getGameById(gameId);
    }

    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    public List<Game> getGamesDescendingDate() {
        return gameDao.getGamesDescendingDate();
    }

    public Round getRoundById(int roundId) {
        return roundDao.getRoundById(roundId);
    }

    public List<Round> getAllRounds() {
        return roundDao.getAllRounds();
    }

    public String generateNewGame(String game) {

        int cows = 0;
        int bulls = 0;
        Boolean finished;
        finished = false;

        String answerNumber;

        Random rand = new Random();
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        while (a == b | a == c | a == d | b == c | b == d | c == d) {
            a = rand.nextInt(10) + 1;
            b = rand.nextInt(10) + 1;
            c = rand.nextInt(10) + 1;
            d = rand.nextInt(10) + 1;
        }
        answerNumber = "a+b+c+d";
        return answerNumber;

    }

    public void getUserGuess() {
        String userGuess;
        int bulls;
        int cows;
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your guess!");
        int userNumber = sc.nextInt();

    }

    public void addGame() {

        int guessCount = 0;
        int userNumber = 0;
        String answerNumber = null;
        int cows = 0;
        int bulls = 0;
        int bullCount = 0;
        int cowCount = 0;
        Boolean finished;
        finished = false;

        while (finished == false) {

            generateNewGame(answerNumber);
            getUserGuess();

            String userStringGuess = String.valueOf(userNumber);

            String u = userStringGuess;
            String c = answerNumber;

            for (int i = 0; i < 4; i++) {
                for (int k = i + 1; k < 4; k++) {

                    if (u.charAt(i) == c.charAt(k)) {
                        bullCount++;
                        bulls = bulls + bullCount;
                    } else if (u.contains(c.charAt(i) + u)) {
                        cowCount++;
                        cows = cows + cowCount;
                    }
                    guessCount++;
                }
                if (bulls == 4) {
                    finished = true;
                    System.out.println("Game over, it took you" + guessCount + "times");

                } else {
                    System.out.println("cows:" + cows);
                    System.out.println("Bulls:" + bulls);
                }
            }

        }
    }
}
