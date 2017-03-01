package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.automation.AutomationService;
import ru.tstu.msword_auto.automation.Template;
import ru.tstu.msword_auto.automation.TemplateException;
import ru.tstu.msword_auto.automation.entity_aggregation.Gek;
import ru.tstu.msword_auto.automation.entity_aggregation.StudentData;
import ru.tstu.msword_auto.automation.entity_aggregation.TemplateData;
import ru.tstu.msword_auto.dao.*;
import ru.tstu.msword_auto.entity.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

public class DocBuilder extends HttpServlet {
	private static final String PARAM_ID = "id";
	private static final String PARAM_TYPE = "type";
	private static final String ERROR_BD = "Ошибка при работе с БД";


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*
			get data and build TemplateData object
			init template
			fill
			send document
		 */

		int id = Integer.parseInt(req.getParameter(PARAM_ID));


		// get all data

		// todo fix null
		// what if null gets to template ?
		Date date = null;
		Gek gek = null;
		StudentData studentData = null;
		try {
			DateDao dateDao = new DateDao();
			date = dateDao.readAll().get(0); // there should be always only one row

			GekHeadDao gekHeadDao = new GekHeadDao();
			GekHead gekHead = gekHeadDao.readAll().get(0); // there should be always only one row
			GekMemberDao gekMemberDao = new GekMemberDao();
			List<GekMember> gekMembers = gekMemberDao.readAll();
			gek = new Gek(gekHead, gekMembers);

			StudentDao studentDao = new StudentDao();
			Student student = studentDao.read(id);
			CourseDao courseDao = new CourseDao();
			Course course = courseDao.readByForeignKey(id).get(0); // 1:1
			VcrDao vcrDao = new VcrDao();
			VCR vcr = vcrDao.readByForeignKey(id).get(0); // 1:1
			studentData = new StudentData(student, course, vcr);

		} catch (SQLException e) {
			e.printStackTrace();
			// TODO handle, check for missing data
		}


		// make template

		TemplateData templateData = new TemplateData(date, gek, studentData);
		String docType = req.getParameter(PARAM_TYPE);
		Template template = Template.newTemplate(docType, templateData);


		// fill template

		try {
			template.fulfillTemplate();
		} catch (TemplateException e) {
			e.printStackTrace();
			// todo handle
		}

		// close template

		template.close();


		// send build doc

		resp.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document; charset=utf-8");
		String fileName = template.getFilename().replace(' ', '_');	// replaces spaces with underscores because spaces decode to plus signs
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

		resp.setHeader("Content-disposition", "attachment; filename=" + "\"" + encodedFileName + "\"");

//		 TODO handle IOExceptions
		ServletOutputStream out = resp.getOutputStream();
		String file = AutomationService.templateSave + File.separator + template.getFilename();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		byte[] buf = new byte[4096];
		int length;
		while((length = in.read(buf)) > 0){
			out.write(buf, 0, length);
		}

		in.close();
		out.flush();

	}

}

























