//,temp,TestClientWithAPI.java,1525,1997,temp,StressTestDirectAttach.java,426,730
//,3
public class xxx {
    private static int executeStop(String server, String developerServer, String username, boolean destroy) throws HttpException, IOException {
        // test steps:
        // - get userId for the given username
        // - list virtual machines for the user
        // - stop all virtual machines
        // - get ip addresses for the user
        // - release ip addresses

        // -----------------------------
        // GET USER
        // -----------------------------
        String userId = s_userId.get().toString();
        String encodedUserId = URLEncoder.encode(userId, "UTF-8");

        String url = server + "?command=listUsers&id=" + encodedUserId;
        s_logger.info("Stopping resources for user: " + username);
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        s_logger.info("get user response code: " + responseCode);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> userIdValues = getSingleValueFromXML(is, new String[] {"id"});
            String userIdStr = userIdValues.get("id");
            if (userIdStr != null) {
                userId = userIdStr;

            } else {
                s_logger.error("get user failed to retrieve a valid user id, aborting depolyment test" + ". Following URL was sent: " + url);
                return -1;
            }
        } else {
            s_logger.error("get user failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        {
            // ----------------------------------
            // LIST VIRTUAL MACHINES
            // ----------------------------------
            String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            String requestToSign = "apikey=" + encodedApiKey + "&command=listVirtualMachines";
            requestToSign = requestToSign.toLowerCase();
            String signature = signRequest(requestToSign, s_secretKey.get());
            String encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listVirtualMachines&apikey=" + encodedApiKey + "&signature=" + encodedSignature;

            s_logger.info("Listing all virtual machines for the user with url " + url);
            String[] vmIds = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list virtual machines response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> vmIdValues = getMultipleValuesFromXML(is, new String[] {"id"});
                if (vmIdValues.containsKey("id")) {
                    List<String> vmIdList = vmIdValues.get("id");
                    if (vmIdList != null) {
                        vmIds = new String[vmIdList.size()];
                        vmIdList.toArray(vmIds);
                        String vmIdLogStr = "";
                        if ((vmIds != null) && (vmIds.length > 0)) {
                            vmIdLogStr = vmIds[0];
                            for (int i = 1; i < vmIds.length; i++) {
                                vmIdLogStr = vmIdLogStr + "," + vmIds[i];
                            }
                        }
                        s_logger.info("got virtual machine ids: " + vmIdLogStr);
                    }
                }

            } else {
                s_logger.error("list virtual machines test failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // LIST USER IP ADDRESSES
            // ----------------------------------

            requestToSign = "apikey=" + encodedApiKey + "&command=listPublicIpAddresses";
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listPublicIpAddresses&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
            String[] ipAddresses = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> ipAddressValues = getMultipleValuesFromXML(is, new String[] {"ipaddress"});
                if (ipAddressValues.containsKey("ipaddress")) {
                    List<String> ipAddressList = ipAddressValues.get("ipaddress");
                    if (ipAddressList != null) {
                        ipAddresses = new String[ipAddressList.size()];
                        ipAddressList.toArray(ipAddresses);
                        String ipAddressLogStr = "";
                        if ((ipAddresses != null) && (ipAddresses.length > 0)) {
                            ipAddressLogStr = ipAddresses[0];
                            for (int i = 1; i < ipAddresses.length; i++) {
                                ipAddressLogStr = ipAddressLogStr + "," + ipAddresses[i];
                            }
                        }
                        s_logger.info("got IP addresses: " + ipAddressLogStr);
                    }
                }

            } else {
                s_logger.error("list user ip addresses failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // LIST ZONES
            // ----------------------------------

            requestToSign = "apikey=" + encodedApiKey + "&command=listZones";
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listZones&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
            String[] zoneNames = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list zones response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> zoneNameValues = getMultipleValuesFromXML(is, new String[] {"name"});
                if (zoneNameValues.containsKey("name")) {
                    List<String> zoneNameList = zoneNameValues.get("name");
                    if (zoneNameList != null) {
                        zoneNames = new String[zoneNameList.size()];
                        zoneNameList.toArray(zoneNames);
                        String zoneNameLogStr = "\n\n";
                        if ((zoneNames != null) && (zoneNames.length > 0)) {
                            zoneNameLogStr += zoneNames[0];
                            for (int i = 1; i < zoneNames.length; i++) {
                                zoneNameLogStr = zoneNameLogStr + "\n" + zoneNames[i];
                            }

                        }
                        zoneNameLogStr += "\n\n";
                        s_logger.info("got zones names: " + zoneNameLogStr);
                    }
                }

            } else {
                s_logger.error("list zones failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // LIST ACCOUNT STATISTICS
            // ----------------------------------

            requestToSign = "apikey=" + encodedApiKey + "&command=listAccounts";
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listAccounts&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
            String[] statNames = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("listAccountStatistics response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> statValues = getMultipleValuesFromXML(is, new String[] {"receivedbytes"});
                if (statValues.containsKey("receivedbytes")) {
                    List<String> statList = statValues.get("receivedbytes");
                    if (statList != null) {
                        statNames = new String[statList.size()];
                        statList.toArray(statNames);
                        String statLogStr = "\n\n";
                        if ((statNames != null) && (zoneNames.length > 0)) {
                            statLogStr += statNames[0];
                            for (int i = 1; i < statNames.length; i++) {
                                statLogStr = statLogStr + "\n" + zoneNames[i];
                            }

                        }
                        statLogStr += "\n\n";
                        s_logger.info("got accountstatistics: " + statLogStr);
                    }
                }

            } else {
                s_logger.error("listAccountStatistics failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // LIST TEMPLATES
            // ----------------------------------

            requestToSign = "apikey=" + encodedApiKey + "&command=listTemplates@templatefilter=self";
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listTemplates&apikey=" + encodedApiKey + "&templatefilter=self&signature=" + encodedSignature;
            String[] templateNames = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list templates response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> templateNameValues = getMultipleValuesFromXML(is, new String[] {"name"});

                if (templateNameValues.containsKey("name")) {
                    List<String> templateNameList = templateNameValues.get("name");
                    if (templateNameList != null) {
                        templateNames = new String[templateNameList.size()];
                        templateNameList.toArray(templateNames);
                        String templateNameLogStr = "\n\n";
                        if ((templateNames != null) && (templateNames.length > 0)) {
                            templateNameLogStr += templateNames[0];
                            for (int i = 1; i < templateNames.length; i++) {
                                templateNameLogStr = templateNameLogStr + "\n" + templateNames[i];
                            }

                        }
                        templateNameLogStr += "\n\n";
                        s_logger.info("got template names: " + templateNameLogStr);
                    }
                }

            } else {
                s_logger.error("list templates failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // LIST SERVICE OFFERINGS
            // ----------------------------------

            requestToSign = "apikey=" + encodedApiKey + "&command=listServiceOfferings";
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listServiceOfferings&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
            String[] serviceOfferingNames = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list service offerings response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> serviceOfferingNameValues = getMultipleValuesFromXML(is, new String[] {"name"});

                if (serviceOfferingNameValues.containsKey("name")) {
                    List<String> serviceOfferingNameList = serviceOfferingNameValues.get("name");
                    if (serviceOfferingNameList != null) {
                        serviceOfferingNames = new String[serviceOfferingNameList.size()];
                        serviceOfferingNameList.toArray(serviceOfferingNames);
                        String serviceOfferingNameLogStr = "";
                        if ((serviceOfferingNames != null) && (serviceOfferingNames.length > 0)) {
                            serviceOfferingNameLogStr = serviceOfferingNames[0];
                            for (int i = 1; i < serviceOfferingNames.length; i++) {
                                serviceOfferingNameLogStr = serviceOfferingNameLogStr + ", " + serviceOfferingNames[i];
                            }
                        }
                        s_logger.info("got service offering names: " + serviceOfferingNameLogStr);
                    }
                }

            } else {
                s_logger.error("list service offerings failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // LIST EVENTS
            // ---------------------------------

            url = server + "?command=listEvents&page=1&pagesize=100&&account=" + s_account.get();
            String[] eventDescriptions = null;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list events response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, List<String>> eventNameValues = getMultipleValuesFromXML(is, new String[] {"description"});

                if (eventNameValues.containsKey("description")) {
                    List<String> eventNameList = eventNameValues.get("description");
                    if (eventNameList != null) {
                        eventDescriptions = new String[eventNameList.size()];
                        eventNameList.toArray(eventDescriptions);
                        String eventNameLogStr = "\n\n";
                        if ((eventDescriptions != null) && (eventDescriptions.length > 0)) {
                            eventNameLogStr += eventDescriptions[0];
                            for (int i = 1; i < eventDescriptions.length; i++) {
                                eventNameLogStr = eventNameLogStr + "\n" + eventDescriptions[i];
                            }
                        }
                        eventNameLogStr += "\n\n";
                        s_logger.info("got event descriptions: " + eventNameLogStr);
                    }
                }
            } else {
                s_logger.error("list events failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // ----------------------------------
            // STOP/DESTROY VIRTUAL MACHINES
            // ----------------------------------
            if (vmIds != null) {
                String cmdName = (destroy ? "destroyVirtualMachine" : "stopVirtualMachine");
                for (String vmId : vmIds) {
                    requestToSign = "apikey=" + encodedApiKey + "&command=" + cmdName + "&id=" + vmId;
                    requestToSign = requestToSign.toLowerCase();
                    signature = signRequest(requestToSign, s_secretKey.get());
                    encodedSignature = URLEncoder.encode(signature, "UTF-8");

                    url = developerServer + "?command=" + cmdName + "&id=" + vmId + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
                    client = new HttpClient();
                    method = new GetMethod(url);
                    responseCode = client.executeMethod(method);
                    s_logger.info(cmdName + " [" + vmId + "] response code: " + responseCode);
                    if (responseCode == 200) {
                        InputStream input = method.getResponseBodyAsStream();
                        Element el = queryAsyncJobResult(server, input);
                        Map<String, String> success = getSingleValueFromXML(el, new String[] {"success"});
                        s_logger.info(cmdName + "..success? " + success.get("success"));
                    } else {
                        s_logger.error(cmdName + "test failed with error code: " + responseCode + ". Following URL was sent: " + url);
                        return responseCode;
                    }
                }
            }
        }

        {
            String[] ipAddresses = null;
            // -----------------------------------------
            // LIST NAT IP ADDRESSES
            // -----------------------------------------
            String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            String requestToSign = "apikey=" + encodedApiKey + "&command=listPublicIpAddresses";
            requestToSign = requestToSign.toLowerCase();
            String signature = signRequest(requestToSign, s_secretKey.get());
            String encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=listPublicIpAddresses&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
            if (responseCode == 200) {

                InputStream is = method.getResponseBodyAsStream();
                List<String> ipAddressList = getNonSourceNatIPs(is);
                ipAddresses = new String[ipAddressList.size()];
                ipAddressList.toArray(ipAddresses);
                String ipAddrLogStr = "";
                if ((ipAddresses != null) && (ipAddresses.length > 0)) {
                    ipAddrLogStr = ipAddresses[0];
                    for (int i = 1; i < ipAddresses.length; i++) {
                        ipAddrLogStr = ipAddrLogStr + "," + ipAddresses[i];
                    }
                }
                s_logger.info("got ip addresses: " + ipAddrLogStr);

            } else {
                s_logger.error("list nat ip addresses failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // -------------------------------------------------------------
            // Delete IP FORWARDING RULE -- Windows VM
            // -------------------------------------------------------------
            String encodedIpFwdId = URLEncoder.encode(s_winipfwdid.get(), "UTF-8");

            requestToSign = "apikey=" + encodedApiKey + "&command=deleteIpForwardingRule&id=" + encodedIpFwdId;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=deleteIpForwardingRule&apikey=" + encodedApiKey + "&id=" + encodedIpFwdId + "&signature=" + encodedSignature;

            s_logger.info("Delete Ip forwarding rule with " + url);
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                s_logger.info("IP forwarding rule was successfully deleted");

            } else {
                s_logger.error("IP forwarding rule creation failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            //--------------------------------------------
            // Disable Static NAT for the Source NAT Ip
            //--------------------------------------------
            encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
            String encodedPublicIpId = URLEncoder.encode(s_publicIpId.get(), "UTF-8");
            requestToSign = "apikey=" + encodedApiKey + "&command=disableStaticNat" + "&id=" + encodedPublicIpId;
            requestToSign = requestToSign.toLowerCase();
            signature = signRequest(requestToSign, s_secretKey.get());
            encodedSignature = URLEncoder.encode(signature, "UTF-8");

            url = developerServer + "?command=disableStaticNat&apikey=" + encodedApiKey + "&id=" + encodedPublicIpId + "&signature=" + encodedSignature;
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("url is " + url);
            s_logger.info("list ip addresses for user " + userId + " response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, String> success = getSingleValueFromXML(is, new String[] {"success"});
                s_logger.info("Disable Static NAT..success? " + success.get("success"));
            } else {
                s_logger.error("Disable Static NAT failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }

            // -----------------------------------------
            // DISASSOCIATE IP ADDRESSES
            // -----------------------------------------
            if (ipAddresses != null) {
                for (String ipAddress : ipAddresses) {
                    requestToSign = "apikey=" + encodedApiKey + "&command=disassociateIpAddress&id=" + ipAddress;
                    requestToSign = requestToSign.toLowerCase();
                    signature = signRequest(requestToSign, s_secretKey.get());
                    encodedSignature = URLEncoder.encode(signature, "UTF-8");

                    url = developerServer + "?command=disassociateIpAddress&apikey=" + encodedApiKey + "&id=" + ipAddress + "&signature=" + encodedSignature;
                    client = new HttpClient();
                    method = new GetMethod(url);
                    responseCode = client.executeMethod(method);
                    s_logger.info("disassociate ip address [" + userId + "/" + ipAddress + "] response code: " + responseCode);
                    if (responseCode == 200) {
                        InputStream input = method.getResponseBodyAsStream();
                        Element disassocipel = queryAsyncJobResult(server, input);
                        Map<String, String> success = getSingleValueFromXML(disassocipel, new String[] {"success"});
                        //       Map<String, String> success = getSingleValueFromXML(input, new String[] { "success" });
                        s_logger.info("disassociate ip address..success? " + success.get("success"));
                    } else {
                        s_logger.error("disassociate ip address failed with error code: " + responseCode + ". Following URL was sent: " + url);
                        return responseCode;
                    }
                }
            }
        }
        s_linuxIP.set("");
        s_linuxIpId.set("");
        s_linuxVmId.set("");
        s_linuxPassword.set("");
        s_windowsIP.set("");
        s_windowsIpId.set("");
        s_windowsVmId.set("");
        s_secretKey.set("");
        s_apiKey.set("");
        s_userId.set(Long.parseLong("0"));
        s_account.set("");
        s_domainRouterId.set("");
        return responseCode;
    }

};