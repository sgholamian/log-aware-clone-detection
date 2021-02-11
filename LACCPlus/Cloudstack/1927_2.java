//,temp,RegionsApiUtil.java,137,169,temp,RegionsApiUtil.java,90,127
//,3
public class xxx {
    protected static RegionAccount makeAccountAPICall(Region region, String command, List<NameValuePair> params) {
        try {
            String url = buildUrl(buildParams(command, params), region);
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
            if (client.executeMethod(method) == 200) {
                InputStream is = method.getResponseBodyAsStream();
                //Translate response to Account object
                XStream xstream = new XStream(new DomDriver());
                xstream.alias("account", RegionAccount.class);
                xstream.alias("user", RegionUser.class);
                xstream.aliasField("id", RegionAccount.class, "uuid");
                xstream.aliasField("name", RegionAccount.class, "accountName");
                xstream.aliasField("accounttype", RegionAccount.class, "type");
                xstream.aliasField("domainid", RegionAccount.class, "domainUuid");
                xstream.aliasField("networkdomain", RegionAccount.class, "networkDomain");
                xstream.aliasField("id", RegionUser.class, "uuid");
                xstream.aliasField("accountId", RegionUser.class, "accountUuid");
                try(ObjectInputStream in = xstream.createObjectInputStream(is);) {
                    return (RegionAccount) in.readObject();
                }catch (IOException e) {
                    s_logger.error(e.getMessage());
                    return null;
                }
            } else {
                return null;
            }
        } catch (HttpException e) {
            s_logger.error(e.getMessage());
            return null;
        } catch (IOException e) {
            s_logger.error(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            s_logger.error(e.getMessage());
            return null;
        }
    }

};