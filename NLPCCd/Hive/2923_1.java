//,temp,sample_116.java,2,17,temp,sample_115.java,2,17
//,3
public class xxx {
public void dummy_method(){
TProcessor processor = new TCLIService.Processor<Iface>(this);
TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
UserGroupInformation serviceUGI = cliService.getServiceUGI();
UserGroupInformation httpUGI = cliService.getHttpUGI();
String authType = hiveConf.getVar(ConfVars.HIVE_SERVER2_AUTHENTICATION);
TServlet thriftHttpServlet = new ThriftHttpServlet(processor, protocolFactory, authType, serviceUGI, httpUGI, hiveAuthFactory);
final ServletContextHandler context = new ServletContextHandler( ServletContextHandler.SESSIONS);
context.setContextPath("/");
if (hiveConf.getBoolean(ConfVars.HIVE_SERVER2_XSRF_FILTER_ENABLED.varname, false)){
} else {


log.info("xsrf filter disabled");
}
}

};