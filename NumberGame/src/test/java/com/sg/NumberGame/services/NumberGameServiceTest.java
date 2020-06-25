/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.NumberGame.services;

import com.sg.NumberGame.NumberGameTestConfig;
import com.sg.NumberGame.daos.NumberGameInMemDao;
import com.sg.NumberGame.dtos.Game;
import com.sg.NumberGame.dtos.Round;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author bkb
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NumberGameTestConfig.class)
public class NumberGameServiceTest {

    @Autowired
    NumberGameService toTest;

    @Autowired
    NumberGameInMemDao dao;

    public NumberGameServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

//******************************************************************************
//*Test Make Guess
//******************************************************************************
    //COMPLETED - TESTED TO COMPLETION
    @Test
    public void testMakeGuessGoldenPath() {
        Round toGuess = new Round();
        toGuess.setGameId(1);
        toGuess.setGuess("2436");
        Round toCheck = toTest.makeGuess(toGuess);
        assertEquals(1, toCheck.getGameId());
        assertEquals(4, toCheck.getRoundNum());
        assertEquals("e:0p:0", toCheck.getRoundResult());

        Round secondGuess = new Round();
        secondGuess.setGameId(1);
        secondGuess.setGuess("1578");
        Round secondToCheck = toTest.makeGuess(secondGuess);
        assertEquals(1, secondToCheck.getGameId());
        assertEquals(5, secondToCheck.getRoundNum());
        assertEquals("e:0p:2", secondToCheck.getRoundResult());

        Round thirdGuess = new Round();
        thirdGuess.setGameId(1);
        thirdGuess.setGuess("9081");
        Round thirdToCheck = toTest.makeGuess(thirdGuess);
        assertEquals(1, thirdToCheck.getGameId());
        assertEquals(6, thirdToCheck.getRoundNum());
        assertEquals("e:4p:0", thirdToCheck.getRoundResult());

    }

    //GuessException
    @Test
    public void testMakeGuessNullGuess() {

    }

    //GuessException
    @Test
    public void testMakeGuessNonNumeric() {

    }

    //GuessException
    @Test
    public void testMakeGuessTooShort() {

    }

    //GuessException
    @Test
    public void testMakeGuessTooLong() {

    }

    //GameIdException
    @Test
    public void testMakeGuessZeroGameId() {

    }

    //GameIdException
    @Test
    public void testMakeGuessNegGameId() {

    }

    //GameIdException
    @Test
    public void testMakeGuessNonExistantGameId() {

    }
//******************************************************************************
//*Test Get list of games
//******************************************************************************    

    //COMPLETED - TESTED TO COMPLETION
    @Test
    public void testGetGameListGoldenPath() {
        List<Game> gameList = toTest.getGameList();

        assertEquals(1, gameList.get(0).getGameId());
        assertEquals("hidden", gameList.get(0).getActualAnswer());
        assertEquals("Incomplete", gameList.get(0).getGameStatus());
        assertEquals(2, gameList.get(1).getGameId());
        assertEquals("7530", gameList.get(1).getActualAnswer());
        assertEquals("Complete", gameList.get(1).getGameStatus());
    }

//******************************************************************************
//*Test get one game
//******************************************************************************
    //COMPLETED - TESTED TO COMPLETION
    @Test
    public void testGetGameGoldenPath() {

        Integer gameId = 1;
        Game toCheck = toTest.getGame(gameId);

        assertEquals(1, toCheck.getGameId());
        assertEquals("hidden", toCheck.getActualAnswer());
        assertEquals("Incomplete", toCheck.getGameStatus());

        Integer gameIdSecond = 2;
        Game toCheckSecond = toTest.getGame(gameIdSecond);

        assertEquals(2, toCheckSecond.getGameId());
        assertEquals("7530", toCheckSecond.getActualAnswer());
        assertEquals("Complete", toCheckSecond.getGameStatus());
    }

    //GameIdException
    @Test
    public void testGetGameZeroGameId() {

    }

    //GameIdException
    @Test
    public void testGetGameNegGameId() {

    }

    //GameIdException
    @Test
    public void testGetGameIdTooBig() {

    }

    //InvalidGameException
    @Test
    public void testGetGameNullStatus() {

    }

//******************************************************************************
//*Test get rounds
//******************************************************************************
    //COMPLETED - TESTED TO COMPLETION!
    @Test
    public void testGetRoundsGoldenPath() {

        List<Round> toReturn = toTest.getRounds(1);

        assertEquals(3, toReturn.size());
        assertEquals(1, toReturn.get(0).getGameId());

        List<Round> secondReturn = toTest.getRounds(2);
        assertEquals(1, secondReturn.size());
        assertEquals(2, secondReturn.get(0).getGameId());
    }

    //GameIdException
    @Test
    public void testGetRoundsZeroGameId() {

    }

    //GameIdException
    @Test
    public void testGetRoundsNegGameId() {

    }

    //GameIdException
    @Test
    public void testGetRoundsIdTooBig() {

    }

//******************************************************************************
//*Test Begin Game
//******************************************************************************
    //COMPLETED - TESTED TO COMPLETION!
    @Test
    public void testBeginGameGoldenPath() throws SQLException {

        Game toBegin = toTest.beginGame();

        String setOfNums = "0413";
        toBegin.setActualAnswer(setOfNums);
        toBegin.setGameStatus("Incomplete");

        assertEquals(setOfNums, toBegin.getActualAnswer());
        assertEquals("Incomplete", toBegin.getGameStatus());
        assertEquals(3, toBegin.getGameId());

        Game secondToBegin = toTest.beginGame();

        String secondSetOfNums = "4210";
        secondToBegin.setActualAnswer(secondSetOfNums);
        secondToBegin.setGameStatus("Incomplete");
        assertEquals(secondSetOfNums, secondToBegin.getActualAnswer());
        assertEquals("Incomplete", secondToBegin.getGameStatus());
        assertEquals(4, secondToBegin.getGameId());

    }

}
