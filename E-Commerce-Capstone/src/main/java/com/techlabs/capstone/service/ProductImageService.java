package com.techlabs.capstone.service;

import java.util.List;

public interface ProductImageService {

	void updateProductImages(int productId, List<String> newImageLinks);

	void deleteImage(int productId, String imageLink);

}
