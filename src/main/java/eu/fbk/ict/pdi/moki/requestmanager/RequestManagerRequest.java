package eu.fbk.ict.pdi.moki.requestmanager;

public class RequestManagerRequest {
	private String serviceName;
	private String locationService;
	private Object data;
	
	RequestManagerRequest (String serviceName, String locationService, Object data) {
		this.serviceName = serviceName;
		this.data = data;
		this.locationService = locationService;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getLocationService() {
		return locationService;
	}

	public void setLocationService(String locationService) {
		this.locationService = locationService;
	}
	
	
}
