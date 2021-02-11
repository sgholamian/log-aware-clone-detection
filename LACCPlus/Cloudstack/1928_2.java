//,temp,RegionsApiUtil.java,179,208,temp,RegionsApiUtil.java,137,169
//,3
public class xxx {
    protected static RegionDomain makeDomainAPICall(Region region, String command, List<NameValuePair> params) {
        try {
            String url = buildUrl(buildParams(command, params), region);
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
            if (client.executeMethod(method) == 200) {
                InputStream is = method.getResponseBodyAsStream();
                XStream xstream = new XStream(new DomDriver());
                //Translate response to Domain object
                xstream.alias("domain", RegionDomain.class);
                xstream.aliasField("id", RegionDomain.class, "uuid");
                xstream.aliasField("parentdomainid", RegionDomain.class, "parentUuid");
                xstream.aliasField("networkdomain", DomainVO.class, "networkDomain");
                try(ObjectInputStream in = xstream.createObjectInputStream(is);) {
                    return (RegionDomain) in.readObject();
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