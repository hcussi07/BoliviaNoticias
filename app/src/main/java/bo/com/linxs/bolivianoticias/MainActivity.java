package bo.com.linxs.bolivianoticias;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Fragment {
    private Context context;

    private RecyclerView mRecyclerView, mRecyclerViewBolivia,mRecyclerViewInt, mRecyclerViewImg, mRecyclerViewVideo;
    private RecyclerView.Adapter mAdapter, mAdapterBolivia, mAdapterInt,mAdapterImg, mAdapterVideo;
    private RecyclerView.LayoutManager mLayoutManager, mLayoutManagerBolivia,mLayoutManagerInt,mLayoutManagerImg, mLayoutManagerVideo;

    TextView textBolivia, textImagenes, textVirales, textInternacional;
    Button button;

    ArrayList<Noticia> noticiasBolivia;
    ArrayList<Noticia> noticiasPortada;
    ArrayList<Noticia> noticiasInternacional;
    ArrayList<Noticia> noticiasImagenes;
    ArrayList<Noticia> noticiaVideos;

    public MainActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.activity_main, container, false);

        Principal actPrincipal = (Principal)getActivity();
        noticiasBolivia = actPrincipal.getNoticiasBolivia();
        noticiasPortada = actPrincipal.getNoticiasPortada();
        noticiasInternacional = actPrincipal.getNoticiasInternacional();
        noticiasImagenes = actPrincipal.getNoticiasImagenes();
        noticiaVideos = actPrincipal.getNoticiaVideos();

        return fragmentView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //button = (Button)getView().findViewById(R.id.buttonError);

        textoFjallaOne();

        cargar();

        context = getActivity();
    }

    public void textoFjallaOne(){

        textBolivia = (TextView)getView().findViewById(R.id.textBolivia);
        textImagenes = (TextView)getView().findViewById(R.id.textImagenes);
        textInternacional = (TextView)getView().findViewById(R.id.textInternacional);
        textVirales = (TextView)getView().findViewById(R.id.textVirales);
        Typeface myTypeface = Typeface.createFromAsset(getView().getContext().getAssets(), "fonts/FjallaOne-Regular.ttf");
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

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(noticiasPortada);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void getNoticiasBolivia(){
        mRecyclerViewBolivia = (RecyclerView) getView().findViewById(R.id.my_recycler_view2);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewBolivia.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerBolivia = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerViewBolivia.setLayoutManager(mLayoutManagerBolivia);

        mAdapterBolivia = new MyAdapterBolivia(noticiasBolivia);
        mRecyclerViewBolivia.setAdapter(mAdapterBolivia);
        mRecyclerViewBolivia.setItemAnimator(new DefaultItemAnimator());
    }

    public void getNoticiasInternacional(){
        mRecyclerViewInt = (RecyclerView) getView().findViewById(R.id.my_recycler_view3);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewInt.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerInt = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerViewInt.setLayoutManager(mLayoutManagerInt);

        mAdapterInt = new MyAdapterInternacional(noticiasInternacional);
        mRecyclerViewInt.setAdapter(mAdapterInt);
        mRecyclerViewInt.setItemAnimator(new DefaultItemAnimator());
    }

    public void getImagenesDia(){
        mRecyclerViewImg = (RecyclerView) getView().findViewById(R.id.my_recycler_view4);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewImg.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerImg = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewImg.setLayoutManager(mLayoutManagerImg);

        mAdapterImg = new MyAdapterImagenDia(noticiasImagenes);
        mRecyclerViewImg.setAdapter(mAdapterImg);
        mRecyclerViewImg.setItemAnimator(new DefaultItemAnimator());

    }

    public void getVirales(){
        mRecyclerViewVideo = (RecyclerView) getView().findViewById(R.id.my_recycler_view5);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewVideo.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerVideo = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewVideo.setLayoutManager(mLayoutManagerVideo);

        mAdapterVideo = new MyAdapterVideosVirales(noticiaVideos);
        mRecyclerViewVideo.setAdapter(mAdapterVideo);
        mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
    }
}
