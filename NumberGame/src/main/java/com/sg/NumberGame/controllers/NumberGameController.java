/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.controllers;

import com.sg.NumberGame.dtos.Game;
import com.sg.NumberGame.dtos.Round;
import com.sg.NumberGame.services.NumberGameService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bkb
 */
@RestController
@RequestMapping("/api")
public class NumberGameController {

    @Autowired
    NumberGameService service;

    //Completed
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer beginGame() throws SQLException {
        Game toBegin = service.beginGame();
        Integer toReturn = toBegin.getGameId();
        return toReturn;
    }

    //Completed
    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Round guess) {
        Round toReturn = service.makeGuess(guess);
        return toReturn;
    }    
    

    //Completed
    @GetMapping("/game")
    public List<Game> getGameList() {
        List<Game> gameList = service.getGameList();
        return gameList;
    }

    //Completed
    @GetMapping("/game{gameId}")
    public Game getGame(@PathVariable("gameId") Integer gameId) {
        Game toReturn = new Game();
        toReturn = service.getGame(gameId);
        return toReturn;
    }

    //Completed
    @GetMapping("/rounds/{gameId}")
    public List<Round> getRounds(@PathVariable("gameId") Integer gameId) {
        List<Round> toReturn = service.getRounds(gameId);
        return toReturn;
    }
}
