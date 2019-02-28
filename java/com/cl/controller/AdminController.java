package com.cl.controller;

import com.cl.constant.BlogConstant;
import com.cl.model.Vo.CommentVo;
import com.cl.model.Vo.ContentVo;
import com.cl.model.Vo.LinkVo;
import com.cl.model.Vo.UserVo;
import com.cl.service.*;
import com.cl.utils.commonTools.DateTool;
import com.cl.utils.commonTools.IpTool;
import com.cl.utils.commonTools.LoginTool;
import com.cl.utils.commonTools.RandTool;
import com.cl.utils.commonTools.ValidateTool;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger admin_logger = LoggerFactory.getLogger("admin_logger");
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private CommentService commentService;


    /**
     * 没有登录拦截器重定向到登录页面
     * 登录了才进入这个方法执行
     */
    @RequestMapping("")
    public String tologin() {
        return "admin/index";
    }

    /**
     * 不拦截此方法，如果拦截，里面又重定向到此方法，则会无限循环
     * 已经登录，跳过登录页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, Model model) {
        UserVo user = LoginTool.getLoginUser(request);
        if (user != null) {
            //model.addAttribute("user", user);
            return "admin/index";
        } else {
            return "admin/login";
        }
    }

    /**
     * 不拦截此方法，用户第一次登录需要去数据库判断
     * admin登录
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, String username, String password) {
        UserVo user = LoginTool.getLoginUser(request);
        if (user != null) {
        	//刷新session时长
        	request.getSession().setMaxInactiveInterval(BlogConstant.SESSION_TIME);
            return "admin/index";
        }
        //如果第一次登录，从数据库中查找
        user = userService.findUserByNameAndPassword(username, password);
        if (user == null) {
            model.addAttribute("errorMsg", "用户不存在！");
            return "admin/login";
        }
        //登录成功放入session
        else {
        	String ip=null;
        	try {
        		//获取实际登录ip，以免他人登录自己后台
        		ip=IpTool.getRealIp();
        	}catch(SocketException se) {
        		admin_logger.warn("获取实际ip错误，访问ip为：{}",IpTool.getIpAddrByRequest(request));
        	}
        	admin_logger.info("后台登录成功，ip为：{}，时间为：{}",ip==null?"无":ip,DateTool.getCurTime());
        	//放入session
            request.getSession().setAttribute(BlogConstant.LOGIN_SESSION_KEY, user);
            request.getSession().setMaxInactiveInterval(BlogConstant.SESSION_TIME);//自己后台登录，半小时后过期
            return "admin/index";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
    	request.getSession().setAttribute(BlogConstant.LOGIN_SESSION_KEY, null);
    	return "admin/index";
    }
    @RequestMapping("/changePass")
    public String changePassword() {
    	return "admin/changePass";
    }
    @RequestMapping("/savePass")
    public String savePassword(String pass) {
    	UserVo user = userService.findUserById(1);
    	user.setPassword(pass);
    	userService.saveUser(user);
    	return "admin/index";
    }

    //文章

    /**
     * 跟文章首页差不多
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("/manage_article")
    public String manageArticles(Integer page, Model model) {
        List<ContentVo> articles = articleService.findCurPageArticles(page, 10);
        long total = articleService.getTotal();
        model.addAttribute("articles", articles);
        model.addAttribute("curPage", page == null ? 0 : page);
        model.addAttribute("lastPage", (total + 9) / 10 - 1);
        return "admin/manageArticle";
    }
    /**
     * 写文章
     *
     * @return
     */
    @RequestMapping("/edit_article")
    public String toWriteArticle() {
        return "admin/edit_article";
    }

    /**
     * 编辑文章
     *
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/edit_article/{id}")
    public String toEditArticle(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        ContentVo article = articleService.findArticleById(id);
        redirectAttributes.addFlashAttribute("article", article);
        return "redirect:/admin/edit_article";
    }

    /**
     * 保存文章
     * 使用ajax提交吧
     *
     * @param article
     * @return
     */
    @RequestMapping("/saveArticle")
    public String postArticle(ContentVo article) {
        if (article.getHits() == null)
            article.setHits(0);
        if (article.getCommentsNum() == null)
            article.setCommentsNum(0);
        if (StringUtils.isBlank(article.getCategories()))
            article.setCategories("default");
        article.setAllowComment(true);
        article.setCreated(DateTool.getCurDate());
        article.setRand(RandTool.getRandPicId());
        articleService.saveArticle(article);
        admin_logger.info("保存文章【{}】成功，时间为：{}",article.getTitle(),DateTool.getCurTime());
        return "redirect:/article/index";
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteArticle/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
    	ContentVo article = articleService.findArticleById(id);
        articleService.deleteArticle(id);
        admin_logger.info("删除文章【{}】成功，时间为：{}",article.getTitle(),DateTool.getCurTime());
        return "redirect:/admin/manage_article";
    }

//评论
    @RequestMapping("/manage_comment")
    public String manageComment(Integer page, Model model) {
        List<CommentVo> comments = commentService.findCurPageComments(page, 10);
        long total = commentService.getTotal();
        model.addAttribute("comments", comments);
        model.addAttribute("curPage", page == null ? 0 : page);
        model.addAttribute("lastPage", (total + 9) / 10 - 1);
        return "admin/manageComment";
    }
    /**
     * 删除评论，文章评论数-1，保存的评论人不删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteComment/{id}/{contentId}")
    public String deleteComment(@PathVariable("id") long id, @PathVariable("contentId") long article_id) {
        commentService.deleteComment(id);
        ContentVo article = articleService.findArticleById(article_id);
        article.setCommentsNum(article.getCommentsNum() - 1);
        return "redirect:/admin/manage_comment";
    }
    /**
     * 是否允许评论
     *
     * @param id
     * @return
     */
    @RequestMapping("/allowComment/{id}")
    @ResponseBody
    public String allowComment(@PathVariable("id") long id) {
        ContentVo article = articleService.findArticleById(id);
        article.setAllowComment(article.getAllowComment() == true ? false : true);
        articleService.saveArticle(article);
        return "允许评论：" + article.getAllowComment();
    }

    //链接
    @RequestMapping("/manage_link")
    public String manageLink(Model model){
        List<LinkVo> links = linkService.findAll();
        model.addAttribute("links",links);
        return "admin/manageLink";
    }
    @RequestMapping("/saveLink")
    @ResponseBody
    public String saveLink(LinkVo link){
        if(ValidateTool.validateUrl(link.getUrl())) {
            linkService.saveLink(link);
            admin_logger.info("保存链接{}成功",link.getUrl());
            return "保存链接成功！";
        }
        else return "链接格式错误！";
    }
    @RequestMapping("/updateLink/{id}")
    public String updateLink(@PathVariable("id") Integer id,Model model){
        LinkVo link = linkService.findById(id);
        model.addAttribute("link",link);
        return "admin/edit_link";
    }
    @RequestMapping("/deleteLink/{id}")
    public String deleteLink(@PathVariable("id") Integer id){
    	LinkVo link = linkService.findById(id);
        linkService.deleteLink(id);
        admin_logger.info("删除链接{}成功",link.getUrl());
        return "redirect:/admin/manage_link";
    }

//文件

    /**
     * 图片上传
     * 使用jsonObject返回需要配置json转换器，这里使用Map
     * 返回url为http://localhost:8080/myBlog/upload/图片名，
     * 其中http://localhost:8080/myBlog/upload/通过配置映射到d:/webPic/
     *
     * @param file
     * @return
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    //
    public Map<String, Object> uploadImg(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String trueFileName = file.getOriginalFilename();
        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));
        String fileName = RandTool.randPicName() + "_" + System.currentTimeMillis() + suffix;
        //保存
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(BlogConstant.UPLOAD_ROOT, fileName);
            Files.write(path, bytes);
            admin_logger.info("上传图片{}成功",fileName);
            map.put("success", 1);//1成功
        } catch (Exception e) {
        	admin_logger.error("上传图片{}异常",fileName);
            e.printStackTrace();
            map.put("success", 0);//0失败
        }
        //3个参数，规范
        map.put("url", BlogConstant.WEB_ROOT + "upload/" + fileName);//先配置静态资源和硬盘对应路径
        map.put("message", "上传成功!");
        return map;
    }
}
