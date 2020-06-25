/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.services;

import com.sg.NumberGame.daos.NumberGameDao;
import com.sg.NumberGame.dtos.Game;
import com.sg.NumberGame.dtos.Round;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bkb
 */
@Service
public class NumberGameService {

    @Autowired
    NumberGameDao dao;

    //Completed
    public Game beginGame() throws SQLException {

        Random rand = new Random();
        Game newGame = new Game();

        String first = String.valueOf(rand.nextInt(10));
        String second = String.valueOf(rand.nextInt(10));
        if (second.equals(first)) {
            boolean validNum = false;
            while (!validNum) {
                second = String.valueOf(rand.nextInt(10));
            }
        }

        String third = String.valueOf(rand.nextInt(10));
        if (third.equals(first) || third.equals(second)) {
            boolean valid = false;
            while (!valid) {
                third = String.valueOf(rand.nextInt(10));
            }
        }

        String fourth = String.valueOf(rand.nextInt(10));
        if (fourth.equals(first) || fourth.equals(second) || fourth.equals(third)) {
            boolean correct = false;
            while (!correct) {
                fourth = String.valueOf(rand.nextInt(10));
            }
        }

        String setOfNums = first + second + third + fourth;
        newGame.setActualAnswer(setOfNums);
        newGame.setGameStatus("Incomplete");
        Game toReturn = dao.beginGame(newGame);

        return toReturn;

    }

    //TODO: Get Postman working!
    public Round makeGuess(Round guess) {
        Integer gameId = guess.getGameId();
        Game toComplete = getGameTrueAnswer(gameId);

        int exactMatches = 0;
        int partialMatches = 0;

        char[] guessDigits = guess.getGuess().toCharArray();
        char[] targetDigits = toComplete.getActualAnswer().toCharArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (guessDigits[i] == targetDigits[j]) {
                    if (i == j) {
                        exactMatches++;
                    } else {
                        partialMatches++;
                    }
                }
            }
        }

        guess.setRoundTime(LocalDateTime.now());
        guess.setGuess(guess.getGuess());
        guess.setRoundResult("e:" + exactMatches + "p:" + partialMatches);

        guess = dao.insertGuess(guess);

        if (exactMatches == 4) {
            toComplete.setGameStatus("Complete");
            toComplete.setGameId(guess.getGameId());
            dao.setGameComplete(toComplete);
        }

        return guess;

    }

    //Completed
    public List<Game> getGameList() {
        List<Game> gameList = dao.getGameList();

        for (Game g : gameList) {
            if (g.getGameStatus().equals("Incomplete")) {
                g.setActualAnswer("hidden");
            }
        }
        return gameList;
    }

    private Game getGameTrueAnswer(Integer gameId) {

        Game toReturn = dao.getGame(gameId);
        return toReturn;
    }

    //Completed
    public Game getGame(Integer gameId) {

        Game toReturn = dao.getGame(gameId);
        if (toReturn.getGameStatus().equals("Complete")) {
            return toReturn;
        } else {
            toReturn.setActualAnswer("hidden");
            return toReturn;
        }

    }

    //Completed
    public List<Round> getRounds(Integer gameId) {
        List<Round> toReturn = dao.getRounds(gameId);
        return toReturn;

    }

}
