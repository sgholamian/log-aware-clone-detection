//,temp,DefaultPackageScanResourceResolver.java,128,224,temp,DefaultPackageScanClassResolver.java,164,270
//,3
public class xxx {
    protected void find(PackageScanFilter test, String packageName, ClassLoader loader, Set<Class<?>> classes) {
        if (log.isTraceEnabled()) {
            log.trace("Searching for: {} in package: {} using classloader: {}",
                    new Object[] { test, packageName, loader.getClass().getName() });
        }

        Enumeration<URL> urls;
        try {
            urls = getResources(loader, packageName);
            if (!urls.hasMoreElements()) {
                log.trace("No URLs returned by classloader");
            }
        } catch (IOException ioe) {
            log.warn("Cannot read package: {}", packageName, ioe);
            return;
        }

        while (urls.hasMoreElements()) {
            URL url = null;
            try {
                url = urls.nextElement();
                log.trace("URL from classloader: {}", url);

                url = customResourceLocator(url);

                String urlPath = url.getFile();
                urlPath = URLDecoder.decode(urlPath, "UTF-8");
                if (log.isTraceEnabled()) {
                    log.trace("Decoded urlPath: {} with protocol: {}", urlPath, url.getProtocol());
                }

                // If it's a file in a directory, trim the stupid file: spec
                if (urlPath.startsWith("file:")) {
                    // file path can be temporary folder which uses characters that the URLDecoder decodes wrong
                    // for example + being decoded to something else (+ can be used in temp folders on Mac OS)
                    // to remedy this then create new path without using the URLDecoder
                    try {
                        urlPath = new URI(url.getFile()).getPath();
                    } catch (URISyntaxException e) {
                        // fallback to use as it was given from the URLDecoder
                        // this allows us to work on Windows if users have spaces in paths
                    }

                    if (urlPath.startsWith("file:")) {
                        urlPath = urlPath.substring(5);
                    }
                }

                // osgi bundles should be skipped
                if (url.toString().startsWith("bundle:") || urlPath.startsWith("bundle:")) {
                    log.trace("Skipping OSGi bundle: {}", url);
                    continue;
                }

                // bundle resource should be skipped
                if (url.toString().startsWith("bundleresource:") || urlPath.startsWith("bundleresource:")) {
                    log.trace("Skipping bundleresource: {}", url);
                    continue;
                }

                // Else it's in a JAR, grab the path to the jar
                if (urlPath.indexOf('!') > 0) {
                    urlPath = urlPath.substring(0, urlPath.indexOf('!'));
                }

                log.trace("Scanning for classes in: {} matching criteria: {}", urlPath, test);

                File file = new File(urlPath);
                if (file.isDirectory()) {
                    log.trace("Loading from directory using file: {}", file);
                    loadImplementationsInDirectory(test, packageName, file, classes);
                } else {
                    InputStream stream = null;
                    try {
                        if (urlPath.startsWith("http:") || urlPath.startsWith("https:")
                                || urlPath.startsWith("sonicfs:")
                                || isAcceptableScheme(urlPath)) {
                            // load resources using http/https, sonicfs and other acceptable scheme
                            // sonic ESB requires to be loaded using a regular URLConnection
                            log.trace("Loading from jar using url: {}", urlPath);
                            URL urlStream = new URL(urlPath);
                            URLConnection con = urlStream.openConnection();
                            // disable cache mainly to avoid jar file locking on Windows
                            con.setUseCaches(false);
                            stream = con.getInputStream();
                        } else {
                            log.trace("Loading from jar using file: {}", file);
                            stream = new FileInputStream(file);
                        }

                        // only create jar cache on-demand when needed
                        if (jarCache == null) {
                            // use a soft cache so it can be claimed if needed
                            jarCache = LRUCacheFactory.newLRUSoftCache(1000);
                        }

                        loadImplementationsInJar(test, packageName, stream, urlPath, classes, jarCache);
                    } finally {
                        IOHelper.close(stream);
                    }
                }
            } catch (IOException e) {
                // use debug logging to avoid being to noisy in logs
                log.debug("Cannot read entries in url: {}", url, e);
            }
        }
    }

};