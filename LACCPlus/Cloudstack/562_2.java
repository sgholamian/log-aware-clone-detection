//,temp,PaloAltoResource.java,1435,1494,temp,PaloAltoResource.java,873,940
//,3
public class xxx {
    public boolean manageSrcNatRule(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim, GuestNetworkType type, Long publicVlanTag, String publicIp,
        long privateVlanTag, String privateGateway) throws ExecutionException {
        String publicInterfaceName;
        if (publicVlanTag == null) {
            publicInterfaceName = genPublicInterfaceName(new Long("9999"));
        } else {
            publicInterfaceName = genPublicInterfaceName(publicVlanTag);
        }
        String srcNatName = genSrcNatRuleName(privateVlanTag);

        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/nat/rules/entry[@name='" + srcNatName + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Source NAT exists: " + srcNatName + ", " + result);
                return result;

            case ADD:
                if (manageSrcNatRule(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, type, publicVlanTag, publicIp, privateVlanTag, privateGateway)) {
                    return true;
                }

                String xml = "";
                xml += "<from><member>" + _privateZone + "</member></from>";
                xml += "<to><member>" + _publicZone + "</member></to>";
                xml += "<source><member>" + privateGateway + "</member></source>";
                xml += "<destination><member>any</member></destination>";
                xml += "<service>any</service>";
                xml += "<nat-type>ipv4</nat-type>";
                xml += "<to-interface>" + publicInterfaceName + "</to-interface>";
                xml += "<source-translation><dynamic-ip-and-port><interface-address>";
                xml += "<ip>" + publicIp + "</ip>";
                xml += "<interface>" + publicInterfaceName + "</interface>";
                xml += "</interface-address></dynamic-ip-and-port></source-translation>";

                Map<String, String> a_params = new HashMap<String, String>();
                a_params.put("type", "config");
                a_params.put("action", "set");
                a_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/nat/rules/entry[@name='" + srcNatName + "']");
                a_params.put("element", xml);
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.POST, a_params));

                return true;

            case DELETE:
                if (!manageSrcNatRule(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, type, publicVlanTag, publicIp, privateVlanTag, privateGateway)) {
                    return true;
                }

                Map<String, String> d_params = new HashMap<String, String>();
                d_params.put("type", "config");
                d_params.put("action", "delete");
                d_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/nat/rules/entry[@name='" + srcNatName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.POST, d_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};