package cn.zsd.controller;

import cn.zsd.daoimpl.UploadedFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by Da on 2017/8/23.
 */
@Controller
public class H5FileUploadController {
    private static final Log logger = LogFactory.getLog(H5FileUploadController.class);
    @RequestMapping(value = "/upload/fileup")
    public String inputProduct(){
        return "upload/fileup";
    }
    @RequestMapping(value = "/upload/file_upload")
    public void saveFile(HttpServletRequest servletRequest,
                         @ModelAttribute UploadedFile uploadedFile,
                         BindingResult bindingResult, Model model){
        MultipartFile multipartFile = uploadedFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            File file = new File(servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/pages/upload/file"),fileName);
            multipartFile.transferTo(file);
            System.out.println(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
