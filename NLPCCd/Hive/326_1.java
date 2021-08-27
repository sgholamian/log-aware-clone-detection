//,temp,sample_5532.java,2,10,temp,sample_5533.java,2,11
//,3
public class xxx {
public boolean supports(Configuration configuration) {
String poolingType = MetastoreConf.getVar(configuration, MetastoreConf.ConfVars.CONNECTION_POOLING_TYPE).toLowerCase();
if (HIKARI.equals(poolingType)) {
int hikariPropsNr = DataSourceProvider.getPrefixedProperties(configuration, HIKARI).size();


log.info("found nr of hikari specific configurations");
}
}

};