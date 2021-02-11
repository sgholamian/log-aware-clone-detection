//,temp,DatabaseIntegrityChecker.java,296,305,temp,ManagementServerNode.java,51,60
//,2
public class xxx {
    @Override
    public boolean start() {
        try {
            check();
        } catch (Exception e) {
            s_logger.error("System integrity check exception", e);
            System.exit(1);
        }
        return true;
    }

};