package async;

import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "async", value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		final AsyncContext ac = request.startAsync(request, response);
		ac.start(new Runnable() {
			@Override
			public void run() {
				// get parameteres
				// invoke a Web service endpoint
				// set results
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(5));
					ac.getResponse().getWriter().write("finished");
				} catch (Exception e) {
					e.printStackTrace();
				}
				ac.complete();

			}
		});
	}
}