package com.st18rai.moviesapp.utils;

import com.st18rai.moviesapp.model.Genre;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MovieUtils {
    public static String prepareGenres(List<Genre> genreList) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genreList.size(); i++) {
            genres.append(", ").append(genreList.get(i).getName());
        }

        if (genreList.size() > 0) {
            genres.deleteCharAt(0);
        }

        return genres.toString();
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }
}
