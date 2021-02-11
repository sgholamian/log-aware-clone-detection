//,temp,ClusterAlertAdapter.java,104,120,temp,ConsoleProxyAlertAdapter.java,147,162
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {

        if (s_logger.isInfoEnabled())
            s_logger.info("Start configuring console proxy alert manager : " + name);

        try {
            SubscriptionMgr.getInstance().subscribe(ConsoleProxyManager.ALERT_SUBJECT, this, "onProxyAlert");
        } catch (SecurityException e) {
            throw new ConfigurationException("Unable to register console proxy event subscription, exception: " + e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Unable to register console proxy event subscription, exception: " + e);
        }

        return true;
    }

};