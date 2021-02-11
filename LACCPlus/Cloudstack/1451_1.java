//,temp,SubmitCert.java,52,164,temp,SignEC2.java,42,142
//,3
public class xxx {
    public static void main(String[] args) {
        // Parameters
        List<String> argsList = Arrays.asList(args);
        Iterator<String> iter = argsList.iterator();
        while (iter.hasNext()) {
            String arg = iter.next();

            if (arg.equals("-c")) {
                certFileName = iter.next();
            }

            if (arg.equals("-s")) {
                secretKey = iter.next();
            }

            if (arg.equals("-a")) {
                apiKey = iter.next();
            }

            if (arg.equals("-action")) {
                url = "Action=" + iter.next();
            }
        }

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("conf/tool.properties"));
        } catch (IOException ex) {
            s_logger.error("Error reading from conf/tool.properties", ex);
            System.exit(2);
        }

        host = prop.getProperty("host");
        port = prop.getProperty("port");

        if (url.equals("Action=SetCertificate") && certFileName == null) {
            s_logger.error("Please set path to certificate (including file name) with -c option");
            System.exit(1);
        }

        if (secretKey == null) {
            s_logger.error("Please set secretkey  with -s option");
            System.exit(1);
        }

        if (apiKey == null) {
            s_logger.error("Please set apikey with -a option");
            System.exit(1);
        }

        if (host == null) {
            s_logger.error("Please set host in tool.properties file");
            System.exit(1);
        }

        if (port == null) {
            s_logger.error("Please set port in tool.properties file");
            System.exit(1);
        }

        TreeMap<String, String> param = new TreeMap<String, String>();

        String req = "GET\n" + host + ":" + prop.getProperty("port") + "\n/" + prop.getProperty("accesspoint") + "\n";
        String temp = "";

        if (certFileName != null) {
            cert = readCert(certFileName);
            param.put("cert", cert);
        }

        param.put("AWSAccessKeyId", apiKey);
        param.put("Expires", prop.getProperty("expires"));
        param.put("SignatureMethod", prop.getProperty("signaturemethod"));
        param.put("SignatureVersion", "2");
        param.put("Version", prop.getProperty("version"));

        StringTokenizer str1 = new StringTokenizer(url, "&");
        while (str1.hasMoreTokens()) {
            String newEl = str1.nextToken();
            StringTokenizer str2 = new StringTokenizer(newEl, "=");
            String name = str2.nextToken();
            String value = str2.nextToken();
            param.put(name, value);
        }

        //sort url hash map by key
        Set c = param.entrySet();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry)it.next();
            String key = (String)me.getKey();
            String value = (String)me.getValue();
            try {
                temp = temp + key + "=" + URLEncoder.encode(value, "UTF-8") + "&";
            } catch (Exception ex) {
                s_logger.error("Unable to set parameter " + value + " for the command " + param.get("command"), ex);
            }

        }
        temp = temp.substring(0, temp.length() - 1);
        String requestToSign = req + temp;
        String signature = UtilsForTest.signRequest(requestToSign, secretKey);
        String encodedSignature = "";
        try {
            encodedSignature = URLEncoder.encode(signature, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String url = "http://" + host + ":" + prop.getProperty("port") + "/" + prop.getProperty("accesspoint") + "?" + temp + "&Signature=" + encodedSignature;
        s_logger.info("Sending request with url:  " + url + "\n");
        sendRequest(url);
    }

};