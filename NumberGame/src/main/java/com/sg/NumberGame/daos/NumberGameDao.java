/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.daos;

import com.sg.NumberGame.dtos.Game;
import com.sg.NumberGame.dtos.Round;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author bkb
 */
public interface NumberGameDao {

    public List<Game> getGameList();

    public Game getGame(Integer gameId);

    public Game beginGame(Game newGame) throws SQLException;

    public List<Round> getRounds(Integer gameId);

    public Round insertGuess(Round guess);

    public Game setGameComplete(Game toComplete);

}
