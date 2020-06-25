/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.daos;

import com.sg.NumberGame.dtos.Game;
import com.sg.NumberGame.dtos.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author bkb
 */
@Component
@Profile({"production", "dao-test"})
public class NumberGameDbDao implements NumberGameDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Game> getGameList() {
        return template.query("SELECT * FROM Game", new GameMapper());
    }

    @Override
    public Game getGame(Integer gameId) {

        final String getGame = "SELECT * FROM Game WHERE GameId=?";
        return template.queryForObject(getGame, new GameMapper(), gameId);

    }

    @Override
    public Game beginGame(Game newGame) throws SQLException {

        final String gameToAdd = "INSERT INTO Game(ActualAnswer,GameStatus) VALUES (?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update((Connection conn) -> {
            PreparedStatement pstmt = conn.prepareStatement(
                    gameToAdd,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newGame.getActualAnswer());
            pstmt.setString(2, newGame.getGameStatus());
            return pstmt;

        }, keyHolder);

        newGame.setGameId(keyHolder.getKey().intValue());

        return newGame;
    }

    @Override
    public List<Round> getRounds(Integer gameId) {
        final String getGame = "SELECT * FROM Round WHERE GameId=?";
        return template.query(getGame, new RoundMapper(), gameId);
    }

    @Override
    public Round insertGuess(Round guess) {
        final String roundToAdd = "INSERT INTO Round(GameId,Guess,RoundResult) VALUES (?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update((Connection conn) -> {
            PreparedStatement pstmt = conn.prepareStatement(
                    roundToAdd,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, guess.getGameId());
            pstmt.setString(2, guess.getGuess());
            pstmt.setString(3, guess.getRoundResult());
            return pstmt;
        }, keyHolder);

        guess.setRoundNum(keyHolder.getKey().intValue());
        return guess;
    }

    @Override
    public Game setGameComplete(Game toComplete) {
        final String updateGame = "UPDATE Game SET GameStatus='Complete' WHERE GameId=?;";
        template.update(updateGame,
                toComplete.getGameId());

        return toComplete;
    }

    private static class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet row, int n) throws SQLException {
            Round toReturn = new Round();

            toReturn.setGameId(row.getInt("GameId"));
            toReturn.setRoundNum(row.getInt("RoundNum"));
            toReturn.setGuess(row.getString("Guess"));
            toReturn.setRoundResult(row.getString("RoundResult"));
            toReturn.setRoundTime(Timestamp.valueOf(row.getString("RoundTime")).toLocalDateTime());
            return toReturn;
        }

    }

    private static class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet row, int n) throws SQLException {
            Game toReturn = new Game();

            toReturn.setGameId(row.getInt("GameId"));
            toReturn.setActualAnswer(row.getString("ActualAnswer"));
            toReturn.setGameStatus(row.getString("GameStatus"));

            return toReturn;
        }
    }

}
