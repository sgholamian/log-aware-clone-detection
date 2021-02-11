//,temp,NetscalerResource.java,1259,1290,temp,NetscalerResource.java,1192,1227
//,3
public class xxx {
        private static void createSite(final nitro_service client, final String siteName, final String siteType, final String siteIP, final String sitePublicIP) throws ExecutionException {
            try {
                gslbsite site;
                site = getSiteObject(client, siteName);

                boolean isUpdateSite = false;
                if (site == null) {
                    site = new gslbsite();
                } else {
                    isUpdateSite = true;
                }

                assert "LOCAL".equalsIgnoreCase(siteType) || "REMOTE".equalsIgnoreCase(siteType);
                site.set_sitetype(siteType);
                site.set_sitename(siteName);
                site.set_siteipaddress(siteIP);
                site.set_publicip(sitePublicIP);
                site.set_metricexchange("ENABLED");
                site.set_nwmetricexchange("ENABLED");
                site.set_sessionexchange("ENABLED");
                if (isUpdateSite) {
                    gslbsite.update(client, site);
                } else {
                    gslbsite.add(client, site);
                }
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Successfully created GSLB site: " + siteName);
                }
            } catch (final Exception e) {
                final String errMsg = "Failed to create GSLB site: " + siteName + " due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};