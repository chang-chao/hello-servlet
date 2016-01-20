package async;

import java.util.Queue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "async", value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		AsyncContext aCtx = request.startAsync(request, response);
		ServletContext appScope = request.getServletContext();
		((Queue<AsyncContext>) appScope.getAttribute("jobQueue")).add(aCtx);
	}
}