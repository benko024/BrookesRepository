/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.dao;

/**
 *
 * @author bkb
 */
public class MovieLibraryDaoException extends Exception {
    

    public MovieLibraryDaoException( String message ){
        super( message );
    }
    
    public MovieLibraryDaoException( String message, Throwable innerException ){
        super( message, innerException );
}
    
}
