//,temp,VirtualMachine.java,57,93,temp,User.java,150,177
//,3
public class xxx {
    public void deployVM(long zoneId, long serviceOfferingId, long templateId, String server, String apiKey, String secretKey) throws IOException {

        String encodedZoneId = URLEncoder.encode("" + zoneId, "UTF-8");
        String encodedServiceOfferingId = URLEncoder.encode("" + serviceOfferingId, "UTF-8");
        String encodedTemplateId = URLEncoder.encode("" + templateId, "UTF-8");
        String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
        String requestToSign =
            "apiKey=" + encodedApiKey + "&command=deployVirtualMachine&serviceOfferingId=" + encodedServiceOfferingId + "&templateId=" + encodedTemplateId + "&zoneId=" +
                encodedZoneId;

        requestToSign = requestToSign.toLowerCase();
        String signature = TestClientWithAPI.signRequest(requestToSign, secretKey);
        String encodedSignature = URLEncoder.encode(signature, "UTF-8");
        String url =
            server + "?command=deployVirtualMachine" + "&zoneId=" + encodedZoneId + "&serviceOfferingId=" + encodedServiceOfferingId + "&templateId=" +
                encodedTemplateId + "&apiKey=" + encodedApiKey + "&signature=" + encodedSignature;

        s_logger.info("Sending this request to deploy a VM: " + url);
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        s_logger.info("deploy linux vm response code: " + responseCode);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> values = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"id", "ipaddress"});
            long linuxVMId = Long.parseLong(values.get("id"));
            s_logger.info("got linux virtual machine id: " + linuxVMId);
            this.setPrivateIp(values.get("ipaddress"));

        } else if (responseCode == 500) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> errorInfo = TestClientWithAPI.getSingleValueFromXML(is, new String[] {"errorcode", "description"});
            s_logger.error("deploy linux vm test failed with errorCode: " + errorInfo.get("errorCode") + " and description: " + errorInfo.get("description"));
        } else {
            s_logger.error("internal error processing request: " + method.getStatusText());
        }
    }

};