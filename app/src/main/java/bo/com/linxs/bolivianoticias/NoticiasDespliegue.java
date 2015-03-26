package bo.com.linxs.bolivianoticias;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.viewpagerindicator.CirclePageIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class NoticiasDespliegue extends ActionBarActivity {

    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    PagerAdapter adapter;

    private int pos;
    private ArrayList<Noticia> noti;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_despliegue);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bb1904")));

        //Intent i = getIntent();
        noti = getIntent().getParcelableArrayListExtra("arraylist");
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("pos");

        viewPager = (ViewPager)findViewById(R.id.pager);

        adapter = new ViewPagerAdapter(NoticiasDespliegue.this,noti);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //TODO: estoy aqui envio de archivo por bluetooth
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_noticias_despliegue, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        return  super.onCreateOptionsMenu(menu);
    }
    private Intent getDefaultShareIntent(){
        String mayus = "";
        switch (noti.get(pos).getBtipo()){
            case "bol":
                mayus = "BOLIVIA";
                break;
            case "int":
                mayus = "INTERNACIONAL";
                break;
            case "dep":
                mayus = "DEPORTE";
                break;
            case "eco":
                mayus = "ECONOMIA";
                break;
            case "ent":
                mayus = "ENTRETENIMIENTO";
                break;
            case "tec":
                mayus = "CIENCIA Y TECNOLOGIA";
                break;
            case "sal":
                mayus = "SALUD";
                break;
            case "cul":
                mayus = "CULTURA";
                break;
            case "ten":
                mayus = "TENDENCIAS";
                break;
            case "dia":
                mayus = "ENTREVISTAS";
                break;
        }
        String css1 = "ul {list-style: none; display:block}" +
                "li{display:block}#content{width:100%}" +
                "section, article{display:block}" +
                "footer{text-align:center}" +
                "#content .autor{overflow:hidden; color:#666; margin-bottom:5px}" +
                "#content .autor li{float:left; padding-left:2px;}" +
                "#content .autor li.datetxt{padding-top:0px; padding-left:2px}" +
                "#content article{display:block;}" +
                "#content article h1{font:normal 24px Georgia, \"Times New Roman\", Times, serif;padding:3px 0px; letter-spacing:-0.2px}" +
                "#content article h1 a{color:#333;}" +
                "#content article h1 a:hover{ text-decoration:underline}" +
                "#content article p{text-align:justify; padding:5px 10px 10px 10px}" +
                "#content article.resume {background:#EAE8F0}" +
                "#content article.resume ul{padding:10px}" +
                "#content article.resume li{ padding:3px 0}" +
                "#content article.resume li a{color:#2D274D}" +
                "#content article.news a{color:#000;text-decoration:none}" +
                "#content article.news a:hover{text-decoration:underline;color:#666}" +
                "#content article.news .nimg{float:none;width:100%;padding-right:0px}" +
                "#content article.news img{width:100%}" +
                "#content article.news .nonimg{float:left;width:346px;padding-right:10px}" +
                "#content article.news .legend{margin-right:10px;background:#E8E8E8;padding:3px 5px; font-size:11px;}" +
                "#content article.news p{padding:0;text-align:left}" +
                "#content article.news h5 {font:bold 15px 'Roboto', sans-serif;color:#C71900}" +
                "#content article.news #adds{display:none}";

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        String strfecha;

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        strfecha = noti.get(pos).getBfecha();
        Calendar calendario = GregorianCalendar.getInstance();
        Date fecha = calendario.getTime();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = formatoDelTexto.parse(strfecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        String summary;
        String leyenda="";
        if(!noti.get(pos).getBleyenda().equals("")){
            leyenda = "<div class=\"legend\">"+noti.get(pos).getBleyenda()+"</div>";
        }

        if(noti.get(pos).getBimagen()==null || noti.get(pos).getBvideo().equals("si")){
            summary = "<!DOCTYPE html>" +
                    "<html><head><meta charset='utf-8'>" +
                    "<style>"+css1+"</style></head>" +
                    "<body>" +
                    "<div id='content'>" +
                    "<section>" +
                    "<article class='news'>" +
                    "<h5>"+mayus+"</h5>\n" +
                    "            <h1>"+noti.get(pos).getBtitulo()+"</h1>" +
                    "<div class=\"autor\">" +
                    "                "+noti.get(pos).getBautor().toUpperCase()+" -" +
                    "                "+formatoDeFecha.format(fecha)+"" +
                    "            </div>"+
                    "<div class='txtnote'>"+noti.get(pos).getBnota()+"</div>"+
                    "</article>" +
                    "</section>" +
                    "<footer><hr>" +
                    "<em>&copy; 2004 - "+today.year+" <br>www.boliviaentusmanos.com</em>" +
                    "</footer>" +
                    "</div>" +
                    "</body></html>";
        }else{

            summary = "<!DOCTYPE html>" +
                    "<html><head><meta charset='utf-8'>" +
                    "<style>"+css1+"</style></head>" +
                    "<body>" +
                    "<div id='content'>" +
                    "<section>" +
                    "<article class='news'>" +
                    "<h5>"+mayus+"</h5>\n" +
                    "            <h1>"+noti.get(pos).getBtitulo()+"</h1>" +
                    "<div class=\"autor\">" +
                    "                "+noti.get(pos).getBautor().toUpperCase()+" - " + formatoDeFecha.format(fecha) +
                    "</div>"+
                    "<div class='nimg'><img src=\"http://www.boliviaentusmanos.com/noticias/images/"+noti.get(pos).getBimagen()+".jpg\">" +
                    leyenda +
                    "</div>" +
                    "<div class='txtnote'>"+noti.get(pos).getBnota()+"</div>"+
                    "</article>" +
                    "</section>" +
                    "<footer><hr>" +
                    "<em>&copy; 2004 - "+today.year+" <br>www.boliviaentusmanos.com</em>" +
                    "</footer>"+
                    "</div>" +
                    "</body></html>";
        }
        String ht=""+noti.get(pos).getBtitulo();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
