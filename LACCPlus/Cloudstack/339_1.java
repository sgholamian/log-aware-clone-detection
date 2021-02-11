//,temp,VmwareResource.java,6044,6084,temp,HypervDirectConnectResource.java,2344,2384
//,3
public class xxx {
    protected String connect(final String vmName, final String ipAddress, final int port) {
        long startTick = System.currentTimeMillis();

        // wait until we have at least been waiting for _ops_timeout time or
        // at least have tried _retry times, this is to coordinate with system
        // VM patching/rebooting time that may need
        int retry = _retry;
        while (System.currentTimeMillis() - startTick <= _opsTimeout || --retry > 0) {
            s_logger.info("Trying to connect to " + ipAddress);
            try (SocketChannel sch = SocketChannel.open();) {
                sch.configureBlocking(true);
                sch.socket().setSoTimeout(5000);

                InetSocketAddress addr = new InetSocketAddress(ipAddress, port);
                sch.connect(addr);
                return null;
            } catch (IOException e) {
                s_logger.info("Could not connect to " + ipAddress + " due to " + e.toString());
                if (e instanceof ConnectException) {
                    // if connection is refused because of VM is being started,
                    // we give it more sleep time
                    // to avoid running out of retry quota too quickly
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        s_logger.debug("[ignored] interupted while waiting to retry connect after failure.", e);
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                s_logger.debug("[ignored] interupted while waiting to retry connect.");
            }
        }

        s_logger.info("Unable to logon to " + ipAddress);

        return "Unable to connect";
    }

};