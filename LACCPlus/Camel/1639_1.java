//,temp,FileIdempotentRepository.java,399,444,temp,FileIdempotentRepository.java,344,382
//,3
public class xxx {
    protected synchronized void trunkStore() {
        if (fileStore == null || !fileStore.exists()) {
            return;
        }

        LOG.debug("Trunking: {} oldest entries from idempotent filestore: {}", dropOldestFileStore, fileStore);

        // we need to re-load the entire file and remove the key and then re-write the file
        List<String> lines = new ArrayList<>();

        int count = 0;
        try {
            try (Scanner scanner = new Scanner(fileStore, null, STORE_DELIMITER)) {
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    count++;
                    if (count > dropOldestFileStore) {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        }

        if (!lines.isEmpty()) {
            // rewrite file
            LOG.debug("Rewriting idempotent filestore: {} with {} entries:", fileStore, lines.size());
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
        } else {
            // its a small file so recreate the file
            LOG.debug("Clearing idempotent filestore: {}", fileStore);
            clearStore();
        }
    }

};