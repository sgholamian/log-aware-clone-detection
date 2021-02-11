//,temp,TestClientWithAPI.java,1001,1395,temp,StressTestDirectAttach.java,426,730
//,3
public class xxx {
    private static int executeCleanup(String server, String developerServer, String username) throws HttpException, IOException {
        // test steps:
        // - get user
        // - delete user

        // -----------------------------
        // GET USER
        // -----------------------------
        String userId = s_userId.get().toString();
        String encodedUserId = URLEncoder.encode(userId, "UTF-8");
        String url = server + "?command=listUsers&id=" + encodedUserId;
        s_logger.info("Cleaning up resources for user: " + userId + " with url " + url);
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        int responseCode = client.executeMethod(method);
        s_logger.info("get user response code: " + responseCode);
        if (responseCode == 200) {
            InputStream is = method.getResponseBodyAsStream();
            Map<String, String> userInfo = getSingleValueFromXML(is, new String[] {"username", "id", "account"});
            if (!username.equals(userInfo.get("username"))) {
                s_logger.error("get user failed to retrieve requested user, aborting cleanup test" + ". Following URL was sent: " + url);
                return -1;
            }

        } else {
            s_logger.error("get user failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // -----------------------------
        // UPDATE USER
        // -----------------------------
        {
            url = server + "?command=updateUser&id=" + userId + "&firstname=delete&lastname=me";
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("update user response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, String> success = getSingleValueFromXML(is, new String[] {"success"});
                s_logger.info("update user..success? " + success.get("success"));
            } else {
                s_logger.error("update user failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // -----------------------------
        // Detach existin dataVolume, create a new volume, attach it to the vm
        // -----------------------------
        {
            url = server + "?command=listVolumes&virtualMachineId=" + s_linuxVmId.get() + "&type=dataDisk";
            s_logger.info("Getting dataDisk id of Centos vm");
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("List volumes response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, String> success = getSingleValueFromXML(is, new String[] {"id"});
                s_logger.info("Got dataDiskVolume with id " + success.get("id"));
                s_dataVolume.set(success.get("id"));
            } else {
                s_logger.error("List volumes failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // Detach volume
        {
            url = server + "?command=detachVolume&id=" + s_dataVolume.get();
            s_logger.info("Detaching volume with id " + s_dataVolume.get());
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("Detach data volume response code: " + responseCode);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                s_logger.info("The volume was detached successfully");
            } else {
                s_logger.error("Detach data disk failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // Delete a volume
        {
            url = server + "?command=deleteVolume&id=" + s_dataVolume.get();
            s_logger.info("Deleting volume with id " + s_dataVolume.get());
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("Delete data volume response code: " + responseCode);
            if (responseCode == 200) {
                s_logger.info("The volume was deleted successfully");
            } else {
                s_logger.error("Delete volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // Create a new volume
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
                    long volumeId = Long.parseLong(values.get("id"));
                    s_logger.info("got volume id: " + volumeId);
                    s_newVolume.set(values.get("id"));
                }
            } else {
                s_logger.error("create volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // attach a new volume to the vm
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
                s_logger.info("The volume was attached successfully");
            } else {
                s_logger.error("Attach volume failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // Create a snapshot
        // list volumes
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
                    s_logger.error("Unable to get root volume. Followin url was sent: " + url);
                }
                s_logger.info("Got rootVolume with id " + success.get("id"));
                s_rootVolume.set(success.get("id"));
            } else {
                s_logger.error("List volumes failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // //Create snapshot from root disk volume
        String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
        String requestToSign = "apikey=" + encodedApiKey + "&command=createSnapshot&volumeid=" + s_rootVolume.get();
        requestToSign = requestToSign.toLowerCase();
        String signature = signRequest(requestToSign, s_secretKey.get());
        String encodedSignature = URLEncoder.encode(signature, "UTF-8");

        url = developerServer + "?command=createSnapshot&volumeid=" + s_rootVolume.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("Create snapshot response code: " + responseCode);
        if (responseCode == 200) {
            InputStream input = method.getResponseBodyAsStream();
            Element el = queryAsyncJobResult(server, input);
            Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

            if (values.get("id") == null) {
                s_logger.info("create snapshot response code: 401");
                return 401;
            } else {
                s_logger.info("create snapshot response code: " + responseCode + ". Got snapshot with id " + values.get("id"));
                s_snapshot.set(values.get("id"));
            }
        } else {
            s_logger.error("create snapshot failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // Create volume from the snapshot created on the previous step and attach it to the running vm
        /*      encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
        requestToSign = "apikey=" + encodedApiKey + "&command=createVolume&name=" + s_account.get() + "&snapshotid=" + s_snapshot.get();
        requestToSign = requestToSign.toLowerCase();
        signature = signRequest(requestToSign, s_secretKey.get());
        encodedSignature = URLEncoder.encode(signature, "UTF-8");

        url = developerServer + "?command=createVolume&name=" + s_account.get() + "&snapshotid=" + s_snapshot.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("Create volume from snapshot response code: " + responseCode);
        if (responseCode == 200) {
            InputStream input = method.getResponseBodyAsStream();
            Element el = queryAsyncJobResult(server, input);
            Map<String, String> values = getSingleValueFromXML(el, new String[] { "id" });

            if (values.get("id") == null) {
                s_logger.info("create volume from snapshot response code: 401");
                return 401;
            } else {
                s_logger.info("create volume from snapshot response code: " + responseCode + ". Got volume with id " + values.get("id") + ". The command was sent with url " + url);
                s_volumeFromSnapshot.set(values.get("id"));
            }
        } else {
            s_logger.error("create volume from snapshot failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        {
            url = server + "?command=attachVolume&id=" + s_volumeFromSnapshot.get() + "&virtualmachineid=" + s_linuxVmId.get();
            s_logger.info("Attaching volume with id " + s_volumeFromSnapshot.get() + " to the vm " + s_linuxVmId.get());
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("Attach volume from snapshot to linux vm response code: " + responseCode);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                s_logger.info("The volume created from snapshot was attached successfully to linux vm");
            } else {
                s_logger.error("Attach volume created from snapshot failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }
         */
        // -----------------------------
        // Execute reboot/stop/start commands for the VMs before deleting the account - made to exercise xen
        // -----------------------------

        // Reboot windows VM
        requestToSign = "apikey=" + encodedApiKey + "&command=rebootVirtualMachine&id=" + s_windowsVmId.get();
        requestToSign = requestToSign.toLowerCase();
        signature = signRequest(requestToSign, s_secretKey.get());
        encodedSignature = URLEncoder.encode(signature, "UTF-8");

        url = developerServer + "?command=rebootVirtualMachine&id=" + s_windowsVmId.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("Reboot windows Vm response code: " + responseCode);
        if (responseCode == 200) {
            InputStream input = method.getResponseBodyAsStream();
            Element el = queryAsyncJobResult(server, input);
            Map<String, String> success = getSingleValueFromXML(el, new String[] {"success"});
            s_logger.info("Windows VM was rebooted with the status: " + success.get("success"));
        } else {
            s_logger.error("Reboot windows VM test failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // Stop centos VM
        requestToSign = "apikey=" + encodedApiKey + "&command=stopVirtualMachine&id=" + s_linuxVmId.get();
        requestToSign = requestToSign.toLowerCase();
        signature = signRequest(requestToSign, s_secretKey.get());
        encodedSignature = URLEncoder.encode(signature, "UTF-8");

        url = developerServer + "?command=stopVirtualMachine&id=" + s_linuxVmId.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("Stop linux Vm response code: " + responseCode);
        if (responseCode == 200) {
            InputStream input = method.getResponseBodyAsStream();
            Element el = queryAsyncJobResult(server, input);
            Map<String, String> success = getSingleValueFromXML(el, new String[] {"success"});
            s_logger.info("Linux VM was stopped with the status: " + success.get("success"));
        } else {
            s_logger.error("Stop linux VM test failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // Create private template from root disk volume
        requestToSign =
            "apikey=" + encodedApiKey + "&command=createTemplate" + "&displaytext=" + s_account.get() + "&name=" + s_account.get() + "&ostypeid=11" + "&snapshotid=" +
                s_snapshot.get();
        requestToSign = requestToSign.toLowerCase();
        signature = signRequest(requestToSign, s_secretKey.get());
        encodedSignature = URLEncoder.encode(signature, "UTF-8");

        url =
            developerServer + "?command=createTemplate" + "&displaytext=" + s_account.get() + "&name=" + s_account.get() + "&ostypeid=11" + "&snapshotid=" +
                s_snapshot.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("Create private template response code: " + responseCode);
        if (responseCode == 200) {
            InputStream input = method.getResponseBodyAsStream();
            Element el = queryAsyncJobResult(server, input);
            Map<String, String> values = getSingleValueFromXML(el, new String[] {"id"});

            if (values.get("id") == null) {
                s_logger.info("create private template response code: 401");
                return 401;
            } else {
                s_logger.info("create private template response code: " + responseCode);
            }
        } else {
            s_logger.error("create private template failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // Start centos VM
        requestToSign = "apikey=" + encodedApiKey + "&command=startVirtualMachine&id=" + s_windowsVmId.get();
        requestToSign = requestToSign.toLowerCase();
        signature = signRequest(requestToSign, s_secretKey.get());
        encodedSignature = URLEncoder.encode(signature, "UTF-8");

        url = developerServer + "?command=startVirtualMachine&id=" + s_windowsVmId.get() + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
        client = new HttpClient();
        method = new GetMethod(url);
        responseCode = client.executeMethod(method);
        s_logger.info("Start linux Vm response code: " + responseCode);
        if (responseCode != 200) {
            s_logger.error("Start linux VM test failed with error code: " + responseCode + ". Following URL was sent: " + url);
            return responseCode;
        }

        // get domainRouter id
        {
            url = server + "?command=listRouters&zoneid=" + zoneId + "&account=" + s_account.get() + "&domainid=1";
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("List domain routers response code: " + responseCode);
            if (responseCode == 200) {
                InputStream is = method.getResponseBodyAsStream();
                Map<String, String> success = getSingleValueFromXML(is, new String[] {"id"});
                s_logger.info("Got the domR with id " + success.get("id"));
                s_domainRouterId.set(success.get("id"));
            } else {
                s_logger.error("List domain routers failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // reboot the domain router
        {
            url = server + "?command=rebootRouter&id=" + s_domainRouterId.get();
            s_logger.info("Rebooting domR with id " + s_domainRouterId.get());
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("Reboot domain router response code: " + responseCode);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                s_logger.info("Domain router was rebooted successfully");
            } else {
                s_logger.error("Reboot domain routers failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }

        // -----------------------------
        // DELETE ACCOUNT
        // -----------------------------
        {
            url = server + "?command=deleteAccount&id=" + s_accountId.get();
            client = new HttpClient();
            method = new GetMethod(url);
            responseCode = client.executeMethod(method);
            s_logger.info("delete account response code: " + responseCode);
            if (responseCode == 200) {
                InputStream input = method.getResponseBodyAsStream();
                Element el = queryAsyncJobResult(server, input);
                s_logger.info("Deleted account successfully");
            } else {
                s_logger.error("delete account failed with error code: " + responseCode + ". Following URL was sent: " + url);
                return responseCode;
            }
        }
        return responseCode;
    }

};