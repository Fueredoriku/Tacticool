package httpRequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {
    public Request() throws MalformedURLException {
    }

    public void getter() throws IOException {
        URL url = new URL("http://localhost:8080");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(content);
        con.disconnect();
    }

    public void postMoves(String body, int gid, int pid) throws IOException {
        URL url = new URL(String.format("http://localhost:8080/api/move/%d/%d",gid,pid));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        byte[] out = body.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        con.setRequestMethod("POST");
        con.setFixedLengthStreamingMode(length);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);
        con.connect();

        try(OutputStream os = con.getOutputStream()) {
            os.write(out);
        }
        System.out.println(body);
        con.disconnect();
    }

    // TODO: GET finished simulation

    // TODO: GET board


    public static void main(String[] args) throws IOException {
        Request request = new Request();
        request.postMoves("{\"actions\":[{\"coordinate\":{\"x\":1,\"y\":0},\"actionType\":\"MOVE\"}]}\n", 1,2);

    }
}
