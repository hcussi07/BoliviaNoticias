package bo.com.linxs.bolivianoticias;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Linxs on 09/02/2015.
 */
public class ViewPagerAdapterImagenes extends PagerAdapter {
    Context context;
    ArrayList<Noticia> objnotas;
    LayoutInflater inflater;

    static class ViewHolder{
        private GridView gridView;
        private GridviewAdapter gridAdapter;
        private TextView titulo;
    }
    public ViewPagerAdapterImagenes(Context context, ArrayList<Noticia> objnotas)
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
        final View itemView = inflater.inflate(R.layout.viewpager_item_imagen, container, false);

        holder.gridView = (GridView) itemView.findViewById(R.id.grid_view);

        final ArrayList<String> lista = new ArrayList<String>();
        final String[] parts = objnotas.get(position).getBnota().split("\\|");
        for (int i = 0; i < parts.length; i++) {
            lista.add("http://www.boliviaentusmanos.com/fotos/galeria/" + parts[i]);
        }

        final ArrayList<String> lista2 = new ArrayList<String>();

        final String[] parts2= objnotas.get(position).getBresumen().split("\\|");
        for (int i = 0; i < parts2.length; i++) {
            lista2.add(parts2[i]);
        }

        holder.titulo = (TextView)itemView.findViewById(R.id.tituloImagen);
        holder.titulo.setText(objnotas.get(position).getBtitulo());

        //Construimos el adaptador pasando como
        //segundo parametro el array de imagenes
        holder.gridAdapter = new GridviewAdapter(context, lista);

        //Especificamos el adaptador
        holder.gridView.setAdapter(holder.gridAdapter);
        holder.gridView.setOnScrollListener(new SampleScrollListener(context));

        //al hacer click en una imagen muestra la imagen grande
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                // Launch ImageViewPager.java on selecting GridView Item
                Intent i = new Intent(itemView.getContext().getApplicationContext(), ImageViewPager.class);
                // Send the click position to ImageViewPager.java using intent
                i.putExtra("pos", pos);
                i.putExtra("arraylist",lista);
                i.putExtra("arraylist2",lista2);

                // Start ImageViewPager
                itemView.getContext().startActivity(i);
            }
        });

        ((ViewPager)container).addView(itemView);

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
