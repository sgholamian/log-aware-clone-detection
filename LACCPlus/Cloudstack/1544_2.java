//,temp,BridgeVifDriver.java,327,382,temp,IvsVifDriver.java,199,254
//,2
public class xxx {
    private void deleteVnetBr(String brName) {
        synchronized (_vnetBridgeMonitor) {
            String cmdout = Script.runSimpleBashScript("ls /sys/class/net/" + brName);
            if (cmdout == null)
                // Bridge does not exist
                return;
            cmdout = Script.runSimpleBashScript("ls /sys/class/net/" + brName + "/brif | tr '\n' ' '");
            if (cmdout != null && cmdout.contains("vnet")) {
                // Active VM remains on that bridge
                return;
            }

            Pattern oldStyleBrNameRegex = Pattern.compile("^cloudVirBr(\\d+)$");
            Pattern brNameRegex = Pattern.compile("^br(\\S+)-(\\d+)$");
            Matcher oldStyleBrNameMatcher = oldStyleBrNameRegex.matcher(brName);
            Matcher brNameMatcher = brNameRegex.matcher(brName);

            String pName = null;
            String vNetId = null;
            if (oldStyleBrNameMatcher.find()) {
                // Actually modifyvlan.sh doesn't require pif name when deleting its bridge so far.
                pName = "undefined";
                vNetId = oldStyleBrNameMatcher.group(1);
            } else if (brNameMatcher.find()) {
                if (brNameMatcher.group(1) != null || !brNameMatcher.group(1).isEmpty()) {
                    pName = brNameMatcher.group(1);
                } else {
                    pName = "undefined";
                }
                vNetId = brNameMatcher.group(2);
            }

            if (vNetId == null || vNetId.isEmpty()) {
                s_logger.debug("unable to get a vNet ID from name " + brName);
                return;
            }

            String scriptPath = null;
            if (cmdout != null && cmdout.contains("vxlan")) {
                scriptPath = _modifyVxlanPath;
            } else {
                scriptPath = _modifyVlanPath;
            }

            final Script command = new Script(scriptPath, _timeout, s_logger);
            command.add("-o", "delete");
            command.add("-v", vNetId);
            command.add("-p", pName);
            command.add("-b", brName);

            final String result = command.execute();
            if (result != null) {
                s_logger.debug("Delete bridge " + brName + " failed: " + result);
            }
        }
    }

};