package com.dunamis.lms.web.rest; 


import com.survey.geneza.persistence.ImageRepository;
import com.survey.geneza.domain.Image;
import com.survey.geneza.service.ImageService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.multipart.MultipartFile;
import java.sql.Blob;
import java.io.IOException;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.util.FileCopyUtils;
import org.hibernate.SessionFactory;


import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;


@Controller("ImageRestController")
public class ImageRestController {
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private ImageRepository imageRepository;
    @Autowired  
    SessionFactory sessionFactory;
    
     
    
    @RequestMapping(value = "/Image", method = RequestMethod.PUT)
    @ResponseBody
    public Image saveImage(@RequestBody Image image) {
        return imageService.saveImage(image,0);
    }

    @RequestMapping(value = "/Image", method = RequestMethod.POST)
    @ResponseBody
    public Image newImage(@RequestBody Image image) {
        return imageService.saveImage(image,0);
    }

    @RequestMapping(value = "/Image", method = RequestMethod.GET)
    @ResponseBody
    public List<Image> listImages() {
        return new java.util.ArrayList<Image>(imageService.findAll());
    }

    @RequestMapping(value = "/Image/{image_id}", method = RequestMethod.GET)
    @ResponseBody
    public Image loadImage(@PathVariable Integer image_id) {
        return imageService.findById(image_id);
    }

    

    @RequestMapping(value="/Image/Upload", method=RequestMethod.POST,headers = "content-type=multipart/*" )
    @ResponseBody
    public Image  saveImage( @RequestParam("file") MultipartFile file, @RequestParam("image") String imageStr){
        System.out.println(imageStr);
        Image image = new Image();
        try {
            ObjectMapper mapper = new ObjectMapper();
            image = mapper.readValue(imageStr, Image.class);
         } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }       

        try
        {
           Blob blob = Hibernate.getLobCreator(sessionFactory.openSession()).createBlob(file.getInputStream(), file.getSize());
            image.setImage(blob);
        } catch (HibernateException e) {
             e.printStackTrace();
        }
           catch (IOException e) {
              e.printStackTrace();
        }
       
        image = imageService.saveImage(image,1);
        return image;
    }


    
    @RequestMapping(value = "/Image/ImageById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        Image image = imageRepository.findById(id);
    
        try{
          Blob imageBlob = image.getImage();
          String fileName = image.getFileName();
              response.setContentType(image.getFileType());
              response.setHeader("Content-Disposition", String.format("inline; filename=\"" + image.getFileName() +"\""));
              response.setContentLength((int)image.getFileSize());
              
              FileCopyUtils.copy(imageBlob.getBinaryStream(), response.getOutputStream());
              imageBlob.free();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    

}