//,temp,FtpsEndpoint.java,170,235,temp,FtpEndpoint.java,146,224
//,3
public class xxx {
    @Override
    public RemoteFileOperations<FTPFile> createRemoteFileOperations() throws Exception {
        // configure ftp client
        FTPClient client = ftpClient;

        if (client == null) {
            // must use a new client if not explicit configured to use a custom
            // client
            client = createFtpClient();
        }

        // use configured buffer size which is larger and therefore faster (as
        // the default is no buffer)
        if (getBufferSize() > 0) {
            client.setBufferSize(getBufferSize());
        }
        // set any endpoint configured timeouts
        if (getConfiguration().getConnectTimeout() > -1) {
            client.setConnectTimeout(getConfiguration().getConnectTimeout());
        }
        if (getConfiguration().getSoTimeout() > -1) {
            soTimeout = getConfiguration().getSoTimeout();
        }
        dataTimeout = getConfiguration().getTimeout();

        if (getConfiguration().getActivePortRange() != null) {
            // parse it as min-max
            String[] parts = getConfiguration().getActivePortRange().split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("The option activePortRange should have syntax: min-max");
            }
            int min = getCamelContext().getTypeConverter().mandatoryConvertTo(int.class, parts[0]);
            int max = getCamelContext().getTypeConverter().mandatoryConvertTo(int.class, parts[1]);
            LOG.debug("Using active port range: {}-{}", min, max);
            client.setActivePortRange(min, max);
        }

        // then lookup ftp client parameters and set those
        if (ftpClientParameters != null) {
            Map<String, Object> localParameters = new HashMap<>(ftpClientParameters);
            // setting soTimeout has to be done later on FTPClient (after it has
            // connected)
            Object timeout = localParameters.remove("soTimeout");
            if (timeout != null) {
                soTimeout = getCamelContext().getTypeConverter().convertTo(int.class, timeout);
            }
            // and we want to keep data timeout so we can log it later
            timeout = localParameters.remove("dataTimeout");
            if (timeout != null) {
                dataTimeout = getCamelContext().getTypeConverter().convertTo(int.class, timeout);
            }
            setProperties(client, localParameters);
        }

        if (ftpClientConfigParameters != null) {
            // client config is optional so create a new one if we have
            // parameter for it
            if (ftpClientConfig == null) {
                ftpClientConfig = new FTPClientConfig();
            }
            Map<String, Object> localConfigParameters = new HashMap<>(ftpClientConfigParameters);
            setProperties(ftpClientConfig, localConfigParameters);
        }

        if (dataTimeout > 0) {
            client.setDataTimeout(dataTimeout);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Created FTPClient[connectTimeout: {}, soTimeout: {}, dataTimeout: {}, bufferSize: {}"
                      + ", receiveDataSocketBufferSize: {}, sendDataSocketBufferSize: {}]: {}",
                    client.getConnectTimeout(), getSoTimeout(), dataTimeout, client.getBufferSize(),
                    client.getReceiveDataSocketBufferSize(), client.getSendDataSocketBufferSize(), client);
        }

        FtpOperations operations = new FtpOperations(client, getFtpClientConfig());
        operations.setEndpoint(this);
        return operations;
    }

};