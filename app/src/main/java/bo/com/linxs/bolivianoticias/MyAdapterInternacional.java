package bo.com.linxs.bolivianoticias;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Linxs on 11/02/2015.
 */
public class MyAdapterInternacional extends RecyclerView.Adapter<MyAdapterInternacional.ViewHolder> {
    private ArrayList<Noticia> pojos;
    NetworkImageView mNetworkImageView;
    ImageLoader mImageLoader;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;

        }
    }

    public MyAdapterInternacional(ArrayList<Noticia> pojos) {
        this.pojos = pojos;
    }

    @Override
    public MyAdapterInternacional.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticias_internacional_rows,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView name = (TextView) holder.view.findViewById(R.id.tituloi);
        mNetworkImageView = (NetworkImageView) holder.view.findViewById(R.id.imageViewI);
        name.setText(pojos.get(position).getBtitulo());
        if(position == 0){
            String IMAGE_URL = "http://www.boliviaentusmanos.com/noticias/images/"+pojos.get(position).getBimagen()+".jpg";
            mImageLoader = VolleyHelper.getInstance(holder.view.getContext()).getImageLoader();
            mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);
        }else{
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            name.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            name.setTextColor(holder.view.getResources().getColor(R.color.secondary_text));

            final float scale = holder.view.getResources().getDisplayMetrics().density;
            int padding_50dp = (int) (50 * scale + 0.5f);
            mNetworkImageView.setDefaultImageResId(R.drawable.ico1);

            CardView cardView = (CardView)holder.view.findViewById(R.id.card_viewNi1);
            android.view.ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.width = layoutParams.MATCH_PARENT;
            layoutParams.height = padding_50dp;
            cardView.setLayoutParams(layoutParams);

            int padding_40dp = (int) (10 * scale + 0.5f);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(padding_40dp,padding_40dp);

            lp.gravity = Gravity.CENTER;
            mNetworkImageView.setLayoutParams(lp);

        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.view.getContext(), NoticiasDespliegue.class);
                intent.putExtra("pos",position);
                intent.putExtra("titulo","NOTICIAS INTERNACIONALES");
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
