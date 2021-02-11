//,temp,VmwareManagerImpl.java,1359,1372,temp,CiscoNexusVSMDeviceManagerImpl.java,266,276
//,3
public class xxx {
    @DB
    public CiscoNexusVSMDeviceVO getCiscoVSMbyClusId(long clusterId) {
        ClusterVSMMapVO mapVO = _clusterVSMDao.findByClusterId(clusterId);
        if (mapVO == null) {
            s_logger.info("Couldn't find a VSM associated with the specified cluster Id");
            return null;
        }
        // Else, pull out the VSM associated with the VSM id in mapVO.
        CiscoNexusVSMDeviceVO result = _ciscoNexusVSMDeviceDao.findById(mapVO.getVsmId());
        return result;
    }

};