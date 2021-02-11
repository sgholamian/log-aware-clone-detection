//,temp,TestClientWithAPI.java,2245,2287,temp,ApiCommand.java,783,821
//,3
public class xxx {
    public Element queryAsyncJobResult(String jobId) {
        Element returnBody = null;
        int code = 400;
        String resultUrl = this.host + ":8096/?command=queryAsyncJobResult&jobid=" + jobId;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(resultUrl);
        while (true) {
            try {
                code = client.executeMethod(method);
                if (code == 200) {
                    InputStream is = method.getResponseBodyAsStream();
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(is);
                    doc.getDocumentElement().normalize();
                    returnBody = doc.getDocumentElement();
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
                    method.releaseConnection();
                } else {
                    s_logger.error("Error during queryJobAsync. Error code is " + code);
                    this.responseCode = code;
                    return null;
                }
            } catch (Exception ex) {
                s_logger.error(ex);
            }
        }
        return returnBody;
    }

};