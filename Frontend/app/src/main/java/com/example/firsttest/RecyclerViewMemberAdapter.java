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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewMemberAdapter extends RecyclerView.Adapter<RecyclerViewMemberAdapter.MyViewHolder>{

    Context mContext;
    List<Member> mData;
    Dialog mDialog;
    Intent intent;

    public RecyclerViewMemberAdapter(Context mContext, List<Member> mData, Intent intent) {
        this.mContext = mContext;
        this.mData = mData;
        this.intent = intent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.individual_member, parent, false);
        MyViewHolder vHolder = new MyViewHolder(view);

        //Dialog initializer
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_member_activity);

        vHolder.individual_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_member_name = (TextView) mDialog.findViewById(R.id.dialog_member_name);
                TextView dialog_member_username = (TextView) mDialog.findViewById(R.id.dialog_member_username);
                TextView dialog_member_email = (TextView) mDialog.findViewById(R.id.dialog_member_email);
                ImageView dialog_member_image = (ImageView) mDialog.findViewById(R.id.dialog_member_image);
                Button dialog_create_room_button = (Button) mDialog.findViewById(R.id.dialog_create_room_button);
                RequestQueue queue = Volley.newRequestQueue(mContext);

                //vHolder position is the key to finding out which View the user clicks on!!
                String name = mData.get(vHolder.getBindingAdapterPosition()).getName();
                dialog_member_name.setText(name);
                String username = mData.get(vHolder.getBindingAdapterPosition()).getUsername();
                dialog_member_username.setText("(" + username + ")");
                String email = mData.get(vHolder.getBindingAdapterPosition()).getEmail();
                dialog_member_email.setText(email);
                int photo = mData.get(vHolder.getBindingAdapterPosition()).getPhoto();
                dialog_member_image.setImageResource(photo);

                int id = mData.get(vHolder.getBindingAdapterPosition()).getId();

                //On click create room with both users in room
                dialog_create_room_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            JSONObject user = new JSONObject(intent.getExtras().getString("currentUser"));
                            String url = "http://10.24.227.150:8080/rooms/create?name=" + username + " and " + user.getString("username");
                            queue.add(new JsonObjectRequest(Request.Method.PUT, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Intent intent = new Intent(view.getContext(), RoomPage.class);
                                        intent.putExtra("currentUser", user.toString());
                                        intent.putExtra("secondUserId", id);
                                        try {
                                            intent.putExtra("roomid", response.getInt("id"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        mContext.startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error", error.toString());
                            }
                            }));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //Show pop up
                mDialog.show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMemberAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_email.setText(mData.get(position).getEmail());
        holder.iv_photo.setImageResource(mData.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout individual_member;
        private TextView tv_name;
        private TextView tv_email;
        private ImageView iv_photo;

        public MyViewHolder(View itemView) {
            super(itemView);

            individual_member = (LinearLayout) itemView.findViewById(R.id.individual_member_id);
            tv_name = (TextView) itemView.findViewById(R.id.member_name);
            tv_email = (TextView) itemView.findViewById(R.id.member_email);
            iv_photo = (ImageView) itemView.findViewById(R.id.member_image);
        }
    }
}
