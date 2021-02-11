//,temp,JuniperSrxResource.java,517,528,temp,NetScalerControlCenterResource.java,639,650
//,3
public class xxx {
    private boolean refreshSrxConnection() {
        if (!(closeSocket() && openSocket())) {
            return false;
        }

        try {
            return login();
        } catch (ExecutionException e) {
            s_logger.error("Failed to login due to " + e.getMessage());
            return false;
        }
    }

};