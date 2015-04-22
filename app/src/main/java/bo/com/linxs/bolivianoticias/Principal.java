package bo.com.linxs.bolivianoticias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;


public class Principal extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mLvDrawerMenu;
    private DrawerMenuItemAdapter mDrawerMenuAdapter;

    private ArrayList<Noticia> noticiasBolivia;
    private ArrayList<Noticia> noticiasPortada;
    private ArrayList<Noticia> noticiasInternacional;
    private ArrayList<Noticia> noticiasImagenes;
    private ArrayList<Noticia> noticiaVideos;

    String url = "http://www.boliviaentusmanos.com/app-web/get_all_portada.php";
    String url2 = "http://www.boliviaentusmanos.com/app-web/get_all_nbolivia.php";
    String url3 = "http://www.boliviaentusmanos.com/app-web/get_all_ninternacional.php";
    String url4 = "http://www.boliviaentusmanos.com/app-web/get_all_nimagenes.php";
    String url5 = "http://www.boliviaentusmanos.com/app-web/get_all_virales.php";

    ProgressDialog progressDialog;

    Button buttonError, btn_contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        buttonError = (Button)findViewById(R.id.button);
        btn_contacto = (Button)findViewById(R.id.btn_contacto);

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere...");
        progressDialog.show();

        if(isOnline()){
            cargar();
        }else{

            try {
                setFragment(1,Fragment_Error.class);
            }catch (Exception e){
                Log.e("fragment",""+e.getMessage());
            }
            progressDialog.dismiss();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLvDrawerMenu = (ListView) findViewById(R.id.lv_drawer_menu);
        mLvDrawerMenu.setSelector( R.drawable.item_selector);

        View header = getLayoutInflater().inflate(R.layout.header,null);
        mLvDrawerMenu.addHeaderView(header);

        List<DrawerMenuItem> menuItems = generateDrawerMenuItems();
        mDrawerMenuAdapter = new DrawerMenuItemAdapter(getApplicationContext(), menuItems);
        mLvDrawerMenu.setAdapter(mDrawerMenuAdapter);

        mLvDrawerMenu.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    public void cargar(){
        noticiasBolivia = new ArrayList<Noticia>();
        noticiasPortada = new ArrayList<Noticia>();
        noticiasInternacional = new ArrayList<Noticia>();
        noticiasImagenes = new ArrayList<Noticia>();
        noticiaVideos = new ArrayList<Noticia>();
        //DESDE AQUI PARA NOTICIAS DE LA PORTADA
        getPortadaView();
        //DESDE AQUI NOTICIAS DE BOLIVIA
        getNoticiasBoliviaView();
        //DESDE AQUI NOTICIAS INTERNACIONALES
        getNoticiasInternacionalView();
        //DESDE AQUI EL DIA EN IMAGENES
        getImagenesDiaView();
        //DESDE AQUI VIDEOS VIRALES
        getViralesView();
    }

    private void getViralesView() {
        final GSONRequest gsonRequestVideos = new GSONRequest(url5,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size(); i++) {
                    Noticia productItem = response.getNoticias().get(i);
                    noticiaVideos.add(new Noticia(productItem.getBitem(), productItem.getBtipo(), productItem.getBtitulo(), productItem.getBresumen(), productItem.getBnota(), productItem.getBimagen(), productItem.getBleyenda(), productItem.getBautor(), productItem.getBfecha(), productItem.getBhora(), productItem.getBsector(), productItem.getBvideo(), productItem.getBgaleria(), productItem.getBurlseo()));
                }
//Para enviar variables al fragment

                setFragment(0, MainActivity.class);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());
                Toast.makeText(getApplicationContext(), "Sin conexion a internet Virales", Toast.LENGTH_SHORT).show();
            }
        });
        gsonRequestVideos.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestVideos);
    }

    private void getImagenesDiaView() {

        final GSONRequest gsonRequestIm = new GSONRequest(url4,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    noticiasImagenes.add(new Noticia(productItem.getBitem(), productItem.getBtipo(), productItem.getBtitulo(), productItem.getBresumen(), productItem.getBnota(), productItem.getBimagen(), productItem.getBleyenda(), productItem.getBautor(), productItem.getBfecha(), productItem.getBhora(), productItem.getBsector(), productItem.getBvideo(), productItem.getBgaleria(), productItem.getBurlseo()));
                }
                //Para enviar variables al fragment

                setFragment(0, MainActivity.class);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());

                Toast.makeText(getApplicationContext(),"Sin conexion a internet Imagenes",Toast.LENGTH_SHORT).show();
            }
        });
        gsonRequestIm.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestIm);
    }

    private void getNoticiasInternacionalView() {

        final GSONRequest gsonRequestI = new GSONRequest(url3,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size(); i++) {
                    Noticia productItem = response.getNoticias().get(i);
                    noticiasInternacional.add(new Noticia(productItem.getBitem(), productItem.getBtipo(), productItem.getBtitulo(), productItem.getBresumen(), productItem.getBnota(), productItem.getBimagen(), productItem.getBleyenda(), productItem.getBautor(), productItem.getBfecha(), productItem.getBhora(), productItem.getBsector(), productItem.getBvideo(), productItem.getBgaleria(), productItem.getBurlseo()));
                }
                //Para enviar variables al fragment

                setFragment(0, MainActivity.class);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());

                Toast.makeText(getApplicationContext(),"Sin conexion a internet Internacional",Toast.LENGTH_SHORT).show();
            }
        });

        gsonRequestI.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestI);
    }

    private void getNoticiasBoliviaView() {
        final GSONRequest gsonRequestB = new GSONRequest(url2,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size(); i++) {
                    Noticia productItem = response.getNoticias().get(i);
                    noticiasBolivia.add(new Noticia(productItem.getBitem(), productItem.getBtipo(), productItem.getBtitulo(), productItem.getBresumen(), productItem.getBnota(), productItem.getBimagen(), productItem.getBleyenda(), productItem.getBautor(), productItem.getBfecha(), productItem.getBhora(), productItem.getBsector(), productItem.getBvideo(), productItem.getBgaleria(), productItem.getBurlseo()));
                }
                //Para enviar variables al fragment

                setFragment(0, MainActivity.class);


                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) Log.e("MainActivity", error.getMessage());

                Toast.makeText(getApplicationContext(), "Sin conexion a internet Bolivia", Toast.LENGTH_SHORT).show();
            }
        });
        gsonRequestB.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequestB);
    }

    private void getPortadaView() {


        final GSONRequest gsonRequest = new GSONRequest(url,Noticias.class, null, new Response.Listener<Noticias>() {
            @Override
            public void onResponse(Noticias response) {

                for (int i = 0; i < response.getNoticias().size();i++){
                    Noticia productItem = response.getNoticias().get(i);
                    noticiasPortada.add(new Noticia(productItem.getBitem(),productItem.getBtipo(),productItem.getBtitulo(),productItem.getBresumen(),productItem.getBnota(),productItem.getBimagen(),productItem.getBleyenda(),productItem.getBautor(),productItem.getBfecha(),productItem.getBhora(),productItem.getBsector(),productItem.getBvideo(),productItem.getBgaleria(),productItem.getBurlseo()));
                }
                //Para enviar variables al fragment
                setFragment(0, MainActivity.class);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null){

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

    public ArrayList<Noticia> getNoticiasBolivia() {
        return noticiasBolivia;
    }

    public ArrayList<Noticia> getNoticiasPortada() {
        return noticiasPortada;
    }

    public ArrayList<Noticia> getNoticiasInternacional() {
        return noticiasInternacional;
    }

    public ArrayList<Noticia> getNoticiasImagenes() {
        return noticiasImagenes;
    }

    public ArrayList<Noticia> getNoticiaVideos() {
        return noticiaVideos;
    }

    private List<DrawerMenuItem> generateDrawerMenuItems() {
        String[] itemsText = getResources().getStringArray(R.array.nav_drawer_items);
        TypedArray itemsIcon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        List<DrawerMenuItem> result = new ArrayList<DrawerMenuItem>();
        for (int i = 0; i < itemsText.length; i++) {
            DrawerMenuItem item = new DrawerMenuItem();
            item.setText(itemsText[i]);
            item.setIcon(itemsIcon.getResourceId(i, -1));
            result.add(item);
        }
        return result;
    }

    public void setFragment(int position, Class<? extends Fragment> fragmentClass) {
        try {
            Fragment fragment = fragmentClass.newInstance();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment, fragmentClass.getSimpleName());
            fragmentTransaction.commit();

            mLvDrawerMenu.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
            mLvDrawerMenu.invalidateViews();
        }
        catch (Exception ex){
            Log.e("setFragment", ex.getMessage());
        }
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
                //Toast.makeText(this,"actualizar",Toast.LENGTH_SHORT).show();
                if(isOnline()){
                    progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setTitle("Actualizando");
                    progressDialog.setMessage("Espere...");
                    progressDialog.show();

                    cargar();

                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(view.getContext(), NoticiasDespliegue.class);
        intent.putExtra("pos",0);
        switch (position){
            case 0:
                if(isOnline()){
                    setFragment(0, MainActivity.class);
                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }
                mDrawerLayout.closeDrawer(mLvDrawerMenu);
                break;
            case 1:
                if(isOnline()){
                    setFragment(0, MainActivity.class);
                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }
                mDrawerLayout.closeDrawer(mLvDrawerMenu);

                break;
            case 2:
                if(isOnline()){
                    intent.putExtra("arraylist",noticiasBolivia);
                    intent.putExtra("titulo", "NOTICIAS DE BOLIVIA");
                    startActivity(intent);
                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }
                mDrawerLayout.closeDrawer(mLvDrawerMenu);

                break;
            case 3:
                if(isOnline()){
                    intent.putExtra("arraylist",noticiasInternacional);
                    intent.putExtra("titulo","NOTICIAS INTERNACIONALES");
                    startActivity(intent);
                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }
                mDrawerLayout.closeDrawer(mLvDrawerMenu);
                break;
            case 4:
                if(isOnline()){
                    Intent inte = new Intent(view.getContext(), ElDiaEnImagenes.class);
                    inte.putExtra("pos",0);
                    inte.putExtra("arraylist",noticiasImagenes);
                    startActivity(inte);
                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }
                mDrawerLayout.closeDrawer(mLvDrawerMenu);
                break;
            case 5:
                if(isOnline()){

                }else{
                    progressDialog.dismiss();
                    setFragment(1, Fragment_Error.class);
                }
                mDrawerLayout.closeDrawer(mLvDrawerMenu);
                mLvDrawerMenu.invalidateViews();
                break;
            case 6:
                setFragment(2, Acerca_de.class);
                mDrawerLayout.closeDrawer(mLvDrawerMenu);
                mLvDrawerMenu.invalidateViews();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mLvDrawerMenu)) {
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    public void onLine(View view) {
        if(isOnline()){
            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Actualizando");
            progressDialog.setMessage("Espere...");
            progressDialog.show();

            cargar();
        }else{
            progressDialog.dismiss();
            setFragment(1, Fragment_Error.class);
            Toast.makeText(this, "Sin conexion a Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void vercontacto(View view){

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@boliviaentusmanos.com"});

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Seleciona un cliente de correo"));

    }
}
