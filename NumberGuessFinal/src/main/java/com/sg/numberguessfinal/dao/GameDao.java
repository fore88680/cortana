/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.numberguessfinal.dao;

import com.sg.numberguessfinal.model.Game;
import java.util.List;

/**
 *
 * @author fore8
 */
public interface GameDao {
    
    public Game addGame(Game game);
    public void deleteGame(int gameId);
    public Game getGameById(int gameId);
    public List<Game>getAllGames();
    public List<Game> getGamesDescendingDate();
    
    
}
