//,temp,UriUtils.java,436,463,temp,UriUtils.java,399,431
//,3
public class xxx {
    public static List<String> getMetalinkUrls(String metalinkUrl) {
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = new GetMethod(metalinkUrl);
        List<String> urls = new ArrayList<>();
        int status;
        try {
            status = httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            s_logger.error("Error retrieving urls form metalink: " + metalinkUrl);
            getMethod.releaseConnection();
            return null;
        }
        try {
            InputStream is = getMethod.getResponseBodyAsStream();
            if (status == HttpStatus.SC_OK) {
                Map<String, List<String>> metalinkUrlsMap = getMultipleValuesFromXML(is, new String[] {"url"});
                if (metalinkUrlsMap.containsKey("url")) {
                    List<String> metalinkUrls = metalinkUrlsMap.get("url");
                    urls.addAll(metalinkUrls);
                }
            }
        } catch (IOException e) {
            s_logger.warn(e.getMessage());
        } finally {
            getMethod.releaseConnection();
        }
        return urls;
    }

};