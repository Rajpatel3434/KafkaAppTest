package com.example.kafkaapptest;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {
    private static final String WEBSOCKET_URL = "wss://pkc-921jm.us-east-2.aws.confluent.cloud:443/kafka/v3/clusters/lkc-dgm89z/topics/sample_data";
    private static final String KAFKA_REST_PROXY_URL = "https://confluent.cloud/environments/env-gkk863/clusters/lkc-dgm89z/topics/sample_data/message-viewer";
    private static final String API_KEY = "PLIB2EMQ5HXZNOBR";
    private static final String API_SECRET = "Orxu1ap+26rIGIIlHUn6Zw8pg3ASxrdlzkcGFr7lZFd1x4S0QVe2ReaWzd6qEd8q";

    private Button produceButton;
    private Button consumeButton;
    private TextView textView;
    private OkHttpClient client = new OkHttpClient();
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        produceButton = findViewById(R.id.buttonProduce);
        consumeButton = findViewById(R.id.buttonConsume);
        textView = findViewById(R.id.textview);
        uiHandler = new Handler(Looper.getMainLooper());

        // Set an onClickListener for the "Produce" button
        produceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute network request in a background thread when the button is clicked
                publishMessageToTopic();
            }
        });

        // Set an onClickListener for the "Consume" button
        consumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute network request in a background thread when the button is clicked
                consumeMessagesFromTopic();
            }
        });
    }

    public void publishMessageToTopic() {
        new NetworkRequestTask().execute("publish");
    }

    public void consumeMessagesFromTopic() {
        new NetworkRequestTask().execute("consume");
    }

    private class NetworkRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String operation = params[0];
            String url = (operation.equals("consume")) ? WEBSOCKET_URL : KAFKA_REST_PROXY_URL;
            String credentials = API_KEY + ":" + API_SECRET;
            String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8)) + "==";

            try {
                Request request;
                if (operation.equals("consume")) {
                    // WebSocket request for consuming messages
                    request = new Request.Builder()
                            .url(WEBSOCKET_URL)
                            .addHeader("Authorization", "Basic " + base64Credentials)
                            .build();

                    WebSocketListener webSocketListener = new WebSocketListener() {
                        @Override
                        public void onMessage(WebSocket webSocket, String text) {
                            uiHandler.post(() -> updateUI(text));
                        }

                        @Override
                        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                            uiHandler.post(() -> updateUI("WebSocket Failure: " + t.getMessage()));
                        }
                    };

                    WebSocket webSocket = client.newWebSocket(request, webSocketListener);
                    webSocket.request();
                } else {
                    // HTTP request for publishing messages
                    MediaType mediaType = MediaType.parse("application/json");
                    String payload = "{\"partition_id\":0,\"headers\":[{\"name\":\"string\",\"value\":\"string\"}],\"key\":{\"type\":\"string\",\"data\":null},\"value\":{\"type\":\"string\",\"data\":null},\"timestamp\":\"2019-08-24T14:15:22Z\"}";
                    RequestBody body = RequestBody.create(mediaType, payload);

                    request = new Request.Builder()
                            .url(KAFKA_REST_PROXY_URL + "/records")
                            .post(body)
                            .addHeader("Authorization", "Basic " + base64Credentials)
                            .build();
                }

                Response response = client.newCall(request).execute();
                return response.body().string() + base64Credentials;
            } catch (IOException e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }

        private void updateUI(String message) {
            textView.append(message + "\n");
        }
    }
}
