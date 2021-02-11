//,temp,TestClientWithAPI.java,1001,1395,temp,TestClientWithAPI.java,517,999
//,3
public class xxx {
    private static Integer executeDeployment(String server, String developerServer, String username, String snapshotTest) throws HttpException, IOException {
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
        // CREATE ACCOUNT
        // -----------------------------
        String encodedUsername = URLEncoder.encode(username, "UTF-8");
        String encryptedPassword = createMD5Password(username);
        String encodedPassword = URLEncoder.encode(encryptedPassword, "UTF-8");

        String url =
            server + "?command=createAccount&username=" + encodedUsername + "&account=" + encodedUsername + "&password=" + encodedPassword +
                "&firstname=Test&lastname=Test&email=test@vmops.com&domainId=1&accounttype=0";

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        long accountId = -1;
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> accountValues = getSingleValueFromXML(is, new String[] {"id", "name"});
            String accountIdStr = accountValues.get("id");
            s_logger.info("created account " + username + " with id " + accountIdStr);
            if (accountIdStr != null) {
                accountId = Long.parseLong(accountIdStr);
                s_accountId.set(accountId);
                s_account.set(accountValues.get("name"));
                if (accountId == -1) {
                    s_logger.error("create account (" + username + ") failed to retrieve a valid user id, aborting depolyment test");
                    return -1;
                }
            }
        } else {
            s_logger.error("create account test failed for account " + username + " with error code :" + responseCode +
                ", aborting deployment test. The command was sent with url " + url);
            return -1;
        }

        // LIST JUST CREATED USER TO GET THE USER ID
        url = server + "?command=listUsers&username=" + encodedUsername + "&account=" + encodedUsername + "&domainId=1";
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        long userId = -1;
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> userIdValues = getSingleValueFromXML(is, new String[] {"id"});
            String userIdStr = userIdValues.get("id");
            s_logger.info("listed user " + username + " with id " + userIdStr);
            if (userIdStr != null) {
                userId = Long.parseLong(userIdStr);
                s_userId.set(userId);
                if (userId == -1) {
                    s_logger.error("list user by username " + username + ") failed to retrieve a valid user id, aborting depolyment test");
                    return -1;
                }
            }
        } else {
            s_logger.error("list user test failed for account " + username + " with error code :" + responseCode +
                ", aborting deployment test. The command was sent with url " + url);
            return -1;
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
        // CREATE VIRTUAL NETWORK
        // ---------------------------------
        url =
            server + "?command=createNetwork&networkofferingid=" + networkOfferingId + "&account=" + encodedUsername + "&domainId=1" + "&zoneId=" + zoneId +
                "&name=virtualnetwork-" + encodedUsername + "&displaytext=virtualnetwork-" + encodedUsername;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> networkValues = getSingleValueFromXML(is, new String[] {"id"});
            String networkIdStr = networkValues.get("id");
            s_logger.info("Created virtual network with name virtualnetwork-" + encodedUsername + " and id " + networkIdStr);
            if (networkIdStr != null) {
                s_networkId.set(networkIdStr);
            }
        } else {
            s_logger.error("Create virtual network failed for account " + username + " with error code :" + responseCode +
                ", aborting deployment test. The command was sent with url " + url);
            return -1;
        }
        /*
        // ---------------------------------
        // CREATE DIRECT NETWORK
        // ---------------------------------
        url = server + "?command=createNetwork&networkofferingid=" + networkOfferingId_dir + "&account=" + encodedUsername + "&domainId=1" + "&zoneId=" + zoneId + "&name=directnetwork-" + encodedUsername + "&displaytext=directnetwork-" + encodedUsername;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> networkValues = getSingleValueFromXML(is, new String[] { "id" });
            String networkIdStr = networkValues.get("id");
            s_logger.info("Created direct network with name directnetwork-" + encodedUsername + " and id " + networkIdStr);
            if (networkIdStr != null) {
                s_networkId_dir.set(networkIdStr);
            }
        } else {
            s_logger.error("Create direct network failed for account " + username + " with error code :" + responseCode + ", aborting deployment test. The command was sent with url " + url);
            return -1;
        }
         */

        // ---------------------------------
        // DEPLOY LINUX VM
        // ---------------------------------
        String linuxVMPrivateIP = null;
        {
            // long templateId = 3;
            long templateId = 4;
            String encodedZoneId = URLEncoder.encode("" + zoneId, "UTF-8");
            String encodedServiceOfferingId = URLEncoder.encode("" + serviceOfferingId, "UTF-8");
            String encodedTemplateId = URLEncoder.encode("" + templateId, "UTF-8");
            String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            String encodedNetworkIds = URLEncoder.encode(s_networkId.get() + ",206", "UTF-8");
            String requestToSign =
                "apikey=" + encodedApiKey + "&command=deployVirtualMachine&diskofferingid=" + diskOfferingId + "&networkids=" + encodedNetworkIds +
                    "&serviceofferingid=" + encodedServiceOfferingId + "&templateid=" + encodedTemplateId + "&zoneid=" + encodedZoneId;
            requestToSign = requestToSign.toLowerCase();
            String signature = signRequest(requestToSign, s_secretKey.get());
            String encodedSignature = URLEncoder.encode(signature, "UTF-8");
            url =
                developerServer + "?command=deployVirtualMachine" + "&zoneid=" + encodedZoneId + "&serviceofferingid=" + encodedServiceOfferingId + "&diskofferingid=" +
                    diskOfferingId + "&networkids=" + encodedNetworkIds + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey + "&signature=" +
                    encodedSignature;

            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});

                if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
                    s_logger.info("deploy linux vm response code: 401, the command was sent with url " + url);
                    return 401;
                } else {
                    s_logger.info("deploy linux vm response code: " + responseCode);
                    long linuxVMId = Long.parseLong(values.get("id"));
                    s_logger.info("got linux virtual machine id: " + linuxVMId);
                    s_linuxVmId.set(values.get("id"));
                    linuxVMPrivateIP = values.get("ipaddress");
                    // s_linuxPassword.set(values.get("password"));
                    s_linuxPassword.set(vmPassword);
                    s_logger.info("got linux virtual machine password: " + s_linuxPassword.get());
                }
            } else {
                s_logger.error("deploy linux vm failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        {
            // ---------------------------------
            // ASSOCIATE IP for windows
            // ---------------------------------
            String ipAddr = null;

            String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            String requestToSign = "apikey=" + encodedApiKey + "&command=associateIpAddress" + "&zoneid=" + zoneId;
            requestToSign = requestToSign.toLowerCase();
            String signature = signRequest(requestToSign, s_secretKey.get());
            String encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=associateIpAddress" + "&apikey=" + encodedApiKey + "&zoneid=" + zoneId + "&signature=" + encodedSignature;

            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                /*Asynchronous Job - Corresponding Changes Made*/
                Element associpel = queryAsyncJobResult(server, is);
                Map<String, String> values = getSingleValueFromXML(associpel, new String[] {"id", "ipaddress"});

                if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
                    s_logger.info("associate ip for Windows response code: 401, the command was sent with url " + url);
                    return 401;
                } else {
                    s_logger.info("Associate IP Address response code: " + responseCode);
                    long publicIpId = Long.parseLong(values.get("id"));
                    s_logger.info("Associate IP's Id: " + publicIpId);
                    s_publicIpId.set(values.get("id"));
                }
            } else {
                s_logger.error("associate ip address for windows vm failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            String encodedPublicIpId = URLEncoder.encode(s_publicIpId.get(), "UTF-8");
            requestToSign = "apikey=" + encodedApiKey + "&command=listPublicIpAddresses" + "&id=" + encodedPublicIpId;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listPublicIpAddresses&apikey=" + encodedApiKey + "&id=" + encodedPublicIpId + "&signature=" + encodedSignature;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("url is " + url);
            s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                //       InputStream ips = method.getResponseBodyAsStream();
                List<String> ipAddressValues = getIPs(is, false);
                //       List<String> ipAddressVals = getIPs(is, false, true);
                if ((ipAddressValues != null) && !ipAddressValues.isEmpty()) {
                    s_windowsIpId.set(ipAddressValues.get(0));
                    s_windowsIP.set(ipAddressValues.get(1));
                    s_logger.info("For Windows, using non-sourceNat IP address ID: " + ipAddressValues.get(0));
                    s_logger.info("For Windows, using non-sourceNat IP address: " + ipAddressValues.get(1));
                }
            } else {
                s_logger.error("list ip addresses failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ---------------------------------
            // Use the SourceNat IP for linux
            // ---------------------------------
            {
                requestToSign = "apikey=" + encodedApiKey + "&command=listPublicIpAddresses";
                requestToSign = requestToSign.toLowerCase();
                signature = signRequest(requestToSign, s_secretKey.get());
                encodedSignature = URLEncoder.encode(signature, "UTF-8");

                url = developerServer + "?command=listPublicIpAddresses&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
                client = new HttpClient();
                method = new GetMethod(url);
                responseCode = client.executeMethod(method);
                s_logger.info("url is " + url);
                s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
                if (responseCode == 200) {
                    InputStream is = method.getResponseBodyAsStream();
//                  InputStream ips = method.getResponseBodyAsStream();
                    List<String> ipAddressValues = getIPs(is, true);
//                    is = method.getResponseBodyAsStream();
//                    List<String> ipAddressVals = getIPs(is, true, true);
                    if ((ipAddressValues != null) && !ipAddressValues.isEmpty()) {
                        s_linuxIpId.set(ipAddressValues.get(0));
                        s_linuxIP.set(ipAddressValues.get(1));
                        s_logger.info("For linux, using sourceNat IP address ID: " + ipAddressValues.get(0));
                        s_logger.info("For linux, using sourceNat IP address: " + ipAddressValues.get(1));
                    }
                } else {
                    s_logger.error("list ip addresses failed with error code: " + responseCode + ". Following URL was sent: " + url);
                    return responseCode;
                }
            }

            //--------------------------------------------
            // Enable Static NAT for the Source NAT Ip
            //--------------------------------------------
            String encodedSourceNatPublicIpId = URLEncoder.encode(s_linuxIpId.get(), "UTF-8");

            /*          requestToSign = "apikey=" + encodedApiKey + "&command=enableStaticNat"+"&id=" + encodedSourceNatPublicIpId + "&virtualMachineId=" + encodedVmId;;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=enableStaticNat&apikey=" + encodedApiKey + "&signature=" + encodedSignature + "&id=" + encodedSourceNatPublicIpId + "&virtualMachineId=" + encodedVmId;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("url is " + url);
            s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, String> success = getSingleValueFromXML(is, new String[] { "success" });
                s_logger.info("Enable Static NAT..success? " + success.get("success"));
            } else {
                s_logger.error("Enable Static NAT failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
             */
            // -------------------------------------------------------------
            // CREATE IP FORWARDING RULE -- Linux VM
            // -------------------------------------------------------------
            String encodedVmId = URLEncoder.encode(s_linuxVmId.get(), "UTF-8");
            String encodedIpAddress = URLEncoder.encode(s_linuxIpId.get(), "UTF-8");
            requestToSign =
                "apikey=" + encodedApiKey + "&command=createPortForwardingRule&ipaddressid=" + encodedIpAddress + "&privateport=22&protocol=TCP&publicport=22" +
                    "&virtualmachineid=" + encodedVmId;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url =
                developerServer + "?command=createPortForwardingRule&apikey=" + encodedApiKey + "&ipaddressid=" + encodedIpAddress +
                    "&privateport=22&protocol=TCP&publicport=22&virtualmachineid=" + encodedVmId + "&signature=" + encodedSignature;

            s_logger.info("Created port forwarding rule with " + url);
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
                s_logger.info("Port forwarding rule was assigned successfully to Linux VM");
                long ipfwdid = Long.parseLong(values.get("id"));
                s_logger.info("got Port Forwarding Rule's Id:" + ipfwdid);
                s_linipfwdid.set(values.get("id"));

            } else {
                s_logger.error("Port forwarding rule creation failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // Create snapshot recurring policy if needed; otherwise create windows vm
            if (snapshotTest.equals("yes")) {

                // list volumes for linux vm
                {
                    url = server + "?command=listVolumes&virtualMachineId=" + s_linuxVmId.get() + "&type=root";
                    s_logger.info("Getting rootDisk id of Centos vm");
                    client = new HttpClient();
                    method = new GetMethod(url);
                    responseCode = client.executeMethod(method);
                    s_logger.info("List volumes response code: " + responseCode);
                    if (responseCode == 200) {
                        InputStream is = method.getResponseBodyAsStream();
                        Map<String, String> success = getSingleValueFromXML(is, new String[] {"id"});
                        if (success.get("id") == null) {
                            s_logger.error("Unable to get root volume for linux vm. Followin url was sent: " + url);
                        }
                        s_logger.info("Got rootVolume for linux vm with id " + success.get("id"));
                        s_rootVolume.set(success.get("id"));
                    } else {
                        s_logger.error("List volumes for linux vm failed with error code: " + responseCode + ". Following URL was sent: " + url);
                        return responseCode;
                    }
                }
                // Create recurring snapshot policy for linux vm
                {
                    String encodedTimeZone = URLEncoder.encode("America/Los Angeles", "UTF-8");
                    url =
                        server + "?command=createSnapshotPolicy&intervaltype=hourly&schedule=10&maxsnaps=4&volumeid=" + s_rootVolume.get() + "&timezone=" +
                            encodedTimeZone;
                    s_logger.info("Creating recurring snapshot policy for linux vm ROOT disk");
                    client = new HttpClient();
                    method = new GetMethod(url);
                    responseCode = client.executeMethod(method);
                    s_logger.info("Create recurring snapshot policy for linux vm ROOT disk: " + responseCode);
                    if (responseCode != 200) {
                        s_logger.error("Create recurring snapshot policy for linux vm ROOT disk failed with error code: " + responseCode + ". Following URL was sent: " +
                            url);
                        return responseCode;
                    }
                }
            } else {
                // ---------------------------------
                // DEPLOY WINDOWS VM
                // ---------------------------------
                String windowsVMPrivateIP = null;
                {
                    // long templateId = 6;
                    long templateId = 4;
                    String encodedZoneId = URLEncoder.encode("" + zoneId, "UTF-8");
                    String encodedServiceOfferingId = URLEncoder.encode("" + serviceOfferingId, "UTF-8");
                    String encodedTemplateId = URLEncoder.encode("" + templateId, "UTF-8");
                    encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
                    String encodedNetworkIds = URLEncoder.encode(s_networkId.get() + ",206", "UTF-8");

                    requestToSign =
                        "apikey=" + encodedApiKey + "&command=deployVirtualMachine&diskofferingid=" + diskOfferingId + "&networkids=" + encodedNetworkIds +
                            "&serviceofferingid=" + encodedServiceOfferingId + "&templateid=" + encodedTemplateId + "&zoneid=" + encodedZoneId;
                    requestToSign = requestToSign.toLowerCase();
                    signature = signRequest(requestToSign, s_secretKey.get());
                    encodedSignature = URLEncoder.encode(signature, "UTF-8");

                    url =
                        developerServer + "?command=deployVirtualMachine" + "&zoneid=" + encodedZoneId + "&serviceofferingid=" + encodedServiceOfferingId +
                            "&diskofferingid=" + diskOfferingId + "&networkids=" + encodedNetworkIds + "&templateid=" + encodedTemplateId + "&apikey=" + encodedApiKey +
                            "&signature=" + encodedSignature;

                    method = new GetMethod(url);
                    responseCode = client.executeMethod(method);
                    if (responseCode == 200) {
                        InputStream input = method.getResponseBodyAsStream();
                        Element el = queryAsyncJobResult(server, input);
                        Map<String, String> values = getSingleValueFromXML(el, new String[] {"id", "ipaddress"});

                        if ((values.get("ipaddress") == null) || (values.get("id") == null)) {
                            s_logger.info("deploy windows vm response code: 401, the command was sent with url " + url);
                            return 401;
                        } else {
                            s_logger.info("deploy windows vm response code: " + responseCode);
                            windowsVMPrivateIP = values.get("ipaddress");
                            long windowsVMId = Long.parseLong(values.get("id"));
                            s_logger.info("got windows virtual machine id: " + windowsVMId);
                            s_windowsVmId.set(values.get("id"));
                        }
                    } else {
                        s_logger.error("deploy windows vm failes with error code: " + responseCode + ". Following URL was sent: " + url);
                        return responseCode;
                    }
                }

                //--------------------------------------------
                // Enable Static NAT for the Non Source NAT Ip
                //--------------------------------------------

                encodedVmId = URLEncoder.encode(s_windowsVmId.get(), "UTF-8");
                encodedPublicIpId = URLEncoder.encode(s_publicIpId.get(), "UTF-8");
                requestToSign = "apikey=" + encodedApiKey + "&command=enableStaticNat" + "&ipaddressid=" + encodedPublicIpId + "&virtualMachineId=" + encodedVmId;
                requestToSign = requestToSign.toLowerCase();
                signature = signRequest(requestToSign, s_secretKey.get());
                encodedSignature = URLEncoder.encode(signature, "UTF-8");

                url =
                    developerServer + "?command=enableStaticNat&apikey=" + encodedApiKey + "&ipaddressid=" + encodedPublicIpId + "&signature=" + encodedSignature +
                        "&virtualMachineId=" + encodedVmId;
                client = new HttpClient();
                method = new GetMethod(url);
                responseCode = client.executeMethod(method);
                s_logger.info("url is " + url);
                s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
                if (responseCode == 200) {
                    InputStream is = method.getResponseBodyAsStream();
                    Map<String, String> success = getSingleValueFromXML(is, new String[] {"success"});
                    s_logger.info("Enable Static NAT..success? " + success.get("success"));
                } else {
                    s_logger.error("Enable Static NAT failed with error code: " + responseCode + ". Following URL was sent: " + url);
                    return responseCode;
                }

                // -------------------------------------------------------------
                // CREATE IP FORWARDING RULE -- Windows VM
                // -------------------------------------------------------------

                // create port forwarding rule for window vm
                encodedIpAddress = URLEncoder.encode(s_windowsIpId.get(), "UTF-8");
                //encodedVmId = URLEncoder.encode(s_windowsVmId.get(), "UTF-8");

                requestToSign = "apikey=" + encodedApiKey + "&command=createIpForwardingRule&endPort=22&ipaddressid=" + encodedIpAddress + "&protocol=TCP&startPort=22";
                requestToSign = requestToSign.toLowerCase();
                signature = signRequest(requestToSign, s_secretKey.get());
                encodedSignature = URLEncoder.encode(signature, "UTF-8");

                url =
                    developerServer + "?command=createIpForwardingRule&apikey=" + encodedApiKey + "&endPort=22&ipaddressid=" + encodedIpAddress +
                        "&protocol=TCP&signature=" + encodedSignature + "&startPort=22";

                s_logger.info("Created Ip forwarding rule with " + url);
                method = new GetMethod(url);
                responseCode = client.executeMethod(method);
                if (responseCode == 200) {
                    InputStream input = method.getResponseBodyAsStream();
                    Element el = queryAsyncJobResult(server, input);
                    Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});
                    s_logger.info("Port forwarding rule was assigned successfully to Windows VM");
                    long ipfwdid = Long.parseLong(values.get("id"));
                    s_logger.info("got Ip Forwarding Rule's Id:" + ipfwdid);
                    s_winipfwdid.set(values.get("id"));
                } else {
                    s_logger.error("Port forwarding rule creation failed with error code: " + responseCode + ". Following URL was sent: " + url);
                    return responseCode;
                }
            }
        }
        return responseCode;
    }

};