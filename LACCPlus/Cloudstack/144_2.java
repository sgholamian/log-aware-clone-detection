//,temp,VmwareStorageProcessor.java,3207,3219,temp,IscsiAdmStorageAdaptor.java,264,276
//,3
public class xxx {
    private static String getComponent(String path, int index) {
        String[] tmp = path.split("/");

        if (tmp.length != 3) {
            String msg = "Wrong format for iScsi path: " + path + ". It should be formatted as '/targetIQN/LUN'.";

            s_logger.warn(msg);

            throw new CloudRuntimeException(msg);
        }

        return tmp[index].trim();
    }

};