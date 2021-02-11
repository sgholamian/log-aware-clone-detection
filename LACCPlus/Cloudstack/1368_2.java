//,temp,PaloAltoResource.java,781,863,temp,PaloAltoResource.java,668,771
//,3
public class xxx {
    public boolean managePrivateInterface(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim, long privateVlanTag, String privateGateway)
        throws ExecutionException {
        String interfaceName = genPrivateInterfaceName(privateVlanTag);

        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/network/interface/" + _privateInterfaceType + "/entry[@name='" + _privateInterface +
                    "']/layer3/units/entry[@name='" + interfaceName + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Private sub-interface exists: " + interfaceName + ", " + result);
                return result;

            case ADD:
                if (managePrivateInterface(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, privateVlanTag, privateGateway)) {
                    return true;
                }

                // add cmds
                // add sub-interface
                Map<String, String> a_sub_params = new HashMap<String, String>();
                a_sub_params.put("type", "config");
                a_sub_params.put("action", "set");
                a_sub_params.put("xpath", "/config/devices/entry/network/interface/" + _privateInterfaceType + "/entry[@name='" + _privateInterface +
                    "']/layer3/units/entry[@name='" + interfaceName + "']");
                a_sub_params.put("element", "<tag>" + privateVlanTag + "</tag><ip><entry name='" + privateGateway + "'/></ip><interface-management-profile>" +
                    _pingManagementProfile + "</interface-management-profile>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_sub_params));

                // add sub-interface to VR...
                Map<String, String> a_vr_params = new HashMap<String, String>();
                a_vr_params.put("type", "config");
                a_vr_params.put("action", "set");
                a_vr_params.put("xpath", "/config/devices/entry/network/virtual-router/entry[@name='" + _virtualRouter + "']/interface");
                a_vr_params.put("element", "<member>" + interfaceName + "</member>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_vr_params));

                // add sub-interface to vsys...
                Map<String, String> a_vsys_params = new HashMap<String, String>();
                a_vsys_params.put("type", "config");
                a_vsys_params.put("action", "set");
                a_vsys_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/import/network/interface");
                a_vsys_params.put("element", "<member>" + interfaceName + "</member>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_vsys_params));

                // add sub-interface to zone...
                Map<String, String> a_zone_params = new HashMap<String, String>();
                a_zone_params.put("type", "config");
                a_zone_params.put("action", "set");
                a_zone_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/zone/entry[@name='" + _privateZone + "']/network/layer3");
                a_zone_params.put("element", "<member>" + interfaceName + "</member>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_zone_params));

                return true;

            case DELETE:
                if (!managePrivateInterface(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, privateVlanTag, privateGateway)) {
                    return true;
                }

                // add cmds to the list
                // delete sub-interface from zone...
                Map<String, String> d_zone_params = new HashMap<String, String>();
                d_zone_params.put("type", "config");
                d_zone_params.put("action", "delete");
                d_zone_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/zone/entry[@name='" + _privateZone + "']/network/layer3/member[text()='" +
                    interfaceName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_zone_params));

                // delete sub-interface from vsys...
                Map<String, String> d_vsys_params = new HashMap<String, String>();
                d_vsys_params.put("type", "config");
                d_vsys_params.put("action", "delete");
                d_vsys_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/import/network/interface/member[text()='" + interfaceName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_vsys_params));

                // delete sub-interface from VR...
                Map<String, String> d_vr_params = new HashMap<String, String>();
                d_vr_params.put("type", "config");
                d_vr_params.put("action", "delete");
                d_vr_params.put("xpath", "/config/devices/entry/network/virtual-router/entry[@name='" + _virtualRouter + "']/interface/member[text()='" + interfaceName +
                    "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_vr_params));

                // delete sub-interface...
                Map<String, String> d_sub_params = new HashMap<String, String>();
                d_sub_params.put("type", "config");
                d_sub_params.put("action", "delete");
                d_sub_params.put("xpath", "/config/devices/entry/network/interface/" + _privateInterfaceType + "/entry[@name='" + _privateInterface +
                    "']/layer3/units/entry[@name='" + interfaceName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_sub_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};