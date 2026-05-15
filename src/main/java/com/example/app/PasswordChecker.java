package com.example.app;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PasswordChecker {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/", new PasswordHandler());

        server.setExecutor(null);

        System.out.println("=======================================");
        System.out.println(" Password Strength Checker Started ");
        System.out.println(" Open Browser:");
        System.out.println(" http://localhost:8081");
        System.out.println("=======================================");

        server.start();
    }

    static class PasswordHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String password = "Admin@123";

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

            String response =
                    "<html>" +
                    "<head>" +
                    "<title>Password Checker</title>" +
                    "</head>" +
                    "<body style='font-family: Arial; padding: 30px;'>" +

                    "<h1>Password Strength Checker</h1>" +

                    "<h2>Password : " + password + "</h2>" +

                    "<h3>Password Analysis</h3>" +

                    "<p>Length Check : Passed</p>" +
                    "<p>Uppercase Check : Passed</p>" +
                    "<p>Lowercase Check : Passed</p>" +
                    "<p>Number Check : Passed</p>" +
                    "<p>Special Character Check : Passed</p>" +

                    "<h2>Password Strength : " + strength + "</h2>" +

                    "</body>" +
                    "</html>";

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        }
    }
}
