//,temp,GenericFileDefaultMoveExistingFileStrategy.java,37,98,temp,FtpDefaultMoveExistingFileStrategy.java,41,109
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
        String parent = FileUtil.onlyPath(fileName);
        String onlyName = FileUtil.stripPath(fileName);
        dummy.getIn().setHeader(Exchange.FILE_NAME, fileName);
        dummy.getIn().setHeader(Exchange.FILE_NAME_ONLY, onlyName);
        dummy.getIn().setHeader(Exchange.FILE_PARENT, parent);

        String to = endpoint.getMoveExisting().evaluate(dummy, String.class);

        if (ObjectHelper.isEmpty(to)) {
            throw new GenericFileOperationFailedException(
                    "moveExisting evaluated as empty String, cannot move existing file: " + fileName);
        }

        to = completePartialRelativePath(to, onlyName, parent);

        // we must normalize it (to avoid having both \ and / in the name which
        // confuses java.io.File)
        to = FileUtil.normalizePath(to);

        // ensure any paths is created before we rename as the renamed file may
        // be in a different path (which may be non exiting)
        // use java.io.File to compute the file path
        File toFile = new File(to);
        String directory = toFile.getParent();
        boolean absolute = FileUtil.isAbsolute(toFile);
        if (directory != null) {
            if (!operations.buildDirectory(directory, absolute)) {
                LOG.debug("Cannot build directory [{}] (could be because of denied permissions)", directory);
            }
        }

        // deal if there already exists a file
        if (operations.existsFile(to)) {
            if (endpoint.isEagerDeleteTargetFile()) {
                LOG.trace("Deleting existing file: {}", to);
                if (!operations.deleteFile(to)) {
                    throw new GenericFileOperationFailedException("Cannot delete file: " + to);
                }
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