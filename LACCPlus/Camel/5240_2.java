//,temp,FileProducerMoveExistingStrategyTest.java,69,129,temp,SftpDefaultMoveExistingFileStrategy.java,38,96
//,3
public class xxx {
    @Override
    public boolean moveExistingFile(GenericFileEndpoint endpoint, GenericFileOperations operations, String fileName)
            throws GenericFileOperationFailedException {
        // need to evaluate using a dummy and simulate the file first, to have
        // access to all the file attributes
        // create a dummy exchange as Exchange is needed for expression
        // evaluation
        // we support only the following 3 tokens.
        Exchange dummy = endpoint.createExchange();
        // we only support relative paths for the ftp component, so we strip out
        //any leading separator
        String parent = FileUtil.stripLeadingSeparator(FileUtil.onlyPath(fileName));
        String onlyName = FileUtil.stripPath(fileName);
        dummy.getIn().setHeader(Exchange.FILE_NAME, fileName);
        dummy.getIn().setHeader(Exchange.FILE_NAME_ONLY, onlyName);
        dummy.getIn().setHeader(Exchange.FILE_PARENT, parent);

        String to = endpoint.getMoveExisting().evaluate(dummy, String.class);

        // we only support relative paths for the ftp component, so strip any
        // leading paths
        to = FileUtil.stripLeadingSeparator(to);

        if (ObjectHelper.isEmpty(to)) {
            throw new GenericFileOperationFailedException(
                    "moveExisting evaluated as empty String, cannot move existing file: " + fileName);
        }

        to = completePartialRelativePath(to, onlyName, parent);

        // normalize accordingly to configuration
        to = ((SftpEndpoint) endpoint).getConfiguration().normalizePath(to);

        // do we have a sub directory
        String dir = FileUtil.onlyPath(to);
        if (dir != null) {
            // ensure directory exists
            operations.buildDirectory(dir, false);
        }

        // deal if there already exists a file
        if (operations.existsFile(to)) {
            if (endpoint.isEagerDeleteTargetFile()) {
                LOG.trace("Deleting existing file: {}", to);
                operations.deleteFile(to);
            } else {
                throw new GenericFileOperationFailedException(
                        "Cannot move existing file from: " + fileName + " to: " + to + " as there already exists a file: "
                                                              + to);
            }
        }

        LOG.trace("Moving existing file: {} to: {}", fileName, to);
        if (!operations.renameFile(fileName, to)) {
            throw new GenericFileOperationFailedException("Cannot rename file from: " + fileName + " to: " + to);
        }

        return true;
    }

};