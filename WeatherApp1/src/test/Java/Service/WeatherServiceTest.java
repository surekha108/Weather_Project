package Service;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WeatherServiceTest {

    String validCityURI = null;
    String validZipURI = null;
    String validCoordURI = null;

    String invalidCityURI = null;
    String anotherInvalidCityURI = null;
    String invalidZipURI = null;
    String invalidcoordURI = null;
    String invalidLatURI = null;
    String invalidLonURI = null;
    WeatherService wsValidCity = null;
    WeatherService wsValidZip = null;
    WeatherService wsValidCoord = null;

    WeatherService wsinValidCity = null;
    WeatherService wsanotherInValidCity = null;
    WeatherService wsinValidZip = null;
    WeatherService wsinValidcoord = null;
    WeatherService wsinValidLat = null;
    WeatherService wsinValidLon = null;
    @Before
    public void setUp() throws Exception {

        //valid tests
        validCityURI = "http://api.openweathermap.org/data/2.5/weather?q=delhi&appid=b1dd2aed59e613140b8c6599fa289c46";
        validZipURI = "http://api.openweathermap.org/data/2.5/weather?q=94587&appid=b1dd2aed59e613140b8c6599fa289c46";
        validCoordURI = "http://api.openweathermap.org/data/2.5/weather?lat=23&lon=135&appid=b1dd2aed59e613140b8c6599fa289c46";

         wsValidCity = new WeatherService(validCityURI);
        wsValidZip = new WeatherService(validZipURI);
        wsValidCoord = new WeatherService(validCoordURI);

        //invalid tests
        invalidCityURI="http://api.openweathermap.org/data/2.5/weather?q=abc&appid=b1dd2aed59e613140b8c6599fa289c46";
        anotherInvalidCityURI = "http://api.openweathermap.org/data/2.5/weather?q=de lhi&appid=b1dd2aed59e613140b8c6599fa289c46";
        invalidZipURI = "http://api.openweathermap.org/data/2.5/weather?q=1&appid=b1dd2aed59e613140b8c6599fa289c46";
        invalidcoordURI="http://api.openweathermap.org/data/2.5/weather?lat=-1. 23&lon=-23&appid=b1dd2aed59e613140b8c6599fa289c46";
        invalidLatURI="http://api.openweathermap.org/data/2.5/weather?lat=-91&lon=-23&appid=b1dd2aed59e613140b8c6599fa289c46";
        invalidLonURI="http://api.openweathermap.org/data/2.5/weather?lat=86&lon=181&appid=b1dd2aed59e613140b8c6599fa289c46";

        wsinValidCity = new WeatherService(invalidCityURI);
        wsanotherInValidCity = new WeatherService(invalidCityURI);
        wsinValidZip = new WeatherService(invalidZipURI);
        wsinValidcoord=new WeatherService(invalidcoordURI);
        wsinValidLat=new WeatherService(invalidLatURI);
        wsinValidLon=new WeatherService(invalidLonURI);
    }


    @Test
    public void call() {

        Map<String, Object> validCitymap = wsValidCity.call();
        Map<String, Object> validZipmap = wsValidCity.call();
        Map<String, Object> validCoordmap = wsValidCity.call();

        Map<String, Object> invalidCitymap = wsinValidCity.call();
        Map<String, Object> anotherInvalidCitymap = wsanotherInValidCity.call();
        Map<String, Object> invalidZipmap = wsinValidZip.call();
        Map<String, Object> invalidcoordmap = wsinValidcoord.call();
        Map<String, Object> invalidLatmap =  wsinValidLat.call();
        Map<String, Object> invalidLonmap =  wsinValidLon.call();

        //positive tests
        assertEquals("Valid City test", validCitymap.get("status"), "true");
        assertEquals("Valid zip test", validZipmap.get("status"), "true");
        assertEquals("Valid Geographical co-ordinates test", validCoordmap.get("status"), "true");

        //negative tests
        assertEquals("Invalid city test","false", invalidCitymap.get("status"));
        assertEquals("Invalid city test with space","false", anotherInvalidCitymap.get("status"));
        assertEquals("Invalid zip test","false", invalidZipmap.get("status"));
        assertEquals("Invalid geographical co-ord test","false", invalidcoordmap.get("status"));
        assertEquals("Invalid Latitude co-ord test","false", invalidLatmap.get("status"));
        assertEquals("Invalid Longitude co-ord test","false", invalidLonmap.get("status"));
    }
}