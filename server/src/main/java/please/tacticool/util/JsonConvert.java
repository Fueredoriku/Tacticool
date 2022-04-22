package please.tacticool.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import please.tacticool.models.Actions.Actions;

import java.lang.reflect.Type;

public class JsonConvert implements JsonDeserializer<Actions> {


    @Override
    public Actions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
