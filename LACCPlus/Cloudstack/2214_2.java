//,temp,JuniperSrxResource.java,517,528,temp,PaloAltoResource.java,374,385
//,3
public class xxx {
    private boolean refreshPaloAltoConnection() {
        if (s_httpclient == null) {
            openHttpConnection();
        }

        try {
            return login(_username, _password);
        } catch (ExecutionException e) {
            s_logger.error("Failed to login due to " + e.getMessage());
            return false;
        }
    }

};