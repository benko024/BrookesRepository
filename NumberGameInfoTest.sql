DROP DATABASE IF EXISTS NumberGameDBTest;

CREATE DATABASE NumberGameDBTest;

USE NumberGameDBTest;

CREATE TABLE Game (
	GameId INT AUTO_INCREMENT PRIMARY KEY,
    ActualAnswer VARCHAR(4) NOT NULL,
    GameStatus VARCHAR(10) DEFAULT 'Incomplete' NOT NULL
);

CREATE TABLE Round (
	GameId INT NOT NULL,
    RoundNum INT AUTO_INCREMENT PRIMARY KEY,
    Guess INT NOT NULL,
    RoundResult VARCHAR(10) DEFAULT 'e:0 p:0',
    FOREIGN KEY (GameId)
		REFERENCES Game(GameId)
);

INSERT INTO Game (ActualAnswer,GameStatus) VALUES
(1234, 'Complete'),
(3456, 'Complete'),
(7891, 'Incomplete'),
(1254, 'Complete');