//,temp,JuniperSrxResource.java,593,608,temp,JuniperSrxResource.java,551,566
//,2
public class xxx {
    private boolean closeUsageSocket() {
        try {
            if (_UsagetoSrx != null) {
                _UsagetoSrx.close();
            }

            if (_UsagefromSrx != null) {
                _UsagefromSrx.close();
            }

            return true;
        } catch (IOException e) {
            s_logger.error(e);
            return false;
        }
    }

};