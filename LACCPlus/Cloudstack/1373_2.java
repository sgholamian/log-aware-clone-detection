//,temp,ConsoleProxyServlet.java,418,484,temp,ConsoleProxyServlet.java,370,416
//,3
public class xxx {
    private String composeThumbnailUrl(String rootUrl, VirtualMachine vm, HostVO hostVo, int w, int h) {
        StringBuffer sb = new StringBuffer(rootUrl);

        String host = hostVo.getPrivateIpAddress();

        Pair<String, Integer> portInfo = _ms.getVncPort(vm);
        Ternary<String, String, String> parsedHostInfo = parseHostInfo(portInfo.first());

        String sid = vm.getVncPassword();
        String tag = vm.getUuid();

        int port = -1;
        if (portInfo.second() == -9) {
            //for hyperv
            port = Integer.parseInt(_ms.findDetail(hostVo.getId(), "rdp.server.port").getValue());
        } else {
            port = portInfo.second();
        }

        String ticket = genAccessTicket(parsedHostInfo.first(), String.valueOf(port), sid, tag);

        ConsoleProxyPasswordBasedEncryptor encryptor = new ConsoleProxyPasswordBasedEncryptor(getEncryptorPassword());
        ConsoleProxyClientParam param = new ConsoleProxyClientParam();
        param.setClientHostAddress(parsedHostInfo.first());
        param.setClientHostPort(portInfo.second());
        param.setClientHostPassword(sid);
        param.setClientTag(tag);
        param.setTicket(ticket);
        if (portInfo.second() == -9) {
            //For Hyperv Clinet Host Address will send Instance id
            param.setHypervHost(host);
            param.setUsername(_ms.findDetail(hostVo.getId(), "username").getValue());
            param.setPassword(_ms.findDetail(hostVo.getId(), "password").getValue());
        }
        if (parsedHostInfo.second() != null && parsedHostInfo.third() != null) {
            param.setClientTunnelUrl(parsedHostInfo.second());
            param.setClientTunnelSession(parsedHostInfo.third());
        }

        sb.append("/ajaximg?token=" + encryptor.encryptObject(ConsoleProxyClientParam.class, param));
        sb.append("&w=").append(w).append("&h=").append(h).append("&key=0");

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Compose thumbnail url: " + sb.toString());
        }
        return sb.toString();
    }

};