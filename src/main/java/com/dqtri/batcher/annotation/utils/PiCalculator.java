package com.dqtri.batcher.annotation.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PiCalculator {
    private static int MARGIN = 5;
    public static final BigDecimal ONE = BigDecimal.ONE;
    public static final BigDecimal TWO = BigDecimal.valueOf(2);
    public static final BigDecimal FOUR = BigDecimal.valueOf(4);
    public static final BigDecimal FIVE = BigDecimal.valueOf(5);
    public static final BigDecimal SIX = BigDecimal.valueOf(6);
    public static final BigDecimal N8 = BigDecimal.valueOf(8);
    public static final BigDecimal N12 = BigDecimal.valueOf(12);
    public static final BigDecimal N32 = BigDecimal.valueOf(32);
    public static final BigDecimal N49 = BigDecimal.valueOf(49);
    public static final BigDecimal N57 = BigDecimal.valueOf(57);
    public static final BigDecimal N239 = BigDecimal.valueOf(239);
    public static final BigDecimal N110443 = BigDecimal.valueOf(110443);

    /**
    * pi = 4 * (4atan(1/5)-atan(1/239))
    * */
    public static BigDecimal calculatePi5(int index) {
        int scale = MARGIN + index;
        BigDecimal atan5 = atan(ONE.divide(FIVE, scale, RoundingMode.HALF_UP), scale);
        BigDecimal atan239 = atan(ONE.divide(N239, scale, RoundingMode.HALF_UP), scale);
        BigDecimal pi = FOUR.multiply(FOUR.multiply(atan5).subtract(atan239));
        return pi.setScale(index, RoundingMode.DOWN);
    }

    /**
     * pi = 4 * (6atan(1/8)+2atan(1/57)+atan(1/239))
     * */
    public static BigDecimal calculatePi8(int index) {
        int scale = MARGIN + index;
        BigDecimal atan8 = atan(ONE.divide(N8, scale, RoundingMode.HALF_UP), scale);
        BigDecimal atan57 = atan(ONE.divide(N57, scale, RoundingMode.HALF_UP), scale);
        BigDecimal atan239 = atan(ONE.divide(N239, scale, RoundingMode.HALF_UP), scale);
        BigDecimal pi = FOUR.multiply(SIX.multiply(atan8).add(TWO.multiply(atan57)).add(atan239));
        return pi.setScale(index, RoundingMode.DOWN);
    }

    /**
     * pi = 4 * (12atan(1/49)+32atan(1/57)-5atan(1/239)+12atan(1/110443))
     * */
    public static BigDecimal calculatePi49(int index) {
        int scale = MARGIN + index;
        BigDecimal atan8 = atan(ONE.divide(N49, scale, RoundingMode.HALF_UP), scale);
        BigDecimal atan57 = atan(ONE.divide(N57, scale, RoundingMode.HALF_UP), scale);
        BigDecimal atan239 = atan(ONE.divide(N239, scale, RoundingMode.HALF_UP), scale);
        BigDecimal atan110443 = atan(ONE.divide(N110443, scale, RoundingMode.HALF_UP), scale);
        BigDecimal pi = FOUR.multiply(N12.multiply(atan8)
                .add(N32.multiply(atan57))
                .subtract(FIVE.multiply(atan239))
                .add(N12.multiply(atan110443)));
        return pi.setScale(index, RoundingMode.DOWN);
    }

    /**
     * Function to calculate arctan using Taylor series with specified iterations
     * @param x value
     * @param scale of the BigDecimal quotient to be returned.
     * @return actan of number
     */
    public static BigDecimal atan(BigDecimal x, int scale) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term = x;
        BigDecimal xSquared = x.multiply(x);
        BigDecimal numerator = x;
        BigDecimal denominator = BigDecimal.ONE;
        int k = 0;
        while (term.compareTo(BigDecimal.ZERO) != 0) {
            sum = sum.add(term);
            k++;
            numerator = numerator.multiply(xSquared).negate();
            denominator = BigDecimal.valueOf(2L * k + 1);
            term = numerator.divide(denominator, scale, RoundingMode.HALF_UP);
        }
        return sum;
    }
}