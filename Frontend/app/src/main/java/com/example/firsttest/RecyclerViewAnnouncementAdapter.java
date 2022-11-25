package com.example.firsttest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class RecyclerViewAnnouncementAdapter extends RecyclerView.Adapter<RecyclerViewAnnouncementAdapter.MyViewHolder>{

    Context mContext;
    List<Announcement> mData;
    Dialog mDialog;
    Intent intent;

    public RecyclerViewAnnouncementAdapter(Context mContext, List<Announcement> mData, Intent intent) {
        this.mContext = mContext;
        this.mData = mData;
        this.intent = intent;
    }

    @NonNull
    @Override
    public RecyclerViewAnnouncementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.individual_announcement, parent, false);
        RecyclerViewAnnouncementAdapter.MyViewHolder vHolder = new RecyclerViewAnnouncementAdapter.MyViewHolder(view);

        //Dialog initializer
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_announcement_activity);

        vHolder.individual_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_club = (TextView) mDialog.findViewById(R.id.dialog_club);
                TextView dialog_announcement_header = (TextView) mDialog.findViewById(R.id.dialog_announcement_header);
                TextView dialog_announcement = (TextView) mDialog.findViewById(R.id.dialog_announcement);
                TextView dialog_date = (TextView) mDialog.findViewById(R.id.dialog_date);

                int clubId = mData.get(vHolder.getBindingAdapterPosition()).getClubId();
                RequestQueue queue = Volley.newRequestQueue(mContext);

                String url = "http://10.24.227.150:8080/clubs/find_by_id?clubid=" + clubId;
                queue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    dialog_club.setText(response.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                }));

                String header = mData.get(vHolder.getBindingAdapterPosition()).getHeader();
                dialog_announcement_header.setText(header);
                String text = mData.get(vHolder.getBindingAdapterPosition()).getText();
                dialog_announcement.setText(text);
                String date = mData.get(vHolder.getBindingAdapterPosition()).getMonth() + "/" + mData.get(vHolder.getBindingAdapterPosition()).getDay() + "/" + mData.get(vHolder.getBindingAdapterPosition()).getYear();
                dialog_date.setText(date);

                //Show pop up
                mDialog.show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAnnouncementAdapter.MyViewHolder holder, int position) {
        holder.tv_header.setText(mData.get(position).getHeader());

        String text = mData.get(position).getText();
        text = text.substring(0, Math.min(text.length(), 125));
        holder.tv_text.setText(text + "...");

        holder.tv_date.setText(mData.get(position).getMonth() + "/" + mData.get(position).getDay() + "/" + mData.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout individual_announcement;
        private TextView tv_header;
        private TextView tv_text;
        private TextView tv_date;

        public MyViewHolder(View itemView) {
            super(itemView);

            individual_announcement = (LinearLayout) itemView.findViewById(R.id.individual_announcement_id);
            tv_header = (TextView) itemView.findViewById(R.id.announcement_header);
            tv_text = (TextView) itemView.findViewById(R.id.announcement_text);
            tv_date = (TextView) itemView.findViewById(R.id.announcement_date);
        }
    }
}
