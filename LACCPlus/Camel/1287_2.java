//,temp,JDBCServiceBuilder.java,45,58,temp,MessagingServiceBuilder.java,57,70
//,3
public class xxx {
    public MessagingService build() {
        String instanceType = System.getProperty("messaging.instance.type");

        if (instanceType == null || instanceType.isEmpty()) {
            LOG.info("Creating a new messaging local container service");
            return new MessagingLocalContainerService(containerSupplier.get(), this.endpointFunction);
        }

        if (instanceType.equals("remote")) {
            return new MessagingRemoteService();
        }

        throw new UnsupportedOperationException("Invalid messaging instance type");
    }

};