package com.survey.geneza.service.impl;
import com.survey.geneza.persistence.ImageRepository;
import com.survey.geneza.domain.Image;
import com.survey.geneza.service.ImageService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ImageService")
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    

    public ImageServiceImpl() {
    }

    @Transactional
    public Image findById(Integer id) {
        return imageRepository.findById(id);
    }

    @Transactional
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
    
      
    
    @Transactional
    private Image setDefaultImage(Image image){
        

        return image;
    }

    
    
     
    @Transactional
    public Image saveImage(Image image,Integer uploadFlag) {
        Image existingImage = imageRepository.findById(image.getId());
        if (existingImage != null) {
        if (existingImage != image) {      
        existingImage.setId(image.getId());
         existingImage.setFileName(image.getFileName());
                existingImage.setFileType(image.getFileType());
                existingImage.setFileSize(image.getFileSize());
                existingImage.setDefaultImage(image.getDefaultImage());
                if (uploadFlag==1) existingImage.setImage(image.getImage());
                
        }
        image = imageRepository.save(existingImage);
    }else{
        image = imageRepository.save(image);
        }
        imageRepository.flush();
        return image;
    }
    
 
    

}