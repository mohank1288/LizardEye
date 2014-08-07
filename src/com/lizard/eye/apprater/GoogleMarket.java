package com.lizard.eye.apprater;

import android.content.Context;
import android.net.Uri;

public class GoogleMarket implements Market {
    private  String marketLink = "market://details?id=";

    @Override
    public Uri getMarketURI(Context context) {
        return Uri.parse(marketLink + context.getPackageName().toString());
    }
}
