import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class Single {
    private static Single mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;


    private Single(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized Single getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Single(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}

