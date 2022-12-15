package com.miage.handyhelp.controller;

import com.miage.handyhelp.model.ItineraryModel;
import com.miage.handyhelp.service.ItineraryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class HandyhelpController {

        @GetMapping("/")
        public String showIndex(){
            return "index";
        }

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
            Pair<String,Boolean> directivesToString = itineraryService.directivesToString(directives);
            switch (i){
                case 0:
                    ItineraryModel.setRoute_1(directivesToString.getFirst());
                    ItineraryModel.setIs_1_accessible(directivesToString.getSecond());
                    break;
                case 1:
                    ItineraryModel.setRoute_2(directivesToString.getFirst());
                    ItineraryModel.setIs_2_accessible(directivesToString.getSecond());
                    i = journeys.length() +5;
                    break;
            }

        }
        model.addAttribute("initeraryModel", ItineraryModel);
        return "pages/itineraryResult";
    }

}
