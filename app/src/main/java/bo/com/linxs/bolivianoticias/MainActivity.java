package bo.com.linxs.bolivianoticias;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView, mRecyclerViewBolivia,mRecyclerViewInt, mRecyclerViewImg, mRecyclerViewVideo;
    private RecyclerView.Adapter mAdapter, mAdapterBolivia, mAdapterInt,mAdapterImg, mAdapterVideo;
    private RecyclerView.LayoutManager mLayoutManager, mLayoutManagerBolivia,mLayoutManagerInt,mLayoutManagerImg, mLayoutManagerVideo;

    ProgressDialog progressDialog;

    String url = "http://www.boliviaentusmanos.com/app-web/get_all_portada.php";
    String url2 = "http://www.boliviaentusmanos.com/app-web/get_all_nbolivia.php";
    String url3 = "http://www.boliviaentusmanos.com/app-web/get_all_ninternacional.php";
    String url4 = "http://www.boliviaentusmanos.com/app-web/get_all_nimagenes.php";
    String url5 = "http://www.boliviaentusmanos.com/app-web/get_all_virales.php";

    TextView textBolivia, textImagenes, textVirales, textInternacional;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoFjallaOne();

        button = (Button)findViewById(R.id.button);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);*/

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_custom_view_home);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bb1904")));
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere...");
        progressDialog.show();

        if(isOnline()){
            cargar();
        }else{
            progressDialog.dismiss();
            setContentView(R.layout.activity_error);
        }
    }

    public void textoFjallaOne(){
        textBolivia = (TextView)findViewById(R.id.textBolivia);
        textImagenes = (TextView)findViewById(R.id.textImagenes);
        textInternacional = (TextView)findViewById(R.id.textInternacional);
        textVirales = (TextView)findViewById(R.id.textVirales);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/FjallaOne-Regular.ttf");
        textBolivia.setTypeface(myTypeface);
        textImagenes.setTypeface(myTypeface);
        textInternacional.setTypeface(myTypeface);
        textVirales.setTypeface(myTypeface);
    }

    public void cargar(){
        //DESDE AQUI PARA NOTICIAS DE LA PORTADA
        getPortada();
        //DESDE AQUI NOTICIAS DE BOLIVIA
        getNoticiasBolivia();
        //DESDE AQUI NOTICIAS INTERNACIONALES
        getNoticiasInternacional();
        //DESDE AQUI EL DIA EN IMAGENES
        getImagenesDia();
        //DESDE AQUI VIDEOS VIRALES
        getVirales();
    }

    public void getPortada(){

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<Noticia> pojos = new ArrayList<Noticia>();


        final GSONRequest gsonRequest = new GSONRequest(url,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    pojos.add(new Noticia(productItem.getBitem(),productItem.getBtipo(),productItem.getBtitulo(),productItem.getBresumen(),productItem.getBnota(),productItem.getBimagen(),productItem.getBleyenda(),productItem.getBautor(),productItem.getBfecha(),productItem.getBhora(),productItem.getBsector(),productItem.getBvideo(),productItem.getBgaleria(),productItem.getBurlseo()));
                }
                mAdapter = new MyAdapter(pojos);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null){
                    progressDialog.dismiss();
                    Log.e("MainActivity", error.getMessage());
                    Toast.makeText(getApplicationContext(),"Sin conexion a internet Portada",Toast.LENGTH_SHORT).show();
                }
            }
        });

        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);
    }

    public void getNoticiasBolivia(){
        mRecyclerViewBolivia = (RecyclerView) findViewById(R.id.my_recycler_view2);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewBolivia.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerBolivia = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerViewBolivia.setLayoutManager(mLayoutManagerBolivia);
        final ArrayList<Noticia> noticiasBolivia = new ArrayList<Noticia>();

        final GSONRequest gsonRequestB = new GSONRequest(url2,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    noticiasBolivia.add(new Noticia(productItem.getBitem(),productItem.getBtipo(),productItem.getBtitulo(),productItem.getBresumen(),productItem.getBnota(),productItem.getBimagen(),productItem.getBleyenda(),productItem.getBautor(),productItem.getBfecha(),productItem.getBhora(),productItem.getBsector(),productItem.getBvideo(),productItem.getBgaleria(),productItem.getBurlseo()));
                }
                mAdapterBolivia = new MyAdapterBolivia(noticiasBolivia);
                mRecyclerViewBolivia.setAdapter(mAdapterBolivia);
                mRecyclerViewBolivia.setItemAnimator(new DefaultItemAnimator());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sin conexion a internet Bolivia",Toast.LENGTH_SHORT).show();
            }
        });
        gsonRequestB.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestB);
    }

    public void getNoticiasInternacional(){
        mRecyclerViewInt = (RecyclerView) findViewById(R.id.my_recycler_view3);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewInt.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerInt = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerViewInt.setLayoutManager(mLayoutManagerInt);

        final ArrayList<Noticia> noticiasInt = new ArrayList<Noticia>();

        final GSONRequest gsonRequestI = new GSONRequest(url3,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    noticiasInt.add(new Noticia(productItem.getBitem(),productItem.getBtipo(),productItem.getBtitulo(),productItem.getBresumen(),productItem.getBnota(),productItem.getBimagen(),productItem.getBleyenda(),productItem.getBautor(),productItem.getBfecha(),productItem.getBhora(),productItem.getBsector(),productItem.getBvideo(),productItem.getBgaleria(),productItem.getBurlseo()));
                }
                mAdapterInt = new MyAdapterInternacional(noticiasInt);
                mRecyclerViewInt.setAdapter(mAdapterInt);
                mRecyclerViewInt.setItemAnimator(new DefaultItemAnimator());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sin conexion a internet Internacional",Toast.LENGTH_SHORT).show();
            }
        });

        gsonRequestI.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestI);
    }

    public void getImagenesDia(){
        mRecyclerViewImg = (RecyclerView) findViewById(R.id.my_recycler_view4);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewImg.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerImg = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewImg.setLayoutManager(mLayoutManagerImg);

        final ArrayList<Noticia> imagenes = new ArrayList<Noticia>();

        final GSONRequest gsonRequestIm = new GSONRequest(url4,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    imagenes.add(new Noticia(productItem.getBitem(),productItem.getBtipo(),productItem.getBtitulo(),productItem.getBresumen(),productItem.getBnota(),productItem.getBimagen(),productItem.getBleyenda(),productItem.getBautor(),productItem.getBfecha(),productItem.getBhora(),productItem.getBsector(),productItem.getBvideo(),productItem.getBgaleria(),productItem.getBurlseo()));
                }

                mAdapterImg = new MyAdapterImagenDia(imagenes);
                mRecyclerViewImg.setAdapter(mAdapterImg);
                mRecyclerViewImg.setItemAnimator(new DefaultItemAnimator());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sin conexion a internet Imagenes",Toast.LENGTH_SHORT).show();
            }
        });
        gsonRequestIm.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestIm);
    }

    public void getVirales(){
        mRecyclerViewVideo = (RecyclerView) findViewById(R.id.my_recycler_view5);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewVideo.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerVideo = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewVideo.setLayoutManager(mLayoutManagerVideo);

        final ArrayList<Noticia> videos = new ArrayList<Noticia>();

        final GSONRequest gsonRequestVideos = new GSONRequest(url5,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    videos.add(new Noticia(productItem.getBitem(),productItem.getBtipo(),productItem.getBtitulo(),productItem.getBresumen(),productItem.getBnota(),productItem.getBimagen(),productItem.getBleyenda(),productItem.getBautor(),productItem.getBfecha(),productItem.getBhora(), productItem.getBsector(),productItem.getBvideo(),productItem.getBgaleria(),productItem.getBurlseo()));
                }

                mAdapterVideo = new MyAdapterVideosVirales(videos);
                mRecyclerViewVideo.setAdapter(mAdapterVideo);
                mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());
                Toast.makeText(getApplicationContext(), "Sin conexion a internet Virales", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        gsonRequestVideos.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestVideos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                if(isOnline()){
                    setContentView(R.layout.activity_main);
                    textoFjallaOne();
                    progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setTitle("Actualizando");
                    progressDialog.setMessage("Espere...");
                    progressDialog.show();

                    cargar();
                }else{
                    progressDialog.dismiss();
                    setContentView(R.layout.activity_error);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void onLine(View view) {
        if(isOnline()){
            setContentView(R.layout.activity_main);
            textoFjallaOne();
            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Actualizando");
            progressDialog.setMessage("Espere...");
            progressDialog.show();
            cargar();
        }else{
            progressDialog.dismiss();
            setContentView(R.layout.activity_error);
            Toast.makeText(getApplicationContext(), "Sin conexion a internet", Toast.LENGTH_SHORT).show();
        }
    }
}
