//,temp,StressTestDirectAttach.java,898,931,temp,StressTestDirectAttach.java,408,424
//,3
public class xxx {
    private static int executeEventsAndBilling(String server, String developerServer) throws HttpException, IOException {
        // test steps:
        // - get all the events in the system for all users in the system
        // - generate all the usage records in the system
        // - get all the usage records in the system

        // -----------------------------
        // GET EVENTS
        // -----------------------------
        String url = server + "?command=listEvents&page=1&account=" + s_account.get();

        s_logger.info("Getting events for the account " + s_account.get());
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        s_logger.info("get events response code: " + responseCode);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, List<String>> eventDescriptions = getMultipleValuesFromXML(is, new String[] {"description"});
            List<String> descriptionText = eventDescriptions.get("description");
            if (descriptionText == null) {
                s_logger.info("no events retrieved...");
            } else {
                for (String text : descriptionText) {
                    s_logger.info("event: " + text);
                }
            }
        } else {
            s_logger.error("list events failed with error code: " + responseCode + ". Following URL was sent: " + url);

            return responseCode;
        }
        return responseCode;
    }

};