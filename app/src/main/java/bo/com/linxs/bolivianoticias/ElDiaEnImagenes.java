package bo.com.linxs.bolivianoticias;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class ElDiaEnImagenes extends ActionBarActivity {
    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el_dia_en_imagenes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_despliegue_noticias);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bb1904")));*/

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
