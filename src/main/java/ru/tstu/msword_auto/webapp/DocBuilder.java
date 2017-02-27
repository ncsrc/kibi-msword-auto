package ru.tstu.msword_auto.webapp;


import com.jacob.com.LibraryLoader;
import ru.tstu.msword_auto.automation.Template;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

public class DocBuilder extends HttpServlet
{
	private static final String PARAM_ID = "id";
	private static final String ERROR_BD = "Ошибка при работе с БД";


	@Override
	public void init() throws ServletException
	{
		// TODO add templates to folder

		String templatePath = getServletContext().getRealPath("/templates");
		System.setProperty("template_folder", templatePath);
		System.setProperty("java.library.path", "jacob-1.14.3-x64.dll");


		// todo dll loading to automation service
		if(System.getProperty("os.arch").equals("amd64")){
			String jacobPath = getServletContext().getRealPath("/WEB-INF/classes/jacob-1.14.3-x64.dll");
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobPath);
		}else{
			String jacobPath = getServletContext().getRealPath("/WEB-INF/classes/jacob-1.14.3-x86.dll");
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobPath);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String jacob = System.getProperty("java.library.path"); // debug (null)


		// TODO implementation
		/*
			get parameter id
			get models from date, gek
			get models from student, course, vcr by id
			use automation module, pass it what doc should it make and insert model's values
			set content-type to docx file, send
			optional: redirect
		 */
		// TODO fix bug with date-year

		Template template = new Template();

		try{
			template.fulfillTemplate(Integer.parseInt(req.getParameter(PARAM_ID)));
		}catch(SQLException e){
			// TODO logging
			throw new ServletException(ERROR_BD);	// TODO different exception
		}


		resp.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document; charset=utf-8");
		String fileName = template.getFilename().replace(' ', '_');	// replaces spaces with underscores because spaces decode to plus signs
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

		resp.setHeader("Content-disposition", "attachment; filename=" + "\"" + encodedFileName + "\"");

//		 TODO handle IOExceptions
		ServletOutputStream out = resp.getOutputStream();
		String file = System.getProperty("template_folder") + File.separator + template.getFilename();
		FileInputStream in = new FileInputStream(file);
		byte[] buf = new byte[4096];
		int length;
		while((length = in.read(buf)) > 0){
			out.write(buf, 0, length);
		}
		in.close();
//		template.close();	// TODO causes exception
		out.flush();

		// TODO add logging if failed, handle exceptions
		new File(file).delete();	// TODO this don't work, add folder with docs in init(), remove it in destroy()
	}

}

























