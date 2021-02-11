//,temp,CitrixOvsSetupBridgeCommandWrapper.java,36,46,temp,LibvirtOvsSetupBridgeCommandWrapper.java,35,48
//,3
public class xxx {
    @Override
    public Answer execute(final OvsSetupBridgeCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final boolean findResult = libvirtComputingResource.findOrCreateTunnelNetwork(command.getBridgeName());
        final boolean configResult = libvirtComputingResource.configureTunnelNetwork(command.getNetworkId(), command.getHostId(),
                command.getBridgeName());

        final boolean finalResult = findResult && configResult;

        if (!finalResult) {
            s_logger.debug("::FAILURE:: OVS Bridge was NOT configured properly!");
        }

        return new Answer(command, finalResult, null);
    }

};