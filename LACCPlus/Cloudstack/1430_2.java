//,temp,VirtualMachine.java,57,93,temp,PerformanceWithAPI.java,150,188
//,3
public class xxx {
    private static int CreateForwardingRule(User myUser, String privateIp, String publicIp, String publicPort, String privatePort) throws IOException {
        String encodedPrivateIp = URLEncoder.encode("" + privateIp, "UTF-8");
        String encodedPublicIp = URLEncoder.encode("" + publicIp, "UTF-8");
        String encodedPrivatePort = URLEncoder.encode("" + privatePort, "UTF-8");
        String encodedPublicPort = URLEncoder.encode("" + publicPort, "UTF-8");
        String encodedApiKey = URLEncoder.encode(myUser.getApiKey(), "UTF-8");
        int responseCode = 500;

        String requestToSign =
            "apiKey=" + encodedApiKey + "&command=createOrUpdateIpForwardingRule&privateIp=" + encodedPrivateIp + "&privatePort=" + encodedPrivatePort +
                "&protocol=tcp&publicIp=" + encodedPublicIp + "&publicPort=" + encodedPublicPort;

        requestToSign = requestToSign.toLowerCase();
        s_logger.info("Request to sign is " + requestToSign);

        String signature = TestClientWithAPI.signRequest(requestToSign, myUser.getSecretKey());
        String encodedSignature = URLEncoder.encode(signature, "UTF-8");

        String url =
            myUser.getDeveloperServer() + "?command=createOrUpdateIpForwardingRule" + "&publicIp=" + encodedPublicIp + "&publicPort=" + encodedPublicPort +
                "&privateIp=" + encodedPrivateIp + "&privatePort=" + encodedPrivatePort + "&protocol=tcp&apiKey=" + encodedApiKey + "&signature=" + encodedSignature;

        s_logger.info("Trying to create IP forwarding rule: " + url);
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("create ip forwarding rule response code: " + responseCode);
        if (responseCode == 200) {
            s_logger.info("The rule is created successfully");
        } else if (responseCode == 500) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> errorInfo = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"errorCode", "description"});
            s_logger.error("create ip forwarding rule (linux) test failed with errorCode: " + errorInfo.get("errorCode") + " and description: " +
                errorInfo.get("description"));
        } else {
            s_logger.error("internal error processing request: " + method.getStatusText());
        }
        return responseCode;
    }

};