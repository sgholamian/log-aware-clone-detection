//,temp,JuniperSrxResource.java,593,608,temp,JuniperSrxResource.java,551,566
//,2
public class xxx {
    private boolean closeSocket() {
        try {
            if (_toSrx != null) {
                _toSrx.close();
            }

            if (_fromSrx != null) {
                _fromSrx.close();
            }

            return true;
        } catch (IOException e) {
            s_logger.error(e);
            return false;
        }
    }

};