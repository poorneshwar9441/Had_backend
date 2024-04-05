package com.example.had_backend.service;

import com.example.had_backend.entity.Image;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public void saveImageForUser(UserInfo userInfo, byte[] imageData) {
        Image image = new Image();
        image.setData(imageData);
        image.setUserInfo(userInfo);
        imageRepository.save(image);
    }
    public Image getImageById(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        return imageOptional.orElse(null);
    }
}
