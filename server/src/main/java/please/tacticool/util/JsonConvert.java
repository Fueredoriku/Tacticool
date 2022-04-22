package please.tacticool.util;

import com.google.gson.*;
import please.tacticool.enums.ActionType;
import please.tacticool.models.Actions.*;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public class JsonConvert{

    public static Action2 convertToAction(JsonObject jo) {
        Gson gson = new Gson();
        Coordinate co = gson.fromJson(jo.get("coordinate"), Coordinate.class);
        switch (ActionType.valueOf(jo.get("actionType").getAsString())){
            case MOVE:
                return new Move(co);
            case RIFLE:
                return new Rifle(co);
            case BAZOOKA:
                return new Bazooka(co);
            default:
                throw new IllegalStateException("Conversion not implemented");
        }
    }
}
