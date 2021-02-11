//,temp,ConsoleProxyThumbnailHandler.java,104,182,temp,ConsoleProxyAjaxImageHandler.java,64,159
//,3
public class xxx {
    private void doHandle(HttpExchange t) throws Exception, IllegalArgumentException {
        String queries = t.getRequestURI().getQuery();
        Map<String, String> queryMap = ConsoleProxyHttpHandlerHelper.getQueryMap(queries);

        String host = queryMap.get("host");
        String portStr = queryMap.get("port");
        String sid = queryMap.get("sid");
        String tag = queryMap.get("tag");
        String ticket = queryMap.get("ticket");
        String keyStr = queryMap.get("key");
        String console_url = queryMap.get("consoleurl");
        String console_host_session = queryMap.get("sessionref");
        String w = queryMap.get("w");
        String h = queryMap.get("h");

        int key = 0;
        int width = 144;
        int height = 110;

        if (tag == null)
            tag = "";

        int port;
        if (host == null || portStr == null || sid == null)
            throw new IllegalArgumentException();

        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            s_logger.warn("Invalid numeric parameter in query string: " + portStr);
            throw new IllegalArgumentException(e);
        }

        try {
            if (keyStr != null)
                key = Integer.parseInt(keyStr);
            if (null != w)
                width = Integer.parseInt(w);

            if (null != h)
                height = Integer.parseInt(h);

        } catch (NumberFormatException e) {
            s_logger.warn("Invalid numeric parameter in query string: " + keyStr);
            throw new IllegalArgumentException(e);
        }

        ConsoleProxyClientParam param = new ConsoleProxyClientParam();
        param.setClientHostAddress(host);
        param.setClientHostPort(port);
        param.setClientHostPassword(sid);
        param.setClientTag(tag);
        param.setTicket(ticket);
        param.setClientTunnelUrl(console_url);
        param.setClientTunnelSession(console_host_session);

        ConsoleProxyClient viewer = ConsoleProxy.getVncViewer(param);

        if (key == 0) {
            Image scaledImage = viewer.getClientScaledImage(width, height);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D bufImageGraphics = bufferedImage.createGraphics();
            bufImageGraphics.drawImage(scaledImage, 0, 0, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(8196);
            javax.imageio.ImageIO.write(bufferedImage, "jpg", bos);
            byte[] bs = bos.toByteArray();
            Headers hds = t.getResponseHeaders();
            hds.set("Content-Type", "image/jpeg");
            hds.set("Cache-Control", "no-cache");
            hds.set("Cache-Control", "no-store");
            t.sendResponseHeaders(200, bs.length);
            OutputStream os = t.getResponseBody();
            os.write(bs);
            os.close();
        } else {
            AjaxFIFOImageCache imageCache = viewer.getAjaxImageCache();
            byte[] img = imageCache.getImage(key);

            if (img != null) {
                Headers hds = t.getResponseHeaders();
                hds.set("Content-Type", "image/jpeg");
                t.sendResponseHeaders(200, img.length);

                OutputStream os = t.getResponseBody();
                try {
                    os.write(img, 0, img.length);
                } finally {
                    os.close();
                }
            } else {
                if (s_logger.isInfoEnabled())
                    s_logger.info("Image has already been swept out, key: " + key);
                t.sendResponseHeaders(404, -1);
            }
        }
    }

};