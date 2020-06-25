/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.dao;

import com.sg.movielibrary.dto.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bkb
 */
public class MovieLibraryDaoFileImpl implements MovieLibraryDao {

    String path = "movies.txt";

    @Override
    public Movie addMovie(Movie toAdd) throws MovieLibraryDaoException {
        List<Movie> allMovies = getAllMovies();

        int maxId = 0;

        for (Movie m : allMovies) {
            if (m.getMovieIDNumber() > maxId) {
                maxId = m.getMovieIDNumber();
            }
        }

        toAdd.setMovieIDNumber(maxId + 1);

        allMovies.add(toAdd);
        writeFile(allMovies);

        return toAdd;

    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(path)
                    )
            );

            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Movie toAdd = convertLineToMovie(row);

                allMovies.add(toAdd);
            }
        } catch (FileNotFoundException ex) {

        }
        return allMovies;

    }

    private void writeFile(List<Movie> toWrite) throws MovieLibraryDaoException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));

            for (Movie m : toWrite) {

                String line = convertMovieToLine(m);
                writer.println(line);
            }

            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new MovieLibraryDaoException("Could not open file: " + path, ex);
        }

    }
    
    
    
    private String convertMovieToLine(Movie m) {
        return m.getMovieIDNumber() + "::"
                + m.getMovieTitle() + "::"
                + m.getMovieReleaseDate() + "::"
                + m.getMPAARating() + "::"
                + m.getDirectorName() + "::"
                + m.getStudio() + "::"
                + m.getUserNotes();
    }
    

    private Movie convertLineToMovie(String row) {
        //:: is the delimiter

        String[] cells = row.split("::");

        int movieID = Integer.parseInt(cells[0]);
        String movieTitle = cells[1];
        String movieRelase = cells[2];
        String MPAARating = cells[3];
        String directorName = cells[4];
        String studio = cells[5];
        String userNotes = cells[6];

        Movie toAdd = new Movie();
        toAdd.setMovieIDNumber(movieID);
        toAdd.setMovieTitle(movieTitle);
        toAdd.setMovieReleaseDate(movieRelase);
        toAdd.setMPAARating(MPAARating);
        toAdd.setDirectorName(directorName);
        toAdd.setStudio(studio);
        toAdd.setUserNotes(userNotes);

        return toAdd;

    }

    @Override
    public Movie searchMovieById(int movieID) {
        Movie toReturn = null;
        List <Movie> allMovies = getAllMovies();
        for ( Movie toCheck : allMovies) { 
            if (toCheck.getMovieIDNumber() == movieID) {
                toReturn = toCheck;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public Movie searchMovieByTitle(String dvdTitle) {
        Movie toReturn = null;
        List <Movie> allMovies = getAllMovies();
        for (Movie toCheck : allMovies) {
            if (toCheck.getMovieTitle().equalsIgnoreCase(dvdTitle)) {
                toReturn = toCheck;
                break;
            }
        }
        
        return toReturn;
    }

    @Override
    //Lets you pull up the movie by the ID # and edit it. 
    public void editMovieInformation(Movie updatedMovie) throws MovieLibraryDaoException {
        List <Movie> allMovies = getAllMovies();
        
        int index = -1;
        
        for ( int i=0; i<allMovies.size(); i++) {
            Movie toCheck = allMovies.get(i);
            
            if (toCheck.getMovieIDNumber() == updatedMovie.getMovieIDNumber()) {
                index = i;
                break;
            }
        }
        
        allMovies.set(index, updatedMovie);
        
        writeFile(allMovies);
        
    }
    
    
    @Override
    public void removeMovieById(int movieID) throws MovieLibraryDaoException {
        
        List <Movie> allMovies = getAllMovies();
        
        int index = -1;
        
        for (int i=0; i<allMovies.size(); i++) {
            Movie toCheck = allMovies.get(i);
            
            if (toCheck.getMovieIDNumber() == movieID) {
                index = i;
                break;
            }
        }
        
        allMovies.remove(index);
        
        writeFile(allMovies);
    }

    @Override
    public void removeMovieByTitle(String dvdTitle) throws MovieLibraryDaoException {
       List <Movie> allMovies = getAllMovies();
        
        int index = -1;
        
        for (int i=0; i<allMovies.size(); i++) {
            Movie toCheck = allMovies.get(i);
            
            
            if (toCheck.getMovieTitle().equalsIgnoreCase(dvdTitle)) {
                index = i;
                break;
            }
        }
        
        allMovies.remove(index);
        
        writeFile(allMovies);
    }

   
    

}
