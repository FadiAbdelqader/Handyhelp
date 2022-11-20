package com.miage.handyhelp.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.handyhelp.repository.DBConnection;
import com.miage.handyhelp.service.ItineraryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class HandyhelpController {

    @GetMapping(value = "/itineraire")
    public String intineraire () {
        return "pages/itineraire";
    }

    @GetMapping(value = "/testapi")
    @ResponseBody
    public String testapi() throws IOException, InterruptedException {
        ItineraryService i = new ItineraryService();
        Map<Double, Double> longitudeLatitude = i.getLongLat("9 rue abel paris");
        Map<Double, Double> longitudeLatitude2 = i.getLongLat("69 quai branly paris");
        JSONArray j = i.curlItinerary(longitudeLatitude,longitudeLatitude2);
        JSONArray arr = i.parseSections(j);
        return i.printDirectives(arr);
        //return arr.toString();
    }

}
