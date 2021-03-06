//,temp,ClusterAlertAdapter.java,104,120,temp,SecondaryStorageVmAlertAdapter.java,151,166
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {

        if (s_logger.isInfoEnabled())
            s_logger.info("Start configuring secondary storage vm alert manager : " + name);

        try {
            SubscriptionMgr.getInstance().subscribe(SecondaryStorageVmManager.ALERT_SUBJECT, this, "onSSVMAlert");
        } catch (SecurityException e) {
            throw new ConfigurationException("Unable to register secondary storage vm event subscription, exception: " + e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Unable to register secondary storage vm event subscription, exception: " + e);
        }

        return true;
    }

};