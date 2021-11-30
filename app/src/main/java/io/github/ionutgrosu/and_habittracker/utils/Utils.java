package io.github.ionutgrosu.and_habittracker.utils;

import android.app.Activity;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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

    public static Date yesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateBefore(Date date1, Date date2){
        LocalDate localDate1 = convertToLocalDate(date1);
        LocalDate localDate2 = convertToLocalDate(date2);

        return localDate1.isBefore(localDate2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isToday(Date date) {
        LocalDate localDate = convertToLocalDate(date);
        LocalDate now = LocalDate.now();
        return localDate.isEqual(now);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate convertToLocalDate(Date date){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        return instant.atZone(defaultZoneId).toLocalDate();
    }
}
