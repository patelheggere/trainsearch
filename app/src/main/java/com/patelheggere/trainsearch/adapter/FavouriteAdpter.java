package com.patelheggere.trainsearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patelheggere.trainsearch.R;
import com.patelheggere.trainsearch.models.FromTo;

import java.util.List;


public class FavouriteAdpter extends RecyclerView.Adapter<FavouriteAdpter.ViewHolder>
{
    public Context mContext;
    public List<FromTo> fromTos;

    public FavouriteAdpter(Context mContext, List<FromTo> fromTos)
    {
        this.mContext = mContext;
        this.fromTos = fromTos ;

    }

    @Override
    public FavouriteAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_list, parent, false);
        return new FavouriteAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdpter.ViewHolder holder, int position)
    {
        FromTo frmto = fromTos.get(position);

        if(frmto!=null)
        {
           if(frmto.getFrom().getLocation().getName()!=null)
            holder.mDptName.setText(frmto.getFrom().getLocation().getName());
           if(frmto.getFrom().getDeparture()!=null)
               holder.mTimePlat.setText(frmto.getFrom().getDeparture());
           if(frmto.getFrom().getPlatform()!=null)
               holder.mTimePlat.setText(holder.mTimePlat.getText()+"-Platform:"+frmto.getFrom().getPlatform());

            if(frmto.getTo().getLocation().getName()!=null)
                holder.mArrivalName.setText(frmto.getTo().getLocation().getName());
            if(frmto.getTo().getArrival()!=null)
                holder.mArrivalTime.setText(frmto.getTo().getArrival());
            if(frmto.getTo().getPlatform()!=null)
                holder.mArrivalTime.setText(holder.mArrivalTime.getText()+"-Platform:"+frmto.getTo().getPlatform());
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return fromTos.size();
    }









    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mDptName;
        private TextView mTimePlat;
        private TextView mArrivalName;
        private TextView mDep;
        private TextView mArrivalTime;
        private TextView mTvAttachment;
        private ImageView mIvAttachment;

        public ViewHolder(View v) {
            super(v);
            mDptName = v.findViewById(R.id.tvTitle);
            mTimePlat = v.findViewById(R.id.tvCategoryNameDate);
            mArrivalName = v.findViewById(R.id.tvDescription);
            mArrivalTime = v.findViewById(R.id.textView7);
            //mIvAttachment = v.findViewById(R.id.tv_message);

           /* mdescription = v.findViewById(R.id.sounding_message_description);
            mtvcommentsnumber = v.findViewById(R.id.tvcommentsnumber);
            mtvattachmentsnumber = v.findViewById(R.id.tvattachmentnumber);*/

        }
    }
}
