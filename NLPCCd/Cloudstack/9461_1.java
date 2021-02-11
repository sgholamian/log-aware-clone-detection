//,temp,sample_6845.java,2,10,temp,sample_6846.java,2,13
//,3
public class xxx {
public boolean hasNexusVSM(Long clusterId) {
ClusterVSMMapVO vsmMapVo = null;
vsmMapVo = _vsmMapDao.findByClusterId(clusterId);
if (vsmMapVo == null) {


log.info("there is no instance of nexus vsm associated with this cluster id yet");
}
}

};