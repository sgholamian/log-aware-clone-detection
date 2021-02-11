//,temp,TestClientWithAPI.java,499,515,temp,StressTestDirectAttach.java,898,931
//,3
public class xxx {
    private static String executeRegistration(String server, String username, String password) throws HttpException, IOException {
        String url = server + "?command=registerUserKeys&id=" + s_userId.get().toString();
        s_logger.info("registering: " + username);
        String returnValue = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> requestKeyValues = getSingleValueFromXML(is, new String[] {"apikey", "secretkey"});
            s_apiKey.set(requestKeyValues.get("apikey"));
            returnValue = requestKeyValues.get("secretkey");
        } else {
            s_logger.error("registration failed with error code: " + responseCode);
        }
        return returnValue;
    }

};