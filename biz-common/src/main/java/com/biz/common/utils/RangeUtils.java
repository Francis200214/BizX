package com.biz.common.utils;

/**
 * 该类提供了一些关于经纬度范围的计算工具方法。
 * 主要用于根据给定的经纬度和距离，计算出扩展后的经纬度范围。
 * 这些方法可以应用于地理信息系统（GIS）中，用于确定地图区域、计算目标点等。
 *
 * <p>类中的方法包括计算地图矩形区域的左下角和右上角经纬度，基于起点经纬度和指定距离计算目标点的经纬度等。</p>
 *
 * <pre>{@code
 * // 示例用法
 * double[] bottomLeft = RangeUtils.calculateBottomLeftCorner(120.0f, 30.0f);
 * double[] topRight = RangeUtils.calculateTopRightCorner(120.0f, 30.0f);
 * double[] targetPoint = RangeUtils.calculateDestinationPoint(120.0, 30.0, 45, 1000);
 * }</pre>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
public final class RangeUtils {

    /**
     * WGS84椭球体的赤道半径，单位为公里。
     */
    private static final double EARTH_EQUATORIAL_RADIUS = 6378.137;

    /**
     * WGS84椭球体的极半径，单位为米。
     */
    private static final double EARTH_POLAR_RADIUS = 6356752.3142;

    /**
     * WGS84椭球体的扁率。
     */
    private static final double EARTH_FLATTENING = 1 / 298.2572236;

    /**
     * 根据给定的经纬度和距离，计算左下角的经纬度点。
     * 该方法主要用于地图的矩形区域的左下角计算。
     *
     * @param longitude 经度，范围为1到180之间。
     * @param latitude  纬度，范围为1到90之间。
     * @return 左下角的经纬度数组，顺序为[经度，纬度]。
     * @throws IllegalArgumentException 如果经度或纬度无效。
     */
    public static double[] calculateBottomLeftCorner(float longitude, float latitude) {
        if (!isValidCoordinate((double) longitude, (double) latitude)) {
            throw new IllegalArgumentException("Invalid longitude or latitude values.");
        }
        return calculateDestinationPoint(longitude, latitude, 225, 1000);
    }

    /**
     * 根据给定的经纬度和距离，计算右上角的经纬度点。
     * 该方法主要用于地图的矩形区域的右上角计算。
     *
     * @param longitude 经度，范围为1到180之间。
     * @param latitude  纬度，范围为1到90之间。
     * @return 右上角的经纬度数组，顺序为[经度，纬度]。
     * @throws IllegalArgumentException 如果经度或纬度无效。
     */
    public static double[] calculateTopRightCorner(float longitude, float latitude) {
        if (!isValidCoordinate((double) longitude, (double) latitude)) {
            throw new IllegalArgumentException("Invalid longitude or latitude values.");
        }
        return calculateDestinationPoint(longitude, latitude, 45, 1000);
    }

    /**
     * 根据经度、纬度、角度和距离，计算目标点的经纬度。
     * 该方法主要用于计算在给定角度和距离下，从起点经纬度出发的目标点的经纬度。
     *
     * @param longitude 起点经度，范围为1到180之间。
     * @param latitude  起点纬度，范围为1到90之间。
     * @param degree    方向角度，正北为0度，顺时针增加。
     * @param dist      距离，单位为米。
     * @return 目标点的经纬度数组，顺序为[经度，纬度]。
     */
    public static double[] calculateDestinationPoint(double longitude, double latitude, double degree, double dist) {
        return calculateDestinationPointByRadian(longitude, latitude, rad(degree), dist);
    }

    /**
     * 根据弧度、经纬度和距离，计算目标点的经纬度。
     * 这是`calculateDestinationPoint`的内部实现方法，用于实际的计算过程。
     *
     * @param longitude 起点经度。
     * @param latitude  起点纬度。
     * @param radian    方向弧度。
     * @param dist      距离，单位为米。
     * @return 目标点的经纬度数组，顺序为[经度，纬度]。
     */
    private static double[] calculateDestinationPointByRadian(double longitude, double latitude, double radian, double dist) {
        // 进行数学计算，得到目标点的经纬度
        double sinAlpha1 = Math.sin(radian);
        double cosAlpha1 = Math.cos(radian);
        double tanU1 = (1 - EARTH_FLATTENING) * Math.tan(rad(latitude));
        double cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1));
        double sinU1 = tanU1 * cosU1;
        double sigma1 = Math.atan2(tanU1, cosAlpha1);
        double sinAlpha = cosU1 * sinAlpha1;
        double cosSqAlpha = 1 - sinAlpha * sinAlpha;
        double uSq = cosSqAlpha * (EARTH_EQUATORIAL_RADIUS * EARTH_EQUATORIAL_RADIUS - EARTH_POLAR_RADIUS * EARTH_POLAR_RADIUS) / (EARTH_POLAR_RADIUS * EARTH_POLAR_RADIUS);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double cos2SigmaM = 0;
        double sinSigma = 0;
        double cosSigma = 0;
        double sigma = dist / (EARTH_POLAR_RADIUS * A), sigmaP = 2 * Math.PI;
        while (Math.abs(sigma - sigmaP) > 1e-12) {
            cos2SigmaM = Math.cos(2 * sigma1 + sigma);
            sinSigma = Math.sin(sigma);
            cosSigma = Math.cos(sigma);
            double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
                    - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
            sigmaP = sigma;
            sigma = dist / (EARTH_POLAR_RADIUS * A) + deltaSigma;
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
     *
     * @param degree 输入的角度值，单位为度。
     * @return 转换后的弧度值。
     */
    private static double rad(double degree) {
        return degree * Math.PI / 180.0;
    }

    /**
     * 将弧度转换为度数。
     *
     * @param radian 弧度值。
     * @return 对应的度数。
     */
    private static double deg(double radian) {
        return radian * 180 / Math.PI;
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
    public static boolean isValidCoordinate(Double longitude, Double latitude) {
        return longitude != null && latitude != null && longitude < 180 && latitude < 90 && longitude > 1 && latitude > 1;
    }
}
