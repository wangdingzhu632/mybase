package me.supercube.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 根据经、纬度得到某地的日升、日落时间
 */
public class SunriseSunset {

    //***********************************************************************/
//* Name:    calcJD									*/
//* Type:    Function									*/
//* Purpose: Julian day from calendar day						*/
//* Arguments:										*/
//*   year : 4 digit year								*/
//*   month: January = 1								*/
//*   day  : 1 - 31									*/
//* Return value:										*/
//*   The Julian day corresponding to the date					*/
//* Note:											*/
//*   Number is returned for start of day.  Fractional days should be	*/
//*   added later.									*/
//***********************************************************************/
    public static double calcJD(int year, int month, int day) {
        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double A = Math.floor(year / 100);
        double B = 2 - A + Math.floor(A / 4);

        double JD = Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;
        return JD;
    }

    // Convert radian angle to degrees
    private static double radToDeg(double angleRad) {
        return (180.0 * angleRad / Math.PI);
    }

//*********************************************************************/

// Convert degree angle to radians

    private static double degToRad(double angleDeg) {
        return (Math.PI * angleDeg / 180.0);
    }


//***********************************************************************/
//* Name:    calcTimeJulianCent							*/
//* Type:    Function									*/
//* Purpose: convert Julian Day to centuries since J2000.0.			*/
//* Arguments:										*/
//*   jd : the Julian Day to convert						*/
//* Return value:										*/
//*   the T value corresponding to the Julian Day				*/
//***********************************************************************/

    private static double calcTimeJulianCent(double jd) {
        double T = (jd - 2451545.0) / 36525.0;
        return T;
    }

//***********************************************************************/
//* Name:    calcJDFromJulianCent							*/
//* Type:    Function									*/
//* Purpose: convert centuries since J2000.0 to Julian Day.			*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   the Julian Day corresponding to the t value				*/
//***********************************************************************/

    private static double calcJDFromJulianCent(double t) {
        double JD = t * 36525.0 + 2451545.0;
        return JD;
    }


//***********************************************************************/
//* Name:    calGeomMeanLongSun							*/
//* Type:    Function									*/
//* Purpose: calculate the Geometric Mean Longitude of the Sun		*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   the Geometric Mean Longitude of the Sun in degrees			*/
//***********************************************************************/

    private static double calcGeomMeanLongSun(double t) {
        double L0 = 280.46646 + t * (36000.76983 + 0.0003032 * t);
        while (L0 > 360.0) {
            L0 -= 360.0;
        }
        while (L0 < 0.0) {
            L0 += 360.0;
        }
        return L0;        // in degrees
    }


//***********************************************************************/
//* Name:    calGeomAnomalySun							*/
//* Type:    Function									*/
//* Purpose: calculate the Geometric Mean Anomaly of the Sun		*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   the Geometric Mean Anomaly of the Sun in degrees			*/
//***********************************************************************/

    private static double calcGeomMeanAnomalySun(double t) {
        double M = 357.52911 + t * (35999.05029 - 0.0001537 * t);
        return M;        // in degrees
    }

//***********************************************************************/
//* Name:    calcEccentricityEarthOrbit						*/
//* Type:    Function									*/
//* Purpose: calculate the eccentricity of earth's orbit			*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   the unitless eccentricity							*/
//***********************************************************************/


    private static double calcEccentricityEarthOrbit(double t) {
        double e = 0.016708634 - t * (0.000042037 + 0.0000001267 * t);
        return e;        // unitless
    }

//***********************************************************************/
//* Name:    calcSunEqOfCenter							*/
//* Type:    Function									*/
//* Purpose: calculate the equation of center for the sun			*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   in degrees										*/
//***********************************************************************/


    private static double calcSunEqOfCenter(double t) {
        double m = calcGeomMeanAnomalySun(t);

        double mrad = degToRad(m);
        double sinm = Math.sin(mrad);
        double sin2m = Math.sin(mrad + mrad);
        double sin3m = Math.sin(mrad + mrad + mrad);

        double C = sinm * (1.914602 - t * (0.004817 + 0.000014 * t)) + sin2m * (0.019993 - 0.000101 * t) + sin3m * 0.000289;
        return C;        // in degrees
    }

//***********************************************************************/
//* Name:    calcSunTrueLong								*/
//* Type:    Function									*/
//* Purpose: calculate the true longitude of the sun				*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   sun's true longitude in degrees						*/
//***********************************************************************/


    private static double calcSunTrueLong(double t) {
        double l0 = calcGeomMeanLongSun(t);
        double c = calcSunEqOfCenter(t);

        double O = l0 + c;
        return O;        // in degrees
    }

//***********************************************************************/
//* Name:    calcSunTrueAnomaly							*/
//* Type:    Function									*/
//* Purpose: calculate the true anamoly of the sun				*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   sun's true anamoly in degrees							*/
//***********************************************************************/

    private static double calcSunTrueAnomaly(double t) {
        double m = calcGeomMeanAnomalySun(t);
        double c = calcSunEqOfCenter(t);

        double v = m + c;
        return v;        // in degrees
    }

//***********************************************************************/
//* Name:    calcSunRadVector								*/
//* Type:    Function									*/
//* Purpose: calculate the distance to the sun in AU				*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   sun radius vector in AUs							*/
//***********************************************************************/

    private static double calcSunRadVector(double t) {
        double v = calcSunTrueAnomaly(t);
        double e = calcEccentricityEarthOrbit(t);

        double R = (1.000001018 * (1 - e * e)) / (1 + e * Math.cos(degToRad(v)));
        return R;        // in AUs
    }

//***********************************************************************/
//* Name:    calcSunApparentLong							*/
//* Type:    Function									*/
//* Purpose: calculate the apparent longitude of the sun			*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   sun's apparent longitude in degrees						*/
//***********************************************************************/

    private static double calcSunApparentLong(double t) {
        double o = calcSunTrueLong(t);

        double omega = 125.04 - 1934.136 * t;
        double lambda = o - 0.00569 - 0.00478 * Math.sin(degToRad(omega));
        return lambda;        // in degrees
    }

//***********************************************************************/
//* Name:    calcMeanObliquityOfEcliptic						*/
//* Type:    Function									*/
//* Purpose: calculate the mean obliquity of the ecliptic			*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   mean obliquity in degrees							*/
//***********************************************************************/

    private static double calcMeanObliquityOfEcliptic(double t) {
        double seconds = 21.448 - t * (46.8150 + t * (0.00059 - t * (0.001813)));
        double e0 = 23.0 + (26.0 + (seconds / 60.0)) / 60.0;
        return e0;        // in degrees
    }

//***********************************************************************/
//* Name:    calcObliquityCorrection						*/
//* Type:    Function									*/
//* Purpose: calculate the corrected obliquity of the ecliptic		*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   corrected obliquity in degrees						*/
//***********************************************************************/

    private static double calcObliquityCorrection(double t) {
        double e0 = calcMeanObliquityOfEcliptic(t);

        double omega = 125.04 - 1934.136 * t;
        double e = e0 + 0.00256 * Math.cos(degToRad(omega));
        return e;        // in degrees
    }

//***********************************************************************/
//* Name:    calcSunRtAscension							*/
//* Type:    Function									*/
//* Purpose: calculate the right ascension of the sun				*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   sun's right ascension in degrees						*/
//***********************************************************************/

    private static double calcSunRtAscension(double t) {
        double e = calcObliquityCorrection(t);
        double lambda = calcSunApparentLong(t);

        double tananum = (Math.cos(degToRad(e)) * Math.sin(degToRad(lambda)));
        double tanadenom = (Math.cos(degToRad(lambda)));
        double alpha = radToDeg(Math.atan2(tananum, tanadenom));
        return alpha;        // in degrees
    }

//***********************************************************************/
//* Name:    calcSunDeclination							*/
//* Type:    Function									*/
//* Purpose: calculate the declination of the sun				*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   sun's declination in degrees							*/
//***********************************************************************/

    private static double calcSunDeclination(double t) {
        double e = calcObliquityCorrection(t);
        double lambda = calcSunApparentLong(t);

        double sint = Math.sin(degToRad(e)) * Math.sin(degToRad(lambda));
        double theta = radToDeg(Math.asin(sint));
        return theta;        // in degrees
    }

//***********************************************************************/
//* Name:    calcEquationOfTime							*/
//* Type:    Function									*/
//* Purpose: calculate the difference between true solar time and mean	*/
//*		solar time									*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//* Return value:										*/
//*   equation of time in minutes of time						*/
//***********************************************************************/

    private static double calcEquationOfTime(double t) {
        double epsilon = calcObliquityCorrection(t);
        double l0 = calcGeomMeanLongSun(t);
        double e = calcEccentricityEarthOrbit(t);
        double m = calcGeomMeanAnomalySun(t);

        double y = Math.tan(degToRad(epsilon) / 2.0);
        y *= y;

        double sin2l0 = Math.sin(2.0 * degToRad(l0));
        double sinm = Math.sin(degToRad(m));
        double cos2l0 = Math.cos(2.0 * degToRad(l0));
        double sin4l0 = Math.sin(4.0 * degToRad(l0));
        double sin2m = Math.sin(2.0 * degToRad(m));

        double Etime = y * sin2l0 - 2.0 * e * sinm + 4.0 * e * y * sinm * cos2l0
                - 0.5 * y * y * sin4l0 - 1.25 * e * e * sin2m;

        return radToDeg(Etime) * 4.0;    // in minutes of time
    }

//***********************************************************************/
//* Name:    calcHourAngleSunrise							*/
//* Type:    Function									*/
//* Purpose: calculate the hour angle of the sun at sunrise for the	*/
//*			latitude								*/
//* Arguments:										*/
//*   lat : latitude of observer in degrees					*/
//*	solarDec : declination angle of sun in degrees				*/
//* Return value:										*/
//*   hour angle of sunrise in radians						*/
//***********************************************************************/

    private static double calcHourAngleSunrise(double lat, double solarDec) {
        double latRad = degToRad(lat);
        double sdRad = degToRad(solarDec);

        double HAarg = (Math.cos(degToRad(90.833)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad));

        double HA = (Math.acos(Math.cos(degToRad(90.833)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad)));

        return HA;        // in radians
    }

//***********************************************************************/
//* Name:    calcHourAngleSunset							*/
//* Type:    Function									*/
//* Purpose: calculate the hour angle of the sun at sunset for the	*/
//*			latitude								*/
//* Arguments:										*/
//*   lat : latitude of observer in degrees					*/
//*	solarDec : declination angle of sun in degrees				*/
//* Return value:										*/
//*   hour angle of sunset in radians						*/
//***********************************************************************/

    private static double calcHourAngleSunset(double lat, double solarDec) {
        double latRad = degToRad(lat);
        double sdRad = degToRad(solarDec);

        double HAarg = (Math.cos(degToRad(90.833)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad));

        double HA = (Math.acos(Math.cos(degToRad(90.833)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad)));

        return -HA;        // in radians
    }


    public static double calcSunriseUTC(double JD, double latitude, double longitude) {
        double t = calcTimeJulianCent(JD);

        // *** First pass to approximate sunrise

        double eqTime = calcEquationOfTime(t);
        double solarDec = calcSunDeclination(t);
        double hourAngle = calcHourAngleSunrise(latitude, solarDec);

        double delta = longitude - radToDeg(hourAngle);
        double timeDiff = 4 * delta;    // in minutes of time
        double timeUTC = 720 + timeDiff - eqTime;    // in minutes

        // alert("eqTime = " + eqTime + "\nsolarDec = " + solarDec + "\ntimeUTC = " + timeUTC);

        // *** Second pass includes fractional jday in gamma calc

        double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC / 1440.0);
        eqTime = calcEquationOfTime(newt);
        solarDec = calcSunDeclination(newt);
        hourAngle = calcHourAngleSunrise(latitude, solarDec);
        delta = longitude - radToDeg(hourAngle);
        timeDiff = 4 * delta;
        timeUTC = 720 + timeDiff - eqTime; // in minutes

        // alert("eqTime = " + eqTime + "\nsolarDec = " + solarDec + "\ntimeUTC = " + timeUTC);

        return timeUTC;
    }


//***********************************************************************/
//* Name:    calcSolNoonUTC								*/
//* Type:    Function									*/
//* Purpose: calculate the Universal Coordinated Time (UTC) of solar	*/
//*		noon for the given day at the given location on earth		*/
//* Arguments:										*/
//*   t : number of Julian centuries since J2000.0				*/
//*   longitude : longitude of observer in degrees				*/
//* Return value:										*/
//*   time in minutes from zero Z							*/
//***********************************************************************/

    private static double calcSolNoonUTC(double t, double longitude) {
        double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + 0.5 + longitude / 360.0);

        double eqTime = calcEquationOfTime(t);
        double solarNoonDec = calcSunDeclination(t);
        double solNoonUTC = 720 + (longitude * 4) - eqTime; // min

        return solNoonUTC;
    }

//***********************************************************************/
//* Name:    calcSunsetUTC								*/
//* Type:    Function									*/
//* Purpose: calculate the Universal Coordinated Time (UTC) of sunset	*/
//*			for the given day at the given location on earth	*/
//* Arguments:										*/
//*   JD  : julian day									*/
//*   latitude : latitude of observer in degrees				*/
//*   longitude : longitude of observer in degrees				*/
//* Return value:										*/
//*   time in minutes from zero Z							*/
//***********************************************************************/

    public static double calcSunsetUTC(double JD, double latitude, double longitude) {
        double t = calcTimeJulianCent(JD);

        // First calculates sunrise and approx length of day

        double eqTime = calcEquationOfTime(t);
        double solarDec = calcSunDeclination(t);
        double hourAngle = calcHourAngleSunset(latitude, solarDec);

        double delta = longitude - radToDeg(hourAngle);
        double timeDiff = 4 * delta;
        double timeUTC = 720 + timeDiff - eqTime;

        // first pass used to include fractional day in gamma calc

        double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC / 1440.0);
        eqTime = calcEquationOfTime(newt);
        solarDec = calcSunDeclination(newt);
        hourAngle = calcHourAngleSunset(latitude, solarDec);

        delta = longitude - radToDeg(hourAngle);
        timeDiff = 4 * delta;
        timeUTC = 720 + timeDiff - eqTime; // in minutes

        return timeUTC;
    }

    public static String timeString(double minutes)
    // timeString returns a zero-padded string (HH:MM:SS) given time in minutes
    {
        double floatHour = minutes / 60;
        double hour = Math.floor(floatHour);
        long h = Math.round(hour);

        double floatMinute = 60 * (floatHour - Math.floor(floatHour));
        double minute = Math.floor(floatMinute);
        long m = Math.round(minute);
        // double floatSec = 60 * (floatMinute - Math.floor(floatMinute));
        // double second = Math.floor(floatSec);

        String timeStr = h + ":";
        if (m < 10)    //	i.e. only one digit
            timeStr += "0" + m;
        else
            timeStr += m;

        return timeStr;
    }

    /**
     * 获取某天的某地年日升时间
     *
     * @param newLocalDateTime   日期
     * @param latitude
     * @param longitude
     * @param timezone  相对GMT的时差，如北京 -8
     * String sunRise=SunCalculator.getSunrise(new LocalDateTime(),39.54,-116.28,-8);
     * @return
     */
    public static String getSunrise(Date newLocalDateTime, double latitude, double longitude, int timezone) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newLocalDateTime);
        int year = calendar.get(Calendar.YEAR);    //获取年
        int month = calendar.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        double jd = calcJD(year, month, day);
        double ti = calcSunriseUTC(jd, latitude, longitude);
        double riseTimeLST = ti - (60 * timezone) + 0;
        return timeString(riseTimeLST);
    }

    /**
     * 获取某天的某地年日落时间
     *
     * @param newLocalDateTime   日期
     * @param latitude
     * @param longitude
     * @param timezone  相对GMT的时差，如北京 -8
     *String sunset=SunCalculator.getSunset(new LocalDateTime(),39.9,-115.4,-8);
     * @return
     */
    public static String getSunset(Date newLocalDateTime, double latitude, double longitude, int timezone) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newLocalDateTime);
        int year = calendar.get(Calendar.YEAR);    //获取年
        int month = calendar.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        double jd = calcJD(year, month, day);
        double ti = calcSunsetUTC(jd, latitude, longitude);
        double riseTimeLST = ti - (60 * timezone) + 0;
        return timeString(riseTimeLST);
    }

}
