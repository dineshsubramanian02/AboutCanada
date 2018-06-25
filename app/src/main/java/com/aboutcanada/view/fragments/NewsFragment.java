package com.aboutcanada.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aboutcanada.adapters.NewsAdapter;
import com.aboutcanada.models.NewsModel;
import com.aboutcanada.models.Rows;
import com.aboutcanada.presenter.MainPresenter;
import com.aboutcanada.presenter.MainViewInterface;
import com.aboutcanada.view.activity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dinesh on 25/06/18.
 */

public class NewsFragment extends BaseFragment implements MainViewInterface {
    private String TAG=NewsFragment.this.getClass().getName();

    @BindView(R.id.news_view)
    RecyclerView newsView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    NewsAdapter adapter;
    MainPresenter mainPresenter;
    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.news_fragment,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    /**
     * initialize view
     */
    private void initView(){
        Log.d(TAG, "initView");
        initMVP();
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        newsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getNewsList();
    }

    /**
     * initialize Model View Presenter
     */
    private void initMVP() {
        Log.d(TAG, "initMVP");
        mainPresenter = new MainPresenter(this);
    }

    /**
     * get news list
     */
    private void getNewsList() {
        Log.d(TAG, "getNewsList");
        if(isNetworkAvailable()) {
            showProgressBar();
            mainPresenter.getRows();
        }
        else {
            //showing error dialog if no network detected
            if(adapter!=null && adapter.getItemCount()>0)
                errorDialog(R.string.network_error_message,false);
            else
                errorDialog(R.string.network_error_message,true);
        }

    }
    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayNews(NewsModel newsModel) {
        if(newsModel !=null) {
            //Log.d(TAG, newsModel.getNewsList().get(1).getTitle());
            toolbar.setTitle(newsModel.getTitle());
            adapter = new NewsAdapter(newsModel.getRows(), getActivity(), new NewsAdapter.OnItemClickListener() {
                @Override
                public void onClick(Rows rows) {
                    showToast(rows.getTitle());
                }
            });
            newsView.setAdapter(adapter);

        }else{
            Log.d(TAG,"NewsResponse response null");
        }
    }

    @Override
    public void displayError(String e) {
        if(adapter!=null && adapter.getItemCount()>0)
            errorDialog(R.string.server_error_message,false);
        else
            errorDialog(R.string.server_error_message,true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh){
            getNewsList();
        }
        return super.onOptionsItemSelected(item);
    }
}

