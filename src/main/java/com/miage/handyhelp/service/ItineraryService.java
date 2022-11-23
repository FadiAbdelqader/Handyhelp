package com.miage.handyhelp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

import org.json.*;


public class ItineraryService {

    private String token = "521617cc-a238-4707-9cfb-4b67e8a48c40";

    //
    public static String sansAccent(String notGoodAddress)
    {
        String goodAddress;
        String strTemp = Normalizer.normalize(notGoodAddress, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return goodAddress =pattern.matcher(strTemp).replaceAll("");
    }

    // -90<latitude<90
    // -180<longitude<180
    // longitude:latitude
    public Map<Double, Double> getLongLat(String address) throws IOException {
        Map<Double, Double> longitudeLatitude = new HashMap<>();
        address=address.replace(" ", "+");
        URL url = new URL(String.format("https://api-adresse.data.gouv.fr/search/?q=%s", sansAccent(address)));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();

            //Parsing the JSONObject
            String JSONString = informationString.toString();
            JSONObject obj = new JSONObject(JSONString);
            JSONArray arr = obj.getJSONArray("features");
            JSONObject features = new JSONObject(arr.get(0).toString());
            JSONObject geometry = features.getJSONObject("geometry");
            JSONArray longlat = geometry.getJSONArray("coordinates");
            BigDecimal bd1= (BigDecimal) longlat.get(0);
            Double d1= bd1.doubleValue();
            BigDecimal bd2= (BigDecimal) longlat.get(1);
            Double d2= bd2.doubleValue();
            longitudeLatitude.put(d1,d2);
        }
        return longitudeLatitude;
    }


    public JSONArray curlItinerary(Map<Double, Double> departure, Map<Double, Double> arrival) throws IOException, InterruptedException,JSONException {
        Double longArrival=0d,latArrival=0d,longDeparture=0d,latDeparture=0d;


        for(Map.Entry entry: arrival.entrySet() ){
            longArrival = (Double) entry.getKey();
            latArrival = (Double) entry.getValue();
        }

        for(Map.Entry entry: departure.entrySet() ){
            longDeparture = (Double) entry.getKey();
            latDeparture = (Double) entry.getValue();
        }

        String sURL = "curl https://521617cc-a238-4707-9cfb-4b67e8a48c40@api.navitia.io/v1/journeys?from="
                +longDeparture +";" + latDeparture + "&to="+ longArrival+ ";"+latArrival +"& ";
        System.out.println(sURL);

        Process process = Runtime.getRuntime().exec(sURL);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line="";
        String JSONString = "";
        while(( line = reader.readLine()) != null) {
            JSONString+=line;
        }
        process.waitFor();

        JSONObject obj = new JSONObject(JSONString);
        try {
            JSONArray journeys = obj.getJSONArray("journeys");
            JSONObject bestJourney = journeys.getJSONObject(0);
            JSONArray sections = bestJourney.getJSONArray("sections");
            return sections;
        } catch (Exception e){
            System.out.println("journey could not be found");
        }

        return null;
    }

    public String transportToString(JSONObject transportRoute){
        String rtr = transportRoute.get("commercial_mode") + " " + transportRoute.get("code")
                + " direction " + transportRoute.get("direction")
                + " de " + transportRoute.get("from") + " à " + transportRoute.get("to");
        return rtr;
    }

    public String directivesToString (JSONArray directives){
        int i=0;
        String rtr = "";
        while (i<directives.length()){
            JSONObject directive = directives.getJSONObject(i);
            if(directive.has("type") && ((String) directive.get("type")).compareTo("public_transport") == 0){
                rtr+=" ||||| " + transportToString(directive);
            }
            i++;
        }
        return rtr;
    }




        public JSONArray parseSections(JSONArray sections){
        int i=0;
        JSONArray route = new JSONArray();
        while(i<sections.length()){
            JSONObject directive = sections.getJSONObject(i);
            String type = (String) directive.get("type");
            if (directive.has("type") && type.compareTo("public_transport") == 0){
                JSONObject from = directive.getJSONObject("from");
                JSONObject display_infos = directive.getJSONObject("display_informations");
                JSONObject to = directive.getJSONObject("to");
                JSONObject rtr = new JSONObject();
                rtr.put("from",from.get("name"));
                rtr.put("commercial_mode",display_infos.get("commercial_mode"));
                rtr.put("to",to.get("name"));
                rtr.put("network",display_infos.get("network"));
                rtr.put("code",display_infos.get("code"));
                rtr.put("direction",display_infos.get("direction"));
                rtr.put("equipments",display_infos.get("equipments"));
                rtr.put("type",directive.get("type"));
                route.put(rtr);
            }
            i++;
        }
        return route;
    }



}


