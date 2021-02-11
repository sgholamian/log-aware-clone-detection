//,temp,UriUtils.java,436,463,temp,UriUtils.java,399,431
//,3
public class xxx {
    protected static boolean checkUrlExistenceMetalink(String url) {
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = new GetMethod(url);
        try {
            if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {
                InputStream is = getMethod.getResponseBodyAsStream();
                Map<String, List<String>> metalinkUrls = getMultipleValuesFromXML(is, new String[] {"url"});
                if (metalinkUrls.containsKey("url")) {
                    List<String> urls = metalinkUrls.get("url");
                    boolean validUrl = false;
                    for (String u : urls) {
                        if (url.endsWith("torrent")) {
                            continue;
                        }
                        try {
                            UriUtils.checkUrlExistence(u);
                            validUrl = true;
                            break;
                        }
                        catch (IllegalArgumentException e) {
                            s_logger.warn(e.getMessage());
                        }
                    }
                    return validUrl;
                }
            }
        } catch (IOException e) {
            s_logger.warn(e.getMessage());
        } finally {
            getMethod.releaseConnection();
        }
        return false;
    }

};