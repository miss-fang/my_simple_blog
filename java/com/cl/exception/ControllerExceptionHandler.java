package com.cl.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//全局异常处理，2个注解
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger exception_logger = LoggerFactory.getLogger("exception_logger");

	/**
	 * 处理文件相关异常
	 * 
	 * @param e
	 * @param redirectAttributes
	 * @return
	 */
	@ExceptionHandler(MultipartException.class)
	public String handleMultipartException(MultipartException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("msg", e.getCause().getMessage());
		exception_logger.error("出现文件异常，原因是{}",e.getCause().getMessage());
		return "redirect:/admin/uploadStatus";
	}

	/**
	 * 处理自定义异常
	 * 
	 * @return
	 */
	/*@ExceptionHandler(MyException.class)
	public String handleMyException() {
		return "";
	}*/

	/**
	 * 处理其他异常
	 * 
	 * @param e
	 * @param redirectAttributes
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
		exception_logger.error("出现其他异常，原因是{}",e.getCause().getMessage());
		return "redirect:/admin/status";
	}
}