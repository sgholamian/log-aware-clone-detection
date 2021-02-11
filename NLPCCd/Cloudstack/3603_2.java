//,temp,sample_8893.java,2,8,temp,sample_5102.java,2,8
//,2
public class xxx {
public static void globalSetUp() throws Exception {
ApiConnectorFactory.setImplementation(ApiConnectorMockito.class);
s_mysqlServerPort = TestDbSetup.init(null);


log.info("mysql server launched on port");
}

};