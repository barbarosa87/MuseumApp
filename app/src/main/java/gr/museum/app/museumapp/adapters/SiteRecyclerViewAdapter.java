package gr.museum.app.museumapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gr.museum.app.museumapp.R;
import gr.museum.app.museumapp.ShowSite;
import gr.museum.app.museumapp.objects.SiteObj;

/**
 * Created by barbarosa on 24/4/2016.
 */
public class SiteRecyclerViewAdapter extends RecyclerView.Adapter<SiteRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<SiteObj> siteObjArrayList;
    private Context context;

    public SiteRecyclerViewAdapter(ArrayList<SiteObj> siteObjArrayList, Context context) {
        this.siteObjArrayList = siteObjArrayList;
        this.context = context;
    }


    public void insert(SiteObj siteObj, int position) {
        siteObjArrayList.add(siteObj);
        notifyItemInserted(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.museums_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(siteObjArrayList.get(position).getPicture1()).into(holder.museumImage);
        if (siteObjArrayList.get(position).isBeaconEnabled()){
            holder.beaconButton.setVisibility(View.VISIBLE);
        }
        holder.museumTitle.setText(siteObjArrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowSite.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key", siteObjArrayList.get(position));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return siteObjArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView museumImage;
        private TextView museumTitle;
        private Button beaconButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            museumImage = (ImageView) itemView.findViewById(R.id.siteImage);
            museumTitle = (TextView) itemView.findViewById(R.id.museumTitleTxt);
            beaconButton = (Button) itemView.findViewById(R.id.beaconButton);

        }
    }


}
