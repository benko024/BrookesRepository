/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.ui;

import com.sg.movielibrary.dto.Movie;
import java.util.List;

/**
 *
 * @author bkb
 */
public class MovieLibraryView {

    private UserIO io;

    public MovieLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("___Main Menu___\n");
        io.print("1. Add a movie");
        io.print("2. Remove a movie");
        io.print("3. Edit Information for existing movie");
        io.print("4. Display info for all movies in library");
        io.print("5. Display info for a particular movie");
        io.print("6. Exit \n");


        return io.readInt("Please select from the "
                + "above choices.", 1, 6);

    }

    public Movie getNewMovieInfo() {
        String movieTitle = io.readString("Please enter the movie's title.");
        String releaseDate = io.readString("Please enter the movie's release date.");
        String mpaaRating = io.readString("Please enter the MPAA Rating.");
        String directorName = io.readString("Please enter the director's name.");
        String studio = io.readString("Please enter the studio name.");
        String userNotes = io.readString("Please enter any relevant notes about the movie.");

        Movie currentMovie = new Movie();
        currentMovie.setMovieTitle(movieTitle);
        currentMovie.setMovieReleaseDate(releaseDate);
        currentMovie.setMPAARating(mpaaRating);
        currentMovie.setDirectorName(directorName);
        currentMovie.setStudio(studio);
        currentMovie.setUserNotes(userNotes);
        return currentMovie;
    }

    public void displayAllMovies(List<Movie> movieList) {

        for (Movie toPrint : movieList) {
            io.print("id # " + toPrint.getMovieIDNumber() + " : " + toPrint.getMovieTitle() + " : " + toPrint.getMovieReleaseDate()
                    + " :" + toPrint.getMPAARating() + " : " + toPrint.getDirectorName() + " : "
                    + toPrint.getStudio() + " : " + toPrint.getUserNotes() + "\n");
        }
        io.readString("Please hit enter to continue");

    }

    public void displayOneMovie(Movie toDisplay) {
        io.print("   Movie Title: " + toDisplay.getMovieTitle() + "\n");
        io.print("   Movie Release Date: " + toDisplay.getMovieReleaseDate() + "\n");
        io.print("   Movie MPAA Rating: " + toDisplay.getMPAARating() + "\n");
        io.print("   Movie Director's Name: " + toDisplay.getDirectorName() + "\n");
        io.print("   Movie Studio: " + toDisplay.getStudio() + "\n");
        io.print("   User Notes: " + toDisplay.getUserNotes() + "\n");
    }

    public int getMovieId() {
        int toReturn = io.readInt("Please enter the movie's id #: ", 1, Integer.MAX_VALUE);
        return toReturn;
    }
    
    public String getMovieTitle() {
        String toReturn = io.readString("Please enter the movie's title: ");
        return toReturn;
    }
    
    public String movieOrTitle() {
        String toReturn = io.readString("Search by id or search by title? Please enter 'id' or 'title.'");
        return toReturn;
    }
    

    //lets user get the movie and edit it by the id number
    public Movie editMovie(Movie toEdit) {
        Movie updatedMovie = new Movie();
        updatedMovie.setMovieIDNumber(toEdit.getMovieIDNumber());

        //Let user change title
        updatedMovie.setMovieTitle(io.editString("Enter a new title or press enter to keep [" + toEdit.getMovieTitle() + "]: ",
                toEdit.getMovieTitle()));

        //Let user change release date
        updatedMovie.setMovieReleaseDate(io.editString("Enter a new release date or press enter to keep [" + toEdit.getMovieReleaseDate() + "]: ",
                toEdit.getMovieReleaseDate()));
        //Let user change MPAA rating
        updatedMovie.setMPAARating(io.editString("Enter a new MPAA Rating or press enter to keep [" + toEdit.getMPAARating() + "]: ",
                toEdit.getMPAARating()));
        //Let user change director's name
        updatedMovie.setDirectorName(io.editString("Enter a new Director Name or press enter to keep [" + toEdit.getDirectorName() + "]: ",
                toEdit.getDirectorName()));
        //Let user change the movie studio
        updatedMovie.setStudio(io.editString("Enter a new studio or press enter to keep [" + toEdit.getStudio() + "]: ",
                toEdit.getStudio()));
        //Let the user change the user notes
        updatedMovie.setUserNotes(io.editString("Enter a new user notes or press enter to keep [" + toEdit.getUserNotes() + "]: ",
                toEdit.getUserNotes()));

        return updatedMovie;
    }

    public void displaySuccessfulEdit() {
        io.print("Edit successful.\n");
    }

    //Need toadd a banner as well, for the edit thing. 
    public boolean confirmRemove(Movie toConfirm) {
        displayOneMovie(toConfirm);

        String userResponse = io.readString("Enter \"yes\" to cotniune: ");

        return userResponse.equalsIgnoreCase("yes");

    }
    
    

    //Banners
    public void displayAddDVDBanner() {
        io.print("=== Add a Movie ===");
    }

    public void displayDisplayAllMoviesBanner() {
        io.print("=== Display All Movies  ===");
    }
    
    public void displayRemoveMovieBanner() {
        io.print("=== Remove a Movie ===");
    }

    public void displayViewOneMovieBanner() {
        io.print("=== Display one Movie ===");
    }

    //Successes
    public void displayAddDVDSuccess() {
        io.readString("Movie successfully added to library. Please hit enter to continue.");
    }

    public void displayDisplayAllMoviesSucces() {
        io.print ("List of movies successfully printed. Please hit enter to continue");
    }
    
    public void displaySuccessfulRemoval() {
        io.print("Removal successful. \n");
    }

    public void displayViewOneMovieSuccess() {
        io.readString("Movie successfully displayed. Please hit enter to continue.");
    }

    
    //Error Messages
    
    public void displayInvalidIdMessage() {
        io.print("Please select a valid id. \n");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }
    
    public void displayInvalidTitleMessage(){
        io.print("Please select a valid title. \n");
    }
    
    public void displayInvalidMessage() {
        io.print("Please select a valid title or id");
    }

}
