//,temp,AtmosAPIFacade.java,79,157,temp,DropboxAPIFacade.java,106,180
//,3
public class xxx {
    private DropboxFileUploadResult putFile(String localPath, DropboxUploadMode mode, String dropboxPath, boolean isPresent)
            throws DropboxException {
        File fileLocalPath = new File(localPath);
        // verify uploading of a single file
        if (fileLocalPath.isFile()) {
            // check if dropbox file exists
            if (isPresent && !DropboxUploadMode.force.equals(mode)) {
                throw new DropboxException(dropboxPath + " exists on dropbox. Use force upload mode to override");
            }
            // in case the entry not exists on dropbox check if the filename
            // should be appended
            if (!isPresent) {
                if (dropboxPath.endsWith(DropboxConstants.DROPBOX_FILE_SEPARATOR)) {
                    dropboxPath = dropboxPath + fileLocalPath.getName();
                }
            }

            LOG.debug("Uploading: {},{}", fileLocalPath, dropboxPath);
            DropboxFileUploadResult result;
            try {
                FileMetadata uploadedFile = putSingleFile(fileLocalPath, dropboxPath, mode);
                if (uploadedFile == null) {
                    result = new DropboxFileUploadResult(dropboxPath, DropboxResultCode.KO);
                } else {
                    result = new DropboxFileUploadResult(dropboxPath, DropboxResultCode.OK);
                }
            } catch (Exception ex) {
                result = new DropboxFileUploadResult(dropboxPath, DropboxResultCode.KO);
            }
            return result;
        } else if (fileLocalPath.isDirectory()) {
            // verify uploading of a list of files inside a dir
            LOG.debug("Uploading a dir...");
            // check if dropbox folder exists
            if (isPresent && !DropboxUploadMode.force.equals(mode)) {
                throw new DropboxException(dropboxPath + " exists on dropbox and is not a folder!");
            }
            if (!dropboxPath.endsWith(DropboxConstants.DROPBOX_FILE_SEPARATOR)) {
                dropboxPath = dropboxPath + DropboxConstants.DROPBOX_FILE_SEPARATOR;
            }
            // revert to old path
            String oldDropboxPath = dropboxPath;
            // list all files in a dir
            Collection<File> listFiles = FileUtils.listFiles(fileLocalPath, null, true);
            if (listFiles.isEmpty()) {
                throw new DropboxException(localPath + " does not contain any files");
            }

            HashMap<String, DropboxResultCode> resultMap = new HashMap<>(listFiles.size());
            for (File file : listFiles) {
                String absPath = file.getAbsolutePath();
                int indexRemainingPath = localPath.length();
                if (!localPath.endsWith("/")) {
                    indexRemainingPath += 1;
                }
                String remainingPath = absPath.substring(indexRemainingPath);
                dropboxPath = dropboxPath + remainingPath;
                try {
                    LOG.debug("Uploading: {},{}", fileLocalPath, dropboxPath);
                    FileMetadata uploadedFile = putSingleFile(file, dropboxPath, mode);
                    if (uploadedFile == null) {
                        resultMap.put(dropboxPath, DropboxResultCode.KO);
                    } else {
                        resultMap.put(dropboxPath, DropboxResultCode.OK);
                    }
                } catch (Exception ex) {
                    resultMap.put(dropboxPath, DropboxResultCode.KO);
                }
                dropboxPath = oldDropboxPath;
            }
            return new DropboxFileUploadResult(resultMap);
        } else {
            return null;
        }
    }

};