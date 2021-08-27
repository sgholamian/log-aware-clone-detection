//,temp,FtpOperations.java,706,788,temp,SftpOperations.java,967,1048
//,3
public class xxx {
    private boolean doStoreFile(String name, String targetName, Exchange exchange) throws GenericFileOperationFailedException {
        LOG.trace("doStoreFile({})", targetName);

        // if an existing file already exists what should we do?
        if (endpoint.getFileExist() == GenericFileExist.Ignore || endpoint.getFileExist() == GenericFileExist.Fail
                || endpoint.getFileExist() == GenericFileExist.Move) {
            boolean existFile = existsFile(targetName);
            if (existFile && endpoint.getFileExist() == GenericFileExist.Ignore) {
                // ignore but indicate that the file was written
                LOG.trace("An existing file already exists: {}. Ignore and do not override it.", name);
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
                LOG.trace("Writing empty file.");
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
                    LOG.trace("Using InputStream {} with charset {}.", is, charset);
                } else {
                    is = exchange.getIn().getMandatoryBody(InputStream.class);
                }
            }

            final StopWatch watch = new StopWatch();
            LOG.debug("About to store file: {} using stream: {}", targetName, is);
            if (endpoint.getFileExist() == GenericFileExist.Append) {
                LOG.trace("Client appendFile: {}", targetName);
                channel.put(is, targetName, ChannelSftp.APPEND);
            } else {
                LOG.trace("Client storeFile: {}", targetName);
                // override is default
                channel.put(is, targetName);
            }
            if (LOG.isDebugEnabled()) {
                long time = watch.taken();
                LOG.debug("Took {} ({} millis) to store file: {} and FTP client returned: true",
                        new Object[] { TimeUtils.printDuration(time), time, targetName });
            }

            // after storing file, we may set chmod on the file
            String mode = endpoint.getConfiguration().getChmod();
            if (ObjectHelper.isNotEmpty(mode)) {
                // parse to int using 8bit mode
                int permissions = Integer.parseInt(mode, 8);
                LOG.trace("Setting chmod: {} on file: {}", mode, targetName);
                channel.chmod(permissions, targetName);
            }

            createResultHeadersFromExchange(null, exchange);
            return true;

        } catch (SftpException e) {
            createResultHeadersFromExchange(e, exchange);
            throw new GenericFileOperationFailedException("Cannot store file: " + name, e);
        } catch (InvalidPayloadException e) {
            throw new GenericFileOperationFailedException("Cannot store file: " + name, e);
        } catch (UnsupportedEncodingException e) {
            throw new GenericFileOperationFailedException("Cannot store file: " + name, e);
        } finally {
            IOHelper.close(is, "store: " + name, LOG);
        }
    }

};