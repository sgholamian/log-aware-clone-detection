//,temp,sample_6838.java,2,14,temp,sample_6839.java,2,15
//,2
public class xxx {
public Map<String, String> getNexusVSMCredentialsByClusterId(Long clusterId) {
CiscoNexusVSMDeviceVO nexusVSM = null;
ClusterVSMMapVO vsmMapVO = null;
vsmMapVO = _vsmMapDao.findByClusterId(clusterId);
long vsmId = 0;
if (vsmMapVO != null) {
vsmId = vsmMapVO.getVsmId();
nexusVSM = _nexusDao.findById(vsmId);
} else {


log.info("found empty vsmmapvo");
}
}

};