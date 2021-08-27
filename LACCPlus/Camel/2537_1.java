//,temp,FtpChangedExclusiveReadLockStrategy.java,50,150,temp,SftpChangedExclusiveReadLockStrategy.java,51,149
//,3
public class xxx {
    @Override
    public boolean acquireExclusiveReadLock(
            GenericFileOperations<FTPFile> operations, GenericFile<FTPFile> file, Exchange exchange)
            throws Exception {
        boolean exclusive = false;

        LOG.trace("Waiting for exclusive read lock to file: {}", file);

        long lastModified = Long.MIN_VALUE;
        long length = Long.MIN_VALUE;
        StopWatch watch = new StopWatch();
        long startTime = new Date().getTime();

        while (!exclusive) {
            // timeout check
            if (timeout > 0) {
                long delta = watch.taken();
                if (delta > timeout) {
                    CamelLogger.log(LOG, readLockLoggingLevel,
                            "Cannot acquire read lock within " + timeout + " millis. Will skip the file: " + file);
                    // we could not get the lock within the timeout period, so
                    // return false
                    return false;
                }
            }

            long newLastModified = 0;
            long newLength = 0;

            List<FTPFile> files;
            if (fastExistsCheck) {
                // use the absolute file path to only pickup the file we want to
                // check, this avoids expensive
                // list operations if we have a lot of files in the directory
                String path = file.getAbsoluteFilePath();
                if (path.equals("/") || path.equals("\\")) {
                    // special for root (= home) directory
                    LOG.trace("Using fast exists to update file information in home directory");
                    files = operations.listFiles();
                } else {
                    LOG.trace("Using fast exists to update file information for {}", path);
                    files = operations.listFiles(path);
                }
            } else {
                // fast option not enabled, so list the directory and filter the
                // file name
                String path = file.getParent();
                if (path.equals("/") || path.equals("\\")) {
                    // special for root (= home) directory
                    LOG.trace(
                            "Using full directory listing in home directory to update file information. Consider enabling fastExistsCheck option.");
                    files = operations.listFiles();
                } else {
                    LOG.trace(
                            "Using full directory listing to update file information for {}. Consider enabling fastExistsCheck option.",
                            path);
                    files = operations.listFiles(path);
                }
            }
            LOG.trace("List files {} found {} files", file.getAbsoluteFilePath(), files.size());
            for (FTPFile f : files) {
                boolean match;
                if (fastExistsCheck) {
                    // uses the absolute file path as well
                    match = f.getName().equals(file.getAbsoluteFilePath()) || f.getName().equals(file.getFileNameOnly());
                } else {
                    match = f.getName().equals(file.getFileNameOnly());
                }
                if (match) {
                    newLength = f.getSize();
                    if (f.getTimestamp() != null) {
                        newLastModified = f.getTimestamp().getTimeInMillis();
                    }
                }
            }

            LOG.trace("Previous last modified: {}, new last modified: {}", lastModified, newLastModified);
            LOG.trace("Previous length: {}, new length: {}", length, newLength);
            long newOlderThan = startTime + watch.taken() - minAge;
            LOG.trace("New older than threshold: {}", newOlderThan);

            if (newLength >= minLength && ((minAge == 0 && newLastModified == lastModified && newLength == length)
                    || (minAge != 0 && newLastModified < newOlderThan))) {
                LOG.trace("Read lock acquired.");
                exclusive = true;
            } else {
                // set new base file change information
                lastModified = newLastModified;
                length = newLength;

                boolean interrupted = sleep();
                if (interrupted) {
                    // we were interrupted while sleeping, we are likely being
                    // shutdown so return false
                    return false;
                }
            }
        }

        return exclusive;
    }

};