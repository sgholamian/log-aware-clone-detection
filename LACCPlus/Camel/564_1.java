//,temp,CurrentWeatherConsumerXmlIT.java,28,34,temp,BaseWeatherConsumerIT.java,39,45
//,3
public class xxx {
    @Override
    protected void checkWeatherContent(String weather) {
        log.debug("The weather in {} format is {}{}", WeatherMode.XML, LS, weather);

        assertStringContains(weather, "<coord");
        assertStringContains(weather, "<temperature");
    }

};