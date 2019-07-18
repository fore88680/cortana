/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.numberguessfinal.dao;

import com.sg.numberguessfinal.model.Game;
import com.sg.numberguessfinal.model.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fore8
 */
@Repository
public class GameDaoImpl implements GameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final List<Game> games = new ArrayList<>();

    private static final String SQL_INSERT_GAME
            = "insert into Game (AnswerNumber, Finished) values (? , ?)";
    private static final String SQL_DELETE_GAME
            = "delete FROM Game where GameId = ?";
    private static final String SQL_UPDATE_GAME
            = "update Game SET AnswerNumber, Finished = ? where RoundId = ?";
    private static final String SQL_SELECT_GAME
            = "select * FROM Game where GameId = ?";
    private static final String SQL_SELECT_ALL_GAMES
            = "select * from Game";

    private static final String SQL_SELECT_GAME_DATES
            = "Select * from Game ORDER BY Date DESC Limit 10";

    private static final String SQL_GET_ROUNDS_FOR_GAME
            = "SELECT * "
            + "FROM Round "
            + "JOIN Game ON Game.RoundId = Round.RoundId "
            + "WHERE Game.GameId = ?";

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO Game(AnswerNumber, Finished) VALUES(?,?)";
        jdbcTemplate.update(INSERT_GAME,
                game.getAnswerNumber(),
                game.isFinished());
        
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;
    }

       

    @Override
    @Transactional
    public void deleteGame(int gameId) {
        final String DELETE_GAME = "DELETE Game * FROM WHERE gameId = ?";
        jdbcTemplate.update(DELETE_GAME, gameId);
        
    }

  
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game Set AnswerNumber = ?, Finished = ? WHERE GameId = ?";
        jdbcTemplate.update(UPDATE_GAME,
                game.getAnswerNumber(),
                game.isFinished(),
                game.getGameId());

    }

    @Override
    public Game getGameById(int gameId) {

        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM Game WHERE GameId = ?";
            return jdbcTemplate.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        } catch(DataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public List<Game> getAllGames() {
       final String SELECT_ALL_GAMES = "SELECT * FROM Game";
       return jdbcTemplate.query(SELECT_ALL_GAMES, new GameMapper());

    }

    @Override
    public List<Game> getGamesDescendingDate() {

        return jdbcTemplate.query(SQL_SELECT_GAME_DATES,
                new GameMapper());
    }

    /////////////Helpers//////////////////
    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {

            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAnswerNumber(rs.getString("AnswerNumber"));
            game.setFinished(rs.getBoolean("Finished"));

            return game;
        }

        private static final class RoundMapper implements RowMapper<Round> {

            @Override
            public Round mapRow(ResultSet rs, int i) throws SQLException {

                Round round = new Round();
                round.setRoundId(rs.getInt("RoundId"));
                round.setDate(rs.getDate("Date").toLocalDate());
                round.setUserGuess(rs.getString("UserGuess"));
                round.setExactMatches(rs.getInt("ExactMatches"));
                round.setPartialMatches(rs.getInt("PartialMatches"));

                return round;

            }

        }
        
        
        
        

    }
}
