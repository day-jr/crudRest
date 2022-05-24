package br.com.escola.client.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class Json {
    @SneakyThrows
    public static String toJson(Object toConvert, boolean array) {
        ObjectMapper objectMapper = new ObjectMapper();

        if (array == false) {

            return objectMapper.writeValueAsString(toConvert);
        }


        return "[" + objectMapper.writeValueAsString(toConvert) + "]";


    }
}
