package br.com.trmasolucoes.droiddocs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.trmasolucoes.droiddocs.adapter.FavoriteAdapter;
import br.com.trmasolucoes.droiddocs.dao.FavoritesDao;
import br.com.trmasolucoes.droiddocs.domain.Favorite;
import br.com.trmasolucoes.droiddocs.interfaces.RecyclerViewOnClickListenerHack;

public class FavoritesList extends AppCompatActivity implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<Favorite> favorites;
    private FavoritesDao favoritesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewFavorites);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(FavoritesList.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        /** Pego os favoritos cadastrados*/
        favoritesDao = new FavoritesDao(FavoritesList.this);
        favorites = favoritesDao.search();

        /** Adiciono o Adapter*/
        FavoriteAdapter adapter = new FavoriteAdapter(FavoritesList.this, favorites);
        mRecyclerView.setAdapter(adapter);

        /** Adiciono o listener para pegar o click*/
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener( FavoritesList.this, mRecyclerView, this ));
    }


    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView recyclerView, RecyclerViewOnClickListenerHack rvoclh){
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                recyclerView.getChildAdapterPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                recyclerView.getChildAdapterPosition(cv));
                    }

                    return(true);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(FavoritesList.this, MainActivity.class);
        intent.putExtra("link", favorites.get(position).getLink());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }
}
