package httpRequests;

import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.Grid;
import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    public boolean getHasChanged(int gameID, boolean turn) throws IOException {
        URL url = new URL(String.format("http://localhost:8080/api/hasChanged/%d/%b", gameID, turn));
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

    public static void main(String[] args) throws IOException, InterruptedException {
        Request request = new Request();
        int niko = request.getPlayerIDFromLogin("nikolai", "notapassword");
        int gameId = request.joinGame(4, niko);
        int tester = request.getPlayerIDFromLogin("tester", "tester");
        int g2 = request.joinGame(gameId, tester);
        
        Grid grid = request.getGameState(gameId);
        System.out.println(grid);
        List<Player> players = grid.getPlayers();

        Player nikola = null;
        Player testers = null;
        for (Player player : players) {
            if (player.getPlayerID() == niko) {
                nikola = player;
                continue;
            }
            if (player.getPlayerID() == tester) {
                testers = player;
                continue;
            }
        }
        if (nikola == null || testers == null) {
            System.out.println("Something went wrong!");
        }
        Serializer serializer = new Serializer();
        List<InputAction> actions = new ArrayList<>();
        actions.add(new InputAction(ActionType.MOVE, 1, 0));
        actions.add(new InputAction(ActionType.MOVE, 1, 0));
        request.postMoves(serializer.serializeActions(actions), gameId, nikola.getPlayerID());

        Thread.sleep(3000);

        System.out.println(request.getHasChanged(gameId, grid.getTurn()));
    }
}
