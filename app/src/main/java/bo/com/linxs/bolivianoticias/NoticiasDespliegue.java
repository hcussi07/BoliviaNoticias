package bo.com.linxs.bolivianoticias;

import android.content.Intent;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


public class NoticiasDespliegue extends ActionBarActivity {

    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    PagerAdapter adapter;

    private String titulo;
    private int pos;
    private ArrayList<Noticia> noti;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_despliegue);

        noti = getIntent().getParcelableArrayListExtra("arraylist");
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("pos");
        titulo = bundle.getString("titulo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_despliegue_noticias);
        toolbar.setTitle(titulo);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //actionBar.setTitle(titulo);

        viewPager = (ViewPager)findViewById(R.id.pager);

        adapter = new ViewPagerAdapter(NoticiasDespliegue.this,noti);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_noticias_despliegue, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        return  super.onCreateOptionsMenu(menu);
    }
    private Intent getDefaultShareIntent(){
        int posi = viewPager.getCurrentItem();
        String mayus = "";
        switch (noti.get(posi).getBtipo()){
            case "bol":
                mayus = "bolivia";
                break;
            case "int":
                mayus = "internacional";
                break;
            case "dep":
                mayus = "deporte";
                break;
            case "eco":
                mayus = "economia";
                break;
            case "ent":
                mayus = "entretenimiento";
                break;
            case "tec":
                mayus = "tecnologia";
                break;
            case "sal":
                mayus = "salud";
                break;
            case "cul":
                mayus = "cultura";
                break;
            case "ten":
                mayus = "tendencias";
                break;
            case "dia":
                mayus = "entrevistas";
                break;
        }

        String summary = noti.get(posi).getBtitulo()+" \n Detalles en: \nwww.boliviaentusmanos.com/noticias/"+mayus+"/"+noti.get(posi).getBitem()+"/"+noti.get(posi).getBurlseo()+".html";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "boliviaentusmanos.com");
        intent.putExtra(Intent.EXTRA_TEXT,summary);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_item_share) {
            Toast.makeText(getApplicationContext(), "compartir", Toast.LENGTH_SHORT).show();
            return true;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
