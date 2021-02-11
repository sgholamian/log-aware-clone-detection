//,temp,sample_2089.java,2,18,temp,sample_4208.java,2,19
//,3
public class xxx {
public void dummy_method(){
tmpList.add(hostName);
List <String> rNameList = dnsToSwitchMapping.resolve(tmpList);
String rName = null;
if (rNameList == null || rNameList.get(0) == null) {
rName = NetworkTopology.DEFAULT_RACK;
if (LOG.isDebugEnabled()) {
}
} else {
rName = rNameList.get(0);
if (LOG.isDebugEnabled()) {


log.info("resolved to");
}
}
}

};