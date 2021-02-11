//,temp,PaloAltoResource.java,1556,1612,temp,PaloAltoResource.java,1435,1494
//,3
public class xxx {
    public boolean manageService(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim, String protocol, String dstPorts, String srcPorts)
        throws ExecutionException {
        String serviceName = genServiceName(protocol, dstPorts, srcPorts);

        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/service/entry[@name='" + serviceName + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Service exists: " + serviceName + ", " + result);
                return result;

            case ADD:
                if (manageService(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, protocol, dstPorts, srcPorts)) {
                    return true;
                }

                String dstPortXML = "<port>" + dstPorts + "</port>";
                String srcPortXML = "";
                if (srcPorts != null) {
                    srcPortXML = "<source-port>" + srcPorts + "</source-port>";
                }

                // add ping profile...
                Map<String, String> a_params = new HashMap<String, String>();
                a_params.put("type", "config");
                a_params.put("action", "set");
                a_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/service/entry[@name='" + serviceName + "']");
                a_params.put("element", "<protocol><" + protocol.toLowerCase() + ">" + dstPortXML + srcPortXML + "</" + protocol.toLowerCase() + "></protocol>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_params));

                return true;

            case DELETE:
                if (!manageService(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, protocol, dstPorts, srcPorts)) {
                    return true;
                }

                // delete ping profile...
                Map<String, String> d_params = new HashMap<String, String>();
                d_params.put("type", "config");
                d_params.put("action", "delete");
                d_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/service/entry[@name='" + serviceName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};