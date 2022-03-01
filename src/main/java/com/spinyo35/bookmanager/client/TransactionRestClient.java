package com.spinyo35.bookmanager.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import org.springframework.stereotype.Component;

@Component
public class TransactionRestClient {
	private final String POST_TRANSACTION = "https://lab.v.co.zw/interview/api/transaction";

	private String responseCode;
	private String responseDescription;
	
	public String purchaseBook(Long bookId,double price, int card) {
		String APIKey = "9ca3d5ed-dc04-4700-8dd6-7d60c3cdf0fa";

		String token = "Bearer " + APIKey;
		String narration = "Purchase book[id='" + bookId + "']";
		String cardNumber;
		switch (card) {
		case 1:
			cardNumber = "1234560000000001";
			break;
		case 2:
			cardNumber = "1234560000000002";
			break;
		case 3:
			cardNumber = "1234560000000003";
			break;
		case 4:
			cardNumber = "1234560000000004";
			break;
		default:
			cardNumber = "1234560000000003";
			break;
		}

		try {
			URL url = new URL(POST_TRANSACTION);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Content-Type", "application/json");
			http.setRequestProperty("Authorization", token);
			http.setRequestProperty("Content-Length", "356");
			http.setRequestProperty("Host", "localhost:8080");

			String data = "{\"type\" : \"PURCHASE\",\"extendedType\" : \"NONE\",\"amount\" : " + price
					+ ",\"created\" : \"2022-02-14T11:28:39.4+02:00\",\"card\" : {\"id\" : \"" + cardNumber
					+ "\",\"expiry\" : \"2020-01-01\"},\"reference\" : \"33d0893a-4c0e-49cf-a373-7e67a64ce036\",\"narration\" : \""
					+ narration + "\",\"additionalData\" : {\"SampleKey\" : \"This is a sample value\"}}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream;

			stream = http.getOutputStream();
			stream.write(out);

			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int length; (length = http.getInputStream().read(buffer)) != -1;) {
				result.write(buffer, 0, length);
			}

			JSONObject obj = new JSONObject(result.toString("UTF-8"));

			responseCode = obj.getString("responseCode");
			responseDescription = obj.getString("responseDescription");
			
			http.disconnect();
			return "SUCCESS";
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	
	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	
}
