package io.github.ionutgrosu.and_habittracker.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import io.github.ionutgrosu.and_habittracker.R;

public class Utils {
    public static void loadProfilePicture(Activity activity, ImageView imageView, String id) {
        GlideApp.with(activity)
                .load("https://api.kwelo.com/v1/media/identicon/" + id)
                .apply(new RequestOptions().override(80, 80))
                .apply(new RequestOptions().transform(new RoundedCorners(8)))
                .placeholder(R.drawable.ic_baseline_no_accounts_24)
                .into(imageView);
    }
}
