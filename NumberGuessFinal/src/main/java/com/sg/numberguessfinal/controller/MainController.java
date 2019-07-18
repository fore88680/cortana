/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.numberguessfinal.controller;

import com.sg.numberguessfinal.dao.GameDaoImpl;
import com.sg.numberguessfinal.model.Game;
import com.sg.numberguessfinal.model.Round;
import com.sg.numberguessfinal.service.GameServiceImpl;
import com.sg.numberguessfinal.service.RoundServiceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fore8
 */
@RestController
@RequestMapping("/api")
public class MainController {

    GameServiceImpl gameService;
    RoundServiceImpl roundService;

    @Autowired
    public MainController(GameServiceImpl gameService, RoundServiceImpl roundService) {
        this.gameService = gameService;
    }

    ///starts game
    @PostMapping("begin")
    public String addGame(String game) {
        return gameService.generateNewGame(game);
    }

    @PostMapping("guess")
    public Round addRound(Round round) {
        return roundService.addRound(round);

    }

    @GetMapping("game")
    public List<Game> displayGames(Game game) {
        List<Game> gamesList = gameService.getAllGames();

        return gamesList;
    }

    @GetMapping("game/{gameId}")
    public Game displayGame(int gameId) {
        return gameService.getGameById(gameId);
    }

    @GetMapping("rounds/{gameId}")
    public List<Round> getAllRounds() {
        return gameService.getAllRounds();

    }
}


  /*  public void addGame() {

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

            gameService.generateNewGame(answerNumber);
            gameService.getUserGuess();

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
                    System.out.println("Game over, it took you" + guessCount +  "times");
                    
                }
                else {
                    System.out.println("cows:" + cows);
                    System.out.println("Bulls:" + bulls);
                }
            }
        }

    }

}
*/