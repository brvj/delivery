package ftn.sf012018.delivery.util;

public class DiscountCalculator {
    public static double calculate (double originalPrice, double discount){
        double val = 100 - discount;

        return (val * originalPrice) / 100;
    }
}
