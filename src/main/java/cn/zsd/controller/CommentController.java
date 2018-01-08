package cn.zsd.controller;

import cn.zsd.model.BlogEntity;
import cn.zsd.model.CommentEntity;
import cn.zsd.repository.BlogRepository;
import cn.zsd.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Da on 2017/2/20.
 */
@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BlogRepository blogRepository;

    // 查看所有评论
    @RequestMapping(value = "/admin/comments", method = RequestMethod.GET)
    public String showComments(ModelMap modelMap) {
        List<CommentEntity> commentList = commentRepository.findAll();
        modelMap.addAttribute("commentList", commentList);
        return "admin/comments";
    }

    // 添加评论
    @RequestMapping(value = "/admin/comments/add", method = RequestMethod.GET)
    public String addComment(ModelMap modelMap) {
        List<BlogEntity> blogList = blogRepository.findAll();
        // 向jsp注入博客列表
        modelMap.addAttribute("blogList", blogList);
        return "admin/addComment";
    }

    // 添加评论，POST请求，重定向为查看评论页面
    @RequestMapping(value = "/admin/comments/addP", method = RequestMethod.POST)
    public String addCommentPost(@ModelAttribute("comment") CommentEntity commentEntity) {
        // 打印博客ID
        System.out.println(commentEntity.getBlogByBlogId().getTitle());
        // 存库
        commentRepository.saveAndFlush(commentEntity);
        // 重定向地址
        return "redirect:/admin/comments";
    }

    // 查看博文评论，默认使用GET方法时，method可以缺省
    @RequestMapping("/admin/comments/show/{id}")
    public String showComment(@PathVariable("id") int id, ModelMap modelMap) {
        CommentEntity comment = commentRepository.findOne(id);
        modelMap.addAttribute("comment", comment);
        return "admin/commentDetail";
    }

    // 修改博文评论，页面
    @RequestMapping("/admin/comments/update/{id}")
    public String updateComment(@PathVariable("id") int id, ModelMap modelMap) {
        // 是不是和上面那个方法很像
        CommentEntity comment = commentRepository.findOne(id);
        List<BlogEntity> blogList = blogRepository.findAll();
        modelMap.addAttribute("comment", comment);
        modelMap.addAttribute("blogList", blogList);
        return "admin/updateComment";
    }

    // 修改博客评论，POST请求
    @RequestMapping(value = "/admin/comments/updateP", method = RequestMethod.POST)
    public String updateCommentP(@ModelAttribute("commentP") CommentEntity commentEntity) {
        // 更新博客评论
        commentRepository.updateComment(commentEntity.getBlogByBlogId().getId(),
                commentEntity.getContent(), commentEntity.getPubDate(), commentEntity.getId());
        commentRepository.flush();
        return "redirect:/admin/comments";
    }

 /*   // 删除博客文章
    @RequestMapping("/admin/comments/delete/{id}")
    public String deleteComment(@PathVariable("id") int id) {
        commentRepository.delete(id);
        commentRepository.flush();
        return "redirect:/admin/comments";
    }*/
}
