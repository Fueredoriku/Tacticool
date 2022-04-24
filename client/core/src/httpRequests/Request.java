package httpRequests;

import com.anything.tacticool.model.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {
    public Request() {
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
        con.disconnect();
    }

    public Grid getGameState(int gid) throws IOException {
        URL url = new URL(String.format("http://localhost:8080/api/getBoard%d", gid));
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
        con.disconnect();
        Deserializer dese = new Deserializer();
        return dese.deserializeTurn(content.toString());
    }


    public int joinGame(int gid, int playerId) throws IOException {
        URL url = new URL(String.format("http://localhost:8080/api/joinGame/%d/%d", gid, playerId));
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
        con.disconnect();
        return Integer.parseInt(content.toString());
    }

    public int getPlayerIDFromLogin(String name, String pass) throws IOException {
        URL url = new URL(String.format("http://localhost:8080/api/getUser/%s/%s", name.toLowerCase(), pass.toLowerCase()));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return Integer.parseInt(content.toString());

    }
}
