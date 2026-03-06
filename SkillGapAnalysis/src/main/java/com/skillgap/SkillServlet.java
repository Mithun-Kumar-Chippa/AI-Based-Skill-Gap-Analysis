package com.skillgap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SkillServlet")

public class SkillServlet extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

String name = request.getParameter("name");
String learningStyle=request.getParameter("learning_style");
String year = request.getParameter("year");
String course = request.getParameter("course");
float cgpa = Float.parseFloat(request.getParameter("cgpa"));
String certification = request.getParameter("certification");
String[] skillsArray = request.getParameterValues("skills");
String skills = "";

if(skillsArray != null){
    skills = String.join(",", skillsArray);
}


int pythonActual = Integer.parseInt(request.getParameter("python_actual"));
int pythonRequired = Integer.parseInt(request.getParameter("python_required"));

int javaActual = Integer.parseInt(request.getParameter("java_actual"));
int javaRequired = Integer.parseInt(request.getParameter("java_required"));

int dsaActual = Integer.parseInt(request.getParameter("dsa_actual"));
int dsaRequired = Integer.parseInt(request.getParameter("dsa_required"));

int dbmsActual = Integer.parseInt(request.getParameter("dbms_actual"));
int dbmsRequired = Integer.parseInt(request.getParameter("dbms_required"));

int pythonGap = pythonRequired - pythonActual;
int javaGap = javaRequired - javaActual;
int dsaGap = dsaRequired - dsaActual;
int dbmsGap = dbmsRequired - dbmsActual;
try {
    Class.forName("com.mysql.cj.jdbc.Driver");

    Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/skillgap",
        "root",
        "sreesha@12"
    );

    String query = "INSERT INTO students(name, year, course, cgpa, certification, skills) VALUES(?,?,?,?,?,?)";

    PreparedStatement ps = con.prepareStatement(query);

    ps.setString(1, name);
    ps.setString(2, year);
    ps.setString(3, course);
    ps.setFloat(4, cgpa);
    ps.setString(5, certification);
    ps.setString(6, skills);
     System.out.println("insert executing..");

    ps.executeUpdate();

    con.close();

} catch (Exception e) {
    e.printStackTrace();
}

response.setContentType("text/html");
PrintWriter out = response.getWriter();

out.println("<html><head>");
out.println("<title>Skill Gap Report</title>");

out.println("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");

out.println("<style>");
out.println("body{font-family:Segoe UI;background:linear-gradient(135deg,#667eea,#764ba2);color:#333;text-align:center;padding:30px;}");
out.println(".card{background:white;padding:25px;border-radius:10px;width:600px;margin:auto;box-shadow:0 10px 25px rgba(0,0,0,0.2);}"); 
out.println("h2{margin-bottom:10px}");
out.println(".skill{background:#eef1ff;padding:10px;margin:8px;border-radius:6px}");
out.println(".recommend{background:#f7f7f7;padding:12px;margin-top:15px;border-radius:6px}");
out.println("iframe{margin-top:10px}");
out.println("a{color:#667eea;text-decoration:none;font-weight:bold}");
out.println("</style>");

out.println("</head><body>");

out.println("<div class='card'>");

out.println("<h2>Skill Gap Analysis Report</h2>");

out.println("<p><b>Name:</b> "+name+"</p>");
out.println("<p><b>Course:</b> "+course+"</p>");

out.println("<div class='skill'>Python Gap: "+pythonGap+"</div>");
out.println("<div class='skill'>Java Gap: "+javaGap+"</div>");
out.println("<div class='skill'>DSA Gap: "+dsaGap+"</div>");
out.println("<div class='skill'>DBMS Gap: "+dbmsGap+"</div>");

out.println("<h3>Skill Comparison</h3>");

out.println("<canvas id='chart' width='350' height='200'></canvas>");

out.println("<script>");
out.println("var ctx=document.getElementById('chart').getContext('2d');");
out.println("new Chart(ctx,{");
out.println("type:'bar',");
out.println("data:{");
out.println("labels:['Python','Java','DSA','DBMS'],");
out.println("datasets:[");
out.println("{label:'Actual',backgroundColor:'blue',data:["+pythonActual+","+javaActual+","+dsaActual+","+dbmsActual+"]},");
out.println("{label:'Required',backgroundColor:'red',data:["+pythonRequired+","+javaRequired+","+dsaRequired+","+dbmsRequired+"]}");
out.println("]");
out.println("}");
out.println("});");
out.println("</script>");

out.println("<div class='recommend'>");
out.println("<h3>Learning Recommendations</h3>");

if(pythonGap>2){
out.println("<p><b>Python:</b> Practice Python fundamentals and problem solving.</p>");
out.println("<iframe width='300' height='170' src='https://www.youtube.com/embed/rfscVS0vtbw'></iframe>");
}

if(javaGap>2){
out.println("<p><b>Java:</b> Focus on OOP concepts and build small projects.</p>");
out.println("<iframe width='300' height='170' src='https://www.youtube.com/embed/grEKMHGYyns'></iframe>");
}

if(dsaGap>2){
out.println("<p><b>Data Structures:</b> Practice algorithms and coding problems.</p>");
out.println("<iframe width='300' height='170' src='https://www.youtube.com/embed/8hly31xKli0'></iframe>");
}

if(dbmsGap>2){
out.println("<p><b>DBMS:</b> Study SQL queries and database design.</p>");
out.println("<iframe width='300' height='170' src='https://www.youtube.com/embed/HXV3zeQKqGY'></iframe>");
}

out.println("</div>");

out.println("<div class='recommend'>");
out.println("<h3>Learning Style Resources</h3>");

if(learningStyle != null){

if(learningStyle.equals("Reading Documentation")){

out.println("<p>Recommended Documentation Resources:</p>");
out.println("<ul>");
out.println("<li><a href='https://docs.python.org/3/'>Python Official Docs</a></li>");
out.println("<li><a href='https://docs.oracle.com/javase/tutorial/'>Java Documentation</a></li>");
out.println("<li><a href='https://dev.mysql.com/doc/'>MySQL Documentation</a></li>");
out.println("</ul>");

}

else if(learningStyle.equals("Practice Coding")){

out.println("<p>Recommended Coding Practice Platforms:</p>");
out.println("<ul>");
out.println("<li><a href='https://leetcode.com'>LeetCode</a></li>");
out.println("<li><a href='https://www.hackerrank.com'>HackerRank</a></li>");
out.println("<li><a href='https://www.geeksforgeeks.org'>GeeksforGeeks</a></li>");
out.println("</ul>");

}

else if(learningStyle.equals("Video Tutorials")){

out.println("<p>Recommended Video Platforms:</p>");
out.println("<ul>");
out.println("<li>YouTube Programming Tutorials</li>");
out.println("<li>FreeCodeCamp Courses</li>");
out.println("<li>Coursera Programming Courses</li>");
out.println("</ul>");

}

else if(learningStyle.equals("Project Based Learning")){

out.println("<p>Recommended Project Ideas:</p>");
out.println("<ul>");
out.println("<li>Build a Student Management System</li>");
out.println("<li>Create a Web Application</li>");
out.println("<li>Develop a Mini AI Project</li>");
out.println("</ul>");

}

}

out.println("</div>");

out.println("<br><a href='index1.html'>Analyze Another Student</a>");

out.println("</div>");

out.println("</body></html>");

}
}