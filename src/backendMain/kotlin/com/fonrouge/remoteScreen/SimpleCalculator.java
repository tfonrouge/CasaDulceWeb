package com.fonrouge.remoteScreen;

import java.util.Scanner;
import java.lang.Math;

public class SimpleCalculator {
    public static void main(String[] args) {

        float shipPercentage;//shipping percentage
        double invoiceTotal; //invoice total
        double shippingTotal; //shipping cost
        float product; //product cost
        float newProduct;//new product price with shipping included
        String choice; // users choice to continue runnig the do/while loop.

            // create an object of Scanner class
            Scanner input = new Scanner(System.in);
            Scanner yesNo = new Scanner(System.in);// scans whether user wants to calculate prices.

            // ask user to enter Invoice Total
            System.out.println("What is your invoice total?");
            invoiceTotal = input.nextDouble();

            // ask user to enter shipping cost
            System.out.println("Please enter your shipping cost");
            shippingTotal = input.nextDouble();

            // displays the shpping percentage
            shipPercentage = (float) (((invoiceTotal + shippingTotal) - invoiceTotal) / (invoiceTotal));
            System.out.println("Your shipping percentage is " + (shipPercentage * 100) + "%");// / 100.0 +"%");


        do {
            // ask what the product cost
            System.out.println("\nHow much is the item?");
            product = input.nextFloat();

            // displays new product price with shipping and assigns it to newProduct
            newProduct = product * (1 + shipPercentage);
            System.out.println("With shipping included, your new Price is $" + Math.round(newProduct * 100.0) / 100.0);


            System.out.println("\nWould you like to calculate your prices? Please answer with \"y\" or \"n\". ");
            String yesno = yesNo.next();
            // if customer answers "y" if statement is executed.
            if (yesno.equals("y")) {
                System.out.println("Great, how many displays come in the case?");
                int display = Integer.parseInt(input.next());// creates variable display and assigns it the quantity of displays in the case of candy.

                System.out.println("What percentage do you want to mark up retail price per piece?");
                float markupRetail = Float.parseFloat(input.next());// assigns markup percentage to variable markup

                System.out.println("What percentage do you want to mark up wholesale price per piece?");
                float markupWholesale = Float.parseFloat(input.next()); // assigns the markup for wholesale per piece.

                System.out.println("What percentage do you want to mark up wholesale price per case?");
                float markupCase = Float.parseFloat(input.next()); // assigns markup by the case to markupCase variable.

                float avgUnitPrice = newProduct / display; // this formula gives you the average unit price
                float retailUnitPrice = avgUnitPrice * (1 + (markupRetail / 100));// this formula gives you the retail price per piece
                float wholesaleUnitPrice = avgUnitPrice * (1 + (markupCase / 100)); // this formula gives the wholesale price per piece
                float wholesaleCasePrice = newProduct * (1 + (markupCase / 100)); // this formula gives you the wholesale case price.

                System.out.print("Your average unit price is $");
                System.out.printf("%.2f%n", avgUnitPrice); // prints average unit price
                System.out.print("Your retail price per piece is $");
                System.out.printf("%.2f%n", retailUnitPrice);// prints retail unit price
                System.out.print("Your wholesale unit price is $");
                System.out.printf("%.2f%n", wholesaleUnitPrice); // prints wholesale unit price
                System.out.print("Your wholesale case price is $");
                System.out.printf("%.2f%n", wholesaleCasePrice); // prints wholesale unit price

            }// end of if statement.

            System.out.println("Do you wish to calculate another price, y/n?");
            choice = input.next();

        }// end of the do statement.
        while (choice.equals("y")); // end of the while loop.


            System.out.println("Goodbye for now.");
            input.close();
        }
}
