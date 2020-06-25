DROP DATABASE IF EXISTS BrookeMovieCollectionDB;

CREATE DATABASE BrookeMovieCollectionDB;

USE BrookeMovieCollectionDB;

CREATE TABLE Movie (
	MovieIdNumber INT AUTO_INCREMENT PRIMARY KEY,
    MovieTitle VARCHAR(30) NOT NULL,
    MovieReleaseDate DATE NOT NULL,
    MPAARating VARCHAR(5) NOT NULL,
    DirectorName VARCHAR(30) NOT NULL,
    Studio VARCHAR(30) NOT NULL,
    UserNotes VARCHAR(30) NULL
);

INSERT INTO Movie VALUES 
('Amistad','1997/01/01','R','Steven Spielberg','DreamWorks', NULL),
('Dances with Wolves','1990/01/01','PG-13','Kevin Costner','Tig Productions', NULL),
('The Prince of Egypt','1998/01/01','PG','Brenda Chapman','DreamwWorks', NULL),
();