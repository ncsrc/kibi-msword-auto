package ru.tstu.msword_auto.webapp;


import ru.tstu.msword_auto.automation.AutomationService;
import ru.tstu.msword_auto.automation.Template;
import ru.tstu.msword_auto.automation.TemplateException;
import ru.tstu.msword_auto.dao.impl.StudentDao;
import ru.tstu.msword_auto.dao.impl.VcrDao;
import ru.tstu.msword_auto.entity.*;

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
		int id = Integer.parseInt(req.getParameter(PARAM_ID));
		String docType = req.getParameter(PARAM_TYPE);
		TemplateData data = getTemplateData(id);
		String templateFilename = "";

		// sync needed to ensure single access to template file
		synchronized (this) {
			try(Template template = Template.newTemplate(docType, data)) {
				template.fillTemplate();
				templateFilename = template.getFilename();
			} catch (TemplateException e) {
				e.printStackTrace();
				// todo handle if thrown
			}
		}

		sendDocument(resp, templateFilename);
	}


	private TemplateData getTemplateData(int id) {
		// todo fix null
		// what if null gets to template ?
		Date date = null;
		Gek gek = null;
		StudentData studentData = null;
		try {
			DateDao dateDao = new DateDao();

			// TODO fix handle no such entity
			List<Date> dates = dateDao.readAll();
			date = dateDao.readAll().get(0); // there should be always only one row

			GekHeadDao gekHeadDao = new GekHeadDao();

			/*
				new gekHead logic:
				get student course_name,
				call getByCourseName(String courseName)

				get gekHead id
				get all members by gekHead id
			 */


			// TODO handle no such entity
			List<GekHead> gekHeads = gekHeadDao.readAll();
			GekHead gekHead = gekHeadDao.readAll().get(0); // there should be always only one row

			// TODO fix {template-strings}
			GekMemberDao gekMemberDao = new GekMemberDao();
			List<GekMember> gekMembers = gekMemberDao.readAll();
			gek = new Gek(gekHead, gekMembers);

			StudentDao studentDao = new StudentDao();
			Student student = studentDao.read(id);
			CourseDao courseDao = new CourseDao();

			// TODO handle no such entity
			Course course = courseDao.readByForeignKey(id).get(0); // 1:1
			VcrDao vcrDao = new VcrDao();

			// TODO handle no such entity
			Vcr vcr = vcrDao.readByForeignKey(id).get(0); // 1:1
			studentData = new StudentData(student, course, vcr);

		} catch (SQLException e) {
			e.printStackTrace();
			// TODO handle, check for missing data
		}

		return new TemplateData(date, gek, studentData);
	}

	private void sendDocument(HttpServletResponse resp, String templateFilename) throws IOException {
		resp.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document; charset=utf-8");
		String fileName = templateFilename.replace(' ', '_');	// replaces spaces with underscores because spaces decode to plus signs
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

		resp.setHeader("Content-disposition", "attachment; filename=" + "\"" + encodedFileName + "\"");

		ServletOutputStream out = resp.getOutputStream();
		String file = AutomationService.templateSave + File.separator + templateFilename;
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

























