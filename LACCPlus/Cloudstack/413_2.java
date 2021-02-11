//,temp,PaloAltoResource.java,524,542,temp,PaloAltoResource.java,506,522
//,3
public class xxx {
    private void implementGuestNetwork(ArrayList<IPaloAltoCommand> cmdList, GuestNetworkType type, Long publicVlanTag, String publicIp, long privateVlanTag,
        String privateGateway, String privateSubnet, long privateCidrNumber) throws ExecutionException {
        privateSubnet = privateSubnet + "/" + privateCidrNumber;

        managePrivateInterface(cmdList, PaloAltoPrimative.ADD, privateVlanTag, privateGateway + "/" + privateCidrNumber);

        if (type.equals(GuestNetworkType.SOURCE_NAT)) {
            managePublicInterface(cmdList, PaloAltoPrimative.ADD, publicVlanTag, publicIp + "/32", privateVlanTag);
            manageSrcNatRule(cmdList, PaloAltoPrimative.ADD, type, publicVlanTag, publicIp + "/32", privateVlanTag, privateGateway + "/" + privateCidrNumber);
            manageNetworkIsolation(cmdList, PaloAltoPrimative.ADD, privateVlanTag, privateSubnet, privateGateway);
        }

        String msg =
            "Implemented guest network with type " + type + ". Guest VLAN tag: " + privateVlanTag + ", guest gateway: " + privateGateway + "/" + privateCidrNumber;
        msg += type.equals(GuestNetworkType.SOURCE_NAT) ? ", source NAT IP: " + publicIp : "";
        s_logger.debug(msg);
    }

};