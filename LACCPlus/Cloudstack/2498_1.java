//,temp,LibvirtCheckSshCommandWrapper.java,37,57,temp,CitrixCheckSshCommandWrapper.java,37,64
//,3
public class xxx {
    @Override
    public Answer execute(final CheckSshCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final String vmName = command.getName();
        final String privateIp = command.getIp();
        final int cmdPort = command.getPort();

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Ping command port, " + privateIp + ":" + cmdPort);
        }

        final VirtualRoutingResource virtRouterResource = libvirtComputingResource.getVirtRouterResource();
        if (!virtRouterResource.connect(privateIp, cmdPort)) {
            return new CheckSshAnswer(command, "Can not ping System vm " + vmName + " because of a connection failure");
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Ping command port succeeded for vm " + vmName);
        }

        return new CheckSshAnswer(command);
    }

};