//,temp,PaloAltoResource.java,1556,1612,temp,PaloAltoResource.java,1435,1494
//,3
public class xxx {
    public boolean manageNetworkIsolation(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim, long privateVlanTag, String privateSubnet, String privateGateway)
        throws ExecutionException {
        String ruleName = genNetworkIsolationName(privateVlanTag);

        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/security/rules/entry[@name='" + ruleName + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Firewall policy exists: " + ruleName + ", " + result);
                return result;

            case ADD:
                if (manageNetworkIsolation(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, privateVlanTag, privateSubnet, privateGateway)) {
                    return true;
                }

                String xml = "";
                xml += "<from><member>" + _privateZone + "</member></from>";
                xml += "<to><member>" + _privateZone + "</member></to>";
                xml += "<source><member>" + privateSubnet + "</member></source>";
                xml += "<destination><member>" + privateGateway + "</member></destination>";
                xml += "<application><member>any</member></application>";
                xml += "<service><member>any</member></service>";
                xml += "<action>deny</action>";
                xml += "<negate-source>no</negate-source>";
                xml += "<negate-destination>yes</negate-destination>";

                Map<String, String> a_params = new HashMap<String, String>();
                a_params.put("type", "config");
                a_params.put("action", "set");
                a_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/security/rules/entry[@name='" + ruleName + "']");
                a_params.put("element", xml);
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.POST, a_params));

                return true;

            case DELETE:
                if (!manageNetworkIsolation(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS, privateVlanTag, privateSubnet, privateGateway)) {
                    return true;
                }

                Map<String, String> d_params = new HashMap<String, String>();
                d_params.put("type", "config");
                d_params.put("action", "delete");
                d_params.put("xpath", "/config/devices/entry/vsys/entry[@name='vsys1']/rulebase/security/rules/entry[@name='" + ruleName + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.POST, d_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};