//,temp,sample_2920.java,2,17,temp,sample_2919.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (latlng == null) {
latlng = endpoint.getLatlng();
}
if (latlng != null) {
GeocoderRequest req = new GeocoderRequest();
req.setLanguage(endpoint.getLanguage());
String lat = ObjectHelper.before(latlng, ",");
String lng = ObjectHelper.after(latlng, ",");
req.setLocation(new LatLng(lat, lng));
GeocodeResponse res = geocoder.geocode(req);


log.info("geocode response");
}
}

};