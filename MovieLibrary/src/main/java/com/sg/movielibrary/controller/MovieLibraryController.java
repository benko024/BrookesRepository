/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.controller;

import com.sg.movielibrary.dao.MovieLibraryDao;
import com.sg.movielibrary.dao.MovieLibraryDaoException;
import com.sg.movielibrary.dao.MovieLibraryDaoFileImpl;
import com.sg.movielibrary.dto.Movie;
import com.sg.movielibrary.ui.MovieLibraryView;
import com.sg.movielibrary.ui.UserIO;
import com.sg.movielibrary.ui.UserIOConsoleImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkb
 */
public class MovieLibraryController {

    private MovieLibraryView view;
    private MovieLibraryDao dao;
    private UserIO io = new UserIOConsoleImpl();

    public MovieLibraryController(MovieLibraryDao dao, MovieLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            try {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        removeMovie();
                        break;
                    case 3:
                        editMovie();
                        break;
                    case 4:
                        displayAllMovies();
                        break;
                    case 5:
                        displayJustOneMovie();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        io.print("UNKNOWN COMMAND");

                }
            } catch (MovieLibraryDaoException | SQLException ex) {
                view.displayErrorMessage(ex.getMessage());

            }

        }
        io.print("GOOD BYE");
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addMovie() throws MovieLibraryDaoException {
        view.displayAddDVDBanner();
        Movie toAdd = view.getNewMovieInfo();
        dao.addMovie(toAdd);
        view.displayAddDVDSuccess();
    }

    private void displayAllMovies() throws MovieLibraryDaoException, SQLException {
        view.displayDisplayAllMoviesBanner();
        List<Movie> allMovies = dao.getAllMovies();
        view.displayAllMovies(allMovies);

    }

    private void displayJustOneMovie() {
        view.displayViewOneMovieBanner();
        String movieOrTitle = view.movieOrTitle();

        if (movieOrTitle.equalsIgnoreCase("id")) {
            int id = view.getMovieId();
            Movie retrieved = dao.searchMovieById(id);
            if (retrieved != null) {
                view.displayOneMovie(retrieved);
                view.displayViewOneMovieSuccess();
            } else {
                view.displayInvalidIdMessage();
            }
            
        }
        
        else if (movieOrTitle.equalsIgnoreCase("title")) {
             String title = view.getMovieTitle();
           Movie retrieved = dao.searchMovieByTitle(title);
            if (retrieved != null) {
                view.displayOneMovie(retrieved);
                view.displayViewOneMovieSuccess();
            } else {
                view.displayInvalidTitleMessage();
            }
            
        }
        
        else {
            view.displayInvalidMessage();
        }
        

    }

    private void removeMovie() throws MovieLibraryDaoException {
        view.displayRemoveMovieBanner();
        String chooseMovieOrTitle = view.movieOrTitle();

        if (chooseMovieOrTitle.equalsIgnoreCase("id")) {
            int id = view.getMovieId();
            Movie retrieved = dao.searchMovieById(id);
            if (retrieved != null) {
                boolean confirm = view.confirmRemove(retrieved);
                if (confirm) {
                    dao.removeMovieById(id);
                    view.displaySuccessfulRemoval();
                }
            } else {
                view.displayInvalidIdMessage();
            }

        } else if (chooseMovieOrTitle.equalsIgnoreCase("title")) {
            String title = view.getMovieTitle();
            Movie retrieved = dao.searchMovieByTitle(title);
            if (retrieved != null) {
                boolean confirm = view.confirmRemove(retrieved);
                if (confirm) {
                    dao.removeMovieByTitle(title);
                    view.displaySuccessfulRemoval();
                }
            } else {
                view.displayInvalidTitleMessage();
            }
        } else {
            view.displayInvalidMessage();
        }

    }

    private void editMovie() throws MovieLibraryDaoException {
        String movieOrTitle = view.movieOrTitle();
        if (movieOrTitle.equalsIgnoreCase("id")) {
            int id = view.getMovieId();

            Movie retrieved = dao.searchMovieById(id);

            if (retrieved != null) {

                Movie updated = view.editMovie(retrieved);

                dao.editMovieInformation(updated);

                view.displaySuccessfulEdit();
            } else {
                view.displayInvalidIdMessage();

            }
        } else if (movieOrTitle.equalsIgnoreCase("title")) {
            String title = view.getMovieTitle();

            Movie retrieved = dao.searchMovieByTitle(title);

            if (retrieved != null) {

                Movie updated = view.editMovie(retrieved);

                dao.editMovieInformation(updated);

                view.displaySuccessfulEdit();
            } else {
                view.displayInvalidTitleMessage();

            }
        } else {
            view.displayInvalidMessage();
        }

    }

}
