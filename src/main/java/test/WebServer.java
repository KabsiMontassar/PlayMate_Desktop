package test;

import com.google.api.services.gmail.model.Profile;
import fi.iki.elonen.NanoHTTPD;
import test.Controllers.ProfileController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

public class WebServer extends NanoHTTPD {
    private String selectedAddress;
    public WebServer() throws IOException {
        super(8080); // Serve on port 8080
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server running on port 8080");
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();

        System.out.println(method + " '" + uri + "' ");


            if (Method.POST.equals(method) && uri.startsWith("/tunisia_map.html/")) {

                String lastString = uri.substring(uri.lastIndexOf("/") + 1);





                // Respond with a success message
                return newFixedLengthResponse(Response.Status.OK, "text/plain", "Address received: " + lastString);
            }
         else if (Method.GET.equals(method) && "/tunisia_map.html".equals(uri)) {
            try {
                Path htmlPath = Paths.get("D://Github//pidev_spartans_3a29//Pidev//src//main//resources//test//tunisia_map.html");
                byte[] htmlBytes = Files.readAllBytes(htmlPath);
                return newFixedLengthResponse(Response.Status.OK, "text/html", new String(htmlBytes));
            } catch (IOException e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Internal Server Error");
            }
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
    }

    public static void main(String[] args) {
        try {
            new WebServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
