package com.miage.handyhelp.controller;

import com.miage.handyhelp.model.ItineraryModel;
import com.miage.handyhelp.service.ItineraryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class HandyhelpController {
/*
    @GetMapping(value = "/testapi")
    @ResponseBody
    public String testapi() throws IOException, InterruptedException {
        ItineraryService i = new ItineraryService();
        Map<Double, Double> longitudeLatitude = i.getLongLat("9 rue abel paris");
        Map<Double, Double> longitudeLatitude2 = i.getLongLat("69 quai branly paris");
        List<JSONArray j = i.curlItinerary(longitudeLatitude,longitudeLatitude2);
        JSONArray arr = i.parseSections(j);
        return j.toString();
    }
*/

    @GetMapping("/itinerary")
    public String greetingForm(Model model) {
        model.addAttribute("initeraryModel", new ItineraryModel());
        return "pages/itinerary";
    }

    @PostMapping("/itinerary")
    public String getItinerary(@ModelAttribute ItineraryModel ItineraryModel, Model model) throws IOException, InterruptedException {

        ItineraryService itineraryService = new ItineraryService();
        Map<Double, Double> longitudeLatitude = itineraryService.getLongLat(ItineraryModel.getDeparture());
        Map<Double, Double> longitudeLatitude2 = itineraryService.getLongLat(ItineraryModel.getArrival());

        JSONArray journeys = itineraryService.curlItinerary(longitudeLatitude,longitudeLatitude2,0);
        for(int i=0;i<journeys.length();i++){
            JSONObject journey = journeys.getJSONObject(i);
            JSONArray sections = journey.getJSONArray("sections");
            JSONArray directives = itineraryService.parseSections(sections);
            switch (i){
                case 0:
                    ItineraryModel.setRoute_1(itineraryService.directivesToString(directives));
                    break;
                case 1:
                    ItineraryModel.setRoute_2(itineraryService.directivesToString(directives));
                    i = journeys.length() +5;
                    break;
        /*case 2:
            ItineraryModel.setRoute_3(itineraryService.directivesToString(directives));
            i = journeys.length() +5;
            break;*/
            }

        }
        /*
        JSONArray sections_1 = itineraryService.curlItinerary(longitudeLatitude,longitudeLatitude2,0);
        JSONArray directives_1 = itineraryService.parseSections(sections_1);
        ItineraryModel.setRoute_1(itineraryService.directivesToString(directives_1));

        JSONArray sections_2 = itineraryService.curlItinerary(longitudeLatitude,longitudeLatitude2,1);
        JSONArray directives_2 = itineraryService.parseSections(sections_2);
        ItineraryModel.setRoute_2(itineraryService.directivesToString(directives_2));

        JSONArray sections_3 = itineraryService.curlItinerary(longitudeLatitude,longitudeLatitude2,2);
        JSONArray directives_3 = itineraryService.parseSections(sections_3);
        ItineraryModel.setRoute_3(itineraryService.directivesToString(directives_3));
*/
        model.addAttribute("initeraryModel", ItineraryModel);
        return "pages/itineraryResult";
    }

}
