/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.daos;

import com.sg.NumberGame.dtos.Game;
import com.sg.NumberGame.dtos.Round;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;

import org.springframework.stereotype.Repository;

/**
 *
 * @author bkb
 */
@Repository
@Profile("service-test")
public class NumberGameInMemDao implements NumberGameDao {

    List<Game> gameList = new ArrayList<>();
    List<Round> roundList = new ArrayList<>();

    public NumberGameInMemDao() {
        Game first = new Game();
        first.setGameId(1);
        first.setActualAnswer("9081");
        first.setGameStatus("Incomplete");

        Game second = new Game();
        second.setGameId(2);
        second.setActualAnswer("7530");
        second.setGameStatus("Complete");

        Round one = new Round();
        one.setGameId(1);
        one.setRoundNum(1);
        one.setGuess("1234");
        one.setRoundResult("e:1 p:3");

        Round two = new Round();
        two.setGameId(1);
        two.setRoundNum(2);
        two.setGuess("4321");
        two.setRoundResult("e:0 p:3");

        Round three = new Round();
        three.setGameId(1);
        three.setRoundNum(3);
        three.setGuess("9081");
        three.setRoundResult("e:4 p:0");

        Round four = new Round();
        four.setGameId(2);
        four.setRoundNum(1);
        four.setGuess("1234");
        four.setRoundResult("e:0 p:0");

        gameList.add(first);
        gameList.add(second);

        roundList.add(one);
        roundList.add(two);
        roundList.add(three);
        roundList.add(four);

    }

    @Override
    public List<Game> getGameList() {
        return gameList;
    }

    @Override
    public Game getGame(Integer gameId) {

        return gameList
                .stream()
                .filter(g -> g.getGameId() == gameId)
                .findAny()
                .orElse(null);

    }

    @Override
    public Game beginGame(Game newGame) throws SQLException {
        Game toAdd = new Game();

        int maxId = gameList
                .stream()
                .mapToInt(g -> g.getGameId())
                .max()
                .orElse(0);

        newGame.setGameId(maxId + 1);
        toAdd.setGameId(newGame.getGameId());
        toAdd.setActualAnswer(newGame.getActualAnswer());
        toAdd.setGameStatus(newGame.getGameStatus());
        gameList.add(toAdd);
        return toAdd;

    }

    @Override
    public List<Round> getRounds(Integer gameId) {

        List<Round> toReturn = new ArrayList<>();
        for (int i = 0; i < roundList.size(); i++) {
            if (roundList.get(i).getGameId() == gameId) {
                toReturn.add(roundList.get(i));
            }
        }

        return toReturn;
    }

    @Override
    public Round insertGuess(Round guess) {
        Round toInsert = new Round();

        int maxRound = roundList
                .stream()
                .mapToInt(r -> r.getRoundNum())
                .max()
                .orElse(0);

        toInsert.setGameId(guess.getGameId());
        toInsert.setRoundNum(maxRound + 1);
        toInsert.setGuess(guess.getGuess());
        toInsert.setRoundResult(guess.getRoundResult());
        roundList.add(toInsert);
        return toInsert;

    }

    @Override
    public Game setGameComplete(Game toComplete) {

        if (toComplete.getGameStatus().equals("e:4p:0")) {
            toComplete.setGameStatus("Complete");
        }

        return toComplete;

    }

}
