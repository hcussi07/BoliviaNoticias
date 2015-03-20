package bo.com.linxs.bolivianoticias;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class ElDiaEnImagenes extends ActionBarActivity {
    private GridView gridView;
    private GridviewAdapter gridAdapter;

    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el_dia_en_imagenes);
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("position");
        ArrayList<Noticia> noti = getIntent().getParcelableArrayListExtra("arraylist");

        viewPager = (ViewPager)findViewById(R.id.pagerImagen);

        adapter = new ViewPagerAdapterImagenes(ElDiaEnImagenes.this,noti);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicatorImagen);
        circlePageIndicator.setViewPager(viewPager);

    }
}
