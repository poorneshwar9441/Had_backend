package com.example.had_backend.controller;
import com.example.had_backend.entity.Image;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.ImageService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.io.IOException;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/upload")
    public String getResult(){
         return "Hello world";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage( @RequestParam("file") MultipartFile file,@RequestParam("username") String username) {
         System.out.println("here this");
        try {
            // Retrieve user info from the database using the username
            UserInfo userInfo = userInfoService.getUserByUsername(username);
            if (userInfo == null) {
                //System.out.println("not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
            }

            byte[] imageData = file.getBytes();
            imageService.saveImageForUser(userInfo, imageData);

            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
        } catch (IOException e) {
            System.out.println("upload failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        try {
            // Retrieve the image from the service layer
            Image image = imageService.getImageById(id);
            if (image == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Return the image data and appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type based on image type
                    .body(image.getData());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}


