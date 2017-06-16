package com;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.*;

public class JsonEncoder implements Encoder.Text<JSONObject> {

    @Override
    public void init(EndpointConfig config) {
    	System.out.println("init");
    }

    @Override
    public void destroy() {System.out.println("destroy");}

	@Override
	public String encode(JSONObject arg0) throws EncodeException {
		System.out.println(arg0);
		return arg0.toString();
	}
}

