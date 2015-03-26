package bo.com.linxs.bolivianoticias;

import android.content.Context;
import android.net.http.SslError;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Noticia> objnotas;
    LayoutInflater inflater;
    String strfecha;


    static class ViewHolder{
        WebView vistanota;
        TextView text;
    }
    public ViewPagerAdapter(Context context, ArrayList<Noticia> objnotas)
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
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = new ViewHolder();


        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);


        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        strfecha = objnotas.get(position).getBfecha();
        Calendar calendario = GregorianCalendar.getInstance();
        Date fecha = calendario.getTime();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = formatoDelTexto.parse(strfecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        holder.vistanota = (WebView)itemView.findViewById(R.id.webView);

        String mayus = "";
        switch (objnotas.get(position).getBtipo()){
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

        String summary;
        String leyenda="";
        if(!objnotas.get(position).getBleyenda().equals("")){
            leyenda = "<div class=\"legend\">"+objnotas.get(position).getBleyenda()+"</div>";
        }

        if(objnotas.get(position).getBimagen()==null || objnotas.get(position).getBvideo().equals("si")){
             summary = "<!DOCTYPE html>" +
                    "<html><head><meta charset='utf-8'>" +
                    "<style>"+css1+"</style></head>" +
                    "<body>" +
                    "<div id='content'>" +
                    "<section>" +
                    "<article class='news'>" +
                    "<h5>"+mayus+"</h5>\n" +
                    "            <h1>"+objnotas.get(position).getBtitulo()+"</h1>" +
                    "<div class=\"autor\">" +
                    "                "+objnotas.get(position).getBautor().toUpperCase()+" -" +
                    "                "+formatoDeFecha.format(fecha)+"" +
                    "            </div>"+
                    "<div class='txtnote'>"+objnotas.get(position).getBnota()+"</div>"+
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
                        "            <h1>"+objnotas.get(position).getBtitulo()+"</h1>" +
                        "<div class=\"autor\">" +
                        "                "+objnotas.get(position).getBautor().toUpperCase()+" - " + formatoDeFecha.format(fecha) +
                        "</div>"+
                        "<div class='nimg'><img src=\"http://www.boliviaentusmanos.com/noticias/images/"+objnotas.get(position).getBimagen()+".jpg\">" +
                        leyenda +
                        "</div>" +
                        "<div class='txtnote'>"+objnotas.get(position).getBnota()+"</div>"+
                        "</article>" +
                        "</section>" +
                        "<footer><hr>" +
                        "<em>&copy; 2004 - "+today.year+" <br>www.boliviaentusmanos.com</em>" +
                        "</footer>"+
                        "</div>" +
                        "</body></html>";


        }
        holder.vistanota.getSettings().setJavaScriptEnabled(true);
        holder.vistanota.getSettings().setPluginState(WebSettings.PluginState.ON);

        holder.vistanota.setWebChromeClient(new WebChromeClient() {

        });

        holder.vistanota.loadData(summary, "text/html; charset=UTF-8", null);

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
