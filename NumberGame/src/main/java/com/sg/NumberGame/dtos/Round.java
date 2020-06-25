/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author bkb
 */
public class Round {
    private Integer gameId;
    private Integer roundNum;
    private String guess;
    private String roundResult;
    private LocalDateTime roundTime = LocalDateTime.now();

    /**
     * @return the gameId
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the roundNum
     */
    public Integer getRoundNum() {
        return roundNum;
    }

    /**
     * @param roundNum the roundNum to set
     */
    public void setRoundNum(Integer roundNum) {
        this.roundNum = roundNum;
    }

    /**
     * @return the guess
     */
    public String getGuess() {
        return guess;
    }

    /**
     * @param guess the guess to set
     */
    public void setGuess(String guess) {
        this.guess = guess;
    }

    /**
     * @return the roundResult
     */
    public String getRoundResult() {
        return roundResult;
    }

    /**
     * @param roundResult the roundResult to set
     */
    public void setRoundResult(String roundResult) {
        this.roundResult = roundResult;
    }

    /**
     * @return the roundTime
     */
    public LocalDateTime getRoundTime() {
        return roundTime;
    }

    /**
     * @param roundTime the roundTime to set
     */
    public void setRoundTime(LocalDateTime roundTime) {
        this.roundTime = roundTime;
    }

}
