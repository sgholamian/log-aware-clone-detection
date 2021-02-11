//,temp,JuniperSrxResource.java,579,591,temp,JuniperSrxResource.java,537,549
//,3
public class xxx {
    private boolean openSocket() {
        try {
            Socket s = new Socket(_ip, 3221);
            s.setKeepAlive(true);
            s.setSoTimeout(_timeoutInSeconds * 1000);
            _toSrx = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
            _fromSrx = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
            return true;
        } catch (IOException e) {
            s_logger.error(e);
            return false;
        }
    }

};