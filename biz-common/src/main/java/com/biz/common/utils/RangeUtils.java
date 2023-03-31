package com.biz.common.utils;

/**
 * 经纬度范围工具类
 *
 * 主要封装了 根据经纬度往外扩多少米的左下角经纬度点和右上角经纬度点
 *
 * @author francis
 * @create 2023/3/31 17:36
 */
public final class RangeUtils {



    /**
     * 长半径 a = 6378137
     */
    private static final double a = 6378.137;

    /**
     * 短半径 b = 6356752.3142
     */
    private static final double b = 6356752.3142;

    /**
     * 扁率 f = 1 / 298.2572236
     */
    private static final double f = 1 / 298.2572236;



    /**
     * 根据 度、某个经纬度外 X米外的经纬度点
     *
     * @param longitude
     * @param latitude
     * @param degree
     * @param dist
     * @return
     */
    public static double[] rangeOutsidePointByLongitudeAndLatitude(double longitude, double latitude, double degree, double dist) {
        return rangeOutsidePoint(longitude, latitude, rad(degree), dist);
    }


    /**
     * 根据 弧度、某个经纬度点外 X米外的经纬度点
     *
     * @param longitude
     * @param latitude
     * @param radian
     * @param dist
     * @return
     */
    private static double[] rangeOutsidePoint(double longitude, double latitude, double radian, double dist) {
        double sinAlpha1 = Math.sin(radian);
        double cosAlpha1 = Math.cos(radian);
        double tanU1 = (1 - f) * Math.tan(rad(latitude));
        double cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1));
        double sinU1 = tanU1 * cosU1;
        double sigma1 = Math.atan2(tanU1, cosAlpha1);
        double sinAlpha = cosU1 * sinAlpha1;
        double cosSqAlpha = 1 - sinAlpha * sinAlpha;
        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double cos2SigmaM = 0;
        double sinSigma = 0;
        double cosSigma = 0;
        double sigma = dist / (b * A), sigmaP = 2 * Math.PI;
        while (Math.abs(sigma - sigmaP) > 1e-12) {
            cos2SigmaM = Math.cos(2 * sigma1 + sigma);
            sinSigma = Math.sin(sigma);
            cosSigma = Math.cos(sigma);
            double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
                    - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
            sigmaP = sigma;
            sigma = dist / (b * A) + deltaSigma;
        }

        double tmp = sinU1 * sinSigma - cosU1 * cosSigma * cosAlpha1;
        double lat2 = Math.atan2(sinU1 * cosSigma + cosU1 * sinSigma * cosAlpha1,
                (1 - f) * Math.sqrt(sinAlpha * sinAlpha + tmp * tmp));
        double lambda = Math.atan2(sinSigma * sinAlpha1, cosU1 * cosSigma - sinU1 * sinSigma * cosAlpha1);
        double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
        double L = lambda - (1 - C) * f * sinAlpha
                * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));

        double[] area = new double[2];
        area[0] = longitude + deg(L);
        area[1] = deg(lat2);
        return area;
    }


    /**
     * 度换成弧度
     *
     * @param d 度
     * @return 弧度
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 弧度换成度
     *
     * @param x 弧度
     * @return 度
     */
    private static double deg(double x) {
        return x * 180 / Math.PI;
    }

    /**
     * 判断是否是一个正确的经纬度点
     *
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static boolean isValidLngLat(Double longitude, Double latitude){
        return longitude != null && latitude != null && longitude < 180 && latitude < 90 && longitude > 1 && latitude > 1;
    }


}
