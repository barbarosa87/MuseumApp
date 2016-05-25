package gr.museum.app.museumapp.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import gr.museum.app.museumapp.objects.SiteObj;

/**
 * Created by ChrisVaio on 21-May-16.
 */
public class Statics {
    public static boolean saveSite(Context context, SiteObj siteObj) {
        try {
            FileOutputStream fos;

            fos = context.openFileOutput(String.valueOf(siteObj.getName()) + ".site", Context.MODE_PRIVATE);


            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(siteObj);
            os.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            } else {
                if (file.getName().contains(".site")) {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


    public static ArrayList<SiteObj> loadAllFavoritesNew(Context context) {
        ArrayList<SiteObj> siteObjArrayList = new ArrayList<>();
        List<File> root = getListFiles(new File(context.getFilesDir().getAbsolutePath()));

        for (File file : root) {
            SiteObj siteObj = loadSite(context, file.getName());
            siteObjArrayList.add(siteObj);
        }
        return siteObjArrayList;
    }

    public static SiteObj loadSite(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            SiteObj simpleClass = (SiteObj) is.readObject();
            is.close();
            fis.close();
            return simpleClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isFavorite(Context context, String fileName) {
        File root = new File(context.getFilesDir().getAbsolutePath() + "/" + fileName + ".site");
        return root.exists();

    }

    public static boolean removeFavoriteNew(Context context, SiteObj fileName) {
        try {
            File root = new File(context.getFilesDir().getAbsolutePath() + "/" + fileName.getId() + ".site");
            if (root.exists()) {
                root.delete();
                return true;
            } else {
                root = new File(context.getFilesDir().getAbsolutePath() + "/" + fileName.getName() + ".site");
                root.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
