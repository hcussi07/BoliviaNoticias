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
 * Created by Linxs on 09/02/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
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

    public MyAdapter(ArrayList<Noticia> pojos) {
        this.pojos = pojos;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView name = (TextView) holder.view.findViewById(R.id.btitulo);
        mNetworkImageView = (NetworkImageView) holder.view.findViewById(R.id.imageView);
        name.setText(pojos.get(position).getBtitulo());
        String IMAGE_URL = "http://www.boliviaentusmanos.com/noticias/images/"+pojos.get(position).getBimagen()+".jpg";
        mImageLoader = VolleyHelper.getInstance(holder.view.getContext()).getImageLoader();
        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.view.getContext(), NoticiasDespliegue.class);
                intent.putExtra("pos",position);
                intent.putExtra("titulo","NOTICIAS DE PORTADA");
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
