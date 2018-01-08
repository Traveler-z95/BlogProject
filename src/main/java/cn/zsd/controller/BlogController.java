package cn.zsd.controller;

import cn.zsd.dao.BlogPageDao;
import cn.zsd.model.BlogEntity;
import cn.zsd.model.CommentEntity;
import cn.zsd.model.TypeEntity;
import cn.zsd.model.UserEntity;
import cn.zsd.repository.*;
import cn.zsd.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Da on 2017/2/19.
 */

@Controller
public class BlogController {

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BlogPageDao districtRepository;
    @Autowired
    BlogService blogService;


    // 查看所有博文
    @RequestMapping(value = "/admin/blogs", method = RequestMethod.GET)
    public String showBlogs(ModelMap modelMap, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber) {
       /*
       //下面二句是没分页功能的。单纯显示全部的信息
        List<BlogEntity> blogList = blogRepository.findAll();
        modelMap.addAttribute("blogList", blogList);
        return "admin/blogs";
        */
        //分页显示
        if (pageNumber == null || pageNumber == -1) {
            pageNumber = 0;
        }
        //pageNumber从0页开始
        int pageSize = 5;   //页面大小
        // PageRequest接口通常使用的起PageRequest实现类，其中封装了需要分页的信息
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        Page<BlogEntity> page = districtRepository.findAll(pageRequest);
        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("当前第几页：" + page.getNumber());
        System.out.println("总页数" + page.getTotalPages());
        System.out.println("当前页面的list：" + page.getContent());
        System.out.println("当前页面记录数：" + page.getNumberOfElements());

        modelMap.addAttribute("sourceCodeList", page.getContent());  //当前页面的list
        modelMap.addAttribute("totalPageNumber", page.getTotalElements());//总记录数
        modelMap.addAttribute("numberPage", page.getNumber());//当前第几页
        modelMap.addAttribute("totalPages", page.getTotalPages());//总页数

        return "admin/blogPage";
    }

    // 添加博文
    @RequestMapping(value = "/admin/blogs/add", method = RequestMethod.GET)
    public String addBlog(ModelMap modelMap) {
        List<UserEntity> userList = userRepository.findAll();
        List<TypeEntity> typeList = typeRepository.findAll();
        // 向jsp注入用户列表
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("typeList", typeList);
        return "admin/addBlog";
    }

    // 添加博文，POST请求，重定向为查看博客页面
    @RequestMapping(value = "/admin/blogs/addP", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") BlogEntity blogEntity) {
        // 打印博客标题
        System.out.println(blogEntity.getTitle());
        // 打印博客作者
        System.out.println(blogEntity.getUserByUserId().getUsername());
        // 打印博客类型
        System.out.println(blogEntity.getTypeByTypeId().getName());
        // 存库
        blogRepository.saveAndFlush(blogEntity);
        // 重定向地址
        return "redirect:/admin/blogs";
    }

    // 查看博文详情，默认使用GET方法时，method可以缺省
    @RequestMapping(value = "/admin/blogs/show/{id}", method = RequestMethod.GET)
    public String showBlog(@PathVariable("id") Integer blog_id, ModelMap modelMap) {
        BlogEntity blog = blogRepository.findOne(blog_id);
        //注意那个传过来的id
        List<CommentEntity> commentEntityList = commentRepository.findByBlogByBlogId(blog_id);
        for (CommentEntity comment : commentEntityList) {
            System.out.println("评论" + comment.getId());
            System.out.println(comment.getContent());
            System.out.println(comment.getPubDate());
        }
        modelMap.addAttribute("commentList", commentEntityList);
        modelMap.addAttribute("blog", blog);
        return "admin/blogDetail";
    }

    //条件查询
    @RequestMapping(value="/admin/blogs/query",method=RequestMethod.POST)
    public String queryBlog(String title,Date minDate,Date maxDate, ModelMap modelMap){
        List<BlogEntity> blogList = blogService.findByCondition(title,minDate, maxDate);
        for(BlogEntity blog : blogList){
            System.out.println("博客" + blog.getId());
            System.out.println(blog.getTitle());
            System.out.println(blog.getContent());
            System.out.println(blog.getPubDate());
        }
        modelMap.addAttribute("sourceCodeList", blogList);
        return  "admin/blogPage";
    }


    // 修改博文内容，页面
    @RequestMapping("/admin/blogs/update/{id}")
    public String updateBlog(@PathVariable("id") int id, ModelMap modelMap) {
        // 是不是和上面那个方法很像
        BlogEntity blog = blogRepository.findOne(id);
        List<UserEntity> userList = userRepository.findAll();
        List<TypeEntity> typeList = typeRepository.findAll();
        modelMap.addAttribute("blog", blog);
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("typeList",typeList);
        return "admin/updateBlog";
    }

    // 修改博客内容，POST请求
    @RequestMapping(value = "/admin/blogs/updateP", method = RequestMethod.POST)
    public String updateBlogP(@ModelAttribute("blogP") BlogEntity blogEntity) {
        // 更新博客信息
        blogRepository.updateBlog(blogEntity.getTitle(), blogEntity.getUserByUserId().getId(),
                blogEntity.getTypeByTypeId().getId(),blogEntity.getContent(),
                blogEntity.getPubDate(), blogEntity.getId());
        blogRepository.flush();
        return "redirect:/admin/blogs";
    }

    // 删除博客文章
    @RequestMapping("/admin/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") int id) {
        blogRepository.delete(id);
        blogRepository.flush();
        return "redirect:/admin/blogs";
    }
}
