package org.siemens.mindverse.controllers;

import java.io.IOException;

import org.json.JSONException;
import org.siemens.mindverse.model.Asset;
import org.siemens.mindverse.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("asset/")
public class AssetController {

	@Autowired
	AssetService assetService;

	@PostMapping("searchAssets")
	Asset getAssets(@RequestParam MultipartFile file) throws IOException, JSONException {
		return assetService.searchAssets(file);
	}

	@PostMapping
	Asset saveAsset(@RequestParam MultipartFile file, @RequestParam String price, @RequestParam String name) throws IOException, JSONException {
		Asset asset = new Asset();
		asset.setName(name);
		asset.setPrice(new Double(price));
		asset.setUrl(file.getName());
		asset.setUrl("/images/");
		return assetService.save(asset, file);
	}
	
	@PostMapping("addImage")
	Asset addImage(@RequestParam MultipartFile file, @RequestParam Long assetId) throws IllegalStateException, IOException, JSONException {
		return assetService.addImage(file,assetId);
	}
	

}
