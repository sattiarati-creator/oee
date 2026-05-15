package com.example.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class PasswordChecker {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/", new PasswordHandler());

        server.setExecutor(null);

        System.out.println("====================================");
        System.out.println(" Password Strength Checker Started ");
        System.out.println(" Open Browser:");
        System.out.println(" http://localhost:8081");
        System.out.println("====================================");

        server.start();
    }

    static class PasswordHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";

            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

                InputStream inputStream = exchange.getRequestBody();

                String formData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                String password = URLDecoder.decode(formData.split("=")[1], "UTF-8");

                boolean hasUppercase = false;
                boolean hasLowercase = false;
                boolean hasNumber = false;
                boolean hasSpecial = false;

                for (char ch : password.toCharArray()) {

                    if (Character.isUpperCase(ch)) {
                        hasUppercase = true;
                    }

                    else if (Character.isLowerCase(ch)) {
                        hasLowercase = true;
                    }

                    else if (Character.isDigit(ch)) {
                        hasNumber = true;
                    }

                    else {
                        hasSpecial = true;
                    }
                }

                int score = 0;

                if (password.length() >= 8) score++;
                if (hasUppercase) score++;
                if (hasLowercase) score++;
                if (hasNumber) score++;
                if (hasSpecial) score++;

                String strength;

                if (score == 5) {
                    strength = "STRONG";
                }

                else if (score >= 3) {
                    strength = "MEDIUM";
                }

                else {
                    strength = "WEAK";
                }

                response =
                        "<html>" +
                        "<head><title>Password Checker</title></head>" +

                        "<body style='font-family: Arial; padding: 30px;'>"

                        + "<h1>Password Strength Checker</h1>"

                        + "<form method='POST'>"

                        + "<input type='password' name='password' "
                        + "placeholder='Enter Password' required "
                        + "style='padding:10px; width:300px;'>"

                        + "<br><br>"

                        + "<button type='submit' "
                        + "style='padding:10px 20px;'>Check Password</button>"

                        + "</form>"

                        + "<hr>"

                        + "<h2>Password Analysis</h2>"

                        + "<p><b>Password:</b> " + password + "</p>"

                        + "<p>Length Check : "
                        + (password.length() >= 8 ? "Passed" : "Failed") + "</p>"

                        + "<p>Uppercase Check : "
                        + (hasUppercase ? "Passed" : "Failed") + "</p>"

                        + "<p>Lowercase Check : "
                        + (hasLowercase ? "Passed" : "Failed") + "</p>"

                        + "<p>Number Check : "
                        + (hasNumber ? "Passed" : "Failed") + "</p>"

                        + "<p>Special Character Check : "
                        + (hasSpecial ? "Passed" : "Failed") + "</p>"

                        + "<h2>Password Strength : " + strength + "</h2>"

                        + "</body></html>";
            }

            else {

                response =
                        "<html>" +
                        "<head><title>Password Checker</title></head>" +

                        "<body style='font-family: Arial; padding: 30px;'>"

                        + "<h1>Password Strength Checker</h1>"

                        + "<form method='POST'>"

                        + "<input type='password' name='password' "
                        + "placeholder='Enter Password' required "
                        + "style='padding:10px; width:300px;'>"

                        + "<br><br>"

                        + "<button type='submit' "
                        + "style='padding:10px 20px;'>Check Password</button>"

                        + "</form>"

                        + "</body></html>";
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        }
    }
}
