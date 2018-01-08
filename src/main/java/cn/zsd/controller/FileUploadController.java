package cn.zsd.controller;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by Da on 2017/7/31.
 */
@Controller
public class FileUploadController {
    /*
    * 图片命名格式
    */
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmssSSS";

    protected Logger logger = Logger.getLogger(FileUploadController.class);

    /*
     * 上传图片文件夹
     */
    private static final String UPLOAD_PATH =
            File.separator+"WEB-INF"+File.separator+"pages"+File.separator+"upload"+File.separator+"img"+File.separator;

    /*
     * 上传图片
     */
    @RequestMapping(value = "/admin/blogs/uploadImg")
    public void uploadImg(@RequestParam("upload") MultipartFile file,
                          HttpServletRequest request,
                          HttpServletResponse response)
    {
        try {
            String path = request.getSession().getServletContext().getRealPath(UPLOAD_PATH);
            System.out.println(path);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
            String fileName = file.getOriginalFilename();
            String uploadContentType = file.getContentType();
            String expandedName = "";
            if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
                // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                expandedName = ".jpg";
            } else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
                // IE6上传的png图片的headimageContentType是"image/x-png"
                expandedName = ".png";
            } else if (uploadContentType.equals("image/gif")) {
                expandedName = ".gif";
            } else if (uploadContentType.equals("image/bmp")) {
                expandedName = ".bmp";
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
                out.println("</script>");
                return;
            }
            if (file.getSize() > 1024 * 1024 * 2) {
                out.println("<script type=\"text/javascript\">");
                out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",''," + "'文件大小不得大于2M');");
                out.println("</script>");
                return;
            }
            DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) + expandedName;
            file.transferTo(new File(path + File.separator + fileName));
            // 返回"图像"选项卡并显示图片 request.getContextPath()为web项目名

            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction" +
                    "(" + CKEditorFuncNum + ",'" + "WEB-INF/pages/upload/img/" + fileName + "','')");
            out.println("</script>");
            return;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
