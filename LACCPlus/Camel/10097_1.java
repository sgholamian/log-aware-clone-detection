//,temp,AtmosAPIFacade.java,79,157,temp,DropboxAPIFacade.java,106,180
//,3
public class xxx {
    public AtmosResult put(String localPath, String remotePath) throws AtmosException {
        AtmosResult result = new AtmosFileUploadResult();
        //a map representing for each path the result of the put operation
        Map<String, AtmosResultCode> resultEntries = null;
        //in case the remote path is not specified, the remotePath = localPath
        String atmosPath = remotePath == null ? localPath : remotePath;
        if (!atmosPath.endsWith(ATMOS_FILE_SEPARATOR)) {
            atmosPath += ATMOS_FILE_SEPARATOR;
        }
        ObjectPath atmosEntry = new ObjectPath(atmosPath);

        if (!atmosPath.equals(ATMOS_FILE_SEPARATOR)) {
            if (AtmosAPIFacade.client.getSystemMetadata(atmosEntry) == null) {
                throw new AtmosException(atmosPath + " does not exist or cannot obtain metadata");
            }
        }

        File fileLocalPath = new File(localPath);
        //verify uploading of a single file
        if (fileLocalPath.isFile()) {
            //check if atmos file exists
            if (!atmosEntry.isDirectory()) {
                throw new AtmosException(atmosPath + " exists on atmos and is not a folder!");
            }
            atmosPath = atmosPath + fileLocalPath.getName();
            resultEntries = new HashMap<>(1);
            try {
                ObjectId uploadedFile = putSingleFile(fileLocalPath, atmosPath);
                if (uploadedFile == null) {
                    resultEntries.put(atmosPath, AtmosResultCode.KO);
                } else {
                    resultEntries.put(atmosPath, AtmosResultCode.OK);
                }

            } catch (Exception ex) {
                resultEntries.put(atmosPath, AtmosResultCode.KO);
            } finally {
                result.setResultEntries(resultEntries);
            }
            return result;
        } else {       //verify uploading of a list of files inside a dir
            LOG.info("uploading a dir...");
            //check if atmos folder exists
            if (!atmosEntry.isDirectory()) {
                throw new AtmosException(atmosPath + " exists on atmos and is not a folder!");
            }
            //revert to old path
            String oldAtmosPath = atmosPath;
            //list all files in a dir
            Collection<File> listFiles = FileUtils.listFiles(fileLocalPath, null, true);
            if (listFiles == null || listFiles.isEmpty()) {
                throw new AtmosException(localPath + " does not contain any files");
            }
            resultEntries = new HashMap<>(listFiles.size());
            for (File file : listFiles) {
                String absPath = file.getAbsolutePath();
                int indexRemainingPath = localPath.length();
                if (!localPath.endsWith("/")) {
                    indexRemainingPath += 1;
                }
                String remainingPath = absPath.substring(indexRemainingPath);
                atmosPath = atmosPath + remainingPath;
                try {
                    LOG.debug("uploading: {} to {}", fileLocalPath, atmosPath);
                    ObjectId uploadedFile = putSingleFile(file, atmosPath);
                    if (uploadedFile == null) {
                        resultEntries.put(atmosPath, AtmosResultCode.KO);
                    } else {
                        resultEntries.put(atmosPath, AtmosResultCode.OK);
                    }
                } catch (Exception ex) {
                    resultEntries.put(atmosPath, AtmosResultCode.KO);
                }
                atmosPath = oldAtmosPath;
            }
            result.setResultEntries(resultEntries);
            return result;
        }
    }

};