package Service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;


public class WeatherService implements Callable<Map<String, Object>> {

    private String uri ;
    private RestTemplate restTemplate = new RestTemplate();

    /* This status flag is to check, if result is fetched from the third party api */
    public static boolean status = false;


    public WeatherService(String uri) {
        this.uri = uri;
    }

    public Map<String, Object> call() {

        // call open weather api end point
        String responseEntity = restTemplate.getForObject(uri, String.class);

        // map to store the end result
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject weatheResponseJsonObj = null;
        try {
            weatheResponseJsonObj = new JSONObject(responseEntity);
            System.out.println("JSON Object : " + weatheResponseJsonObj);

            //extract nested json object with the name "main" from the response
            JSONObject mainJsonObj = weatheResponseJsonObj.getJSONObject("main");

            //extract the temperature with key as "temp"
            String temp = mainJsonObj.getString("temp");

            //if the http response code is 200, return success message with the temperature info
            if (weatheResponseJsonObj.getInt("cod") == 200) {
                map.put("status", "true");
                map.put("current temperature", temp+" "+"kelvin");
            } else {
                map.put("status", "false");
                map.put("Error Message", "Error fetching data from API");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(map.entrySet());

        /*By now, third api query processing must be completed, set this flag to true */
        status = true;

    return map;

    }
}
