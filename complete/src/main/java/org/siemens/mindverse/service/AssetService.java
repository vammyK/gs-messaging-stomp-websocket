package org.siemens.mindverse.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONException;
import org.siemens.mindverse.model.Aspect;
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
	
	public Asset addAspects(Asset asset, String aspects) {
		
		String [] as= aspects.split(",");
		Set<Aspect> aspect=new HashSet<>();
		for(int i=0;i<as.length;i++) {
			Aspect a= new Aspect();
			a.setName(as[i]);
			aspect.add(a);
		}
		
		asset.setAspect(aspect);
		return asset;
		
	}

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

	public Asset addImage(MultipartFile file, String assetId) throws IllegalStateException, IOException, JSONException {
		Asset asset=assetRepository.findById(assetId).get();
		String fullPath = staticFilePath + asset.getId().toString() + "." + getExtension(file.getContentType());
		File saveFile= new File(fullPath);
		file.transferTo(saveFile);
		asset.setUrl("/images/" + asset.getId() + "." + getExtension(file.getContentType()));
		imageMatchingService.indexImage(asset,saveFile.getAbsolutePath());
		
		return asset;
		
	}

	public Asset searchAssets(MultipartFile file) throws IOException, JSONException {
		String fullPath = staticFilePath + "test"+ "." + getExtension(file.getContentType());

		File testFile= new File(fullPath);
		file.transferTo(testFile);
		String id=imageMatchingService.searchImage(testFile.getAbsolutePath());
		return assetRepository.findById(id).get();
	}

	public Asset onboardAsset(Asset ta) {
		
		return assetRepository.save(ta);
	}

	

}
