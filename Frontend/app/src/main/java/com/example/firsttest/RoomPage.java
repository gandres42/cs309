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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class RoomPage extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;

    private TextView submitButton;
    private TextView whiteBackground;
    private TextView example_right;
    private TextView example_left;
    private EditText mInput;
    private ScrollView sv;
    private LinearLayout ll;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_page_activity);

        //Connect to Web Socket
        connectWebSocket();

        //Initialize interactable page objects
        TextView BackButton = findViewById(R.id.back_button);
        TextView room_header = findViewById(R.id.chat_room_header);
        TextView additional_settings = findViewById(R.id.additional_settings);
        queue = Volley.newRequestQueue(this);

        //Send user back to the HomePage on click
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

        //Set header to the room name
        String url = "http://10.24.227.150:8080/rooms/all";
        final int roomId = getIntent().getExtras().getInt("roomid");
        queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <= response.length() - 1; ++i) {
                            try {
                                if (response.getJSONObject(i).getInt("id") == roomId) {
                                    room_header.setText(response.getJSONObject(i).getString("name"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }));

        additional_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(RoomPage.this);
                dialog.setContentView(R.layout.dialog_room_edit_activity);
                dialog.show();

                //Initialize dialog views
                TextView clearButton = dialog.findViewById(R.id.clear_button);
                Button changeTitle = dialog.findViewById(R.id.change_room_title);
                Button buttonAddUsers = dialog.findViewById(R.id.button_add_users);
                Button buttonRemoveUsers = dialog.findViewById(R.id.button_remove_users);
                EditText editTitle = dialog.findViewById(R.id.edit_room_title);
                EditText editAddUsers = dialog.findViewById(R.id.edit_add_users);
                EditText editRemoveUsers = dialog.findViewById(R.id.edit_remove_users);

                editTitle.setText(room_header.getText().toString());

                //Leave dialog
                clearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                //change title to text when clicked
                changeTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "http://10.24.227.150:8080/rooms/change_title?roomid=" + roomId + "&title=" + editTitle.getText().toString();
                        queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Changed room to: " + editTitle.getText().toString(), Toast.LENGTH_SHORT);
                                        toast.show();
                                        room_header.setText(editTitle.getText().toString());
                                        editTitle.setText("");
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error", error.toString());
                            }
                        }));
                    }
                });

                //add user from text when clicked
                buttonAddUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "http://10.24.227.150:8080/users/search_username?username=" + editAddUsers.getText().toString();
                        queue.add(new JsonArrayRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        try {
                                            addUser(roomId, response.getJSONObject(0).getInt("studentid"), editAddUsers);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Could not find user", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }));
                    }
                });

                //remove user from text when clicked
                buttonRemoveUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> usernames = getIntent().getExtras().getStringArrayList("usernames");
                        ArrayList<Integer> studentIds = getIntent().getExtras().getIntegerArrayList("studentIds");
                        boolean usernameCheck = false;
                        int usernameLocation = -1;
                        for (int i = 0; i <= usernames.size() - 1; ++i) {
                            if (usernames.get(i).equals(editRemoveUsers.getText().toString())) {
                                usernameCheck = true;
                                usernameLocation = i;
                            }
                        }
                        final int usernameLocation1 = usernameLocation;
                        if (!usernameCheck) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Username does not match anyone in room.", Toast.LENGTH_SHORT);
                            toast.show();
                            editRemoveUsers.setText("");
                        }
                        else {
                            String url = "http://10.24.227.150:8080/users/remove_room?roomid=" + roomId + "&studentid=" + studentIds.get(usernameLocation);
                            queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Removed " + usernames.get(usernameLocation1) + " from the room.", Toast.LENGTH_SHORT);
                                            toast.show();
                                            editRemoveUsers.setText("");
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("error", error.toString());
                                }
                            }));
                        }
                    }
                });
            }
        });

        //Check if user is already a member of the room
        boolean memberOfRoom = false;
        try {
            JSONObject user = new JSONObject(getIntent().getExtras().getString("currentUser"));
            for (int i = 0; i <= user.getJSONArray("rooms").length() - 1; ++i) {
                if (user.getJSONArray("rooms").getJSONObject(i).getInt("id") == roomId) {
                    memberOfRoom = true;
                }
            }

            if (memberOfRoom == false) {
                url = "http://10.24.227.150:8080/users/add_room?roomid=" + roomId + "&studentid=" + user.getInt("studentid");
                queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                }));

                url = "http://10.24.227.150:8080/users/add_room?roomid=" + roomId + "&studentid=" + getIntent().getExtras().getInt("secondUserId");
                queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                }));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                } else {
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
                if (message != null && message.length() > 0) {
                    mWebSocketClient.send(message);
                }
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
            uri = new URI("ws://10.24.227.150:8080/direct_chat/" + getIntent().getExtras().getInt("roomid") + "/" + user.getInt("studentid"));// 10.24.227.150 = localhost
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

    public void addUser(int roomId, int studentId, EditText editAddUsers) {
        queue = Volley.newRequestQueue(this);
        String url = "http://10.24.227.150:8080/users/add_room?roomid=" + roomId + "&studentid=" + studentId;
        queue.add(new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Added " + editAddUsers.getText().toString() + " to the room.", Toast.LENGTH_SHORT);
                        toast.show();
                        editAddUsers.setText("");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }));
    }
}