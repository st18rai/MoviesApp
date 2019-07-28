package com.st18rai.moviesapp.utils;

import com.st18rai.moviesapp.model.Genre;

import java.util.List;

public class MovieUtils {
    public static String prepareGenres(List<Genre> genreList) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genreList.size(); i++) {
            genres.append(", ").append(genreList.get(i).getName());
        }
        genres.deleteCharAt(0);

        return genres.toString();
    }
}
