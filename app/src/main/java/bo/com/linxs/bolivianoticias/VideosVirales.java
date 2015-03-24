package bo.com.linxs.bolivianoticias;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideosVirales extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener{

    public static final String API_KEY = "AIzaSyCc2-qWFZH0MFH9nxGF8HGpZhoUU60KDdM";
    public static String VIDEO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_virales);

        Bundle bundle = getIntent().getExtras();
        VIDEO_ID = bundle.getString("idvideo");
        String desc = bundle.getString("contenido");
        String titulo = bundle.getString("titulo");

        WebView video = (WebView)findViewById(R.id.webViewViral);
        /*TextView tit = (TextView)findViewById(R.id.tituloVideo);
        tit.setText(titulo);
        TextView descrip = (TextView)findViewById(R.id.notaVideo);
        descrip.setText(desc);*/

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeplayerview);
        youTubePlayerView.initialize(API_KEY, this);

        String css1 = "ul {list-style: none; display:block}" +
                "li{display:block}#content{width:100%}" +
                "section, article{display:block}" +
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
                "#content article.news .legend{margin-right:10px;background:#E8E8E8;padding:3px 5px; font-size:11px }" +
                "#content article.news p{padding:0;text-align:left}" +
                "#content article.news h5 {font:bold 15px 'Roboto', sans-serif;color:#C71900}" +
                "#content article.news #adds{display:none}";
        String summary;

            summary = "<!DOCTYPE html>" +
                    "<html><head><meta charset='utf-8'>" +
                    "<style>"+css1+"</style></head>" +
                    "<body>" +
                    "<div id='content'>" +
                    "<section>" +
                    "<article class='news'>" +
                    "<h5></h5>\n" +
                    "            <h1>"+titulo+"</h1>" +
                    "<div class=\"autor\">" +
                    "              <ul>" +
                    "                <li class=\"datetxt\"> -</li>" +
                    "                <li class=\"datetxt\"></li>" +
                    "              </ul>" +
                    "            </div>"+
                    "<div class='txtnote'>"+desc+"</div>"+
                    "</article>" +
                    "</section>" +
                    "</div>" +
                    "</body></html>";

        video.getSettings().setJavaScriptEnabled(true);
        video.getSettings().setPluginState(WebSettings.PluginState.ON);
        video.setWebChromeClient(new WebChromeClient() {
        });

        video.loadData(summary, "text/html; charset=UTF-8", null);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(), "onInitializationFailure()", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_videos_virales, menu);
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
