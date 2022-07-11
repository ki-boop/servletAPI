import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/staff")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String countGet = req.getParameter("count"); // берем параметр из строки запроса с именем "count"
        PrintWriter printWriter = resp.getWriter();
        String connectionUrl = "jdbc:mysql://localhost:3306/items?useSSL=false";

        try {
            //создаем подключение к СУБД
            Class.forName("com.mysql.cj.jdbc.Driver"); // пробуем найти класс который позволяет работать с нашей СУБД
            Connection connection = DriverManager.getConnection(connectionUrl,"root","root"); // подключаемся к БД вводим login password
            //получаем данные из БД
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, label, count FROM staff where count>=" + countGet); // выполняем запрос из БД
            //записываем данные на страницу
            printWriter.println("<h3>Request param: " + countGet + "</h3>");
            printWriter.println("<table>" +
                                "<th>Id</th>" +
                                "<th>Label</th>" +
                                "<th>Count</th>" +
                                "<tbody>"); // таблица + заголовки
            while (rs.next()) {
                printWriter.println("<tr>" +"<td>" +rs.getString("id") + "</td>" + "<td>" +  rs.getString("label") + "</td>" + "<td>" + rs.getString("count")+"</td></tr>");
            } // заполняем таблтцу данными из БД
            printWriter.println("</tbody></table>");

        } catch (SQLException |ClassNotFoundException sqlex) {
            printWriter.println(sqlex);
        }
        printWriter.close();
    }
}