package gr.museum.app.museumapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gr.museum.app.museumapp.R;
import gr.museum.app.museumapp.objects.SiteObj;

/**
 * Created by ChrisVaio on 4/27/2016.
 */
public class SitePhotoViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    ArrayList<SiteObj> siteObjArrayList;
    ArrayList<String> photoList;

    public SitePhotoViewPagerAdapter(Context context, ArrayList<String> photoList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.photoList = photoList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);

    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.photo_slide,container,false);

        ImageView siteImage = (ImageView) linearLayout.findViewById(R.id.siteImage);
        Glide.with(mContext).load(photoList.get(position)).into(siteImage);
        container.addView(linearLayout);
        return linearLayout;

    }
}
