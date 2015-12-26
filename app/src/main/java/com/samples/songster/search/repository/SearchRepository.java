package com.samples.songster.search.repository;

import com.samples.songster.search.repository.dto.SearchResultDto;

/**
 * Created by chrisbraunschweiler1 on 02/11/15.
 */
public interface SearchRepository {

    void search(String searchString, SearchListener listener);

    interface SearchListener{
        void onSearchSuccess(SearchResultDto result);
    }
}
