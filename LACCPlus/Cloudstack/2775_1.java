//,temp,CitrixCheckNetworkCommandWrapper.java,40,95,temp,OvmResourceBase.java,1109,1145
//,3
public class xxx {
    @Override
    public Answer execute(final CheckNetworkCommand command, final CitrixResourceBase citrixResourceBase) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Checking if network name setup is done on the resource");
        }

        final List<PhysicalNetworkSetupInfo> infoList = command.getPhysicalNetworkInfoList();

        try {
            boolean errorout = false;
            String msg = "";
            for (final PhysicalNetworkSetupInfo info : infoList) {
                if (!citrixResourceBase.isNetworkSetupByName(info.getGuestNetworkName())) {
                    msg =
                            "For Physical Network id:" + info.getPhysicalNetworkId() + ", Guest Network is not configured on the backend by name " +
                                    info.getGuestNetworkName();
                    errorout = true;
                    break;
                }
                if (!citrixResourceBase.isNetworkSetupByName(info.getPrivateNetworkName())) {
                    msg =
                            "For Physical Network id:" + info.getPhysicalNetworkId() + ", Private Network is not configured on the backend by name " +
                                    info.getPrivateNetworkName();
                    errorout = true;
                    break;
                }
                if (!citrixResourceBase.isNetworkSetupByName(info.getPublicNetworkName())) {
                    msg =
                            "For Physical Network id:" + info.getPhysicalNetworkId() + ", Public Network is not configured on the backend by name " +
                                    info.getPublicNetworkName();
                    errorout = true;
                    break;
                }
                /*if(!isNetworkSetupByName(info.getStorageNetworkName())){
                    msg = "For Physical Network id:"+ info.getPhysicalNetworkId() + ", Storage Network is not configured on the backend by name " + info.getStorageNetworkName();
                    errorout = true;
                    break;
                }*/
            }
            if (errorout) {
                s_logger.error(msg);
                return new CheckNetworkAnswer(command, false, msg);
            } else {
                return new CheckNetworkAnswer(command, true, "Network Setup check by names is done");
            }

        } catch (final XenAPIException e) {
            final String msg = "CheckNetworkCommand failed with XenAPIException:" + e.toString() + " host:" + citrixResourceBase.getHost().getUuid();
            s_logger.warn(msg, e);
            return new CheckNetworkAnswer(command, false, msg);
        } catch (final Exception e) {
            final String msg = "CheckNetworkCommand failed with Exception:" + e.getMessage() + " host:" + citrixResourceBase.getHost().getUuid();
            s_logger.warn(msg, e);
            return new CheckNetworkAnswer(command, false, msg);
        }
    }

};