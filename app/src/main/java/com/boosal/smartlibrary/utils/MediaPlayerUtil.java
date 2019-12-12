package com.boosal.smartlibrary.utils;

import android.content.Context;
import android.media.AsyncPlayer;
import android.net.Uri;

import static android.media.AudioAttributes.CONTENT_TYPE_MUSIC;
import static android.media.AudioAttributes.CONTENT_TYPE_SPEECH;

/**
 * Created by boosal on 2018/10/31.
 */

public class MediaPlayerUtil {

    private static AsyncPlayer mAsyncPlayer = new AsyncPlayer("smart_library");

    public static void playmusic(Context context, Uri uri){
        mAsyncPlayer.play(context,uri,false,CONTENT_TYPE_MUSIC);
    }

    public static void stopmusic(){
        mAsyncPlayer.stop();
    }


}
