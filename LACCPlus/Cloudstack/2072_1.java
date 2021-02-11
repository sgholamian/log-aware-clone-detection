//,temp,NetScalerControlCenterResource.java,838,923,temp,HypervDirectConnectResource.java,2195,2275
//,3
public class xxx {
    public static String postHttpRequest(final String jsonCmd, final URI agentUri, String sessionID) throws ExecutionException {
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
        HttpClient httpClient = getHttpClient();
        TrustStrategy easyStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
                return true;
            }
        };

        try {
            SSLSocketFactory sf = new SSLSocketFactory(easyStrategy, new AllowAllHostnameVerifier());
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", DEFAULT_PORT, sf));
            ClientConnectionManager ccm = new BasicClientConnectionManager(registry);
            httpClient = new DefaultHttpClient(ccm);
        } catch (KeyManagementException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        } catch (UnrecoverableKeyException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        } catch (KeyStoreException e) {
            s_logger.error("failed to initialize http client " + e.getMessage());
        }

        String result = null;

        // TODO: are there timeout settings and worker thread settings to tweak?
        try {
            HttpPost request = new HttpPost(agentUri);

            // JSON encode command
            // Assumes command sits comfortably in a string, i.e. not used for
            // large data transfers
            StringEntity cmdJson = new StringEntity(jsonCmd);
            request.addHeader("content-type", "application/json");
            request.addHeader("Cookie", "SessId=" + sessionID);
            request.setEntity(cmdJson);
            s_logger.debug("Sending cmd to " + agentUri.toString()
                    + " cmd data:" + logMessage + "SEssion id: " + sessionID);
            HttpResponse response = httpClient.execute(request);

            // Unsupported commands will not route.
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                String errMsg = "Failed : HTTP error code : " + response.getStatusLine().getStatusCode();
                throw new ExecutionException(NccHttpCode.NOT_FOUND);
            } else if ((response.getStatusLine().getStatusCode() != HttpStatus.SC_OK ) && (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED )) {
                String errMsg = "Command Not Success " + agentUri.toString() + " : HTTP error code : " + response.getStatusLine().getStatusCode();
                s_logger.error(errMsg);
                throw new ExecutionException(NccHttpCode.INTERNAL_ERROR + " " + errMsg);
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                //Successfully created the resource in the NCC, Now get the Job ID and send to the response
                // make login request and store new session id
                throw new ExecutionException(NccHttpCode.UNAUTHORIZED);
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                //Successfully created the resource in the NCC, Now get the Job ID and send to the response
                result = response.getFirstHeader(NccHttpCode.JOB_ID).getValue();
            } else {
                result = EntityUtils.toString(response.getEntity());
                String logResult = cleanPassword(StringEscapeUtils.unescapeJava(result));
                s_logger.debug("POST response is " + logResult);
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