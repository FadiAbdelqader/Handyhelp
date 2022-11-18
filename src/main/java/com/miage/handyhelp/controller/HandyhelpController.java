package com.miage.handyhelp.controller;

import com.miage.handyhelp.repository.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class HandyhelpController {

    @GetMapping(value = "/getItineraire")
    public String intineraire () {
        return "pages/itineraire";
    }

}
