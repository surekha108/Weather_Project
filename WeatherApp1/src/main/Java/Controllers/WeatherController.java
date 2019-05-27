package Controllers;


import Service.WeatherService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


@RestController
public class WeatherController {

    private final String appId = "b1dd2aed59e613140b8c6599fa289c46";
   // ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
   ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors. newCachedThreadPool();


//    @RequestMapping(value = "/current", method = RequestMethod.GET)
//    public ModelAndView hello(@RequestParam(value = "city", required = false) String city,
//                              @RequestParam(value = "zip", required = false) String zip,
//                              @RequestParam(value = "lat", required = false) String lat,
//                              @RequestParam(value = "lon", required = false) String lon) throws IOException, JSONException {
//
//        String uri = null;
//
//        if(city != null)
//             uri = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+appId;
//        else if(zip != null)
//            uri = "http://api.openweathermap.org/data/2.5/weather?zip="+zip+"&appid="+appId;
//        else if(lat !=null && lon != null)
//            uri = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+appId;
//
//        String responseEntity = restTemplate.getForObject(uri, String.class);
//
//        JSONObject obj_JSONObject = new JSONObject(responseEntity);
//
//        System.out.println("JSON Object : "+obj_JSONObject);
//
//        JSONObject mainObj =   obj_JSONObject.getJSONObject("main");
//        String temp = mainObj.getString("temp");
//        ModelAndView mnv =new ModelAndView();
//        mnv.setViewName("result");
//        mnv.addObject("currentWeather",temp);
//        return mnv;
//
//    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Map<String, Object> getCurrentWeather(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                 @RequestParam(value = "city", required = false) String city,
                                                 @RequestParam(value = "zip", required = false) String zip,
                                                 @RequestParam(value = "lat", required = false) String lat,
                                                 @RequestParam(value = "lon", required = false) String lon) throws IOException, JSONException, ExecutionException, InterruptedException {

        String uri = null;

            if (city != null)
                uri = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + appId;
            else if (zip != null)
                uri = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip + "&appid=" + appId;
            else if (lat != null && lon != null)
                uri = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appId;

        //create a Callable object
        WeatherService ws = new WeatherService(uri);

        //submit the task to the thread pool
        Future<Map<String, Object>> result = executor.submit(ws);

        if(WeatherService.status) {
            System.out.println("Task completed! Retrieving the result");
            Map<String, Object> result1 = result.get();
            System.out.println(result1.entrySet());
            WeatherService.status = false;
            return result1;
        }
        else{
            httpServletResponse.setStatus(202);
            return new HashMap<String, Object>(){{put("status", "Fetching data, please try again in few seconds");}};
        }

    }

}
