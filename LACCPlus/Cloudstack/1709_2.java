//,temp,ComponentContext.java,154,167,temp,CloudStackExtendedLifeCycle.java,73,92
//,3
public class xxx {
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

};