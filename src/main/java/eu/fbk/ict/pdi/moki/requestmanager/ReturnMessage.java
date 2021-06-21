package eu.fbk.ict.pdi.moki.requestmanager;

import com.google.gson.JsonObject;

public class ReturnMessage {
	private String name_service;
	private String type_service;
	private String message_text;
	private long time;
	private Object data;
	private JsonObject json;
	
	public ReturnMessage () {}
	
	public String getName_service() {
		return name_service;
	}
	public void setName_service(String name_service) {
		this.name_service = name_service;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public String getType_service() {
		return type_service;
	}
	public void setType_service(String type_service) {
		this.type_service = type_service;
	}
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public JsonObject getJson() {
		return json;
	}
	public void setJson(JsonObject json) {
		this.json = json;
	}
}
