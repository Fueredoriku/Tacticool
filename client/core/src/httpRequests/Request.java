package httpRequests;

import com.anything.tacticool.model.Grid;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Request {

    private final String urlAndroidEmulator = "http://10.0.2.2:8080";
    private final String urlDesktopEmulator = "http://localhost:8080";
    private String baseURL;

    public Request() {

        if (Gdx.app.getType() == ApplicationType.Android) {
            baseURL = urlAndroidEmulator;
        } else {
            baseURL = urlDesktopEmulator;
        }
    }

    public void getter() throws IOException {
        URL url = new URL(String.format("%s", baseURL));
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
        URL url = new URL(String.format("%s/api/move/%d/%d", baseURL,gid,pid));
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

        String json_response = "";
        InputStreamReader in = new InputStreamReader(con.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String text = "";
        while ((text = br.readLine()) != null) {
            json_response += text;
        }
        System.out.println(json_response);
        con.disconnect();
    }

    public Grid getGameState(int gid) throws IOException {
        URL url = new URL(String.format("%s/api/getBoard%d", baseURL, gid));
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
        URL url = new URL(String.format("%s/api/joinGame/%d/%d", baseURL, gid, playerId));
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
        URL url = new URL(String.format("%s/api/getUser/%s/%s", baseURL, name.toLowerCase(), pass.toLowerCase()));
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

    public boolean getHasChanged(int gameID, boolean turn) throws IOException {
        URL url = new URL(String.format("%s/api/hasChanged/%d/%b", baseURL, gameID, turn));
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
        return Boolean.parseBoolean(content.toString());
    }
}
