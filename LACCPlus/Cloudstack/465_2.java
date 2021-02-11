//,temp,VmwareManagerImpl.java,796,834,temp,HypervManagerImpl.java,224,269
//,3
public class xxx {
    protected String mount(String path, String parent, String scheme, String query) {
        String mountPoint = setupMountPoint(parent);
        if (mountPoint == null) {
            s_logger.warn("Unable to create a mount point");
            return null;
        }

        Script script = null;
        String result = null;
        if (scheme.equals("cifs")) {
            String user = System.getProperty("user.name");
            Script command = new Script(true, "mount", _timeout, s_logger);
            command.add("-t", "cifs");
            command.add(path);
            command.add(mountPoint);

            if (user != null) {
                command.add("-o", "uid=" + user + ",gid=" + user);
            }

            if (query != null) {
                query = query.replace('&', ',');
                command.add("-o", query);
            }

            result = command.execute();
        }

        if (result != null) {
            s_logger.warn("Unable to mount " + path + " due to " + result);
            File file = new File(mountPoint);
            if (file.exists()) {
                file.delete();
            }
            return null;
        }

        // Change permissions for the mountpoint
        script = new Script(true, "chmod", _timeout, s_logger);
        script.add("-R", "777", mountPoint);
        result = script.execute();
        if (result != null) {
            s_logger.warn("Unable to set permissions for " + mountPoint + " due to " + result);
        }
        return mountPoint;
    }

};