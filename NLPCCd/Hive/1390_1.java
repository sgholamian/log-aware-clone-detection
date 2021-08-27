//,temp,sample_4422.java,2,9,temp,sample_3391.java,2,10
//,3
public class xxx {
public StaticPermanentFunctionChecker(Configuration conf) {
URL logger = conf.getResource(PERMANENT_FUNCTIONS_LIST);
if (logger == null) {


log.info("could not find udf whitelist in configuration");
}
}

};