package net.rusb.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.rusb.dao.ArticleDao;
import net.rusb.model.Article;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
* @author Xing,XiuDong
*/
public class HTMLGenerator {

public static final String generate(final String url) {
	if (StringUtils.isBlank(url)) {
	return null;
	}
	
	Pattern pattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
	Matcher matcher = pattern.matcher(url);
	if (!matcher.find()) {
		return null;
	}
	
	StringBuffer sb = new StringBuffer();
	
	try {
			URL _url = new URL(url);
			URLConnection urlConnection = _url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
		return sb.toString();
}
	public static String getBasePath(HttpServletRequest req){
		String path = req.getContextPath();
		String url = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path;
		return url;
	}
	/**
	 * 将指定的articleId的文章生成静态页面，放入到指定的目录targetDir中
	 * @param articleId 文章id
	 * @param targetDir 静态页面的存放目录
	 */
	public static void generateHtmlArticlePage(Article article,String targetDir,HttpServletRequest req){
		String path = req.getContextPath();
		String url = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/viewArticle.do?articleid="+article.getArticleId();
		String src = HTMLGenerator.generate(url);
		String fileName = article.getPageUrl();
		File file = new File(targetDir+File.separator+fileName);
		try {
			FileUtils.writeStringToFile(file, src, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}