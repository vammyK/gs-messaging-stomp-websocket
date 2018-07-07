package org.siemens.mindverse.service;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.siemens.mindverse.model.Asset;
import org.siemens.mindverse.repos.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AssetService {

	@Autowired
	AssetRepository assetRepository;

	@Autowired 
	ImageMatchingService imageMatchingService;
	
	@Value("${static_file_path}")
	String staticFilePath;

	public Asset save(Asset asset, MultipartFile file) throws IOException, JSONException {

		assetRepository.save(asset);
		String fullPath = staticFilePath + asset.getId().toString() + "." + getExtension(file.getContentType());
		File saveFile= new File(fullPath);
		file.transferTo(saveFile);
		asset.setUrl("/images/" + asset.getId() + "." + getExtension(file.getContentType()));
		imageMatchingService.indexImage(asset,saveFile.getAbsolutePath());
		assetRepository.save(asset);
		return asset;
	}
	
	public String getExtension(String fileType) {
		return fileType.split("/")[1];
	}

	public Asset addImage(MultipartFile file, Long assetId) throws IllegalStateException, IOException, JSONException {
		Asset asset=assetRepository.findById(assetId).get();
		String fullPath = staticFilePath + asset.getId().toString() + "." + getExtension(file.getContentType());
		File saveFile= new File(fullPath);
		file.transferTo(saveFile);
		asset.setUrl("/images/" + asset.getId() + "." + getExtension(file.getContentType()));
		imageMatchingService.indexImage(asset,saveFile.getAbsolutePath());
		
		return asset;
		
	}

	public Asset searchAssets(MultipartFile file) throws IOException, JSONException {
		// TODO Auto-generated method stub
		String fullPath = staticFilePath + "test"+ "." + getExtension(file.getContentType());

		File testFile= new File(fullPath);
		file.transferTo(testFile);
		Long id=imageMatchingService.searchImage(testFile.getAbsolutePath());
		return assetRepository.findById(id).get();
	}

}
