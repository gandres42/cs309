package com.example.firsttest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class RecyclerViewClubAdapter extends RecyclerView.Adapter<RecyclerViewClubAdapter.MyViewHolder>{

    Context mContext;
    List<Club> mData;
    Dialog mDialog;
    Intent intent;

    public RecyclerViewClubAdapter(Context mContext, List<Club> mData, Intent intent) {
        this.mContext = mContext;
        this.mData = mData;
        this.intent = intent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.individual_club, parent, false);
        MyViewHolder vHolder = new MyViewHolder(view);

        //Dialog initializer
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_club_activity);

        vHolder.individual_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_name = (TextView) mDialog.findViewById(R.id.dialog_name);
                TextView dialog_members = (TextView) mDialog.findViewById(R.id.dialog_members);
                Button join_club = (Button) mDialog.findViewById(R.id.join_club_button);
                Button chat_button = (Button) mDialog.findViewById(R.id.chat_button);
                Button members_button = (Button) mDialog.findViewById(R.id.members_button);
                Button invisible_view = (Button) mDialog.findViewById(R.id.invisible_example);
                RequestQueue queue = Volley.newRequestQueue(mContext);

                //vHolder position is the key to finding out which View the user clicks on!!
                dialog_name.setText(mData.get(vHolder.getBindingAdapterPosition()).getName());
                int members = mData.get(vHolder.getBindingAdapterPosition()).getNumOfMembers();
                dialog_members.setText("Members: " + members);
                final int clubId = mData.get(vHolder.getBindingAdapterPosition()).getId();

                //Search club to check if user is already a member of club
                String url = "http://10.24.227.150:8080/clubs/get_users?clubid=" + clubId;
                queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean isMember = false;
                            for(int i = 0; i <= response.length() - 1; ++i) {
                                JSONObject user = new JSONObject(intent.getExtras().getString("currentUser"));
                                if (response.getJSONObject(i).getInt("studentid") == user.getInt("studentid")) {
                                    isMember = true;
                                }
                            }
                            if (isMember) {
                                //When club is joined, rid of "join club" button and show other buttons
                                chat_button.setLayoutParams(join_club.getLayoutParams());
                                members_button.setLayoutParams(join_club.getLayoutParams());
                                chat_button.setVisibility(View.VISIBLE);
                                members_button.setVisibility(View.VISIBLE);
                                join_club.setText("Leave Club");
                            }
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

                //On click, add user as a member of the club
                join_club.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (join_club.getText().toString().equals("Join Club")) {
                                JSONObject currentUser = new JSONObject(intent.getExtras().getString("currentUser"));
                                String url = "http://10.24.227.150:8080/users/add_club?clubid=" + clubId + "&studentid="
                                        + currentUser.getInt("studentid");

                                queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                dialog_members.setText("Members: " + (members + 1));
                                                chat_button.setLayoutParams(join_club.getLayoutParams());
                                                members_button.setLayoutParams(join_club.getLayoutParams());
                                                chat_button.setVisibility(View.VISIBLE);
                                                members_button.setVisibility(View.VISIBLE);
                                                join_club.setText("Leave Club");
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("error", error.toString());
                                    }
                                }));
                            }
                            else {
                                JSONObject currentUser = new JSONObject(intent.getExtras().getString("currentUser"));
                                String url = "http://10.24.227.150:8080/users/remove_club?clubid=" + clubId + "&studentid="
                                        + currentUser.getInt("studentid");
                                queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                chat_button.setLayoutParams(invisible_view.getLayoutParams());
                                                members_button.setLayoutParams(invisible_view.getLayoutParams());
                                                chat_button.setVisibility(View.INVISIBLE);
                                                members_button.setVisibility(View.INVISIBLE);
                                                join_club.setText("Join Club");
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("error", error.toString());
                                    }
                                }));
                            }
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                });

                //Send user to club specific chat room when clicked
                chat_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //User might have joined this club so we need to update the user information in intent
                        try {
                            JSONObject userInfo = new JSONObject(intent.getExtras().getString("currentUser"));
                            String url = "http://10.24.227.150:8080/users/search_student_id?studentid=" + userInfo.getString("studentid");
                            queue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Intent intent = new Intent(view.getContext(), ChatRoomsPage.class);
                                            intent.putExtra("currentUser", response.toString());

                                            //Get club id and store it inside intent
                                            intent.putExtra("currentClub", clubId);

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

                //Send user to members list page when clicked
                members_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //User might have joined this club so we need to update the user information in intent
                        try {
                            JSONObject userInfo = new JSONObject(intent.getExtras().getString("currentUser"));
                            String url = "http://10.24.227.150:8080/users/search_student_id?studentid=" + userInfo.getString("studentid");
                            queue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Intent intent = new Intent(view.getContext(), MemberListPage.class);
                                            intent.putExtra("currentUser", response.toString());

                                            //Get club id and store it inside intent
                                            intent.putExtra("currentClub", clubId);

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Alternate background between maroon and gold of items
        if (position % 2 == 0) {
            holder.tv_name.setBackgroundResource(R.drawable.gold_box);
        }
        else {
            holder.tv_name.setBackgroundResource(R.drawable.maroon_box);
        }

        holder.tv_name.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout individual_club;
        private TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);

            individual_club = (LinearLayout) itemView.findViewById(R.id.individual_club_id);
            tv_name = (TextView) itemView.findViewById(R.id.example);
        }
    }
}
