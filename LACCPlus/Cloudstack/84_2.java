//,temp,OvsVifDriver.java,69,88,temp,LibvirtComputingResource.java,1258,1277
//,3
public class xxx {
    private void getOvsPifs() {
        final String cmdout = Script.runSimpleBashScript("ovs-vsctl list-br | sed '{:q;N;s/\\n/%/g;t q}'");
        s_logger.debug("cmdout was " + cmdout);
        final List<String> bridges = Arrays.asList(cmdout.split("%"));
        for (final String bridge : bridges) {
            s_logger.debug("looking for pif for bridge " + bridge);
            // String pif = getOvsPif(bridge);
            // Not really interested in the pif name at this point for ovs
            // bridges
            final String pif = bridge;
            if (isPublicBridge(bridge)) {
                _pifs.put("public", pif);
            }
            if (isGuestBridge(bridge)) {
                _pifs.put("private", pif);
            }
            _pifs.put(bridge, pif);
        }
        s_logger.debug("done looking for pifs, no more bridges");
    }

};