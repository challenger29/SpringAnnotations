package com.example.demo;

//https://dzone.com/articles/creating-custom-annotations-in-java

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class JsonSerializer {

    public String serialize(Object object){
        try{
            Class<?> objectClass = requireNonNull(object).getClass();
            Map<String, String> jsonElements = new HashMap<>();

            for(Field field : objectClass.getDeclaredFields()){
                field.setAccessible(true);
                if(field.isAnnotationPresent(JsonField.class)){
                    jsonElements.put(getSerializedKey(field), (String) field.get(object));
                }
            }
            String str = toJsonString(jsonElements);
            System.out.println(str);
            return str;
        }
        catch (IllegalAccessException e){
            System.out.println("Error e" + e.getMessage());
        }

        return null;
    }

    private String toJsonString(Map<String, String> jsonElements) {
        String elementString = jsonElements.entrySet().stream()
                .map(entry -> "\""+ entry.getKey() +"\" : \"" + entry.getValue() +"\"")
                .collect(Collectors.joining(","));
        return "{" +  elementString + "}";
    }


    private static String getSerializedKey(Field field) {
        String annotationValue = field.getAnnotation(JsonField.class).value();
        if (annotationValue.isEmpty()) {
            return field.getName();
        }
        else {
            return annotationValue;
        }
    }

}
