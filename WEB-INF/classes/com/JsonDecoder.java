package com;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonDecoder implements Decoder.Text<JSONObject> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		System.out.println("decode");

	}

	@Override
	public JSONObject decode(String arg0) throws DecodeException {
		JSONObject obj = null;
		try {
			obj = new JSONObject(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public boolean willDecode(String arg0) {
		return false;
	}

}
