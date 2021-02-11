//,temp,ClusterAlertAdapter.java,104,120,temp,ConsoleProxyAlertAdapter.java,147,162
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {

        if (s_logger.isInfoEnabled()) {
            s_logger.info("Start configuring cluster alert manager : " + name);
        }

        try {
            SubscriptionMgr.getInstance().subscribe(ClusterManager.ALERT_SUBJECT, this, "onClusterAlert");
        } catch (SecurityException e) {
            throw new ConfigurationException("Unable to register cluster event subscription");
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Unable to register cluster event subscription");
        }

        return true;
    }

};