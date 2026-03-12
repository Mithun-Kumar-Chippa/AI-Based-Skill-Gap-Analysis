package com.skillgap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AssessmentServlet")

public class AssessmentServlet extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

int score = 0;

/* check answers */

if("b".equals(request.getParameter("q1"))) score++;
if("a".equals(request.getParameter("q2"))) score++;
if("c".equals(request.getParameter("q3"))) score++;
if("b".equals(request.getParameter("q4"))) score++;
if("a".equals(request.getParameter("q5"))) score++;

/* convert score to skill level */

int level;

if(score <= 2)
level = 3;
else if(score <= 4)
level = 6;
else
level = 9;

/* generate result page */

response.setContentType("text/html");

PrintWriter out = response.getWriter();

out.println("<html><head>");
out.println("<title>Assessment Result</title>");

out.println("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");

out.println("<style>");
out.println("body{font-family:Segoe UI;background:linear-gradient(135deg,#667eea,#764ba2);text-align:center;padding:40px;}");
out.println(".card{background:white;width:500px;margin:auto;padding:30px;border-radius:10px;box-shadow:0 10px 25px rgba(0,0,0,0.2);}");
out.println("</style>");

out.println("</head><body>");

out.println("<div class='card'>");

out.println("<h2>Assessment Result</h2>");
out.println("<h3>Your Score: "+score+" / 5</h3>");
out.println("<h3>Skill Level: "+level+" / 10</h3>");

out.println("<canvas id='chart'></canvas>");

out.println("<script>");

out.println("var ctx=document.getElementById('chart').getContext('2d');");

out.println("new Chart(ctx,{");
out.println("type:'doughnut',");
out.println("data:{");
out.println("labels:['Correct','Wrong'],");
out.println("datasets:[{");
out.println("data:["+score+","+(5-score)+"],");
out.println("backgroundColor:['green','lightgray']");
out.println("}]");
out.println("}");
out.println("});");

out.println("</script>");

out.println("<br><br>");
out.println("<a href='index.html'>Back to Skill Analysis</a>");

out.println("</div>");

out.println("</body></html>");

}

}