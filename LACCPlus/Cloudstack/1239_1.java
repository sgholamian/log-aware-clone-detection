//,temp,HttpTemplateDownloader.java,132,148,temp,UriUtils.java,558,583
//,3
public class xxx {
    private void checkCredentials(String user, String password) {
        try {
            Pair<String, Integer> hostAndPort = UriUtils.validateUrl(downloadUrl);
            if ((user != null) && (password != null)) {
                client.getParams().setAuthenticationPreemptive(true);
                Credentials defaultcreds = new UsernamePasswordCredentials(user, password);
                client.getState().setCredentials(new AuthScope(hostAndPort.first(), hostAndPort.second(), AuthScope.ANY_REALM), defaultcreds);
                s_logger.info("Added username=" + user + ", password=" + password + "for host " + hostAndPort.first() + ":" + hostAndPort.second());
            } else {
                s_logger.info("No credentials configured for host=" + hostAndPort.first() + ":" + hostAndPort.second());
            }
        } catch (IllegalArgumentException iae) {
            errorString = iae.getMessage();
            status = TemplateDownloader.Status.UNRECOVERABLE_ERROR;
            inited = false;
        }
    }

};