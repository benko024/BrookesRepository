/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary;

import com.sg.movielibrary.controller.MovieLibraryController;
import com.sg.movielibrary.dao.MovieLibraryDao;
import com.sg.movielibrary.dao.MovieLibraryDaoDBImpl;
import com.sg.movielibrary.ui.MovieLibraryView;
import com.sg.movielibrary.ui.UserIO;
import com.sg.movielibrary.ui.UserIOConsoleImpl;
import java.sql.SQLException;

/**
 *
 * @author bkb
 */
public class App {
    public static void main(String[] args) throws SQLException {
    UserIO myIo = new UserIOConsoleImpl();
    MovieLibraryView myView = new MovieLibraryView(myIo);
    MovieLibraryDao myDao = new MovieLibraryDaoDBImpl();
    MovieLibraryController controller = new MovieLibraryController(myDao, myView);
    controller.run();}
    
}
