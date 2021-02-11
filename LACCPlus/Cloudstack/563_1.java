//,temp,PaloAltoResource.java,1096,1186,temp,PaloAltoResource.java,781,863
//,3
public class xxx {
    public boolean manageStcNatRule(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim, StaticNatRuleTO rule) throws ExecutionException {
        String publicIp = rule.getSrcIp();
        String stcNatName = genStcNatRuleName(publicIp, rule.getId());

        String publicInterfaceName;
        String publicVlanTag;
        if (rule.getSrcVlanTag() == null) {
            publicInterfaceName = genPublicInterfaceName(new Long("9999"));
        } else {
            publicVlanTag = parsePublicVlanTag(rule.getSrcVlanTag());
            if (publicVlanTag.equals("untagged")) {
                publicInterfaceName = genPublicInterfaceName(new Long("9999"));
            } else {
                publicInterfaceName = genPublicInterfaceName(new Long(publicVlanTag));
            }
        }

        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/nat/rules/entry[@name='" + stcNatName + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Static NAT exists: " + stcNatName + ", " + result);
                return result;

            case ADD:
                if (manageStcNatRule(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, rule)) {
                    return true;
                }

                // add public IP to the sub-interface
                Map<String, String> a_sub_params = new HashMap<String, String>();
                a_sub_params.put("type", "config");
                a_sub_params.put("action", "set");
                a_sub_params.put("xpath", "/config/devices/entry/network/interface/" + _publicInterfaceType + "/entry[@name='" + _publicInterface +
                    "']/layer3/units/entry[@name='" + publicInterfaceName + "']/ip");
                a_sub_params.put("element", "<entry name='" + publicIp + "/32'/>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_sub_params));

                // add the static nat rule for the public IP
                String xml = "";
                xml += "<from><member>" + _publicZone + "</member></from>";
                xml += "<to><member>" + _publicZone + "</member></to>";
                xml += "<source><member>any</member></source>";
                xml += "<destination><member>" + publicIp + "</member></destination>";
                xml += "<service>any</service>";
                xml += "<nat-type>ipv4</nat-type>";
                xml += "<to-interface>" + publicInterfaceName + "</to-interface>";
                xml += "<destination-translation><translated-address>" + rule.getDstIp() + "</translated-address></destination-translation>";

                Map<String, String> a_params = new HashMap<String, String>();
                a_params.put("type", "config");
                a_params.put("action", "set");
                a_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/nat/rules/entry[@name='" + stcNatName + "']");
                a_params.put("element", xml);
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.POST, a_params));

                return true;

            case DELETE:
                if (!manageStcNatRule(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, rule)) {
                    return true;
                }

                // delete the static nat rule
                Map<String, String> d_params = new HashMap<String, String>();
                d_params.put("type", "config");
                d_params.put("action", "delete");
                d_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/nat/rules/entry[@name='" + stcNatName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.POST, d_params));

                // delete IP from sub-interface...
                Map<String, String> d_sub_params = new HashMap<String, String>();
                d_sub_params.put("type", "config");
                d_sub_params.put("action", "delete");
                d_sub_params.put("xpath", "/config/devices/entry/network/interface/" + _publicInterfaceType + "/entry[@name='" + _publicInterface +
                    "']/layer3/units/entry[@name='" + publicInterfaceName + "']/ip/entry[@name='" + publicIp + "/32']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_sub_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};