package bo.com.linxs.bolivianoticias;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Linxs on 09/02/2015.
 */
public class Noticia implements Parcelable{
//    fitem
    private String bitem;
//    ftipo
    private String btipo;
//    ftitulo
    private String btitulo;
//    fdescripcion
    private String bresumen;
//    fimagen
    private String bnota;
//    fresumen
    private String bimagen;
//    fcomentarios
    private String bleyenda;
//    fautor
    private String bautor;
//    fseo
    private String bfecha;
    private String bhora;
    private String bsector;
    private String bvideo;
    private String bgaleria;
    private String burlseo;

    public static final Creator<Noticia> CREATOR = new Creator<Noticia>(){

        @Override
        public Noticia createFromParcel(Parcel source) {
            return new Noticia(source);
        }

        @Override
        public Noticia[] newArray(int size) {
            return new Noticia[size];
        }
    };

    public Noticia(String bitem, String btipo, String btitulo, String bresumen, String bnota, String bimagen, String bleyenda, String bautor, String bfecha, String bhora, String bsector, String bvideo, String bgaleria, String burlseo) {
        this.bitem = bitem;
        this.btipo = btipo;
        this.btitulo = btitulo;
        this.bresumen = bresumen;
        this.bnota = bnota;
        this.bimagen = bimagen;
        this.bleyenda = bleyenda;
        this.bautor = bautor;
        this.bfecha = bfecha;
        this.bhora = bhora;
        this.bsector = bsector;
        this.bvideo = bvideo;
        this.bgaleria = bgaleria;
        this.burlseo = burlseo;
    }

    public Noticia(Parcel parcel) {
        bitem=parcel.readString();
        btipo=parcel.readString();
        btitulo=parcel.readString();
        bresumen=parcel.readString();
        bnota=parcel.readString();
        bimagen=parcel.readString();
        bleyenda=parcel.readString();
        bautor=parcel.readString();
        bfecha=parcel.readString();
        bhora=parcel.readString();
        bsector=parcel.readString();
        bvideo=parcel.readString();
        bgaleria=parcel.readString();
        burlseo=parcel.readString();
    }


    public Noticia() {
        super();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(bitem);
        parcel.writeString(btipo);
        parcel.writeString(btitulo);
        parcel.writeString(bresumen);
        parcel.writeString(bnota);
        parcel.writeString(bimagen);
        parcel.writeString(bleyenda);
        parcel.writeString(bautor);
        parcel.writeString(bfecha);
        parcel.writeString(bhora);
        parcel.writeString(bsector);
        parcel.writeString(bvideo);
        parcel.writeString(bgaleria);
        parcel.writeString(burlseo);
    }


    public String getBitem() {
        return bitem;
    }

    public String getBtipo() {
        return btipo;
    }

    public String getBtitulo() {
        return btitulo;
    }

    public String getBresumen() {
        return bresumen;
    }

    public String getBnota() {
        return bnota;
    }

    public String getBimagen() {
        return bimagen;
    }

    public String getBleyenda() {
        return bleyenda;
    }

    public String getBautor() {
        return bautor;
    }

    public String getBfecha() {
        return bfecha;
    }

    public String getBhora() {
        return bhora;
    }

    public String getBsector() {
        return bsector;
    }

    public String getBvideo() {
        return bvideo;
    }

    public String getBgaleria() {
        return bgaleria;
    }

    public String getBurlseo() {
        return burlseo;
    }
}

