package cn.zsd.controller;

import cn.zsd.model.TypeEntity;
import cn.zsd.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Da on 2017/2/19.
 */
@Controller
public class TypeController {
    @Autowired
    TypeRepository typeRepository;

    // 查看所有类型
    @RequestMapping(value = "/admin/types", method = RequestMethod.GET)
    public String showTypes(ModelMap modelMap) {
        List<TypeEntity> typeList = typeRepository.findAll();
        modelMap.addAttribute("typeList", typeList);
        return "admin/types";
    }

    // 添加博文类型
    @RequestMapping(value = "/admin/types/add", method = RequestMethod.GET)
    public String addType( ) {
        return "admin/addType";
    }

    // 添加博文类型，POST请求，重定向为查看博客页面
    @RequestMapping(value = "/admin/types/addP", method = RequestMethod.POST)
    public String addTypePost(@ModelAttribute("type") TypeEntity typeEntity) {
        // 注意此处，post请求传递过来的是一个TypeEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'type'，并创建这个对象
        // 存库
        typeRepository.saveAndFlush(typeEntity);
        // 重定向地址
        return "redirect:/admin/types";
    }

    // 查看博文类型详情，默认使用GET方法时，method可以缺省
    @RequestMapping("/admin/types/show/{id}")
    public String showType(@PathVariable("id") int id, ModelMap modelMap) {
        TypeEntity type = typeRepository.findOne(id);
        modelMap.addAttribute("type", type);
        return "admin/typeDetail";
    }

    // 修改博文类型内容，页面
    @RequestMapping("/admin/types/update/{id}")
    public String updateType(@PathVariable("id") int id, ModelMap modelMap) {
        // 找到id所表示的用户
        TypeEntity type = typeRepository.findOne(id);
        // 传递给请求页面
        modelMap.addAttribute("type", type);
        return "admin/updateType";
    }

    // 修改博客类型内容，POST请求
    @RequestMapping(value = "/admin/types/updateP", method = RequestMethod.POST)
    public String updateTypeP(@ModelAttribute("typeP") TypeEntity typeEntity) {
        // 更新博客信息
        typeRepository.updateType(typeEntity.getName(), typeEntity.getDescription(),typeEntity.getId());
        typeRepository.flush();
        return "redirect:/admin/types";
    }

    // 删除博客文章
    @RequestMapping("/admin/types/delete/{id}")
    public String deleteType(@PathVariable("id") int id) {
        typeRepository.delete(id);
        typeRepository.flush();
        return "redirect:/admin/types";
    }
}
