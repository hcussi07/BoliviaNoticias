package bo.com.linxs.bolivianoticias;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Linxs on 12/02/2015.
 */
public class ImagenDia{// implements Parcelable{

    private String fitem;
    private String ftipo;
    private String ftitulo;
    private String fdescripcion;
    private String fimagen;
    private String fresumen;
    private String fcomentarios;
    private String fautor;
    private String fseo;

    /*public static final Creator<ImagenDia> CREATOR = new Creator<ImagenDia>(){

        @Override
        public ImagenDia createFromParcel(Parcel source) {
            return new ImagenDia(source);
        }

        @Override
        public ImagenDia[] newArray(int size) {
            return new ImagenDia[size];
        }
    };

    public ImagenDia() {
        super();
    }*/

    public ImagenDia(String fitem, String ftipo, String ftitulo, String fdescripcion, String fimagen, String fresumen, String fcomentarios, String fautor, String fseo) {
        this.fitem = fitem;
        this.ftipo = ftipo;
        this.ftitulo = ftitulo;
        this.fdescripcion = fdescripcion;
        this.fimagen = fimagen;
        this.fresumen = fresumen;
        this.fcomentarios = fcomentarios;
        this.fautor = fautor;
        this.fseo = fseo;
    }

    /*public ImagenDia(Parcel parcel) {
        fitem=parcel.readString();
        ftipo=parcel.readString();
        ftitulo=parcel.readString();
        fdescripcion=parcel.readString();
        fimagen=parcel.readString();
        fresumen=parcel.readString();
        fcomentarios=parcel.readString();
        fautor=parcel.readString();
        fseo=parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(fitem);
        parcel.writeString(ftipo);
        parcel.writeString(ftitulo);
        parcel.writeString(fdescripcion);
        parcel.writeString(fimagen);
        parcel.writeString(fresumen);
        parcel.writeString(fcomentarios);
        parcel.writeString(fautor);
        parcel.writeString(fseo);
    }*/

    public String getFitem() {
        return fitem;
    }

    public void setFitem(String fitem) {
        this.fitem = fitem;
    }

    public String getFtipo() {
        return ftipo;
    }

    public void setFtipo(String ftipo) {
        this.ftipo = ftipo;
    }

    public String getFtitulo() {
        return ftitulo;
    }

    public void setFtitulo(String ftitulo) {
        this.ftitulo = ftitulo;
    }

    public String getFdescripcion() {
        return fdescripcion;
    }

    public void setFdescripcion(String fdescripcion) {
        this.fdescripcion = fdescripcion;
    }

    public String getFimagen() {
        return fimagen;
    }

    public void setFimagen(String fimagen) {
        this.fimagen = fimagen;
    }

    public String getFresumen() {
        return fresumen;
    }

    public void setFresumen(String fresumen) {
        this.fresumen = fresumen;
    }

    public String getFcomentarios() {
        return fcomentarios;
    }

    public void setFcomentarios(String fcomentarios) {
        this.fcomentarios = fcomentarios;
    }

    public String getFautor() {
        return fautor;
    }

    public void setFautor(String fautor) {
        this.fautor = fautor;
    }

    public String getFseo() {
        return fseo;
    }

    public void setFseo(String fseo) {
        this.fseo = fseo;
    }
}
