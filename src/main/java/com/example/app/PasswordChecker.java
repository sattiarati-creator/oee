package com.example.app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;

public class PasswordChecker {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", 8081), 0);

        server.createContext("/", exchange -> {

            String response = "";

            if ("POST".equals(exchange.getRequestMethod())) {

                byte[] data = exchange.getRequestBody().readAllBytes();
                String formData = new String(data, StandardCharsets.UTF_8);

                String password = formData.split("=")[1];
                password = URLDecoder.decode(password, StandardCharsets.UTF_8);

                boolean hasUpper = false;
                boolean hasLower = false;
                boolean hasDigit = false;
                boolean hasSpecial = false;

                for (char ch : password.toCharArray()) {
                    if (Character.isUpperCase(ch))
                        hasUpper = true;
                    else if (Character.isLowerCase(ch))
                        hasLower = true;
                    else if (Character.isDigit(ch))
                        hasDigit = true;
                    else
                        hasSpecial = true;
                }

                String strength;

                if (password.length() >= 8 &&
                        hasUpper && hasLower &&
                        hasDigit && hasSpecial) {

                    strength = "STRONG";

                } else if (password.length() >= 6 &&
                        (hasUpper || hasDigit)) {

                    strength = "MEDIUM";

                } else {
                    strength = "WEAK";
                }

                response =
                        "<html>" +
                        "<body style='font-family:Arial'>" +
                        "<h1>Password Strength Checker</h1>" +

                        "<p><b>Password:</b> " + password + "</p>" +

                        "<p>Length Check : "
                        + (password.length() >= 8 ? "Passed" : "Failed") + "</p>" +

                        "<p>Uppercase Check : "
                        + (hasUpper ? "Passed" : "Failed") + "</p>" +

                        "<p>Lowercase Check : "
                        + (hasLower ? "Passed" : "Failed") + "</p>" +

                        "<p>Number Check : "
                        + (hasDigit ? "Passed" : "Failed") + "</p>" +

                        "<p>Special Character Check : "
                        + (hasSpecial ? "Passed" : "Failed") + "</p>" +

                        "<h2>Password Strength : " + strength + "</h2>" +

                        "<br><a href='/'>Check Another Password</a>" +
                        "</body></html>";

            } else {

                response =
                        "<html>" +
                        "<body style='font-family:Arial'>" +
                        "<h1>Password Strength Checker</h1>" +

                        "<form method='POST'>" +
                        "<input type='password' name='password' placeholder='Enter Password' required/>" +

                        "<br><br>" +

                        "<button type='submit'>Check Password</button>" +
                        "</form>" +

                        "</body></html>";
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Server started!");
        System.out.println("Open browser:");
        System.out.println("http://localhost:8081");
    }
}
