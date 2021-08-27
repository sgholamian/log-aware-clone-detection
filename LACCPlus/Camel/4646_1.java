//,temp,FileStateRepository.java,147,178,temp,FileIdempotentRepository.java,315,342
//,3
public class xxx {
    private void appendToStore(String key, String value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Appending {}={} to state filestore: {}", key, value, fileStore);
        }
        FileOutputStream fos = null;
        try {
            // create store parent directory if missing
            File storeParentDirectory = fileStore.getParentFile();
            if (storeParentDirectory != null && !storeParentDirectory.exists()) {
                LOG.info("Parent directory of file store {} doesn't exist. Creating.", fileStore);
                if (fileStore.getParentFile().mkdirs()) {
                    LOG.info("Parent directory of file store {} successfully created.", fileStore);
                } else {
                    LOG.warn("Parent directory of file store {} cannot be created.", fileStore);
                }
            }
            // create store if missing
            if (!fileStore.exists()) {
                FileUtil.createNewFile(fileStore);
            }
            // append to store
            fos = new FileOutputStream(fileStore, true);
            fos.write(key.getBytes());
            fos.write(KEY_VALUE_DELIMITER.getBytes());
            fos.write(value.getBytes());
            fos.write(STORE_DELIMITER.getBytes());
        } catch (IOException e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        } finally {
            IOHelper.close(fos, "Appending to file state repository", LOG);
        }
    }

};