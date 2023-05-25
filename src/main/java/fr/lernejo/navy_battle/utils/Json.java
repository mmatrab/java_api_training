package fr.lernejo.navy_battle.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Json {
    public final String id;
    public final String url;
    public final String message;

    public Json(
        @JsonProperty("id") JsonNode id,
        @JsonProperty("url") JsonNode url,
        @JsonProperty("message") JsonNode message) {

        this.id = id.toString();
        this.url = url.toString();
        this.message = message.toString();
    }
    public static Json fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Json.class);
    }


}

