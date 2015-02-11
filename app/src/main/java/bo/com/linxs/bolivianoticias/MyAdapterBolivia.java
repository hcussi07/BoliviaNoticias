package bo.com.linxs.bolivianoticias;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Linxs on 09/02/2015.
 */
public class MyAdapterBolivia extends RecyclerView.Adapter<MyAdapterBolivia.ViewHolder> {
    private ArrayList<Noticia> pojos;
    NetworkImageView mNetworkImageView;
    ImageLoader mImageLoader;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;

        }
    }

    public MyAdapterBolivia(ArrayList<Noticia> pojos) {
        this.pojos = pojos;
    }

    @Override
    public MyAdapterBolivia.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticias_bolivia_rows,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView name = (TextView) holder.view.findViewById(R.id.titulob);
        mNetworkImageView = (NetworkImageView) holder.view.findViewById(R.id.imageViewB);
        name.setText(pojos.get(position).getBtitulo());
        if(position == 0){
            String IMAGE_URL = "http://www.boliviaentusmanos.com/noticias/images/"+pojos.get(position).getBimagen()+".jpg";
            mImageLoader = VolleyHelper.getInstance(holder.view.getContext()).getImageLoader();
            mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);
        }else{
            final float scale = holder.view.getResources().getDisplayMetrics().density;
            int padding_50dp = (int) (70 * scale + 0.5f);
            mNetworkImageView.setDefaultImageResId(R.drawable.feed);
            CardView cardView = (CardView)holder.view.findViewById(R.id.card_viewNb1);
            android.view.ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.width = layoutParams.MATCH_PARENT;
            layoutParams.height = padding_50dp;
            cardView.setLayoutParams(layoutParams);

            int padding_40dp = (int) (60 * scale + 0.5f);
            layoutParams = mNetworkImageView.getLayoutParams();
            layoutParams.width = padding_40dp;
            layoutParams.height = layoutParams.WRAP_CONTENT;
            mNetworkImageView.setLayoutParams(layoutParams);
            mNetworkImageView.setPadding(2,3,0,0);
            mNetworkImageView.setScaleType(ImageView.ScaleType.FIT_START);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.view.getContext(), NoticiasDespliegue.class);
                intent.putExtra("pos",position);

                intent.putExtra("arraylist",pojos);
                holder.view.getContext().startActivity(intent);

//                Toast.makeText(v.getContext(), "Aqui estoy : " + pojos.get(position).getBitem(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojos.size();
    }
}
