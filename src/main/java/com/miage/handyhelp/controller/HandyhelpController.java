package com.miage.handyhelp.controller;

import com.miage.handyhelp.repository.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class HandyhelpController {

    @GetMapping(value = "/OrderG7")
    public String G7Form () {
        return "pages/taxi2";
    }

    @GetMapping(value = "/")
    @ResponseBody
    public String testbd() throws SQLException {
      String test="";
      DBConnection bd = new DBConnection();
      bd.executeStatement("INSERT INTO test(msg) VALUES ('hello')");
      return test;
    }
}
