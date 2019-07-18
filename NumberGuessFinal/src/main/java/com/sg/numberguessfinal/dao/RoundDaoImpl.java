/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.numberguessfinal.dao;

import com.sg.numberguessfinal.model.Game;
import com.sg.numberguessfinal.model.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fore8
 */
@Repository
public class RoundDaoImpl implements RoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final List<Round> rounds = new ArrayList<>();

    private static final String SQL_INSERT_ROUND
            = "insert into Round (Date, UserGuess, ExactMatches, PartialMatches, GameId) values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_ROUND
            = "delete FROM Round where RoundId = ?";

    private static final String SQL_UPDATE_ROUND
            = "update Round SET Date = ?, UserGuess = ? , ExactMatches = ?, PartialMatches = ?, GameId = ? where RoundId = ?";

    private static final String SQL_SELECT_ROUND
            = "select * "
            + "FROM Game "
            + "INNER JOIN ON Game.GameId = Round.RoundId"
            + "WHERE RoundId = ?";

    private static final String SQL_SELECT_ALL_ROUNDS
            = "select * FROM Round";
            

    private static final String SQL_SELECT_GAME_DATES
            = "Select * from Game ORDER BY Date DESC Limit 10";

    private static final String SQL_GET_ALL_ROUNDS_FOR_GAME
            = "SELECT * "
            + "FROM Round "
            + "JOIN Game ON Game.RoundId = Round.RoundId "
            + "WHERE Game.GameId = ?";

    @Override
    @Transactional
    public Round addRound(Round round) {

        jdbcTemplate.update(SQL_INSERT_ROUND,
                round.getDate().toString(),
                round.getUserGuess(),
                round.getExactMatches(),
                round.getPartialMatches(),
                round.getGameId());

        int roundId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        round.setRoundId(roundId);
        return round;
    }

    @Override
    public void deleteRound(int roundId) {

    }

    @Override
    public void updateRound(Round round) {
        jdbcTemplate.update(SQL_UPDATE_ROUND,
                round.getDate().toString(),
                round.getUserGuess(),
                round.getExactMatches(),
                round.getPartialMatches(),
                round.getGameId());
    }

    @Override
    public Round getRoundById(int roundId) {
        
         try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM Round WHERE RoundId = ?";
            return jdbcTemplate.queryForObject(SELECT_ROUND_BY_ID, new RoundDaoImpl.RoundMapper(), roundId);
        } catch(DataAccessException ex) {
            return null;
        }
        
    }        
    
        
    @Override
    public List<Round> getAllRounds() {
       
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_ROUNDS,
               new RoundMapper());
        }catch (EmptyResultDataAccessException ex) {
            return null;
        }
             
    }
    
    
    private List<Round> getAllRoundsForGame(Game game) {
        List<Round> roundList = jdbcTemplate.query(SQL_GET_ALL_ROUNDS_FOR_GAME,
                new RoundMapper(),
                game.getGameId());
        
        return roundList;
        
    }
    
    private Round getRoundForGame(Game game) {
        final String SELECT_ROUND_FOR_GAME = "SELECT Round * FROM Round "
                + "JOIN Game ON Round.RoundId = Game.RoundId WHERE Game.GameId = ?";
        return jdbcTemplate.queryForObject(SELECT_ROUND_FOR_GAME, new RoundMapper(),
                game.getGameId());
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

    }

    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {

            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAnswerNumber(rs.getString("AnswerNumber"));
            game.setFinished(rs.getBoolean("Finished"));

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
