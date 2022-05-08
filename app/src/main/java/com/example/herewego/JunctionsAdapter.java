package com.example.herewego;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JunctionsAdapter extends RecyclerView.Adapter<JunctionsAdapter.myviewholder> implements Filterable {

    private List<junctionmodel> exampleList;
    private List<junctionmodel> exampleListFull;
    Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String Sdate;
    int i=0;
    String itemkey;

    private DatabaseReference databaseRef,databaseRef4;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseRef5;



    @Override
    public Filter getFilter() {
        return examplefilter;
    }

  /*  public ShopsAdapter(@NonNull FirebaseRecyclerOptions<junctionmodel> options, Context context) {
        super(options);
        this.context = context;
    }*/




    @NonNull
    @Override
    public com.example.herewego.JunctionsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jndisplaylayout, parent, false);

        return new com.example.herewego.JunctionsAdapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        junctionmodel model = exampleList.get(position);
        holder.name.setText(" "+model.getName());
        holder.count.setText(""+model.getNumber());



       // Glide.with(context).load(model.getShopimage()).into(holder.image);


//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, Displayitems.class);
//                i.putExtra("id",model.getUserid());
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }
//        });
    }




    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, count;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txt);
            count=(TextView) itemView.findViewById(R.id.txt2);



        }

    }
    public JunctionsAdapter(List<junctionmodel> exampleList,Context context) {

        this.exampleList = exampleList;
        this.context=context;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    private Filter examplefilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<junctionmodel> filterlist=new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterlist.addAll(exampleListFull);
            }
            else{
                String pattrn=constraint.toString().toLowerCase().trim();
                for(junctionmodel item :exampleListFull){
                    if(item.getName().toLowerCase().contains(pattrn)){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


}
