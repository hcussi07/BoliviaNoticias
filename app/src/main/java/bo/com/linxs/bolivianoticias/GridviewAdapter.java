package bo.com.linxs.bolivianoticias;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by Linxs on 19/03/2015.
 */
public class GridviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> items;
    private int numColumn;

    //Constructor de dos parametros
    public GridviewAdapter(Context context, ArrayList<String> items){
        super();
        this.context = context;
        this.items = items;
    }

    //Obetenemos la cantidad de imagenes
    @Override
    public int getCount() {
        return items.size();
    }

    //Obtenemos el objeto a partir de su posicion
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Generamos la vista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
            view.setPadding(2,2,2,2);
        }

        // Get the image URL for the current position.
        String url = items.get(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(url) //
                .placeholder(R.drawable.galeria) //
                .error(R.drawable.thumbs) //
                .fit() //
                .tag(context)
                 .centerCrop()//
                .into(view);

        return view;
    }
}
