package com.example.kelvincb.ikazi.mPicasso;


import android.widget.ImageView;

import com.example.kelvincb.ikazi.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {

    //DOWNLOAD AND CACHE IMG
    public static void loadImage(String url, ImageView img)
    {
        if(url != null && url.length()>0)
        {
            Picasso.get().load(url).into(img);
        }
    }

}
