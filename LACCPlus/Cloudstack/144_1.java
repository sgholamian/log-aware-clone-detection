//,temp,VmwareStorageProcessor.java,3207,3219,temp,IscsiAdmStorageAdaptor.java,264,276
//,3
public class xxx {
    private static String trimIqn(String iqn) {
        String[] tmp = iqn.split("/");

        if (tmp.length != 3) {
            String msg = "Wrong format for iSCSI path: " + iqn + ". It should be formatted as '/targetIQN/LUN'.";

            s_logger.warn(msg);

            throw new CloudRuntimeException(msg);
        }

        return tmp[1].trim();
    }

};