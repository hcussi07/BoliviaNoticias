package bo.com.linxs.bolivianoticias;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Linxs on 09/02/2015.
 */
public class VolleyHelper {
    private static VolleyHelper INSTANCE;
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;
    private Context context;
    private VolleyHelper(Context context) {

        this.context = context;
        requestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

    }

    public static synchronized VolleyHelper getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new VolleyHelper(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
