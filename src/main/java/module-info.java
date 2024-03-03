module test {

    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires java.sql;
    requires sinch.sdk.java;
    requires mailjet.client;
   // requires com.mailjet.api;
    requires javax.mail.api;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.services.gmail.v1.rev110;
    requires google.api.client;
    requires org.apache.commons.codec;
    requires javafx.web;
    requires com.gluonhq.maps;
    requires com.gluonhq.attach.util;

    requires java.desktop;
    requires nanohttpd;
    opens test to javafx.fxml;
    exports test;
    exports services.GestionUser;
    exports models;
    opens services.GestionUser to javafx.fxml;
    exports test.Controllers;
    opens test.Controllers to javafx.fxml;
    exports test.Controllers.Common;
    opens test.Controllers.Common to javafx.fxml;

}
