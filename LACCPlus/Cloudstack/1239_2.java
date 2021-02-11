//,temp,HttpTemplateDownloader.java,132,148,temp,UriUtils.java,558,583
//,3
public class xxx {
    public static InputStream getInputStreamFromUrl(String url, String user, String password) {

        try {
            Pair<String, Integer> hostAndPort = validateUrl(url);
            HttpClient httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());
            if ((user != null) && (password != null)) {
                httpclient.getParams().setAuthenticationPreemptive(true);
                Credentials defaultcreds = new UsernamePasswordCredentials(user, password);
                httpclient.getState().setCredentials(new AuthScope(hostAndPort.first(), hostAndPort.second(), AuthScope.ANY_REALM), defaultcreds);
                s_logger.info("Added username=" + user + ", password=" + password + "for host " + hostAndPort.first() + ":" + hostAndPort.second());
            }
            // Execute the method.
            GetMethod method = new GetMethod(url);
            int statusCode = httpclient.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                s_logger.error("Failed to read from URL: " + url);
                return null;
            }

            return method.getResponseBodyAsStream();
        } catch (Exception ex) {
            s_logger.error("Failed to read from URL: " + url);
            return null;
        }
    }

};