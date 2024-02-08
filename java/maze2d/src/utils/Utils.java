package utils;

public class Utils {
static public double currentTimeSecs(double bias)
{
        return (double)System.currentTimeMillis() / 1000. - bias;
}
static public double currentTimeSecs()
{
        return currentTimeSecs(0.);
}
}
