//,temp,SecondaryStorageVmAlertAdapter.java,151,166,temp,ConsoleProxyAlertAdapter.java,147,162
//,2
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