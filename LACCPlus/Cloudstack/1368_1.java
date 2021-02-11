//,temp,PaloAltoResource.java,781,863,temp,PaloAltoResource.java,668,771
//,3
public class xxx {
    public boolean managePublicInterface(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim, Long publicVlanTag, String publicIp, long privateVlanTag)
        throws ExecutionException {
        String interfaceName;
        if (publicVlanTag == null) {
            interfaceName = genPublicInterfaceName(new Long("9999"));
        } else {
            interfaceName = genPublicInterfaceName(publicVlanTag);
        }

        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/network/interface/" + _publicInterfaceType + "/entry[@name='" + _publicInterface +
                    "']/layer3/units/entry[@name='" + interfaceName + "']/ip/entry[@name='" + publicIp + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Public sub-interface & IP exists: " + interfaceName + " : " + publicIp + ", " + result);
                return result;

            case ADD:
                if (managePublicInterface(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, publicVlanTag, publicIp, privateVlanTag)) {
                    return true;
                }

                // add IP to the sub-interface
                Map<String, String> a_sub_params = new HashMap<String, String>();
                a_sub_params.put("type", "config");
                a_sub_params.put("action", "set");
                a_sub_params.put("xpath", "/config/devices/entry/network/interface/" + _publicInterfaceType + "/entry[@name='" + _publicInterface +
                    "']/layer3/units/entry[@name='" + interfaceName + "']/ip");
                a_sub_params.put("element", "<entry name='" + publicIp + "'/>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_sub_params));

                // add sub-interface to VR (does nothing if already done)...
                Map<String, String> a_vr_params = new HashMap<String, String>();
                a_vr_params.put("type", "config");
                a_vr_params.put("action", "set");
                a_vr_params.put("xpath", "/config/devices/entry/network/virtual-router/entry[@name='" + _virtualRouter + "']/interface");
                a_vr_params.put("element", "<member>" + interfaceName + "</member>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_vr_params));

                // add sub-interface to vsys (does nothing if already done)...
                Map<String, String> a_vsys_params = new HashMap<String, String>();
                a_vsys_params.put("type", "config");
                a_vsys_params.put("action", "set");
                a_vsys_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/import/network/interface");
                a_vsys_params.put("element", "<member>" + interfaceName + "</member>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_vsys_params));

                // add sub-interface to zone (does nothing if already done)...
                Map<String, String> a_zone_params = new HashMap<String, String>();
                a_zone_params.put("type", "config");
                a_zone_params.put("action", "set");
                a_zone_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/zone/entry[@name='" + _publicZone + "']/network/layer3");
                a_zone_params.put("element", "<member>" + interfaceName + "</member>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_zone_params));

                return true;

            case DELETE:
                if (!managePublicInterface(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, publicVlanTag, publicIp, privateVlanTag)) {
                    return true;
                }

                // delete IP from sub-interface...
                Map<String, String> d_sub_params = new HashMap<String, String>();
                d_sub_params.put("type", "config");
                d_sub_params.put("action", "delete");
                d_sub_params.put("xpath", "/config/devices/entry/network/interface/" + _publicInterfaceType + "/entry[@name='" + _publicInterface +
                    "']/layer3/units/entry[@name='" + interfaceName + "']/ip/entry[@name='" + publicIp + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_sub_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};