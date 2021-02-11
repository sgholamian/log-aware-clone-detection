//,temp,ApiCommand.java,726,781,temp,ApiCommand.java,649,724
//,3
public class xxx {
    public static boolean verifyEvents(HashMap<String, Integer> expectedEvents, String level, String host, String parameters) {
        boolean result = false;
        HashMap<String, Integer> actualEvents = new HashMap<String, Integer>();
        try {
            // get actual events
            String url = host + "/?command=listEvents&" + parameters;
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
            int responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                ArrayList<HashMap<String, String>> eventValues = UtilsForTest.parseMulXML(is, new String[] {"event"});

                for (int i = 0; i < eventValues.size(); i++) {
                    HashMap<String, String> element = eventValues.get(i);
                    if (element.get("level").equals(level)) {
                        if (actualEvents.containsKey(element.get("type")) == true) {
                            actualEvents.put(element.get("type"), actualEvents.get(element.get("type")) + 1);
                        } else {
                            actualEvents.put(element.get("type"), 1);
                        }
                    }
                }
            }
            method.releaseConnection();
        } catch (Exception ex) {
            s_logger.error(ex);
        }

        // compare actual events with expected events
        Iterator<?> iterator = expectedEvents.keySet().iterator();
        Integer expected;
        Integer actual;
        int fail = 0;
        while (iterator.hasNext()) {
            expected = null;
            actual = null;
            String type = iterator.next().toString();
            expected = expectedEvents.get(type);
            actual = actualEvents.get(type);
            if (actual == null) {
                s_logger.error("Event of type " + type + " and level " + level + " is missing in the listEvents response. Expected number of these events is " + expected);
                fail++;
            } else if (expected.compareTo(actual) != 0) {
                fail++;
                s_logger.info("Amount of events of  " + type + " type and level " + level + " is incorrect. Expected number of these events is " + expected +
                    ", actual number is " + actual);
            }
        }

        if (fail == 0) {
            result = true;
        }

        return result;
    }

};