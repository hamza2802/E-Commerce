package com.techlabs.capstone.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {


	void uploadImages(int productId, MultipartFile[] file) throws IOException;


}
