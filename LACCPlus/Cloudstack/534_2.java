//,temp,NetScalerControlCenterResource.java,838,923,temp,NetScalerControlCenterResource.java,780,836
//,3
public class xxx {
    public static String getHttpRequest(final String jsonCmd, final URI agentUri, String sessionID) throws ExecutionException {
        // Using Apache's HttpClient for HTTP POST
        // Java-only approach discussed at on StackOverflow concludes with
        // comment to use Apache HttpClient
        // http://stackoverflow.com/a/2793153/939250, but final comment is to
        // use Apache.
        String logMessage = StringEscapeUtils.unescapeJava(jsonCmd);
        logMessage = cleanPassword(logMessage);
        s_logger.debug("GET request to " + agentUri.toString()
                + " with contents " + logMessage);

        // Create request
        HttpClient httpClient = getHttpClient();
        String result = null;

        // TODO: are there timeout settings and worker thread settings to tweak?
        try {
            HttpGet request = new HttpGet(agentUri);

            // JSON encode command
            // Assumes command sits comfortably in a string, i.e. not used for
            // large data transfers
            StringEntity cmdJson = new StringEntity(jsonCmd);
            request.addHeader("content-type", "application/json");
            request.addHeader("Cookie", "SessId=" + sessionID);
            s_logger.debug("Sending cmd to " + agentUri.toString()
                    + " cmd data:" + logMessage);
            HttpResponse response = httpClient.execute(request);

            // Unsupported commands will not route.
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                String errMsg = "Failed to send : HTTP error code : " + response.getStatusLine().getStatusCode();
                s_logger.error(errMsg);
                String unsupportMsg = "Unsupported command " + agentUri.getPath() + ".  Are you sure you got the right f of" + " server?";
                Answer ans = new UnsupportedAnswer(null, unsupportMsg);
                s_logger.error(ans);
                result = s_gson.toJson(new Answer[] {ans});
            } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String errMsg = "Failed send to " + agentUri.toString() + " : HTTP error code : " + response.getStatusLine().getStatusCode();
                s_logger.error(errMsg);
                throw new ExecutionException("UNAUTHORIZED");
            } else {
                result = EntityUtils.toString(response.getEntity());
                String logResult = cleanPassword(StringEscapeUtils.unescapeJava(result));
                s_logger.debug("Get response is " + logResult);
            }
        } catch (ClientProtocolException protocolEx) {
            // Problem with HTTP message exchange
            s_logger.error(protocolEx);
        } catch (IOException connEx) {
            // Problem with underlying communications
            s_logger.error(connEx);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

};