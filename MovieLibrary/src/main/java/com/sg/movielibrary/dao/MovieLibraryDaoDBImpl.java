/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sg.movielibrary.dto.Movie;
import com.sg.movielibrary.ui.UserIO;
import com.sg.movielibrary.ui.UserIOConsoleImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author bkb
 */
public class MovieLibraryDaoDBImpl implements MovieLibraryDao {

    private MysqlDataSource ds;

    private UserIOConsoleImpl io;

    public MovieLibraryDaoDBImpl() throws SQLException {
        ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("BrookeMovieCollectionDB"); //Create database named this
        ds.setUser("root");
        ds.setPassword("JavaC0h0rt2020May!"); //replace with MySQL Workbench pw
        ds.setServerTimezone("America/Chicago");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);
    }

//    private String movieTitle;
//    private int movieIDNumber;
//    private String movieReleaseDate;
//    private String MPAARating;
//    private String directorName;
//    private String studio;
//    private String userNotes;
    @Override
    public Movie addMovie(Movie movieTitle) throws MovieLibraryDaoException, SQLException {
        System.out.println("Add Movie");
        String title = io.readString("Movie Title: \n");
        String releaseDate = io.readString("Release date: \n");
        String rating = io.readString("MPAA Rating: \n");
        String director = io.readString("Director: \n");
        String studio = io.readString("Studio: \n");
        String userNotes = io.readString("User Notes: \n");

        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Movie(MovieTitle,ReleaseDate,MPAARating,"
                    + "DirectorName,Studio,UserNotes VALUES (?,?,?,?,?,?)";
            PreparedStatement pStmt = conn.prepareCall(sql);

            pStmt.setString(1, title);
            pStmt.setString(2, releaseDate);
            pStmt.setString(3, rating);
            pStmt.setString(4, director);
            pStmt.setString(5, studio);
            pStmt.setString(6, userNotes);
            pStmt.executeUpdate();
            io.print("Add complete.");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(MovieId) AS Id FROM Movie");
            
            int movieId = rs.getInt("MovieId");
            movieId = movieId + 1;
            pStmt.setInt(0, movieId);
            
        }

    }

    @Override
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> allMovies = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Movie");
            while (rs.next()) {
                Movie toAdd = new Movie();
                toAdd.setMovieIDNumber(rs.getInt("MovieId"));
                toAdd.setMovieTitle(rs.getString("MovieTitle"));
                toAdd.setMovieReleaseDate(rs.getString("ReleaseDate"));
                toAdd.setMPAARating(rs.getString("MPAARating"));
                toAdd.setDirectorName(rs.getString("DirectorName"));
                toAdd.setStudio(rs.getString("Studio"));
                toAdd.setUserNotes(rs.getString("UserNote"));
                allMovies.add(toAdd);
            }
        }
        return allMovies;
    }

    @Override
    public Movie searchMovieById(int movieID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Movie searchMovieByTitle(String dvdTitle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeMovieById(int movieID) throws MovieLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeMovieByTitle(String dvdTitle) throws MovieLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editMovieInformation(Movie toEdit) throws MovieLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
