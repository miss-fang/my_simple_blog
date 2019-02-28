package com.cl.controller;

import com.cl.model.Vo.CommentVo;
import com.cl.model.Vo.ContentVo;
import com.cl.model.Vo.LinkVo;
import com.cl.model.Vo.UserVo;
import com.cl.service.*;
import com.cl.utils.commonTools.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 一个controller用了好几个service
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	private ArticleService service;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private LinkService linkService;

	@RequestMapping("/")
	public String toIndex() {
		return "redirect:/article/index";
	}

	@RequestMapping("/index")
	public String index(Integer page, Model model) {
		List<ContentVo> curPageArticles = service.findCurPageArticles(page, 10);
		long total = service.getTotal();
		model.addAttribute("curPage", page == null ? 0 : page);
		model.addAttribute("lastPage", (total + 9) / 10 - 1);
		model.addAttribute("articles", curPageArticles);
		return "user/index";
	}

	/**
	 * 根据id查找文章 浏览量加1
	 *
	 * @param article_id
	 * @param model
	 * @return
	 */
	@RequestMapping("/{article_id}")
	public String findArticle(@PathVariable("article_id") Long article_id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ContentVo article = service.findArticleById(article_id);
		// 第一次访问或者2小时后访问，浏览量加1，并设置cookie
		if (CookieTool.getCookieIp(request, article_id) == null) {
			article.setHits(article.getHits() + 1);
			service.saveArticle(article);
			CookieTool.setCookie(response, IpTool.getIpAddrByRequest(request), article_id);
		}
		long total = commentService.findCommentNumsByArticleId(article_id);
		long lastPage = (total + 9) / 10 - 1;
		// 先查找10条
		List<CommentVo> comments = commentService.findCurPageCommentsByArticleId(article_id, 0, 10);
		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
		model.addAttribute("curPage", 0);
		model.addAttribute("lastPage", lastPage >= 0 ? lastPage : 0);
		return "user/article";
	}

	/**
	 * 通过分类寻找文章，默认查找20条
	 *
	 * @param categories
	 * @param model
	 * @return
	 */
	@RequestMapping("/category/{categories}")
	public String findArticlesByCategory(@PathVariable String categories, Model model) {
		List<ContentVo> articles = service.findByCategory(categories);
		int size = articles.size();
		int fromIndex = size > 20 ? size - 20 : 0;
		int endIndex = size > 0 ? size - 1 : 0;
		model.addAttribute("articles", articles.subList(fromIndex, endIndex + 1));
		return "user/searchArticles";
	}

	/**
	 * 标签查找
	 * 
	 * @param tag
	 * @param model
	 * @return
	 */
	@RequestMapping("/tag/{tag}")
	public String findArticlesByTag(@PathVariable String tag, Model model) {
		List<ContentVo> articles = service.findByTag(tag);
		int size = articles.size();
		int fromIndex = size > 20 ? size - 20 : 0;
		int endIndex = size > 0 ? size - 1 : 0;
		model.addAttribute("articles", articles.subList(fromIndex, endIndex + 1));
		return "user/searchArticles";
	}

	/**
	 * 关键词查找
	 * 
	 * @param keyword
	 * @param model
	 * @return
	 */
	@RequestMapping("/keyword")
	public String findArticlesByKeyword(String keyword, Model model) {
		List<ContentVo> result = service.findByKeyword(keyword);
		int size = result.size();
		if (result.size() == 0)
			return "user/notFound";
		else {
			int fromIndex = size > 20 ? size - 20 : 0;
			int endIndex = size > 0 ? size - 1 : 0;
			List<ContentVo> articles = result.subList(fromIndex, endIndex + 1);
			model.addAttribute("articles", articles);
			return "user/searchArticles";
		}
	}

	/**
	 * 保存评论 1;如果提供邮箱或网址，保存用户  2;文章评论数加1 3;邮件通知到邮箱
	 *
	 * @param comment
	 * @return
	 */
	@RequestMapping("/postComment")
	@ResponseBody
	public String saveComment(@RequestBody CommentVo comment) {
		comment.setCreated(DateTool.getCurTime());
		if (StringUtils.isBlank(comment.getName()))
			comment.setName("热心网友");
		// 验证邮箱和网址
		if (ValidateTool.validateEmail(comment.getMail().trim()))
			comment.setMail(comment.getMail().trim());
		else
			comment.setMail(null);
		if (ValidateTool.validateUrl(comment.getUrl().trim()))
			comment.setUrl(comment.getUrl().trim());
		else
			comment.setUrl(null);
		// 符合条件保存用户
		if (StringUtils.isNotBlank(comment.getMail()) || StringUtils.isNotBlank(comment.getUrl())) {
			UserVo user = new UserVo();
			user.setEmail(comment.getMail() == null ? null : comment.getMail());
			user.setHomeUrl(comment.getUrl() == null ? null : comment.getUrl());
			user.setUsername(comment.getName());
			userService.saveUser(user);
		}
		// 保存评论
		commentService.saveComment(comment);
		// 评论数加1
		ContentVo article = service.findArticleById(comment.getContentId());
		article.setCommentsNum(article.getCommentsNum() + 1);
		service.saveArticle(article);
		// 邮件通知
		String msg = comment.getName() + "评论了你的文章【" + article.getTitle() + "】，评论内容为：" + comment.getContent();
		mailSendService.sendMail("842662006@qq.com", "博客评论", msg);
		return "ok";
	}

	/**
	 * 显示最近10条评论
	 * @param article_id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/comment/{id}/{page}")
	public String findCurPageComments(@PathVariable("id") long article_id, @PathVariable("page") Integer page,
			Model model) {
		ContentVo article = service.findArticleById(article_id);
		long total = commentService.findCommentNumsByArticleId(article_id);
		long lastPage = (total + 9) / 10 - 1;
		// 先查找10条
		List<CommentVo> comments = commentService.findCurPageCommentsByArticleId(article_id, page, 10);
		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
		model.addAttribute("curPage", page);
		model.addAttribute("lastPage", lastPage >= 0 ? lastPage : 0);
		return "user/article";
	}

	/**
	 * about就以第一篇文章说明吧，偷个懒
	 *
	 * @return
	 */
	@RequestMapping("/about")
	public String about() {
		return "redirect:/article/1";
	}

	/**
	 * 链接
	 *
	 * @return
	 */
	@RequestMapping("/link")
	public String link(Model model) {
		List<LinkVo> links = linkService.findAll();
		model.addAttribute("links", links);
		return "user/link";
	}

}
