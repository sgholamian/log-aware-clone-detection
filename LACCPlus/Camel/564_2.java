//,temp,CurrentWeatherConsumerXmlIT.java,28,34,temp,BaseWeatherConsumerIT.java,39,45
//,3
public class xxx {
    protected void checkWeatherContent(String weather) {
        // the default mode is json
        log.debug("The weather in {} format is {}{}", WeatherMode.JSON, LS, weather);

        assertStringContains(weather, "\"coord\":{");
        assertStringContains(weather, "temp");
    }

};