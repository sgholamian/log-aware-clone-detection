//,temp,User.java,179,200,temp,User.java,150,177
//,3
public class xxx {
    public void retrievePublicIp(long zoneId) throws IOException {

        String encodedApiKey = URLEncoder.encode(this.apiKey, "UTF-8");
        String encodedZoneId = URLEncoder.encode("" + zoneId, "UTF-8");
        String requestToSign = "apiKey=" + encodedApiKey + "&command=associateIpAddress" + "&zoneId=" + encodedZoneId;
        requestToSign = requestToSign.toLowerCase();
        String signature = TestClientWithAPI.signRequest(requestToSign, this.secretKey);
        String encodedSignature = URLEncoder.encode(signature, "UTF-8");

        String url = this.developerServer + "?command=associateIpAddress" + "&apiKey=" + encodedApiKey + "&zoneId=" + encodedZoneId + "&signature=" + encodedSignature;

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> values = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"ipaddress"});
            this.getPublicIp().add(values.get("ipaddress"));
            s_logger.info("Ip address is " + values.get("ipaddress"));
        } else if (responseCode == 500) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> errorInfo = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"errorcode", "description"});
            s_logger.error("associate ip test failed with errorCode: " + errorInfo.get("errorCode") + " and description: " + errorInfo.get("description"));
        } else {
            s_logger.error("internal error processing request: " + method.getStatusText());
        }

    }

};