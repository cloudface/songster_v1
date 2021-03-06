package com.samples.songster.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.samples.songster.R;
import com.samples.songster.search.repository.SearchServiceRepository;
import com.samples.songster.search.repository.dto.SongDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisbraunschweiler1 on 26/10/15.
 */
public class SearchFragment extends Fragment implements SearchView {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EditText mSearchEditText;
    private SearchPresenter mPresenter;
    private ArrayList<SongDto> mSongs;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;
    private TextView mInfoText;
    private TextView mNoResultsText;

    public static SearchFragment getInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        //TODO pass arguments into bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle args) {
        super.onCreate(args);
        //TODO retrieve arguments from bundle

        mSongs = new ArrayList<>();
        mPresenter = new SearchPresenter(new SearchServiceRepository(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchEditText = (EditText) view.findViewById(R.id.searchEditText);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.searchedSongsRecyclerView);
        mInfoText = (TextView) view.findViewById(R.id.infoText);
        mNoResultsText = (TextView) view.findViewById(R.id.noResultsText);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchAdapter = new SearchAdapter(mSongs);
        mRecyclerView.setAdapter(mSearchAdapter);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mPresenter.onSearch(mSearchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mPresenter.present();

        return view;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResults(List<SongDto> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        mSearchAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mInfoText.setVisibility(View.GONE);
        mNoResultsText.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultsMessage() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mInfoText.setVisibility(View.GONE);
        mNoResultsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInfoMessage() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mNoResultsText.setVisibility(View.GONE);
        mInfoText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyboard() {
        if(isAdded() && getActivity() != null) {
            // Check if no view has focus:
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
