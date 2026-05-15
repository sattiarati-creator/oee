package com.example.app;

import java.util.Scanner;

public class PasswordChecker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== PASSWORD STRENGTH CHECKER =====");

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

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

        System.out.println("\n===== PASSWORD ANALYSIS =====");

        if (password.length() >= 8) {
            System.out.println("Length Check       : Passed");
        } else {
            System.out.println("Length Check       : Failed");
        }

        if (hasUppercase) {
            System.out.println("Uppercase Check    : Passed");
        } else {
            System.out.println("Uppercase Check    : Failed");
        }

        if (hasLowercase) {
            System.out.println("Lowercase Check    : Passed");
        } else {
            System.out.println("Lowercase Check    : Failed");
        }

        if (hasNumber) {
            System.out.println("Number Check       : Passed");
        } else {
            System.out.println("Number Check       : Failed");
        }

        if (hasSpecial) {
            System.out.println("Special Char Check : Passed");
        } else {
            System.out.println("Special Char Check : Failed");
        }

        int score = 0;

        if (password.length() >= 8) score++;
        if (hasUppercase) score++;
        if (hasLowercase) score++;
        if (hasNumber) score++;
        if (hasSpecial) score++;

        System.out.println();

        if (score == 5) {
            System.out.println("Password Strength  : STRONG");
        }

        else if (score >= 3) {
            System.out.println("Password Strength  : MEDIUM");
        }

        else {
            System.out.println("Password Strength  : WEAK");
        }

        sc.close();
    }
}
