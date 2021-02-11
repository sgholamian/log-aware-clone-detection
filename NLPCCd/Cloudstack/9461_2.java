//,temp,sample_6845.java,2,10,temp,sample_6846.java,2,13
//,3
public class xxx {
public boolean hasNexusVSM(Long clusterId) {
ClusterVSMMapVO vsmMapVo = null;
vsmMapVo = _vsmMapDao.findByClusterId(clusterId);
if (vsmMapVo == null) {
return false;
}
else {


log.info("an instance of nexus vsm id associated with this cluster id");
}
}

};