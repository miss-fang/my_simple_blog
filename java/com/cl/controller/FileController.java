package com.cl.controller;

/*import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;*/

/**
 * 文件下载，目前没必要
 * @author chenlun
 *
 */
//@RestController
//@RequestMapping("/file")
public class FileController {

	/**
	 * 暂时没用
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	// @GetMapping("/{id}")
/*	public void downLoad(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		try (InputStream inputStream = new FileInputStream(new File("", id + ".txt"));
				OutputStream outputStream = response.getOutputStream();) {
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename=test.txt");

			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} catch (Exception e) {

		}
	}*/
}
