//,temp,User.java,179,200,temp,User.java,150,177
//,3
public class xxx {
    public void registerUser() throws HttpException, IOException {

        String encodedUsername = URLEncoder.encode(this.userName, "UTF-8");
        String encodedPassword = URLEncoder.encode(this.password, "UTF-8");
        String url = server + "?command=register&username=" + encodedUsername + "&domainid=1";
        s_logger.info("registering: " + this.userName + " with url " + url);
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> requestKeyValues = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"apikey", "secretkey"});
            this.setApiKey(requestKeyValues.get("apikey"));
            this.setSecretKey(requestKeyValues.get("secretkey"));
        } else if (responseCode == 500) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> errorInfo = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"errorcode", "description"});
            s_logger.error("registration failed with errorCode: " + errorInfo.get("errorCode") + " and description: " + errorInfo.get("description"));
        } else {
            s_logger.error("internal error processing request: " + method.getStatusText());
        }
    }

};