/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.dto;

/**
 *
 * @author bkb
 */
public class Movie {
    
    //This is all the information you can call when you say
    //"List dvds," or "add dvd" or "edit info for existing dvd."
    
    private String movieTitle;
    private int movieIDNumber;
    private String movieReleaseDate;
    private String MPAARating;
    private String directorName;
    private String studio;
    private String userNotes;

    /**
     * @return the movieTitle
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * @param movieTitle the movieTitle to set
     */
    public void setMovieTitle(String movieTitle) {
        movieTitle.equalsIgnoreCase(movieTitle);
        this.movieTitle = movieTitle;
    }

    /**
     * @return the movieIDNumber
     */
    public int getMovieIDNumber() {
        return movieIDNumber;
    }

    /**
     * @param movieIDNumber the movieIDNumber to set
     */
    public void setMovieIDNumber(int movieIDNumber) {
        this.movieIDNumber = movieIDNumber;
    }

    /**
     * @return the movieReleaseDate
     */
    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    /**
     * @param movieReleaseDate the movieReleaseDate to set
     */
    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    /**
     * @return the MPAARating
     */
    public String getMPAARating() {
        return MPAARating;
    }

    /**
     * @param MPAARating the MPAARating to set
     */
    public void setMPAARating(String MPAARating) {
        this.MPAARating = MPAARating;
    }

    /**
     * @return the directorName
     */
    public String getDirectorName() {
        return directorName;
    }

    /**
     * @param directorName the directorName to set
     */
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    /**
     * @return the studio
     */
    public String getStudio() {
        return studio;
    }

    /**
     * @param studio the studio to set
     */
    public void setStudio(String studio) {
        this.studio = studio;
    }

    /**
     * @return the userNotes
     */
    public String getUserNotes() {
        return userNotes;
    }

    /**
     * @param userNotes the userNotes to set
     */
    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    
}
