package com.example.kirill.testtask;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.kirill.testtask.realm.Shot;


import java.util.ArrayList;
import java.util.List;



public class StartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private List<Shot> shots = new ArrayList<>();
    private Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.e("steps","1");
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        presenter = new Presenter(this);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = (RecyclerView)findViewById(R.id.shots_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter( new RecyclerAdapter(shots,presenter));

        recyclerView.setItemViewCacheSize(5);


        presenter.showData();

        if(shots.size() == 0){
            Toast.makeText(this,"Выполните загрузку шотов, путем движения вних  по экрану телефона ",Toast.LENGTH_LONG).show();
        }

    }

    public  RecyclerView getRecyclerView(){
        return recyclerView;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void refreshSwipe(){
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {presenter.loadNextShots();

    }
}
