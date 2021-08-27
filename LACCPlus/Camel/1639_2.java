//,temp,FileIdempotentRepository.java,399,444,temp,FileIdempotentRepository.java,344,382
//,3
public class xxx {
    protected synchronized void removeFromStore(String key) {
        LOG.debug("Removing: {} from idempotent filestore: {}", key, fileStore);

        // we need to re-load the entire file and remove the key and then re-write the file
        List<String> lines = new ArrayList<>();

        boolean found = false;
        try {
            try (Scanner scanner = new Scanner(fileStore, null, STORE_DELIMITER)) {
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    if (key.equals(line)) {
                        found = true;
                    } else {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        }

        if (found) {
            // rewrite file
            LOG.debug("Rewriting idempotent filestore: {} due to key: {} removed", fileStore, key);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileStore);
                for (String line : lines) {
                    fos.write(line.getBytes());
                    fos.write(STORE_DELIMITER.getBytes());
                }
            } catch (IOException e) {
                throw RuntimeCamelException.wrapRuntimeCamelException(e);
            } finally {
                IOHelper.close(fos, "Rewriting file idempotent repository", LOG);
            }
        }
    }

};