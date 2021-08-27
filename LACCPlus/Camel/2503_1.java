//,temp,FtpOperations.java,706,788,temp,SftpOperations.java,967,1048
//,3
public class xxx {
    private boolean doStoreFile(String name, String targetName, Exchange exchange) throws GenericFileOperationFailedException {
        log.trace("doStoreFile({})", targetName);

        // if an existing file already exists what should we do?
        if (endpoint.getFileExist() == GenericFileExist.Ignore || endpoint.getFileExist() == GenericFileExist.Fail
                || endpoint.getFileExist() == GenericFileExist.Move) {
            boolean existFile = existsFile(targetName);
            if (existFile && endpoint.getFileExist() == GenericFileExist.Ignore) {
                // ignore but indicate that the file was written
                log.trace("An existing file already exists: {}. Ignore and do not override it.", name);
                return true;
            } else if (existFile && endpoint.getFileExist() == GenericFileExist.Fail) {
                throw new GenericFileOperationFailedException("File already exist: " + name + ". Cannot write new file.");
            } else if (existFile && endpoint.getFileExist() == GenericFileExist.Move) {
                // move any existing file first
                this.endpoint.getMoveExistingFileStrategy().moveExistingFile(endpoint, this, targetName);
            }
        }

        InputStream is = null;
        if (exchange.getIn().getBody() == null) {
            // Do an explicit test for a null body and decide what to do
            if (endpoint.isAllowNullBody()) {
                log.trace("Writing empty file.");
                is = new ByteArrayInputStream(new byte[] {});
            } else {
                throw new GenericFileOperationFailedException("Cannot write null body to file: " + name);
            }
        }

        try {
            if (is == null) {
                String charset = endpoint.getCharset();
                if (charset != null) {
                    // charset configured so we must convert to the desired
                    // charset so we can write with encoding
                    is = new ByteArrayInputStream(exchange.getIn().getMandatoryBody(String.class).getBytes(charset));
                    log.trace("Using InputStream {} with charset {}.", is, charset);
                } else {
                    is = exchange.getIn().getMandatoryBody(InputStream.class);
                }
            }

            final StopWatch watch = new StopWatch();
            boolean answer;
            log.debug("About to store file: {} using stream: {}", targetName, is);
            if (endpoint.getFileExist() == GenericFileExist.Append) {
                log.trace("Client appendFile: {}", targetName);
                answer = client.appendFile(targetName, is);
            } else {
                log.trace("Client storeFile: {}", targetName);
                answer = client.storeFile(targetName, is);
            }
            if (log.isDebugEnabled()) {
                long time = watch.taken();
                log.debug("Took {} ({} millis) to store file: {} and FTP client returned: {}",
                        TimeUtils.printDuration(time), time, targetName, answer);
            }

            // store client reply information after the operation
            exchange.getIn().setHeader(FtpConstants.FTP_REPLY_CODE, client.getReplyCode());
            exchange.getIn().setHeader(FtpConstants.FTP_REPLY_STRING, client.getReplyString());

            // after storing file, we may set chmod on the file
            String chmod = endpoint.getConfiguration().getChmod();
            if (org.apache.camel.util.ObjectHelper.isNotEmpty(chmod)) {
                log.debug("Setting chmod: {} on file: {}", chmod, targetName);
                String command = "chmod " + chmod + " " + targetName;
                log.trace("Client sendSiteCommand: {}", command);
                boolean success = client.sendSiteCommand(command);
                log.trace("Client sendSiteCommand successful: {}", success);
            }

            return answer;

        } catch (IOException e) {
            throw new GenericFileOperationFailedException(client.getReplyCode(), client.getReplyString(), e.getMessage(), e);
        } catch (InvalidPayloadException e) {
            throw new GenericFileOperationFailedException("Cannot store file: " + name, e);
        } finally {
            IOHelper.close(is, "store: " + name, log);
        }
    }

};