package br.com.trmasolucoes.droiddocs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import br.com.trmasolucoes.droiddocs.R;
import br.com.trmasolucoes.droiddocs.domain.Favorite;
import br.com.trmasolucoes.droiddocs.interfaces.RecyclerViewOnClickListenerHack;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private Context mContext;
    private List<Favorite> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;


    public FavoriteAdapter(Context c, List<Favorite> l){
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_favorite, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        myViewHolder.txtDesc.setText(mList.get(position).getDesc());
        myViewHolder.txtLink.setText(mList.get(position).getLink());
        myViewHolder.imgStat.setImageResource(android.R.drawable.star_big_on);

        try{
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(myViewHolder.itemView);
        }
        catch(Exception e){
            Log.i("Script", "Error: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(Favorite favorite, int position){
        mList.add(favorite);
        notifyItemInserted(position);
    }


    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imgStat;
        public TextView txtDesc;
        public TextView txtLink;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgStat = (ImageView) itemView.findViewById(R.id.imgStar);
            txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
            txtLink = (TextView) itemView.findViewById(R.id.txtLink);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }
    }
}
