//,temp,NetworkServiceImpl.java,3864,3897,temp,NetworkModelImpl.java,1146,1180
//,2
public class xxx {
    @Override
    public long findPhysicalNetworkId(long zoneId, String tag, TrafficType trafficType) {
        List<PhysicalNetworkVO> pNtwks = new ArrayList<PhysicalNetworkVO>();
        if (trafficType != null) {
            pNtwks = _physicalNetworkDao.listByZoneAndTrafficType(zoneId, trafficType);
        } else {
            pNtwks = _physicalNetworkDao.listByZone(zoneId);
        }

        if (pNtwks.isEmpty()) {
            throw new InvalidParameterValueException("Unable to find physical network in zone id=" + zoneId);
        }

        if (pNtwks.size() > 1) {
            if (tag == null) {
                throw new InvalidParameterValueException("More than one physical networks exist in zone id=" + zoneId + " and no tags are specified in order to make a choice");
            }

            Long pNtwkId = null;
            for (PhysicalNetwork pNtwk : pNtwks) {
                if (pNtwk.getTags().contains(tag)) {
                    s_logger.debug("Found physical network id=" + pNtwk.getId() + " based on requested tags " + tag);
                    pNtwkId = pNtwk.getId();
                    break;
                }
            }
            if (pNtwkId == null) {
                throw new InvalidParameterValueException("Unable to find physical network which match the tags " + tag);
            }
            return pNtwkId;
        } else {
            return pNtwks.get(0).getId();
        }
    }

};