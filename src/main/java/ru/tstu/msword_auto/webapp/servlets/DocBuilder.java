package ru.tstu.msword_auto.webapp.servlets;


import ru.tstu.msword_auto.automation.*;
import ru.tstu.msword_auto.dao.exceptions.DaoSystemException;
import ru.tstu.msword_auto.dao.exceptions.NoSuchEntityException;
import ru.tstu.msword_auto.dao.impl.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DocBuilder extends HttpServlet {
	static final String KEY_STUDENT_ID = "id";
	static final String KEY_GROUP_ID = "group_id";
	static final String KEY_GROUP_NAME = "group_name";
	static final String KEY_COURSE_NAME = "course_name";
	static final String KEY_GEK_HEAD_ID = "gek_head_id";

	static final String PARAM_ID = "id";
	static final String PARAM_TYPE = "type";
	static final String ERROR_BD = "Ошибка при работе с БД";

	private final StudentDao studentDao = new StudentDao();
	private final CourseDao courseDao = new CourseDao();
	private final DateDao dateDao = new DateDao();
	private final VcrDao vcrDao = new VcrDao();
	private final GekHeadDao gekHeadDao = new GekHeadDao();
	private final GekMemberDao gekMemberDao = new GekMemberDao();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter(PARAM_ID));
		String docType = req.getParameter(PARAM_TYPE);

		TemplateData data;
		try {
			data = getTemplateData(id);
		} catch (HandlingException e) {
			String response = "{\"Result\": \"ERROR\",\"Message\": \"" + e.getMessage() + "\"}";
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().print(response);
			return;
		}
		String templateFilename;

		// sync needed to ensure single access to template file
		synchronized (this) {
			try(Template template = Template.newTemplate(docType, data)) {
				template.fillTemplate();
				templateFilename = template.getFilename();
			} catch (TemplateException e) {
				e.printStackTrace();
				// todo handle if thrown
				return;
			}
		}

		sendDocument(resp, templateFilename);
	}


	// TODO chain of responsibility ? buildDate - buildStudent - buildCourse - etc ?
	private TemplateData getTemplateData(int id) throws HandlingException {
		Map<String, String> searchKeys = new HashMap<>();
		searchKeys.put(KEY_STUDENT_ID, String.valueOf(id));
		TemplateDataBuilder tplDataBuilder = new TemplateDataBuilder();

		// student, course, vcr
		getStudentData(tplDataBuilder, searchKeys);

		// date
		getDateData(tplDataBuilder, searchKeys);

		// gekhead, gekmembers
		getGekData(tplDataBuilder, searchKeys);

		return tplDataBuilder.build();
	}

	private void getStudentData(TemplateDataBuilder builder, Map<String, String> searchKeys) throws HandlingException {
		int studentId = Integer.valueOf(searchKeys.get(KEY_STUDENT_ID));
		try {
			Student student = studentDao.read(studentId);
			builder.setStudent(student);
		} catch (DaoSystemException e) {
			throw new HandlingException(ERROR_BD);
		} catch (NoSuchEntityException e) {
			/*NOP*/
		}

		this.getCourseData(builder, searchKeys);
		this.getVcrData(builder, searchKeys);
	}

	private void getCourseData(TemplateDataBuilder builder, Map<String, String> searchKeys) throws HandlingException {
		int studentId = Integer.valueOf(searchKeys.get(KEY_STUDENT_ID));
		try {
			List<Course> courses = courseDao.readByForeignKey(studentId);
			Course course = courses.get(0);
			builder.setCourse(course);
			searchKeys.put(KEY_GROUP_ID, String.valueOf(course.getSubgroupId()));
			searchKeys.put(KEY_GROUP_NAME, course.getGroupName());
			searchKeys.put(KEY_COURSE_NAME, course.getCourseName());
		} catch (DaoSystemException e) {
			throw new HandlingException(ERROR_BD);
		} catch (NoSuchEntityException e) {
			/*NOP*/
		}
	}

	private void getVcrData(TemplateDataBuilder builder, Map<String, String> searchKeys) throws HandlingException {
		int studentId = Integer.valueOf(searchKeys.get(KEY_STUDENT_ID));
		try {
			List<Vcr> vcrs = vcrDao.readByForeignKey(studentId);
			builder.setVcr(vcrs.get(0));
		} catch (DaoSystemException e) {
			throw new HandlingException(ERROR_BD);
		} catch (NoSuchEntityException e) {
			/*NOP*/
		}
	}

	private void getDateData(TemplateDataBuilder builder, Map<String, String> searchKeys) throws HandlingException {
		String groupId = searchKeys.get(KEY_GROUP_ID);
		String groupName = searchKeys.get(KEY_GROUP_NAME);
		try {
			if(groupId != null && groupName != null) {
				Date date = dateDao.readByGroupNameAndGroupId(groupName, Integer.valueOf(groupId));
				builder.setDate(date);
			}
		} catch (DaoSystemException e) {
			throw new HandlingException(ERROR_BD);
		} catch (NoSuchEntityException e) {
			/*NOP*/
		}
	}

	// gek head
	private void getGekData(TemplateDataBuilder builder, Map<String, String> searchKeys) throws HandlingException {
		String courseName = searchKeys.get(KEY_COURSE_NAME);
		try {
			if(courseName != null && !courseName.isEmpty()) {
				GekHead gekHead = gekHeadDao.readByCourseName(courseName);
				searchKeys.put(KEY_GEK_HEAD_ID, String.valueOf(gekHead.getGekId()));
				builder.setGekHead(gekHead);
			}
		} catch (DaoSystemException e) {
			throw new HandlingException(ERROR_BD);
		} catch (NoSuchEntityException e) {
			/*NOP*/
		}

		this.getGekMembersData(builder, searchKeys);
	}

	private void getGekMembersData(TemplateDataBuilder builder, Map<String, String> searchKeys) throws HandlingException {
		String gekHeadId = searchKeys.get(KEY_GEK_HEAD_ID);
		try {
			if(gekHeadId != null) {
				List<GekMember> gekMembers = gekMemberDao.readByForeignKey(Integer.valueOf(gekHeadId));
				builder.setGekMembers(gekMembers);
			}
		} catch (DaoSystemException e) {
			throw new HandlingException(ERROR_BD);
		} catch (NoSuchEntityException e) {
			// builder sets empty list internally
		}
	}

	// todo send filled template without saving it inb4
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

























