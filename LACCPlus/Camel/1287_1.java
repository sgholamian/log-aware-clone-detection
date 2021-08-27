//,temp,JDBCServiceBuilder.java,45,58,temp,MessagingServiceBuilder.java,57,70
//,3
public class xxx {
    public JDBCService build() {
        String instanceType = System.getProperty("jdbc.instance.type");

        if (instanceType == null || instanceType.isEmpty()) {
            LOG.info("Creating a new messaging local container service");
            return new JDBCLocalContainerService(container);
        }

        if (instanceType.equals("remote")) {
            return new JDBCRemoteService();
        }

        throw new UnsupportedOperationException("Invalid messaging instance type");
    }

};