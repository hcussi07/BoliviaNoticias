package bo.com.linxs.bolivianoticias;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Linxs on 23/03/2015.
 */
public class MyAdapterVideosVirales extends RecyclerView.Adapter<MyAdapterVideosVirales.ViewHolder> {
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

    public MyAdapterVideosVirales(ArrayList<Noticia> pojos) {
        this.pojos = pojos;
    }

    @Override
    public MyAdapterVideosVirales.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_viral_rows,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView name = (TextView) holder.view.findViewById(R.id.btituloVid);
        mNetworkImageView = (NetworkImageView) holder.view.findViewById(R.id.imageViewVid);
        name.setText(pojos.get(position).getBtitulo());
        String IMAGE_URL = "http://www.boliviaentusmanos.com/noticias/images/"+pojos.get(position).getBimagen()+".jpg";
        mImageLoader = VolleyHelper.getInstance(holder.view.getContext()).getImageLoader();
        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);

        String idvideo = pojos.get(position).getBnota();
        final String [] id = idvideo.split("\\|");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.view.getContext(), VideosVirales.class);
//                intent.putExtra("pos", position);
                intent.putExtra("contenido", id[0]);
                intent.putExtra("idvideo",id[1]);
                intent.putExtra("titulo",pojos.get(position).getBtitulo());
//                intent.putExtra("arraylist", pojos);
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
