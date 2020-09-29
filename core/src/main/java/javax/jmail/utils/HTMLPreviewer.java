/**
 * 
 */
package javax.jmail.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author abhi
 *
 */
public final class HTMLPreviewer {
	
	public static void preview(String text) {
		String filePath = System.getProperty("user.home") + File.separatorChar;
		String htmlCode = "<html>" + "<body> <div style=\"margin-left:15%;margin-right:15%;\">"+ text + "</div></body>" + "</html>";
		try {
			File file = new File("mailIndex.html");
			file.createNewFile();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath + file))) {
				bw.write(htmlCode);
				bw.flush();
			}
			Desktop.getDesktop().open(new File(filePath + file));
		} catch (Exception e) {
			RuntimeException re = new RuntimeException();
			re.addSuppressed(e);
			throw re;
		}
	}

	private static String readFile(String filename, boolean cssfile) {
		if (filename == null)
			return "";

		StringBuilder sb;
		String data = "";

		if (cssfile) {
			sb = new StringBuilder("<style>");
		} else {
			sb = new StringBuilder("<script>");
		}

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			br.lines().forEach(sb::append);
			if (cssfile) {
				sb.append("</style>");
			} else {
				sb.append("</script>");
			}
			data = sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return data;
	}

	public static String preview(String htmlContent, String cssfilename, String jsfilename) {
		String data = htmlContent + readFile(cssfilename, true) + readFile(jsfilename, false);
		preview(data);
		return data;
	}

	public static void main(String[] args) {
		String html = "<h1 >Testing </h1>" + "<img src=\"https://static.toiimg.com/photo/72975551.cms\">";
		String data = html + readFile("D:\\test.css", true) + readFile(null, false);
		preview(data);
	}
}
