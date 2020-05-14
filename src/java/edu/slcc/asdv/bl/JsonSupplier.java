package edu.slcc.asdv.bl;

import edu.slcc.asdv.bl.Query;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;

public class JsonSupplier {

    /**
     * Creates a JsonObject by traversing the arralyList of LinkedHashMap
     *
     * @param trips ArrayList of LinkedHashMap
     * @return JsonObject
     */
    public static JsonObject createJsonObjectForSuppliers(ArrayList< LinkedHashMap<String, String>> suppliers) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        int counter = 1;
        for (LinkedHashMap<String, String> supplier : suppliers) {
// create an array of key-value pairs
            JsonArrayBuilder arraySupplierBld = Json.createArrayBuilder();
            // create each key-value pair as seperate object and add it to the array

            Set<Map.Entry<String, String>> entrySet = supplier.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                arraySupplierBld.add(Json.createObjectBuilder().add(entry.getKey(), entry.getValue()).build());
            }

            // add trip-array to object
            String objectID = "+" + (counter++);
            jsonBuilder.add(objectID, arraySupplierBld);
        }

        JsonObject allTripsJsonObject = jsonBuilder.build();
        return allTripsJsonObject;
    }

    public static JsonObject createJsonObjectForSupplier(String jsonData) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
        JsonObject o = jsonReader.readObject();
        return o;
    }

    public static LinkedHashMap<String, String> createMapOfSupplier(
            String snumber,
            String sname,
            String status,
            String city) {
        LinkedHashMap<String, String> mapSupplier
                = new LinkedHashMap<>();
        mapSupplier.put("snumber", snumber);
        mapSupplier.put("sname", sname);
        mapSupplier.put("status", status);
        mapSupplier.put("city", city);

        return mapSupplier;
    }

//    public static void main(String[] args) throws SQLException {
//        ProductsForSale<String, Keyable> pfs;
//
//        ArrayList<LinkedHashMap<String, String>> productsforsale = new ArrayList();
//        for (Item item: Query.getAllInfo2().findAll2()) {
//            LinkedHashMap<String, String> temp = new LinkedHashMap<>();           
//            temp.put("id", item.getItem_no());
//            temp.put("type", item.getCategory());
//            temp.put("name", item.getTitle());
//            temp.put("description", item.getDescription());
//            temp.put("qty", item.getQty());
//            temp.put("price", item.getPrice());
//            temp.put("image", item.getPicture_ref());
//            productsforsale.add(temp);
//        }
//        JsonObject j = createJsonObjectForSuppliers(productsforsale);
//        StringWriter strWtr = new StringWriter();
//        JsonWriter jsonWtr = Json.createWriter(strWtr);
//        jsonWtr.writeObject(j);
//        jsonWtr.close();
//        
//        JSONobj.readJASONdataUsingParser(strWtr.toString());
//        JsonObject o = createJsonObjectForSupplier(strWtr.toString());
//        System.out.println("----------------");
//        JSONobj.writeObjectModelToStream(o); 
//    }

}
