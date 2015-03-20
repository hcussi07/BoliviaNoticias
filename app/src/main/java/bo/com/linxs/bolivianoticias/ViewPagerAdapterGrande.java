package bo.com.linxs.bolivianoticias;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by Linxs on 20/03/2015.
 */
public class ViewPagerAdapterGrande extends PagerAdapter {
    Context context;
    ArrayList<String> objnotas;
    LayoutInflater inflater;


    static class ViewHolder{
        private ImageView img;

    }
    public ViewPagerAdapterGrande(Context context, ArrayList<String> objnotas)
    {
        this.context = context;
        this.objnotas = objnotas;

    }

    @Override
    public int getCount() {
        return objnotas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ViewHolder holder = new ViewHolder();

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item_imagengrande, container, false);
        holder.img=(ImageView)itemView.findViewById(R.id.imageViewGrande);


        String url = objnotas.get(position);

        Picasso.with(itemView.getContext())
                .load(url)
                .placeholder(R.drawable.galeria)
                .fit()
                .centerInside()
                .into(holder.img);

        /*ImageView img = null;
        if (itemView == null) {
//Referenciamos el ImageView
            img = new ImageView(itemView.getContext());
//Referenciamos el ImageView al convertView
            itemView = img;
            img.setPadding(5, 5, 5, 5);
        } else {
            img = (ImageView) itemView;
        }

        // Get the image URL for the current position.
        String url = objnotas.get(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(itemView.getContext()) //
                .load(url) //
                .placeholder(R.drawable.galeria) //
                .error(R.drawable.thumbs) //
                .fit() //
                .tag(itemView.getContext())
                .centerCrop()//
                .into(img);*/
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
}
