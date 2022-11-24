package com.miage.handyhelp.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class ItineraryServiceTest {


    ItineraryService it = new ItineraryService();
    @Test
    void sansAccentTest() {

        //Liste de caractères accentués en majuscule
        assertThat(it.sansAccent("Â, Ê, Î, Ô, Û, Ä, Ë, Ï, Ö, Ü, À, Ç, É, È, Ù"),equalTo("A, E, I, O, U, A, E, I, O, U, A, C, E, E, U"));

        //Liste de caractères accentués en minuscule
        assertThat(it.sansAccent("é, è, à, î, û, ê, ô, ï, ë, ö, ä"),equalTo("e, e, a, i, u, e, o, i, e, o, a"));

        //Mots avec un ou plusieurs caractères accentués
        assertThat(it.sansAccent("épée"),equalTo("epee"));
        assertThat(it.sansAccent("Ouweïs"),equalTo("Ouweis"));

        //Mot avec un caractère accentué en majuscule
        assertThat(it.sansAccent("Özil"),equalTo("Ozil"));

        //Phrase contenant plusieurs caractères accentués
        assertThat(it.sansAccent("Épopée du garçon, de l'élève et du maître de Noël"),equalTo("Epopee du garcon, de l'eleve et du maitre de Noel"));

    }


    @Test
    void getLongLatTest() throws IOException {
        // Test sur différentes adresses
        assertThat(it.getLongLat("5 Avenue Anatole France, 75007 Paris"), equalTo(Map.of((2.294597),  48.858819)));
        assertThat(it.getLongLat("200 Av. de la République, 92000 Nanterre"), equalTo(Map.of((2.209615),  48.903003)));
        assertThat(it.getLongLat(" Chemin des Bœufs, 94000 Créteil"), equalTo(Map.of((2.433898),  48.770105)));
    }


    @Test
    void transportToStringTest() {

        //Test sur un JSON crée
        JSONObject test1 = new JSONObject();
        test1.put("commercial_mode","RER");
        test1.put("code","D");
        test1.put("direction","Gare de Lyon");
        test1.put("from","Créteil");
        test1.put("to","Paris");

        assertThat(it.transportToString(test1),equalTo("RER"+" "+"D"+ " direction " +  "Gare de Lyon"+ " de " +"Créteil" + " à " + "Paris"));

        //Test sur un 2e JSON crée
        JSONObject test2 = new JSONObject();
        test2.put("commercial_mode","Bus");
        test2.put("code","393");
        test2.put("direction","Pompadour");
        test2.put("from","Thiais");
        test2.put("to","Créteil");

        assertThat(it.transportToString(test2),equalTo("Bus"+" "+"393"+ " direction " +  "Pompadour"+ " de " +"Thiais" + " à " + "Créteil"));



    }

    /*@Test
    void directivesToStringTest() {
        JSONArray test = new JSONArray();

        JSONObject test2 = new JSONObject();
        test2.put("commercial_mode","Bus");
        test2.put("code","393");
        test2.put("direction","Pompadour");
        test2.put("from","Thiais");
        test2.put("to","Créteil");

        test.put(0,test2);



        assertThat(it.directivesToString(test),equalTo("oui"));

    }*/
}