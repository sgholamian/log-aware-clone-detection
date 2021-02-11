//,temp,ComponentContext.java,154,167,temp,CloudStackExtendedLifeCycle.java,69,96
//,3
public class xxx {
    public void startBeans() {
        log.info("Starting CloudStack Components");

        with(new WithComponentLifeCycle() {
            @Override
            public void with(ComponentLifecycle lifecycle) {
                lifecycle.start();

                if (lifecycle instanceof ManagementBean) {
                    ManagementBean mbean = (ManagementBean)lifecycle;
                    try {
                        JmxUtil.registerMBean(mbean);
                    } catch (MalformedObjectNameException e) {
                        log.warn("Unable to register MBean: " + mbean.getName(), e);
                    } catch (InstanceAlreadyExistsException e) {
                        log.warn("Unable to register MBean: " + mbean.getName(), e);
                    } catch (MBeanRegistrationException e) {
                        log.warn("Unable to register MBean: " + mbean.getName(), e);
                    } catch (NotCompliantMBeanException e) {
                        log.warn("Unable to register MBean: " + mbean.getName(), e);
                    }
                    log.info("Registered MBean: " + mbean.getName());
                }
            }
        });

        log.info("Done Starting CloudStack Components");
    }

};