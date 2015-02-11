package bo.com.linxs.bolivianoticias;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


public class NoticiasDespliegue extends ActionBarActivity {

    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_despliegue);

        Intent i = getIntent();
        ArrayList<Noticia> noti = getIntent().getParcelableArrayListExtra("arraylist");
        //ArrayList<Noticia> noti = (ArrayList<Noticia>) i.getParcelableExtra("arraylist");
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("pos");

        viewPager = (ViewPager)findViewById(R.id.pager);
        try{
            adapter = new ViewPagerAdapter(NoticiasDespliegue.this,noti);
        }catch (Exception e){
            Log.e("error---->", e.toString());
        }


        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_noticias_despliegue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
