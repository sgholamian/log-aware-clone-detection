//,temp,CitrixCheckNetworkCommandWrapper.java,40,95,temp,OvmResourceBase.java,1109,1145
//,3
public class xxx {
    protected CheckNetworkAnswer execute(CheckNetworkCommand cmd) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Checking if network name setup is done on the resource");
        }

        List<PhysicalNetworkSetupInfo> infoList = cmd.getPhysicalNetworkInfoList();

        boolean errorout = false;
        String msg = "";
        for (PhysicalNetworkSetupInfo info : infoList) {
            if (!isNetworkSetupByName(info.getGuestNetworkName())) {
                msg = "For Physical Network id:" + info.getPhysicalNetworkId() + ", Guest Network is not configured on the backend by name " + info.getGuestNetworkName();
                errorout = true;
                break;
            }
            if (!isNetworkSetupByName(info.getPrivateNetworkName())) {
                msg =
                    "For Physical Network id:" + info.getPhysicalNetworkId() + ", Private Network is not configured on the backend by name " +
                        info.getPrivateNetworkName();
                errorout = true;
                break;
            }
            if (!isNetworkSetupByName(info.getPublicNetworkName())) {
                msg =
                    "For Physical Network id:" + info.getPhysicalNetworkId() + ", Public Network is not configured on the backend by name " + info.getPublicNetworkName();
                errorout = true;
                break;
            }
        }

        if (errorout) {
            s_logger.error(msg);
            return new CheckNetworkAnswer(cmd, false, msg);
        } else {
            return new CheckNetworkAnswer(cmd, true, "Network Setup check by names is done");
        }
    }

};