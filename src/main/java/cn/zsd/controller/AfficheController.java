package cn.zsd.controller;

import cn.zsd.model.AfficheEntity;
import cn.zsd.repository.AfficheRepository;
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
public class AfficheController {
    @Autowired
    AfficheRepository afficheRepository;

    // 查看所有公告
    @RequestMapping(value = "/admin/affiches", method = RequestMethod.GET)
    public String showAffiches(ModelMap modelMap) {
        List<AfficheEntity> afficheList = afficheRepository.findAll();
        modelMap.addAttribute("afficheList", afficheList);
        return "admin/affiches";
    }

    // 添加公告
    @RequestMapping(value = "/admin/affiches/add", method = RequestMethod.GET)
    public String addAffiche( ) {
        return "admin/addAffiche";
    }

    // 添加公告，POST请求，重定向为查看公告页面
    @RequestMapping(value = "/admin/affiches/addP", method = RequestMethod.POST)
    public String addAffichePost(@ModelAttribute("affiche") AfficheEntity afficheEntity) {
        // 注意此处，post请求传递过来的是一个AfficheEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'affiche'，并创建这个对象
        // 存库
        afficheRepository.saveAndFlush(afficheEntity);
        // 重定向地址
        return "redirect:/admin/affiches";
    }

    // 查看公告详情，默认使用GET方法时，method可以缺省
    @RequestMapping("/admin/affiches/show/{id}")
    public String showAffiche(@PathVariable("id") int id, ModelMap modelMap) {
        AfficheEntity affiche = afficheRepository.findOne(id);
        modelMap.addAttribute("affiche", affiche);
        return "admin/afficheDetail";
    }

    // 修改公告内容，页面
    @RequestMapping("/admin/affiches/update/{id}")
    public String updateAffiche(@PathVariable("id") int id, ModelMap modelMap) {
        // 找到id所表示的用户
        AfficheEntity affiche = afficheRepository.findOne(id);
        // 传递给请求页面
        modelMap.addAttribute("affiche", affiche);
        return "admin/updateAffiche";
    }

    // 修改公告内容，POST请求
    @RequestMapping(value = "/admin/affiches/updateP", method = RequestMethod.POST)
    public String updateAfficheP(@ModelAttribute("afficheP") AfficheEntity afficheEntity) {
        // 更新公告信息
        afficheRepository.updateAffiche(afficheEntity.getTitle(), afficheEntity.getContent(),
                afficheEntity.getPubDate(), afficheEntity.getId());
        afficheRepository.flush();
        return "redirect:/admin/affiches";
    }

    // 删除公告
    @RequestMapping("/admin/affiches/delete/{id}")
    public String deleteAffiche(@PathVariable("id") int id) {
        afficheRepository.delete(id);
        afficheRepository.flush();
        return "redirect:/admin/affiches";
    }

}
