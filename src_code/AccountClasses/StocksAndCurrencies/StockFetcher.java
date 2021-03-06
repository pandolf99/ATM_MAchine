package AccountClasses.StocksAndCurrencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import java.util.regex.*;


public class StockFetcher implements Serializable {
	private String apiKey;

	public StockFetcher(String apiKey){
		this.apiKey = apiKey;
	}

	private String generateEndpoint(String symbol){
		return "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
	}

	public HashMap<String, Double> getCurrentStockInfo(String symbol) throws IOException {
		URL url = new URL(generateEndpoint(symbol));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();

		con.disconnect();
		return JSONStringToDict(content.toString());

	}

	private static HashMap<String, Double> JSONStringToDict(String json) {
		Pattern openPattern = Pattern.compile("02. open\"[^,]*");
		Pattern highPattern = Pattern.compile("03. high\": [^,]*");
		Pattern lowPattern = Pattern.compile("04. low\": [^,]*");
		Pattern pricePattern = Pattern.compile("05. price\": [^,]*");
		Pattern volumePattern = Pattern.compile("06. volume\": [^,]*");
		Pattern changePattern = Pattern.compile("09. change\": [^,]*");

		HashMap<String, Double> hashmap = new HashMap<>();

		Matcher openMatch = openPattern.matcher(json);
		if (openMatch.find()) {
			String x = openMatch.group(0).replace("02. open\": ", "");;
			hashmap.put("open", Double.parseDouble(x.replace("\"", "")));
		}

		Matcher highMatch = highPattern.matcher(json);
		if (highMatch.find()) {
			String x = highMatch.group(0).replace("03. high\": ", "");
			hashmap.put("high", Double.parseDouble(x.replace("\"", "")));
		}

		Matcher lowMatch = lowPattern.matcher(json);
		if (lowMatch.find()) {
			String x = lowMatch.group(0).replace("04. low\": ", "");
			hashmap.put("low", Double.parseDouble(x.replace("\"", "")));
		}


		Matcher priceMatch = pricePattern.matcher(json);
		if (priceMatch.find()) {
			String x = priceMatch.group(0).replace("05. price\": ", "");
			hashmap.put("price", Double.parseDouble(x.replace("\"", "")));
		}

		Matcher volumeMatch = volumePattern.matcher(json);
		if (volumeMatch.find()) {
			String x = volumeMatch.group(0).replace("06. volume\": ", "");
			hashmap.put("volume", Double.parseDouble(x.replace("\"", "")));
		}

		Matcher changeMatch = changePattern.matcher(json);
		if (changeMatch.find()) {
			String x = changeMatch.group(0).replace("09. change\": ", "");
			hashmap.put("change", Double.parseDouble(x.replace("\"", "")));
		}
		return hashmap;
	}

	public double getPrice(String symbol){
		try {
			return getCurrentStockInfo(symbol).get("price");
		}catch (IOException e){
			System.out.println("Stock can't be located");
			return 0.0;
		}
	}
}