//,temp,TestClientWithAPI.java,517,999,temp,StressTestDirectAttach.java,426,730
//,3
public class xxx {
    private static Integer executeDeployment(String server, String developerServer, String username) throws HttpException, IOException {
        // test steps:
        // - create user
        // - deploy Windows VM
        // - deploy Linux VM
        // - associate IP address
        // - create two IP forwarding rules
        // - create load balancer rule
        // - list IP forwarding rules
        // - list load balancer rules

        // -----------------------------
        // CREATE USER
        // -----------------------------
        String encodedUsername = URLEncoder.encode(username, "UTF-8");
        String encryptedPassword = createMD5Password(username);
        String encodedPassword = URLEncoder.encode(encryptedPassword, "UTF-8");

        String url =
            server + "?command=createUser&username=" + encodedUsername + "&password=" + encodedPassword +
                "&firstname=Test&lastname=Test&email=test@vmops.com&domainId=1&accounttype=0";
        if (accountName != null) {
            url =
                server + "?command=createUser&username=" + encodedUsername + "&password=" + encodedPassword +
                    "&firstname=Test&lastname=Test&email=test@vmops.com&domainId=1&accounttype=0&account=" + accountName;
        }
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        long userId = -1;
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> userIdValues = getSingleValueFromXML(is, new String[] {"id", "account"});
            String userIdStr = userIdValues.get("id");
            s_logger.info("created user " + username + " with id " + userIdStr);
            if (userIdStr != null) {
                userId = Long.parseLong(userIdStr);
                s_userId.set(userId);
                s_account.set(userIdValues.get("account"));
                if (userId == -1) {
                    s_logger.error("create user (" + username + ") failed to retrieve a valid user id, aborting depolyment test");
                    return -1;
                }
            }
        } else {
            s_logger.error("create user test failed for user " + username + " with error code :" + responseCode);
            return responseCode;
        }

        s_secretKey.set(executeRegistration(server, username, username));

        if (s_secretKey.get() == null) {
            s_logger.error("FAILED to retrieve secret key during registration, skipping user: " + username);
            return -1;
        } else {
            s_logger.info("got secret key: " + s_secretKey.get());
            s_logger.info("got api key: " + s_apiKey.get());
        }

        // ---------------------------------
        // CREATE NETWORK GROUP AND ADD INGRESS RULE TO IT
        // ---------------------------------
        String networkAccount = null;
        if (accountName != null) {
            networkAccount = accountName;
        } else {
            networkAccount = encodedUsername;
        }
        String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
        String requestToSign = "apikey=" + encodedApiKey + "&command=createSecurityGroup&name=" + encodedUsername;
        requestToSign = requestToSign.toLowerCase();
        String signature = signRequest(requestToSign, s_secretKey.get());
        String encodedSignature = URLEncoder.encode(signature, "UTF-8");
        url = developerServer + "?command=createSecurityGroup&name=" + encodedUsername + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> values = getSingleValueFromXML(is, new String[] {"id"});

            if (values.get("id") == null) {
                s_logger.info("Create network rule response code: 401");
                return 401;
            } else {
                s_logger.info("Create security group response code: " + responseCode);
            }
        } else {
            s_logger.error("Create security group failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        String encodedCidr = URLEncoder.encode("192.168.1.143/32", "UTF-8");
        url =
            server + "?command=authorizeSecurityGroupIngress&cidrlist=" + encodedCidr + "&endport=22&" + "securitygroupname=" + encodedUsername +
                "&protocol=tcp&startport=22&account=" + networkAccount + "&domainid=1";

        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream input = method.getResponseBodyAsStream();
            Element el = queryAsyncJobResult(server, input);
            Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

            if (values.get("id") == null) {
                s_logger.info("Authorise security group ingress response code: 401");
                return 401;
            } else {
                s_logger.info("Authorise security group ingress response code: " + responseCode);
            }
        } else {
            s_logger.error("Authorise security group ingress failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // ---------------------------------
        // DEPLOY LINUX VM
        // ---------------------------------
        {
            long templateId = 2;
            String encodedZoneId = URLEncoder.encode("" + zoneId, "UTF-8");
            String encodedServiceOfferingId = URLEncoder.encode("" + serviceOfferingId, "UTF-8");
            String encodedTemplateId = URLEncoder.encode("" + templateId, "UTF-8");
            encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            requestToSign =
                "apikey=" + encodedApiKey + "&command=deployVirtualMachine&securitygrouplist=" + encodedUsername + "&serviceofferingid=" + encodedServiceOfferingId +
                    "&templateid=" + encodedTemplateId + "&zoneid=" + encodedZoneId;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");
            url =
                developerServer + "?command=deployVirtualMachine&securitygrouplist=" + encodedUsername + "&zoneid=" + encodedZoneId + "&serviceofferingid=" +
                    encodedServiceOfferingId + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;

            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});

                if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
                    s_logger.info("deploy linux vm response code: 401");
                    return 401;
                } else {
                    s_logger.info("deploy linux vm response code: " + responseCode);
                    long linuxVMId = Long.parseLong(values.get("id"));
                    s_logger.info("got linux virtual machine id: " + linuxVMId);
                    s_linuxVmId.set(values.get("id"));
                    s_linuxIP.set(values.get("ipaddress"));
                    s_linuxPassword.set("rs-ccb35ea5");
                }
            } else {
                s_logger.error("deploy linux vm failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        //Create a new volume
        {
            url = server + "?command=createVolume&diskofferingid=" + diskOfferingId + "&zoneid=" + zoneId + "&name=newvolume&account=" + s_account.get() + "&domainid=1";
            s_logger.info("Creating volume....");
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

                if (values.get("id") == null) {
                    s_logger.info("create volume response code: 401");
                    return 401;
                } else {
                    s_logger.info("create volume response code: " + responseCode);
                    String volumeId = values.get("id");
                    s_logger.info("got volume id: " + volumeId);
                    s_newVolume.set(volumeId);
                }
            } else {
                s_logger.error("create volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        //attach a new volume to the vm
        {
            url = server + "?command=attachVolume&id=" + s_newVolume.get() + "&virtualmachineid=" + s_linuxVmId.get();
            s_logger.info("Attaching volume with id " + s_newVolume.get() + " to the vm " + s_linuxVmId.get());
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("Attach data volume response code: " + responseCode);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

                if (values.get("id") == null) {
                    s_logger.info("Attach volume response code: 401");
                    return 401;
                } else {
                    s_logger.info("Attach volume response code: " + responseCode);
                }
            } else {
                s_logger.error("Attach volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        //DEPLOY SECOND VM, ADD VOLUME TO IT

        // ---------------------------------
        // DEPLOY another linux vm
        // ---------------------------------
        {
            long templateId = 2;
            String encodedZoneId = URLEncoder.encode("" + zoneId, "UTF-8");
            String encodedServiceOfferingId = URLEncoder.encode("" + serviceOfferingId, "UTF-8");
            String encodedTemplateId = URLEncoder.encode("" + templateId, "UTF-8");
            encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            requestToSign =
                "apikey=" + encodedApiKey + "&command=deployVirtualMachine&securitygrouplist=" + encodedUsername + "&serviceofferingid=" + encodedServiceOfferingId +
                    "&templateid=" + encodedTemplateId + "&zoneid=" + encodedZoneId;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");
            url =
                developerServer + "?command=deployVirtualMachine&securitygrouplist=" + encodedUsername + "&zoneid=" + encodedZoneId + "&serviceofferingid=" +
                    encodedServiceOfferingId + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;

            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});

                if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
                    s_logger.info("deploy linux vm response code: 401");
                    return 401;
                } else {
                    s_logger.info("deploy linux vm response code: " + responseCode);
                    long linuxVMId = Long.parseLong(values.get("id"));
                    s_logger.info("got linux virtual machine id: " + linuxVMId);
                    s_linuxVmId1.set(values.get("id"));
                }
            } else {
                s_logger.error("deploy linux vm failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        //Create a new volume
        {
            url = server + "?command=createVolume&diskofferingid=" + diskOfferingId1 + "&zoneid=" + zoneId + "&name=newvolume1&account=" + s_account.get() + "&domainid=1";
            s_logger.info("Creating volume....");
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

                if (values.get("id") == null) {
                    s_logger.info("create volume response code: 401");
                    return 401;
                } else {
                    s_logger.info("create volume response code: " + responseCode);
                    String volumeId = values.get("id");
                    s_logger.info("got volume id: " + volumeId);
                    s_newVolume1.set(volumeId);
                }
            } else {
                s_logger.error("create volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        //attach a new volume to the vm
        {
            url = server + "?command=attachVolume&id=" + s_newVolume1.get() + "&virtualmachineid=" + s_linuxVmId1.get();
            s_logger.info("Attaching volume with id " + s_newVolume1.get() + " to the vm " + s_linuxVmId1.get());
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("Attach data volume response code: " + responseCode);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

                if (values.get("id") == null) {
                    s_logger.info("Attach volume response code: 401");
                    return 401;
                } else {
                    s_logger.info("Attach volume response code: " + responseCode);
                }
            } else {
                s_logger.error("Attach volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }
        return 200;
    }

};