//,temp,ServerCnxnFactory.java,159,176,temp,WatchManagerFactory.java,35,51
//,3
public class xxx {
    public static IWatchManager createWatchManager() throws IOException {
        String watchManagerName = System.getProperty(ZOOKEEPER_WATCH_MANAGER_NAME);
        if (watchManagerName == null) {
            watchManagerName = WatchManager.class.getName();
        }
        try {
            IWatchManager watchManager =
                    (IWatchManager) Class.forName(watchManagerName).newInstance();
            LOG.info("Using {} as watch manager", watchManagerName);
            return watchManager;
        } catch (Exception e) {
            IOException ioe = new IOException("Couldn't instantiate "
                    + watchManagerName);
            ioe.initCause(e);
            throw ioe;
        }
    }

};