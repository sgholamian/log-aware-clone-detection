//,temp,sample_4343.java,2,11,temp,sample_4342.java,2,10
//,3
public class xxx {
public boolean supports(Configuration configuration) {
String poolingType = MetastoreConf.getVar(configuration, MetastoreConf.ConfVars.CONNECTION_POOLING_TYPE).toLowerCase();
if (BONECP.equals(poolingType)) {
int boneCpPropsNr = DataSourceProvider.getPrefixedProperties(configuration, BONECP).size();


log.info("found nr of bonecp specific configurations");
}
}

};