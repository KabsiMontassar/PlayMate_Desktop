module test {

    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires java.sql;
    requires sinch.sdk.java;
    requires mailjet.client;

    //requires javax.mail.api;

    requires org.apache.commons.codec;
    requires javafx.web;

    requires com.gluonhq.attach.util;

    requires java.desktop;

    requires org.json;
    requires jdk.jsobject;
    requires java.net.http;
    requires com.google.auth.oauth2;

    requires google.api.services.gmail.v1.rev110;
    requires twilio;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.media;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client;
   // requires mail;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client.json.gson;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires fastjson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
 //   requires mail;
    // requires java.http.client;
    opens test to javafx.fxml;

    exports services.GestionUser;

    opens services.GestionUser to javafx.fxml;
    exports test.Controllers.UserController;

    opens test.Controllers.UserController to javafx.fxml;
    opens test.Controllers.EquipeController to javafx.fxml;
    opens test.Controllers.TerrainController to javafx.fxml;
    opens test.Controllers.TournoiController to javafx.fxml;
    opens test.Controllers.ReservationController to javafx.fxml;
    opens test.Controllers.ProduitController to javafx.fxml;


    requires mail;
    requires com.google.gson;
    requires okhttp3;
    requires org.apache.pdfbox;
    requires jbcrypt;

    exports test;
    exports models;
}
