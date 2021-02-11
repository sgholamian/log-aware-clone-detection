//,temp,ApiCommand.java,726,781,temp,ApiCommand.java,649,724
//,3
public class xxx {
    public static boolean verifyEvents(String fileName, String level, String host, String account) {
        boolean result = false;
        HashMap<String, Integer> expectedEvents = new HashMap<String, Integer>();
        HashMap<String, Integer> actualEvents = new HashMap<String, Integer>();
        String key = "";

        File file = new File(fileName);
        if (file.exists()) {
            Properties pro = new Properties();
            try {
                // get expected events
                FileInputStream in = new FileInputStream(file);
                pro.load(in);
                Enumeration<?> en = pro.propertyNames();
                while (en.hasMoreElements()) {
                    key = (String)en.nextElement();
                    expectedEvents.put(key, Integer.parseInt(pro.getProperty(key)));
                }

                // get actual events
                String url = host + "/?command=listEvents&account=" + account + "&level=" + level + "&domainid=1&pagesize=100";
                s_logger.info("Getting events with the following url " + url);
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

                // compare actual events with expected events

                // compare expected result and actual result
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
                        s_logger.error("Event of type " + type + " and level " + level + " is missing in the listEvents response. Expected number of these events is " +
                            expected);
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
            } catch (Exception ex) {
                s_logger.error(ex);
            }
        } else {
            s_logger.info("File " + fileName + " not found");
        }
        return result;
    }

};