package br.com.escola.client.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.List;


public class Json {

    public static class indexClass {
        public enum index {
            singleArray,
            middle,
            begin,
            end,
        }
    }

    @SneakyThrows
    public static String getAtribbute(String jsonString, String elementToGet){


        var elementIndex = jsonString.indexOf(elementToGet);
        var start = jsonString.indexOf(":",elementIndex)+1;
        var end = jsonString.indexOf(",",elementIndex);
        return jsonString.substring(start,end);
    }

    public static String toJson(Object toConvert, indexClass.index index) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return switch (index) {
            case singleArray -> "[" + objectMapper.writeValueAsString(toConvert) + "]";
            case middle -> objectMapper.writeValueAsString(toConvert) + ",";
            case begin -> "[" + objectMapper.writeValueAsString(toConvert) + ", ";
            case end -> objectMapper.writeValueAsString(toConvert) + "]";
        };
    }

    public static String toJson(Object toConvert) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(toConvert);
    }


    public static String toJson(List<Object> toConvert) throws JsonProcessingException {
        var start = true;
        ObjectMapper objectMapper = new ObjectMapper();
        var max = toConvert.size();
        var cont = 0;
        var convertedList = "";


        for (Object element : toConvert) {

            if (start&&cont!=max) {
                start = false;
                convertedList += "[ " + objectMapper.writeValueAsString(element) + ", ";
            }
            if (!start&&cont!=max) {
                convertedList +=  objectMapper.writeValueAsString(element)+ ", ";

            }
            if (cont==max) {
                convertedList += objectMapper.writeValueAsString(element)+"]";

            }
            cont++;
        }

        return objectMapper.writeValueAsString(convertedList);
    }
}
