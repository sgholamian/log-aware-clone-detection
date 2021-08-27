//,temp,sample_571.java,2,17,temp,sample_570.java,2,17
//,3
public class xxx {
public void dummy_method(){
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/pig/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/hive/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/sqoop/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/queue/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/jobs/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/mapreduce/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/status/*", dispatches);
root.addFilter(fHolder, "/" + SERVLET_PATH + "/v1/version/*", dispatches);
if (conf.getBoolean(AppConfig.XSRF_FILTER_ENABLED, false)){
root.addFilter(makeXSRFFilter(), "/" + SERVLET_PATH + "/*", dispatches);


log.info("xsrf filter enabled");
}
}

};