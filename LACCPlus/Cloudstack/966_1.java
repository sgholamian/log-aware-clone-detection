//,temp,ComponentContext.java,154,167,temp,CloudStackExtendedLifeCycle.java,69,96
//,3
public class xxx {
    static void registerMBean(ManagementBean mbean) {
        try {
            JmxUtil.registerMBean(mbean);
        } catch (MalformedObjectNameException e) {
            s_logger.warn("Unable to register MBean: " + mbean.getName(), e);
        } catch (InstanceAlreadyExistsException e) {
            s_logger.warn("Unable to register MBean: " + mbean.getName(), e);
        } catch (MBeanRegistrationException e) {
            s_logger.warn("Unable to register MBean: " + mbean.getName(), e);
        } catch (NotCompliantMBeanException e) {
            s_logger.warn("Unable to register MBean: " + mbean.getName(), e);
        }
        s_logger.info("Registered MBean: " + mbean.getName());
    }

};