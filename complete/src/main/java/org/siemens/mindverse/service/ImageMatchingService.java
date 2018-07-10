package org.siemens.mindverse.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.siemens.mindverse.model.Asset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageMatchingService {
	
	@Value("${spring.server.name}")
	String serverName;
	
	@Value("${spring.server.http}")
	String serverHttp;

	public void indexImage(Asset asset, String fullFilePath) throws JSONException, IOException {
		
		String dem = "from elasticsearch import Elasticsearch\r\n"
				+ "from image_match.elasticsearch_driver import SignatureES\r\n" + "import sys\r\n"
				+ "es = Elasticsearch()\r\n" + "ses = SignatureES(es)\r\n"
				+ "ses.add_image(sys.argv[1],metadata= sys.argv[2])\r\n";
		BufferedWriter out = new BufferedWriter(new FileWriter("imageSearch.py"));
		out.write(dem);
		out.close();
		
		JSONObject child= new JSONObject();
		child.put("productId", asset.getId());
		ProcessBuilder pb = new ProcessBuilder("python", "imageSearch.py",
				fullFilePath,child.toString());
		Process p = pb.start();
		BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String s = null;
		while ((s = err.readLine()) != null) {
			System.out.println(s);
		}
	}
	
	public String searchImage(String fullFilePath) throws IOException, JSONException {
		String dem = "from elasticsearch import Elasticsearch\r\n"
				+ "from image_match.elasticsearch_driver import SignatureES\r\n" + "import sys\r\n"
				+ "es = Elasticsearch()\r\n" + "ses = SignatureES(es)\r\n"
				+ "data=ses.search_image(sys.argv[1],all_orientations=True)\r\n" + "print(data)";
		BufferedWriter out = new BufferedWriter(new FileWriter("imageSearch.py"));
		out.write(dem);
		out.close();

		ProcessBuilder pb = new ProcessBuilder("python", "imageSearch.py",
				fullFilePath);
		Process p = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String s = null;
		while ((s = err.readLine()) != null) {
			System.out.println(err.readLine());
		}
		StringBuffer imageMatchJson = new StringBuffer();
		s = null;
		while ((s = in.readLine()) != null) {
			imageMatchJson.append(s);
		}
		
		JSONArray jsonArray = new JSONArray(imageMatchJson.toString());
		JSONObject ret=new JSONObject();
		for(int i=0;i<jsonArray.length();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Double distance= jsonObject.getDouble("dist");
			if(distance<0.5) {
				JSONObject mdata= new JSONObject(jsonObject.getString("metadata"));
				return mdata.getString("productId");
			}
			
		}
		return null;
	}

}
