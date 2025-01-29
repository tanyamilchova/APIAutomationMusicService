package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private static String RESOURSE_ID = "resource_id";
    private static String RESOURSE_TO_UPDATE = "resource_to_update";
    private static String RESOURSE_TO_DELETE = "resource_to_remove";
    private static String ILLEGAL_RESOURSE = "resource.illegalId";

//    public static String validateProperty(String propertyValue) {
//        if (propertyValue == null || propertyValue.isEmpty()) {
//            throw new IllegalStateException("The property is missing or empty.");
//        }
//        return propertyValue;
//    }
    public static String validateProperty(String propertyValue) {
        if (propertyValue == null || propertyValue.isEmpty()) {
            throw new IllegalArgumentException("The property is null or empty.");
        }
            return propertyValue;
    }

//    public static   int getResourceIdFromProperty(){
//        String id = (Objects.requireNonNull(
//                        TestDataReader.getTestData("resource_id"))
//                .trim());
//        return Integer.parseInt (id );
//    }
public static  long getResourceIdFromProperty(){
    return getDataFromProperty(RESOURSE_ID);
}
//    public static   long getIllegalResourceIdFromProperty(){
//        String id = (Objects.requireNonNull(
//                        TestDataReader.getTestData("resource.illegalId"))
//                .trim());
//        return Integer.parseInt (id );
//    }
public static   long getIllegalResourceIdFromProperty(){
 return getDataFromProperty(ILLEGAL_RESOURSE);
}
//    public static long getLongResourceIdFromProperty(String property){
//        String id = (Objects.requireNonNull(
//                        TestDataReader.getTestData(property))
//                .trim());
//        return Integer.parseInt (id );
//    }
    public static long getLongResourceIdFromProperty(String property){
     return getDataFromProperty(property);
    }

    public static  long getResourceToDelete(){
        return getDataFromProperty(RESOURSE_TO_DELETE);
    }

    public static  long getResourceToUpdate(){
        return getDataFromProperty(RESOURSE_TO_UPDATE);
    }

    public static long getDataFromProperty(String property){
        validateProperty(property);
        String id = (Objects.requireNonNull(
                        TestDataReader.getTestData(property))
                .trim());
        try {
            return Integer.parseInt (id );
        } catch (NumberFormatException e) {
            logger.error("Error in converting property value to long");
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Util.getLongResourceIdFromProperty("nm");

    }


}
