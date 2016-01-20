package async;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebListener;

@WebListener
public class JobExecutor implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Queue<AsyncContext> jobQueue = new ConcurrentLinkedQueue<AsyncContext>();
		sce.getServletContext().setAttribute("jobQueue", jobQueue);

		new Thread(new Runnable() {

			@Override
			public void run() {
				executeJobs(jobQueue);
			}
		}).start();
	}

	private void executeJobs(Queue<AsyncContext> jobQueue) {
		// pool size matching Web services capacity
		Executor executor = Executors.newFixedThreadPool(10);
		while (true) {
			if (!jobQueue.isEmpty()) {
				final AsyncContext aCtx = jobQueue.poll();
				executor.execute(new Runnable() {
					public void run() {
						ServletRequest request = aCtx.getRequest();
						// get parameteres
						// invoke a Web service endpoint
						// set results
						try {
							Thread.sleep(TimeUnit.SECONDS.toMillis(5));
							aCtx.getResponse().getWriter().write("finished");
						} catch (Exception e) {
							e.printStackTrace();
						}
						aCtx.complete();
					}
				});
			}
		}
	}

}
