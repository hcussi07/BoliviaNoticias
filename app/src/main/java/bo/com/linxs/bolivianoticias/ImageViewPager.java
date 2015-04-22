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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


public class ImageViewPager extends ActionBarActivity {

    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_despliegue_noticias);
        setSupportActionBar(toolbar);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bb1904")));*/

        ArrayList<String> noti = getIntent().getStringArrayListExtra("arraylist");
        ArrayList<String> noti2 = getIntent().getStringArrayListExtra("arraylist2");
        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("pos");

//        Toast.makeText(this,pos+"->"+noti2.get(pos),Toast.LENGTH_SHORT).show();
        viewPager = (ViewPager)findViewById(R.id.pagerGrande);
        adapter = new ViewPagerAdapterGrande(ImageViewPager.this,noti,noti2);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicatorGrande);
        circlePageIndicator.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_view_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
