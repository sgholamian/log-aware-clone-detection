//,temp,NetscalerResource.java,1259,1290,temp,NetscalerResource.java,1192,1227
//,3
public class xxx {
        private static void updateSite(final nitro_service client, final String siteType, final String siteName, final String siteIP, final String sitePublicIP) throws ExecutionException {
            try {
                gslbsite site;
                site = getSiteObject(client, siteName);
                if (site == null) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.warn("Ignoring update request for non existing  GSLB site: " + siteName);
                    }
                    return;
                }
                assert "LOCAL".equalsIgnoreCase(siteType) || "REMOTE".equalsIgnoreCase(siteType);
                site.set_sitetype(siteType);
                site.set_sitename(siteName);
                site.set_siteipaddress(siteIP);
                site.set_publicip(sitePublicIP);
                site.set_metricexchange("ENABLED");
                site.set_nwmetricexchange("ENABLED");
                site.set_sessionexchange("ENABLED");
                gslbsite.update(client, site);

                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Successfully updated GSLB site: " + siteName);
                }

            } catch (final Exception e) {
                final String errMsg = "Failed to update GSLB site: " + siteName + " due to " + e.getMessage();
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug(errMsg);
                }
                throw new ExecutionException(errMsg);
            }
        }

};