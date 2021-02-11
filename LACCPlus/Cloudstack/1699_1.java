//,temp,VmwareStorageProcessor.java,1495,1513,temp,LibvirtComputingResource.java,1390,1401
//,3
public class xxx {
    private void createTemplateFolder(String installPath, String installFullPath, NfsTO nfsSvr) {
        synchronized (installPath.intern()) {
            Script command = new Script(false, "mkdir", _timeout, s_logger);

            command.add("-p");
            command.add(installFullPath);

            String result = command.execute();

            if (result != null) {
                String secStorageUrl = nfsSvr.getUrl();
                String msg = "unable to prepare template directory: " + installPath + "; storage: " + secStorageUrl + "; error msg: " + result;

                s_logger.error(msg);

                throw new CloudRuntimeException(msg);
            }
        }
    }

};