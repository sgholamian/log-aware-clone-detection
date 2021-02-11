//,temp,LegacyDbUpgrade.java,32,40,temp,ClusterServiceServletContainer.java,112,121
//,3
public class xxx {
    protected void closeAutoCloseable(AutoCloseable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (Exception e) {
                s_logger.info("[ignored]",e);
            }
        }
    }

};