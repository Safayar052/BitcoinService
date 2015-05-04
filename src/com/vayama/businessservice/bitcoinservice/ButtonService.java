package com.vayama.businessservice.bitcoinservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vayama.jsonobject.Button;

public class ButtonService {
	
	private static final String COINBASE_API_KEY = "oorwHHvct2Rdu30k";
	private static final String COINBASE_API_SECRET = "9FtUCd6SQEfdnlqx2Z4DKLDwzXYXoj7x";
	
	private static final String COINBASE_API_URL = "https://api.sandbox.coinbase.com/v1/buttons";
	
	public String createButton() throws IOException{
		
		// Populate this from Amar's UI input
		Button buttonParams = new Button();
		buttonParams.setName("test");
		buttonParams.setType("buy_now");
		buttonParams.setSubscription(false);
		buttonParams.setPriceString("1.23");
		buttonParams.setPriceCurrencyIso("USD");
		buttonParams.setCustom("Order123");
		buttonParams.setCallbackUrl("http://www.example.com/my_custom_button_callback");
		buttonParams.setSuccessUrl("http://www.vayama.com/abc.jsp?result=success");
		buttonParams.setCancelUrl("http://www.vayama.com/abc.jsp?result=failure");
		buttonParams.setDescription("Sample description");
		buttonParams.setStyle("custom_large");
		buttonParams.setIncludeEmail(true);
		
		URL buttonsUrl;
        try {
            buttonsUrl = new URL(COINBASE_API_URL);
        } catch (MalformedURLException ex) {
            throw new AssertionError(ex);
        }
        
        Request request = new Request();
        request.setButton(buttonParams);
        
        String body = null;
        if (request != null) {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        	mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            try {
				body = mapper.writeValueAsString(request);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
        }
        
        String nonce = String.valueOf(System.currentTimeMillis());
        String message = nonce + buttonsUrl.toString() + (body != null ? body : "");
        
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(COINBASE_API_SECRET.getBytes(), "HmacSHA256"));
        } catch (Exception t) {
            t.printStackTrace();
        }
        
        String signature = new String(Hex.encodeHex(mac.doFinal(message.getBytes())));
        
        URLConnection urlConnection;
        HttpsURLConnection conn;
        
        InputStream is = null;
        OutputStream outputStream = null;
		try {
			urlConnection = buttonsUrl.openConnection();
			if (!(urlConnection instanceof HttpsURLConnection)) {
	            throw new RuntimeException(
	                    "Custom Base URL must return javax.net.ssl.HttpsURLConnection on openConnection.");
	        }
			conn = (HttpsURLConnection) urlConnection;
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("ACCESS_KEY", COINBASE_API_KEY);
	        conn.setRequestProperty("ACCESS_SIGNATURE", signature);
	        conn.setRequestProperty("ACCESS_NONCE", nonce);
	        
	        if (body != null) {
	            conn.setDoOutput(true);
	            outputStream = conn.getOutputStream();
	            outputStream.write(body.getBytes(Charset.forName("UTF-8")));
	        }    
	        
	        is = conn.getInputStream();
	        return IOUtils.toString(is, "UTF-8");
		} catch (IOException e2) {
			e2.printStackTrace();
			if (is != null) {
                return IOUtils.toString(is, "UTF-8");
            }
		} finally {
			if (outputStream != null) {
				outputStream.close();
            }
            if (is != null) {
                is.close();
            }
        }
		return "{failed}";
	}

}
