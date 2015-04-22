package bo.com.linxs.bolivianoticias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hector on 19/04/2015.
 */
public class Fragment_Error extends Fragment {

    public Fragment_Error (){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_error, container, false);
        return view;

    }
}
