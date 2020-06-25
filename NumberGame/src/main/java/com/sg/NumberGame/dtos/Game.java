/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.dtos;

/**
 *
 * @author bkb
 */
public class Game {
    
    private Integer gameId;
    private String actualAnswer;
    private String gameStatus;

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
     * @return the actualAnswer
     */
    public String getActualAnswer() {
        return actualAnswer;
    }

    /**
     * @param actualAnswer the actualAnswer to set
     */
    public void setActualAnswer(String actualAnswer) {
        this.actualAnswer = actualAnswer;
    }

    /**
     * @return the gameStatus
     */
    public String getGameStatus() {
        return gameStatus;
    }

    /**
     * @param gameStatus the gameStatus to set
     */
    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
    
}
