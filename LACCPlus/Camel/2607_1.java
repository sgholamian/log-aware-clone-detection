//,temp,FtpConsumer.java,124,219,temp,SftpConsumer.java,115,211
//,3
public class xxx {
    @Override
    protected boolean doPollDirectory(String absolutePath, String dirName, List<GenericFile<FTPFile>> fileList, int depth) {
        LOG.trace("doPollDirectory from absolutePath: {}, dirName: {}", absolutePath, dirName);

        depth++;

        // remove trailing /
        dirName = FileUtil.stripTrailingSeparator(dirName);

        // compute dir depending on stepwise is enabled or not
        String dir = null;
        List<FTPFile> files = null;
        try {
            if (isStepwise()) {
                dir = ObjectHelper.isNotEmpty(dirName) ? dirName : absolutePath;
                operations.changeCurrentDirectory(dir);
            } else {
                dir = absolutePath;
            }

            LOG.trace("Polling directory: {}", dir);
            if (isUseList()) {
                if (isStepwise()) {
                    files = operations.listFiles();
                } else {
                    files = operations.listFiles(dir);
                }
            } else {
                // we cannot use the LIST command(s) so we can only poll a named
                // file so created a pseudo file with that name
                Exchange dummy = endpoint.createExchange();
                String name = evaluateFileExpression(dummy);
                if (name != null) {
                    FTPFile file = new FTPFile();
                    file.setType(FTPFile.FILE_TYPE);
                    file.setName(name);
                    files = new ArrayList<>(1);
                    files.add(file);
                }
            }
        } catch (GenericFileOperationFailedException e) {
            if (ignoreCannotRetrieveFile(null, null, e)) {
                LOG.debug("Cannot list files in directory {} due directory does not exists or file permission error.", dir);
            } else {
                throw e;
            }
        }

        if (files == null || files.isEmpty()) {
            // no files in this directory to poll
            LOG.trace("No files found in directory: {}", dir);
            return true;
        } else {
            // we found some files
            LOG.trace("Found {} in directory: {}", files.size(), dir);
        }

        if (getEndpoint().isPreSort()) {
            Collections.sort(files, (a, b) -> a.getName().compareTo(b.getName()));
        }

        for (FTPFile file : files) {

            if (LOG.isTraceEnabled()) {
                LOG.trace("FtpFile[name={}, dir={}, file={}]", file.getName(), file.isDirectory(), file.isFile());
            }

            // check if we can continue polling in files
            if (!canPollMoreFiles(fileList)) {
                return false;
            }

            if (file.isDirectory()) {
                RemoteFile<FTPFile> remote = asRemoteFile(absolutePath, file, getEndpoint().getCharset());
                if (endpoint.isRecursive() && depth < endpoint.getMaxDepth() && isValidFile(remote, true, files)) {
                    // recursive scan and add the sub files and folders
                    String subDirectory = file.getName();
                    String path = ObjectHelper.isNotEmpty(absolutePath) ? absolutePath + "/" + subDirectory : subDirectory;
                    boolean canPollMore = pollSubDirectory(path, subDirectory, fileList, depth);
                    if (!canPollMore) {
                        return false;
                    }
                }
            } else if (file.isFile()) {
                RemoteFile<FTPFile> remote = asRemoteFile(absolutePath, file, getEndpoint().getCharset());
                if (depth >= endpoint.getMinDepth() && isValidFile(remote, false, files)) {
                    // matched file so add
                    fileList.add(remote);
                }
            } else {
                LOG.debug("Ignoring unsupported remote file type: {}", file);
            }
        }

        return true;
    }

};