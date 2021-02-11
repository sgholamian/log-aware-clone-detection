//,temp,NetScalerControlCenterResource.java,838,923,temp,HypervDirectConnectResource.java,2195,2275
//,3
public class xxx {
    public static String postHttpRequest(final String jsonCmd, final URI agentUri) {
        // Using Apache's HttpClient for HTTP POST
        // Java-only approach discussed at on StackOverflow concludes with
        // comment to use Apache HttpClient
        // http://stackoverflow.com/a/2793153/939250, but final comment is to
        // use Apache.
        String logMessage = StringEscapeUtils.unescapeJava(jsonCmd);
        logMessage = cleanPassword(logMessage);
        s_logger.debug("POST request to " + agentUri.toString()
                + " with contents " + logMessage);

        // Create request
        HttpClient httpClient = null;
        final TrustStrategy easyStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(final X509Certificate[] chain, final String authType)
                    throws CertificateException {
                return true;
            }
        };

        try {
            final SSLSocketFactory sf = new SSLSocketFactory(easyStrategy, new AllowAllHostnameVerifier());
            final SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", DEFAULT_AGENT_PORT, sf));
            final ClientConnectionManager ccm = new BasicClientConnectionManager(registry);
            httpClient = new DefaultHttpClient(ccm);
        } catch (final KeyManagementException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        } catch (final UnrecoverableKeyException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        } catch (final NoSuchAlgorithmException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        } catch (final KeyStoreException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        }

        String result = null;

        // TODO: are there timeout settings and worker thread settings to tweak?
        try {
            final HttpPost request = new HttpPost(agentUri);

            // JSON encode command
            // Assumes command sits comfortably in a string, i.e. not used for
            // large data transfers
            final StringEntity cmdJson = new StringEntity(jsonCmd);
            request.addHeader("content-type", "application/json");
            request.setEntity(cmdJson);
            s_logger.debug("Sending cmd to " + agentUri.toString()
                    + " cmd data:" + logMessage);
            final HttpResponse response = httpClient.execute(request);

            // Unsupported commands will not route.
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                final String errMsg = "Failed to send : HTTP error code : " + response.getStatusLine().getStatusCode();
                s_logger.error(errMsg);
                final String unsupportMsg = "Unsupported command " + agentUri.getPath() + ".  Are you sure you got the right type of" + " server?";
                final Answer ans = new UnsupportedAnswer(null, unsupportMsg);
                s_logger.error(ans);
                result = s_gson.toJson(new Answer[] {ans});
            } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                final String errMsg = "Failed send to " + agentUri.toString() + " : HTTP error code : " + response.getStatusLine().getStatusCode();
                s_logger.error(errMsg);
                return null;
            } else {
                result = EntityUtils.toString(response.getEntity());
                final String logResult = cleanPassword(StringEscapeUtils.unescapeJava(result));
                s_logger.debug("POST response is " + logResult);
            }
        } catch (final ClientProtocolException protocolEx) {
            // Problem with HTTP message exchange
            s_logger.error(protocolEx);
        } catch (final IOException connEx) {
            // Problem with underlying communications
            s_logger.error(connEx);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

};