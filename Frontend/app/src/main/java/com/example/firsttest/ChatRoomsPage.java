package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;


public class ChatRoomsPage extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;

    private TextView submitButton;
    private TextView whiteBackground;
    private TextView example_right;
    private TextView example_left;
    private EditText mInput;
    private ScrollView sv;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_rooms_page_activity);

        //Connect to Web Socket
        connectWebSocket();

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);
        TextView AnnouncementButton = findViewById(R.id.announcement_create);
        TextView ChatRoomHeader = findViewById(R.id.chat_room_header);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Check if user is allowed to create announcements and meetings
        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            if (!user.getString("permissionlevel").equals("CLUB_BOARD") && !user.getString("permissionlevel").equals("FACULTY_MANAGER") &&
                !user.getString("permissionlevel").equals("STUDENT_MANAGER")) {
                Log.d("permmision level", user.getString("permissionlevel"));
                AnnouncementButton.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set header to club name
        String url = "http://10.24.227.150:8080/clubs/find_by_id?clubid=" + getIntent().getExtras().getInt("currentClub");
        queue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ChatRoomHeader.setText(response.getString("name"));
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

        //Decrease size of club name if longer than 60 characters
        if (ChatRoomHeader.getText().length() >= 60) {
            ChatRoomHeader.setTextSize(18);
        }

        //WebSocket code for chat rooms
        submitButton = findViewById(R.id.submit_chat);
        whiteBackground = findViewById(R.id.white_background);
        example_right = findViewById(R.id.chat_example_right);
        example_left = findViewById(R.id.chat_example_left);
        mInput = findViewById(R.id.create_chat);
        sv = findViewById(R.id.chat_scroll);
        ll = findViewById(R.id.all_messages);

        whiteBackground.bringToFront();
        submitButton.bringToFront();
        mInput.bringToFront();

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Gray out and make the submitButton un-clickable when there is no text in mInput
                if (!mInput.getText().toString().matches("")) {
                    submitButton.setBackgroundTintList(getResources().getColorStateList(R.color.maroon));
                    submitButton.setEnabled(true);
                }
                else {
                    submitButton.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    submitButton.setEnabled(false);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the message from the input
                String message = mInput.getText().toString();

                // If the message is not empty, send the message
                if(message != null && message.length() > 0){
                    mWebSocketClient.send(message);
                }
            }
        });

        //Send user back to the HomePage on click and close web socket
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebSocketClient.close();
                ll.removeAllViews();

                Bundle user = getIntent().getExtras();
                String userInfo = user.getString("currentUser");
                Intent intent = new Intent(view.getContext(), HomePage.class);
                intent.putExtra("currentUser", userInfo);
                startActivity(intent);
            }
        });

        AnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ChatRoomsPage.this);
                dialog.setContentView(R.layout.dialog_create_announcement_activity);
                dialog.show();

                TextView nav_announcement = dialog.findViewById(R.id.nav_view_announcement);
                TextView nav_meeting = dialog.findViewById(R.id.nav_view_meeting);
                EditText meetingDateExample = dialog.findViewById(R.id.edit_date_example);
                EditText meetingDate = dialog.findViewById(R.id.edit_date);
                EditText announcementHeader = dialog.findViewById(R.id.edit_announcement_header);
                EditText announcement = dialog.findViewById(R.id.edit_announcement);
                Button announcementSubmit = dialog.findViewById(R.id.create_announcement_button);

                nav_announcement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        announcementHeader.setHint("Announcement Header");
                        announcementHeader.setText("");
                        announcement.setHint("Announcement:");
                        announcement.setText("");
                        meetingDate.setText("a");
                        meetingDate.setLayoutParams(meetingDateExample.getLayoutParams());
                        meetingDate.setVisibility(View.INVISIBLE);
                        announcementSubmit.setText("Submit Announcement");
                    }
                });

                nav_meeting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        announcementHeader.setHint("Meeting Header and Time");
                        announcementHeader.setText("");
                        announcement.setHint("Location:");
                        announcement.setText("");
                        meetingDate.setText("");
                        meetingDate.setLayoutParams(announcementHeader.getLayoutParams());
                        meetingDate.setVisibility(View.VISIBLE);
                        announcementSubmit.setText("Submit Meeting");
                    }
                });

                announcementSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (announcementHeader.getText().toString().equals("") || announcement.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please fill out all of the information", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if (announcementSubmit.getText().toString().equals("Submit Meeting") && meetingDate.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please fill out all of the information", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if (announcementSubmit.getText().toString().equals("Submit Meeting") && meetingDate.getText().toString().length() < 10) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Please use zeros when filling out the date", Toast.LENGTH_SHORT);
                        }
                        else if(announcementSubmit.getText().toString().equals("Submit Meeting")) {
                            String date = meetingDate.getText().toString() + " ";
                            int day = Integer.parseInt(date.substring(3, 5));
                            int month = Integer.parseInt(date.substring(0, 2));
                            int year = Integer.parseInt(date.substring(6, 10));

                            String url = "http://10.24.227.150:8080/clubs/create_meeting?clubid=" + getIntent().getExtras().getInt("currentClub") + "&day=" + day + "&location=" + announcement.getText().toString() +
                                "&month=" + month + "&title=" + announcementHeader.getText().toString() + "&year=" + year;
                            queue.add(new JsonObjectRequest(Request.Method.PUT, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Created Meeting: " + announcementHeader.getText().toString(), Toast.LENGTH_SHORT);
                                            toast.show();
                                            dialog.dismiss();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error", error.toString());
                                }
                            }));
                        }
                        else {
                            String url = "http://10.24.227.150:8080/clubs/create_annoucement?clubid=" + getIntent().getExtras().getInt("currentClub") + "&text=" + announcement.getText().toString() + "&title=" + announcementHeader.getText().toString();
                            queue.add(new JsonObjectRequest(Request.Method.PUT, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Created Announcement: " + announcementHeader.getText().toString(), Toast.LENGTH_SHORT);
                                            toast.show();
                                            dialog.dismiss();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error", error.toString());
                                }
                            }));
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            /*
             * To test the clientside without the backend, simply connect to an echo server such as:
             *  "ws://echo.websocket.org"
             */
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            uri = new URI("ws://10.24.227.150:8080/chat/" + getIntent().getExtras().getInt("currentClub") + "/" + user.getInt("studentid"));// 10.24.227.150 = localhost
            // uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.d("Websocket", "Opened");
            }

            @Override
            public void onMessage(String msg) {
                Log.d("Websocket", "Message Received");
                // Appends the message received to the previous messages
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray messages = new JSONArray(msg);
                            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
                            for (int i = 0; i <= messages.length() - 1; ++i) {
                                if(messages.getJSONObject(i).getJSONObject("sender").getInt("studentid") == user.getInt("studentid")) {
                                    TextView message = createMessageView(messages.getJSONObject(i).getString("message"), true, example_right);
                                    ll.addView(message);
                                }
                                else {
                                    TextView otherUserView = sentUser(messages.getJSONObject(i).getJSONObject("sender").getString("username"), example_left);
                                    ll.addView(otherUserView);
                                    TextView message = createMessageView(messages.getJSONObject(i).getString("message"), false, example_left);
                                    ll.addView(message);
                                }
                            }

                            //Scroll down to bottom when all chats are created
                            sv.post(new Runnable() {
                                @Override
                                public void run() {
                                    sv.fullScroll(View.FOCUS_DOWN);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (!mInput.getText().toString().equals("")) {
                                try {
                                    JSONObject chat = new JSONObject(msg);
                                    JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
                                    if(chat.getJSONObject("sender").getInt("studentid") == user.getInt("studentid")) {
                                        TextView message = createMessageView(chat.getString("message"), true, example_right);
                                        ll.addView(message);
                                    }
                                    else {
                                        TextView message = createMessageView(chat.getString("message"), false, example_left);
                                        ll.addView(message);
                                    }

                                    //Scroll down to the bottom when a chat is created by user
                                    sv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            sv.fullScroll(View.FOCUS_DOWN);
                                        }
                                    });
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }
                            }
                        }
                        mInput.setText("");
                    }
                });
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.d("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    public TextView createMessageView(String message, boolean currentUser, TextView example) {
        TextView display = new TextView(this);
        display.setLayoutParams(example.getLayoutParams());
        display.setTextSize(16);
        display.setPadding(50, 10, 50, 10);

        if (currentUser) {
            display.setBackgroundResource(R.drawable.maroon_box);
        }
        else {
            display.setBackgroundResource(R.drawable.gold_box);
        }

        display.setText(message);
        return display;
    }

    public TextView sentUser(String currentUser, TextView example) {
        TextView display = new TextView(this);
        display.setLayoutParams(example.getLayoutParams());
        display.setTextSize(16);
        display.setPadding(30, 0, 30, 0);

        display.setBackgroundResource(R.drawable.white_box);


        display.setText(currentUser);
        return display;
    }
}
