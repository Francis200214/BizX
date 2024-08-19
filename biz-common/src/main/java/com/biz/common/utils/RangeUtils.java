package com.biz.common.utils;

/**
 * 该类提供了一些关于经纬度范围的计算工具方法。
 * 主要用于根据给定的经纬度和距离，计算出扩展后的经纬度范围。
 *
 * @author francis
 * @since 1.0.1
 */
public final class RangeUtils {

    /**
     * WGS84椭球体的长半轴。
     */
    private static final double EARTH_MAJOR_AXIS = 6378.137;

    /**
     * WGS84椭球体的短半轴。
     */
    private static final double EARTH_MINOR_AXIS = 6356752.3142;

    /**
     * WGS84椭球体的扁率。
     */
    private static final double EARTH_FLATTENING = 1 / 298.2572236;

    /**
     * 根据给定的经纬度和距离，计算左下角的经纬度点。
     * 该方法主要用于地图的矩形区域的左下角计算。
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 左下角的经纬度数组，顺序为[经度，纬度]
     */
    public static double[] leftDownPoint(float longitude, float latitude) {
        if (!isValidLngLat((double) longitude, (double) latitude)) {
            throw new IllegalArgumentException("Invalid longitude or latitude values.");
        }
        return rangeOutsidePointByLongitudeAndLatitude(longitude, latitude, 225, 1000);
    }


    /**
     * 根据给定的经纬度和距离，计算右上角的经纬度点。
     * 该方法主要用于地图的矩形区域的右上角计算。
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 右上角的经纬度数组，顺序为[经度，纬度]
     */
    public static double[] rightUpPoint(float longitude, float latitude) {
        if (!isValidLngLat((double) longitude, (double) latitude)) {
            throw new IllegalArgumentException("Invalid longitude or latitude values.");
        }
        return rangeOutsidePointByLongitudeAndLatitude(longitude, latitude, 45, 1000);
    }


    /**
     * 根据经度、纬度、角度和距离，计算目标点的经纬度。
     * 该方法主要用于计算在给定角度和距离下，从起点经纬度出发的目标点的经纬度。
     *
     * @param longitude 起点经度
     * @param latitude  起点纬度
     * @param degree    方向角度，正北为0度，顺时针增加
     * @param dist      距离，单位为米
     * @return 目标点的经纬度数组，顺序为[经度，纬度]
     */
    public static double[] rangeOutsidePointByLongitudeAndLatitude(double longitude, double latitude, double degree, double dist) {
        return rangeOutsidePoint(longitude, latitude, rad(degree), dist);
    }


    /**
     * 根据弧度、经纬度和距离，计算目标点的经纬度。
     * 这是rangeOutsidePointByLongitudeAndLatitude的内部实现方法，用于实际的计算过程。
     *
     * @param longitude 起点经度
     * @param latitude  起点纬度
     * @param radian    方向弧度
     * @param dist      距离，单位为米
     * @return 目标点的经纬度数组，顺序为[经度，纬度]
     */
    private static double[] rangeOutsidePoint(double longitude, double latitude, double radian, double dist) {
        double sinAlpha1 = Math.sin(radian);
        double cosAlpha1 = Math.cos(radian);
        double tanU1 = (1 - EARTH_FLATTENING) * Math.tan(rad(latitude));
        double cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1));
        double sinU1 = tanU1 * cosU1;
        double sigma1 = Math.atan2(tanU1, cosAlpha1);
        double sinAlpha = cosU1 * sinAlpha1;
        double cosSqAlpha = 1 - sinAlpha * sinAlpha;
        double uSq = cosSqAlpha * (EARTH_MAJOR_AXIS * EARTH_MAJOR_AXIS - EARTH_MINOR_AXIS * EARTH_MINOR_AXIS) / (EARTH_MINOR_AXIS * EARTH_MINOR_AXIS);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double cos2SigmaM = 0;
        double sinSigma = 0;
        double cosSigma = 0;
        double sigma = dist / (EARTH_MINOR_AXIS * A), sigmaP = 2 * Math.PI;
        while (Math.abs(sigma - sigmaP) > 1e-12) {
            cos2SigmaM = Math.cos(2 * sigma1 + sigma);
            sinSigma = Math.sin(sigma);
            cosSigma = Math.cos(sigma);
            double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
                    - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
            sigmaP = sigma;
            sigma = dist / (EARTH_MINOR_AXIS * A) + deltaSigma;
        }

        double tmp = sinU1 * sinSigma - cosU1 * cosSigma * cosAlpha1;
        double lat2 = Math.atan2(sinU1 * cosSigma + cosU1 * sinSigma * cosAlpha1,
                (1 - EARTH_FLATTENING) * Math.sqrt(sinAlpha * sinAlpha + tmp * tmp));
        double lambda = Math.atan2(sinSigma * sinAlpha1, cosU1 * cosSigma - sinU1 * sinSigma * cosAlpha1);
        double C = EARTH_FLATTENING / 16 * cosSqAlpha * (4 + EARTH_FLATTENING * (4 - 3 * cosSqAlpha));
        double L = lambda - (1 - C) * EARTH_FLATTENING * sinAlpha
                * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        return new double[]{longitude + deg(L), lat2};
    }


    /**
     * 将角度转换为弧度。
     * <p>
     * 弧度和角度之间的转换是数学中常见的需求。这个方法提供了一个简单的途径，
     * 将给定的角度值转换为对应的弧度值。在数学计算中，弧度常常比角度更方便使用。
     *
     * @param d 输入的角度值，单位为度。
     * @return 转换后的弧度值。
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 将弧度转换为度数。
     * <p>
     * 由于地球经纬度的度量单位是度，而某些计算可能使用弧度制，这个函数提供了弧度到度的转换。
     * 它是数学中常见的一种单位转换，尤其在几何和三角学中。
     *
     * @param x 弧度值。
     * @return 对应的度数。
     */
    private static double deg(double x) {
        return x * 180 / Math.PI;
    }

    /**
     * 判断给定的经纬度是否有效。
     * <p>
     * 在地理信息系统（GIS）中，有效的经纬度必须在特定的范围内。
     * 这个函数用于验证经度是否在1到180之间，纬度是否在1到90之间。
     * 这是因为经度的范围是180度，从西经180度到东经180度，而纬度的范围是90度，从南纬90度到北纬90度。
     *
     * @param longitude 经度值。
     * @param latitude  纬度值。
     * @return 如果经纬度有效，则返回true；否则返回false。
     */
    public static boolean isValidLngLat(Double longitude, Double latitude) {
        return longitude != null && latitude != null && longitude < 180 && latitude < 90 && longitude > 1 && latitude > 1;
    }


}
