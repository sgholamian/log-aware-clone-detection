//,temp,VmwareManagerImpl.java,1359,1372,temp,CiscoNexusVSMDeviceManagerImpl.java,266,276
//,3
public class xxx {
    @Override
    public boolean hasNexusVSM(Long clusterId) {
        ClusterVSMMapVO vsmMapVo = null;

        vsmMapVo = _vsmMapDao.findByClusterId(clusterId);
        if (vsmMapVo == null) {
            s_logger.info("There is no instance of Nexus 1000v VSM associated with this cluster [Id:" + clusterId + "] yet.");
            return false;
        }
        else {
            s_logger.info("An instance of Nexus 1000v VSM [Id:" + vsmMapVo.getVsmId() + "] associated with this cluster [Id:" + clusterId + "]");
            return true;
        }
    }

};