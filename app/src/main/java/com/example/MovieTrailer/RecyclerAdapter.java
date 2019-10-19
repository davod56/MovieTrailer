package com.example.MovieTrailer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Arrays;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<Contact> mArrayList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){

            mlistener=listener;

    }


    public RecyclerAdapter(Context context, ArrayList<Contact> mArrayList){
        this.mArrayList=mArrayList;
        this.mcontext=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String DES= mArrayList.get(position).getDes();
        String Name=mArrayList.get(position).getName();

            if (DES.length()>10){

                String res =String.valueOf(Arrays.copyOfRange(DES.toCharArray(),0,20))+ "...";
                String res2 =String.valueOf(Arrays.copyOfRange(Name.toCharArray(),0,20))+ "...";

                holder.des.setText(res);
                holder.name.setText(res2);

            }
            else {

                holder.des.setText(DES);
                holder.name.setText(mArrayList.get(position).getName());
            }

        Picasso.get()
                .load(mArrayList.get(position).getImage())
                .error(R.mipmap.ic_launcher)
                .resize(120, 100) // resizes the image to these dimensions (in pixel). does not respect aspect ratio
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
          }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,des;
        ImageView img;

        public MyViewHolder(final View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.textView);
            des=itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mlistener!=null){

                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){

                            mlistener.onItemClick(position);


                        }

                    }
                }
            });
        }
    }

}
