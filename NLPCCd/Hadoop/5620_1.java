//,temp,sample_1872.java,2,16,temp,sample_1873.java,2,16
//,2
public class xxx {
public void dummy_method(){
DataNodeTestUtils.triggerBlockReport(dn);
MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
ObjectName mxbeanName = new ObjectName( "Hadoop:service=DataNode,name=DataNodeInfo");
String bpActorInfo = (String)mbs.getAttribute(mxbeanName, "BPServiceActorInfo");
Assert.assertEquals(dn.getBPServiceActorInfo(), bpActorInfo);
TypeReference<ArrayList<Map<String, String>>> typeRef = new TypeReference<ArrayList<Map<String, String>>>() {};
ArrayList<Map<String, String>> bpActorInfoList = new ObjectMapper().readValue(bpActorInfo, typeRef);
int maxDataLength = Integer.valueOf(bpActorInfoList.get(0).get("maxDataLength"));
int confMaxDataLength = dn.getConf().getInt( CommonConfigurationKeys.IPC_MAXIMUM_DATA_LENGTH, CommonConfigurationKeys.IPC_MAXIMUM_DATA_LENGTH_DEFAULT);
int maxBlockReportSize = Integer.valueOf(bpActorInfoList.get(0).get("maxBlockReportSize"));


log.info("maxdatalength is");
}

};