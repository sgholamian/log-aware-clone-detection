//,temp,FileStateRepository.java,204,234,temp,FileIdempotentRepository.java,459,486
//,3
public class xxx {
    protected void loadStore() throws IOException {
        // auto create starting directory if needed
        if (!fileStore.exists()) {
            LOG.debug("Creating filestore: {}", fileStore);
            File parent = fileStore.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            boolean created = FileUtil.createNewFile(fileStore);
            if (!created) {
                throw new IOException("Cannot create filestore: " + fileStore);
            }
        }

        LOG.trace("Loading to 1st level cache from state filestore: {}", fileStore);

        cache.clear();
        try (Scanner scanner = new Scanner(fileStore, null, STORE_DELIMITER)) {
            while (scanner.hasNext()) {
                String line = scanner.next();
                int separatorIndex = line.indexOf(KEY_VALUE_DELIMITER);
                String key = line.substring(0, separatorIndex);
                String value = line.substring(separatorIndex + KEY_VALUE_DELIMITER.length());
                cache.put(key, value);
            }
        } catch (IOException e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        }

        LOG.debug("Loaded {} to the 1st level cache from state filestore: {}", cache.size(), fileStore);
    }

};