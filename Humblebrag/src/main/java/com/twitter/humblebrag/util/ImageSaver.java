package com.twitter.humblebrag.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by vipulsomani on 1/30/14.
 */
public class ImageSaver {
    private static boolean external = true;
    private static String FILENAME="myImage.png";

    public static String saveImage(String filename, String imageUrl, Context context) throws IOException{
        Log.i("HB", imageUrl);
        FILENAME = filename + ".png";
        return writeImage(imageUrl,getTarget(context));
    }

    private static File getTarget(Context context) {
        File root;

        if (external) {
            root=context.getExternalFilesDir(null);
        }
        else {
            root=context.getFilesDir();
        }
        Log.i("HB", root.toString() + "/" + FILENAME);
        return(new File(root, FILENAME));
    }

    private static String writeImage(String imageUrl, File target) throws IOException {
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        Log.i("HB", "Input Stream");
        FileOutputStream fos=new FileOutputStream(target);
        byte[] buffer = new byte[640000];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) >= 0) {
            fos.write(buffer, 0, bytesRead);

        }
        fos.getFD().sync();
        fos.flush();
        fos.close();
        inputStream.close();
        return target.getAbsolutePath().toString();
    }


}
