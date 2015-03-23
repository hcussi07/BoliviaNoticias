package bo.com.linxs.bolivianoticias;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Linxs on 20/03/2015.
 */
public class ViewPagerAdapterGrande extends PagerAdapter {
    Context context;
    ArrayList<String> objnotas;
    ArrayList<String> objnotas2;
    LayoutInflater inflater;

    static class ViewHolder{
        private ImageView img;
        private TextView text;
    }
    public ViewPagerAdapterGrande(Context context, ArrayList<String> objnotas,ArrayList<String> objnotas2)
    {
        this.context = context;
        this.objnotas = objnotas;
        this.objnotas2 = objnotas2;
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
        holder.img = (ImageView)itemView.findViewById(R.id.imageViewGrande);

        holder.text = (TextView)itemView.findViewById(R.id.tituloImg);
        holder.text.setText(objnotas2.get(position).toString());

        String url = objnotas.get(position);

        Picasso.with(itemView.getContext())
                .load(url)
                .placeholder(R.drawable.galeria)
                .fit()
                .centerInside()
                .into(holder.img);

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
