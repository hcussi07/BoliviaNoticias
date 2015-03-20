package bo.com.linxs.bolivianoticias;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Linxs on 12/02/2015.
 */
public class MyAdapterImagenDia extends RecyclerView.Adapter<MyAdapterImagenDia.ViewHolder> {
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

    public MyAdapterImagenDia(ArrayList<Noticia> pojos) {
        this.pojos = pojos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dia_imagenes_rows,parent,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView name = (TextView) holder.view.findViewById(R.id.tituloImg);
        mNetworkImageView = (NetworkImageView) holder.view.findViewById(R.id.imageViewImg);
        name.setText(pojos.get(position).getBtitulo().toString());

        final String  img = pojos.get(position).getBnota();

        final String[] parts = img.split("\\|");
//        Toast.makeText(holder.view.getContext(),parts[0],Toast.LENGTH_LONG).show();
        String IMAGE_URL = "http://www.boliviaentusmanos.com/fotos/galeria/"+parts[0].toString();
        mImageLoader = VolleyHelper.getInstance(holder.view.getContext()).getImageLoader();
        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);

//        mNetworkImageView.setDefaultImageResId(R.drawable.feed);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.view.getContext(),ElDiaEnImagenes.class);
                intent.putExtra("position", position);
                intent.putExtra("arraylist", pojos);
                holder.view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojos.size();
    }


}
