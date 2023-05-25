package fr.lernejo.navy_battle.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

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

}

