/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.dao;

import com.sg.movielibrary.dto.Movie;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author bkb
 */
public interface MovieLibraryDao {
    
    //Adds a movie to the library and associates it
    //with an auto generated dvd ID Number
    Movie addMovie(Movie movieTitle) throws MovieLibraryDaoException, SQLException;
    
    //Returns a list of all movies in the library
    List <Movie> getAllMovies() throws SQLException;
    
    //Method will allow the user to get the movie
    //by the unique ID assigned to it
    Movie searchMovieById(int movieID);
 
    //Method will allow the user to get the movie
    //by inputting the title associated with the movie    
    Movie searchMovieByTitle (String dvdTitle);
    
    //Method allows the user to remove the movie
    //by inputting the id that is associated with the movie
    void removeMovieById (int movieID) throws MovieLibraryDaoException;
      
    //Method allows the user to remove the movie
    //by inputting the title of the movie. 
    void removeMovieByTitle (String dvdTitle) throws MovieLibraryDaoException ;
    
    //Method allows the user to edit the information
    //for an existing DVD in the collection...Gotta
    //figure out this method
    void editMovieInformation (Movie toEdit) throws MovieLibraryDaoException;
  

}
