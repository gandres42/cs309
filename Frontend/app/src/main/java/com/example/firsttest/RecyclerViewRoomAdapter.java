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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRoomAdapter extends RecyclerView.Adapter<RecyclerViewRoomAdapter.MyViewHolder> {

    Context mContext;
    List<Room> mData;
    Intent intent;

    public RecyclerViewRoomAdapter(Context mContext, List<Room> mData, Intent intent) {
        this.mContext = mContext;
        this.mData = mData;
        this.intent = intent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.individual_room, parent, false);
        RecyclerViewRoomAdapter.MyViewHolder vHolder = new RecyclerViewRoomAdapter.MyViewHolder(view);


        vHolder.individual_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(mContext);

                //vHolder position is the key to finding out which View the user clicks on!!
                int roomId = mData.get(vHolder.getBindingAdapterPosition()).getRoomId();
                ArrayList<Integer> studentIds = mData.get(vHolder.getBindingAdapterPosition()).getStudentIds();
                ArrayList<String> usernames = mData.get(vHolder.getBindingAdapterPosition()).getUsernames();

                Intent intent1 = new Intent(view.getContext(), RoomPage.class);
                intent1.putExtra("roomid", roomId);
                intent1.putExtra("usernames", usernames);
                intent1.putExtra("studentIds", studentIds);
                intent1.putExtra("currentUser", intent.getExtras().getString("currentUser"));
                mContext.startActivity(intent1);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewRoomAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_users.setText(mData.get(position).getUsers());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout individual_room;
        private TextView tv_name;
        private TextView tv_users;

        public MyViewHolder(View itemView) {
            super(itemView);

            individual_room = (LinearLayout) itemView.findViewById(R.id.individual_room_id);
            tv_name = (TextView) itemView.findViewById(R.id.room_name);
            tv_users = (TextView) itemView.findViewById(R.id.room_users);
        }
    }
}
