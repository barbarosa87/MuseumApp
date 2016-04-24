package gr.museum.app.museumapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import gr.museum.app.museumapp.R;
import gr.museum.app.museumapp.ShowMuseum;
import gr.museum.app.museumapp.objects.MuseumObj;

/**
 * Created by barbarosa on 24/4/2016.
 */
public class MuseumRecyclerViewAdapter extends RecyclerView.Adapter<MuseumRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<MuseumObj> museumObjArrayList;
    private Context context;

    public MuseumRecyclerViewAdapter(ArrayList<MuseumObj> museumObjArrayList,Context context) {
        this.museumObjArrayList=museumObjArrayList;
        this.context=context;
    }


    public void  insert(MuseumObj museumObj,int position){
        museumObjArrayList.add(museumObj);
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
        Glide.with(context).load(museumObjArrayList.get(position).getPicture1()).into(holder.museumImage);
        holder.museumTitle.setText(museumObjArrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ShowMuseum.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("key",museumObjArrayList.get(position));
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return museumObjArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView museumImage;
        private TextView museumTitle;


        public MyViewHolder(View itemView) {
            super(itemView);
            museumImage=(ImageView) itemView.findViewById(R.id.museumImage);
            museumTitle=(TextView)itemView.findViewById(R.id.museumTitleTxt);

        }
    }


}
