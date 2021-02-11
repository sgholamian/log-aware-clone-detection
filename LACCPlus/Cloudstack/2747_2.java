//,temp,PaloAltoResource.java,1556,1612,temp,PaloAltoResource.java,1497,1544
//,3
public class xxx {
    public boolean managePingProfile(ArrayList<IPaloAltoCommand> cmdList, PaloAltoPrimative prim) throws ExecutionException {
        switch (prim) {

            case CHECK_IF_EXISTS:
                // check if one exists already
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "config");
                params.put("action", "get");
                params.put("xpath", "/config/devices/entry/network/profiles/interface-management-profile/entry[@name='" + _pingManagementProfile + "']");
                String response = request(PaloAltoMethod.GET, params);
                boolean result = (validResponse(response) && responseNotEmpty(response));
                s_logger.debug("Management profile exists: " + _pingManagementProfile + ", " + result);
                return result;

            case ADD:
                if (managePingProfile(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS)) {
                    return true;
                }

                // add ping profile...
                Map<String, String> a_params = new HashMap<String, String>();
                a_params.put("type", "config");
                a_params.put("action", "set");
                a_params.put("xpath", "/config/devices/entry/network/profiles/interface-management-profile/entry[@name='" + _pingManagementProfile + "']");
                a_params.put("element", "<ping>yes</ping>");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, a_params));

                return true;

            case DELETE:
                if (!managePingProfile(cmdList, PaloAltoPrimative.CHECK_IF_EXISTS)) {
                    return true;
                }

                // delete ping profile...
                Map<String, String> d_params = new HashMap<String, String>();
                d_params.put("type", "config");
                d_params.put("action", "delete");
                d_params.put("xpath", "/config/devices/entry/network/profiles/interface-management-profile/entry[@name='" + _pingManagementProfile + "']");
                cmdList.add(new DefaultPaloAltoCommand(PaloAltoMethod.GET, d_params));

                return true;

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};