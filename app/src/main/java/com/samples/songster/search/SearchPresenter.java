package com.samples.songster.search;

import com.samples.songster.search.repository.SearchRepository;
import com.samples.songster.search.repository.dto.SearchResultDto;

/**
 * Created by chrisbraunschweiler1 on 02/11/15.
 */
public class SearchPresenter implements SearchRepository.SearchListener {

    private SearchRepository mSearchRepository;
    private SearchView mSearchView;

    public SearchPresenter(SearchRepository searchRepository, SearchView searchView){
        mSearchRepository = searchRepository;
        mSearchView = searchView;
    }

    public void onSearch(String searchString) {
        if(searchString != null && !searchString.isEmpty()){
            mSearchView.showProgressBar();
            mSearchView.hideKeyboard();
            mSearchRepository.search(searchString, this);
        }
    }

    @Override
    public void onSearchSuccess(SearchResultDto result) {
        if(result != null && result.getSongs().size() > 0){
            mSearchView.showResults(result.getSongs());
        } else {
            mSearchView.showNoResultsMessage();
        }
    }

    public void present() {
        mSearchView.showInfoMessage();
    }
}
