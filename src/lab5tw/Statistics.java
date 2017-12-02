package lab5tw;

import java.math.BigInteger;

public class Statistics
{
    private final long[] data;
    private final int size;

    public Statistics(long[] data)
    {
        this.data = data;
        size = data.length;
    }

    double getMean()
    {
        BigInteger sum = BigInteger.valueOf(0);
        for(long a : data)
            sum = sum.add(BigInteger.valueOf(a));

        return sum.divide(BigInteger.valueOf(size)).doubleValue();
    }

    double getVariance()
    {
        double mean = getMean();
        double temp = 0;
        for(double a : data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

    double getStdDev()
    {
        return Math.sqrt(getVariance());
    }

}
