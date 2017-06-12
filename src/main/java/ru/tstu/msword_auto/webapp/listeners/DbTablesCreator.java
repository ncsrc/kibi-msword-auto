package ru.tstu.msword_auto.webapp.listeners;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbTablesCreator implements ServletContextListener {

    private static final String[] SQLS =
            {
                    "CREATE TABLE IF NOT EXISTS Students\n" +
                            "(\n" +
                            "  student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                            "  first_name_i VARCHAR(60),\n" +
                            "  last_name_i VARCHAR(60),\n" +
                            "  middle_name_i VARCHAR(60),\n" +
                            "  first_name_r VARCHAR(60),\n" +
                            "  last_name_r VARCHAR(60),\n" +
                            "  middle_name_r VARCHAR(60),\n" +
                            "  first_name_t VARCHAR(60),\n" +
                            "  last_name_t VARCHAR(60),\n" +
                            "  middle_name_t VARCHAR(60)\n" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS Courses\n" +
                            "(\n" +
                            "  student_id INT NOT NULL PRIMARY KEY,\n" +
                            "  subgroup_id INT,\n" +
                            "  group_name VARCHAR(10),\n" +
                            "  course_code VARCHAR(30),\n" +
                            "  course_name VARCHAR(60),\n" +
                            "  course_profile VARCHAR(60),\n" +
                            "  qualification VARCHAR(60),\n" +
                            "  FOREIGN KEY(student_id) REFERENCES Students(student_id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS VCRs\n" +
                            "(\n" +
                            "  student_id INT NOT NULL UNIQUE,\n" +
                            "  vcr_name VARCHAR(100) NOT NULL PRIMARY KEY,\n" +
                            "  vcr_head VARCHAR(60),\n" +
                            "  vcr_reviewer VARCHAR(60),\n" +
                            "  FOREIGN KEY(student_id) REFERENCES Students(student_id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS Gek_Head\n" +
                            "(\n" +
                            "  gek_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                            "  course_name VARCHAR(60) UNIQUE NOT NULL,\n" +
                            "  gek_head VARCHAR(60),\n" +
                            "  gek_subhead VARCHAR(60),\n" +
                            "  gek_secretary VARCHAR(60)\n" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS Gek_Members\n" +
                            "(\n" +
                            "  member_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                            "  head_id INT NOT NULL,\n" +
                            "  gek_member VARCHAR(60) NOT NULL,\n" +
                            "  FOREIGN KEY (head_id) REFERENCES Gek_Head (GEK_ID) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                            ");",

                    "CREATE TABLE IF NOT EXISTS DATE\n" +
                            "(\n" +
                            "  date_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                            "  group_id INT,\n" +
                            "  GROUP_NAME VARCHAR(10),\n" +
                            "  DATE_GOS DATE,\n" +
                            "  DATE_VCR DATE\n" +
                            ");"
            };


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Connection connection = (Connection) servletContextEvent.getServletContext().getAttribute("connection");

        try {
            for (String sql : SQLS) {
                try(Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // TODO handle
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // intentionally blank

    }

}


















