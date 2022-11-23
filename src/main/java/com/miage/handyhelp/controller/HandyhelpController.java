package com.miage.handyhelp.controller;

import com.miage.handyhelp.model.ItineraryModel;
import com.miage.handyhelp.service.ItineraryService;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class HandyhelpController {

    @GetMapping(value = "/testapi")
    @ResponseBody
    public String testapi() throws IOException, InterruptedException {
        ItineraryService i = new ItineraryService();
        Map<Double, Double> longitudeLatitude = i.getLongLat("9 rue abel paris");
        Map<Double, Double> longitudeLatitude2 = i.getLongLat("69 quai branly paris");
        JSONArray j = i.curlItinerary(longitudeLatitude,longitudeLatitude2);
        JSONArray arr = i.parseSections(j);
        return j.toString();
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
        JSONArray sections = itineraryService.curlItinerary(longitudeLatitude,longitudeLatitude2);
        JSONArray directives = itineraryService.parseSections(sections);
        ItineraryModel.setRoute(itineraryService.directivesToString(directives));
        model.addAttribute("initeraryModel", ItineraryModel);
        return "pages/itineraryResult";
    }

}
