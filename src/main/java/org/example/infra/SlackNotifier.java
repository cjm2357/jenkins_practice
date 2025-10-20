package org.example.infra;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class SlackNotifier {

    private final System.Logger logger = System.getLogger(this.getClass().getName());
    private String webhookUrl;


    public SlackNotifier()  {
        try (InputStream input = SlackNotifier.class.getClassLoader().getResourceAsStream("config/application.properties");) {
            Properties prop = new Properties();
            prop.load(input);
            webhookUrl = prop.getProperty("slack-webhook-url");

        } catch (IOException e) {
            logger.log(System.Logger.Level.ERROR, "fail to create " + this.getClass().getName());
        }
    }

    public void sendMessage(String message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String payload = "{\"text\":\"" + message + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes("UTF-8"));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                logger.log(System.Logger.Level.INFO, "Slack 메시지 전송 성공");
            } else {
                logger.log(System.Logger.Level.WARNING, "Slack 메시지 전송 실패 : " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
