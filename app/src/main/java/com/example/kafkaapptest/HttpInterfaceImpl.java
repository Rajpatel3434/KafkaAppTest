package com.example.kafkaapptest;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpInterfaceImpl implements HttpInterface {
    private TextView textView;
    private final OkHttpClient client;

    public HttpInterfaceImpl() {
        client = new OkHttpClient();
    }

    @Override
    public void subscribe(String topic) throws IOException {
        // Create an AsyncTask to make the network request in a background thread.
        NetworkRequestTask task = new NetworkRequestTask(topic, "subscribe");
        task.execute();
    }

    @Override
    public void publish(String topic, String message) throws IOException {
        // Create an AsyncTask to make the network request in a background thread.
        NetworkRequestTask task = new NetworkRequestTask(topic, "publish", message);
        task.execute();
    }

    @Override
    public String getStatus(String topic) throws IOException, ExecutionException, InterruptedException {
        // Create an AsyncTask to make the network request in a background thread.
        NetworkRequestTask task = new NetworkRequestTask(topic, "status");
        task.execute();

//        // Get the result of the network request.
//        String status = task.get();
//
//        return status;
        return null;
    }

    private class NetworkRequestTask extends AsyncTask<Void, Void, Void> {

        private final String topic;
        private final String operation;
        private String message;
        View rootView;

        public NetworkRequestTask(String topic, String operation) {
            this.topic = topic;
            this.operation = operation;
        }

        public NetworkRequestTask(String topic, String operation, String message) {
            this.topic = topic;
            this.operation = operation;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Make the network request based on the operation.
                switch (operation) {
                    case "subscribe":
                        subscribeToTopic();
                        break;
                    case "publish":
                        publishMessageToTopic();
                        break;
                    case "status":
                        getTopicStatus();
                        break;
                }
            } catch (IOException e) {
                // Handle the exception here.
            }

            return null;
        }

        private void subscribeToTopic() throws IOException {
            Request request = new Request.Builder()
                    .url("https://pkc-921jm.us-east-2.aws.confluent.cloud:443/topics" + topic + "/subscriptions")
                    .post(null)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() != 200) {
                throw new RuntimeException("Error subscribing to topic: " + response.code());
            }
        }

        private void publishMessageToTopic() throws IOException {
            String topic = "sample_data";
            String kafkaRestProxyUrl = "https://pkc-921jm.us-east-2.aws.confluent.cloud:443";

            // Replace this with the actual message you want to publish
            String message = "{\"key\": \"18\", \"value\": \"Please get this message\"}";

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/vnd.kafka.json.v2+json");
            RequestBody body = RequestBody.create(mediaType, message);

            Request request = new Request.Builder()
                    .url(kafkaRestProxyUrl + "/topics/" + topic + "/messages")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 200) {
                    // Message was successfully published to Kafka
                    // You can read the response if needed
                } else {
                    throw new RuntimeException("Error publishing message to topic: " + response.code());
                }
            }
        }

        private String getTopicStatus() throws IOException {
            Request request = new Request.Builder()
                    .url("https://pkc-921jm.us-east-2.aws.confluent.cloud:443/topics" + topic + "/status")
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() != 200) {
                throw new RuntimeException("Error getting topic status: " + response.code());
            }

            return response.body().string();


        }
    }
}