package cn.zsd.daoimpl;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by Da on 2017/8/23.
 */
public class UploadedFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private MultipartFile multipartFile;
    public MultipartFile getMultipartFile(){
        return multipartFile;
    }
    public void setMultipartFile(MultipartFile multipartFile){
        this.multipartFile = multipartFile;
    }
}
