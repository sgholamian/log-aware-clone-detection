//,temp,TestClientWithAPI.java,2245,2287,temp,StressTestDirectAttach.java,1309,1351
//,2
public class xxx {
    public static Element queryAsyncJobResult(String host, InputStream inputStream) {
        Element returnBody = null;

        Map<String, String> values = getSingleValueFromXML(inputStream, new String[] {"jobid"});
        String jobId = values.get("jobid");

        if (jobId == null) {
            s_logger.error("Unable to get a jobId");
            return null;
        }

        // s_logger.info("Job id is " + jobId);
        String resultUrl = host + "?command=queryAsyncJobResult&jobid=" + jobId;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(resultUrl);
        while (true) {
            try {
                client.executeMethod(method);
                // s_logger.info("Method is executed successfully. Following url was sent " + resultUrl);
                InputStream is = method.getResponseBodyAsStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                returnBody = doc.getDocumentElement();
                doc.getDocumentElement().normalize();
                Element jobStatusTag = (Element)returnBody.getElementsByTagName("jobstatus").item(0);
                String jobStatus = jobStatusTag.getTextContent();
                if (jobStatus.equals("0")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        s_logger.debug("[ignored] interupted while during async job result query.");
                    }
                } else {
                    break;
                }

            } catch (Exception ex) {
                s_logger.error(ex);
            }
        }
        return returnBody;
    }

};