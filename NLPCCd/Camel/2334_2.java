//,temp,sample_7240.java,2,8,temp,sample_4339.java,2,8
//,2
public class xxx {
public Boolean executeTest(ITestConfig config, String dataFormat) throws Exception {
DataFormat df = context.resolveDataFormat(dataFormat);
assertNotNull("Cannot get dataformat with name: " + dataFormat, df);


log.info("found camel dataformat instance with classname");
}

};