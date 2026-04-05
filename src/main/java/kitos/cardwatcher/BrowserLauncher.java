package kitos.cardwatcher;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.awt.Desktop;
import java.net.URI;

@Profile("dev")
@Component
public class BrowserLauncher implements ApplicationRunner {

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//	    Thread.sleep(1500);
//	    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//	        Desktop desktop = Desktop.getDesktop();
//	        desktop.browse(new URI("http://localhost:8080/swagger-ui"));
//	        desktop.browse(new URI("http://localhost:8080/h2-console"));
//	    }
//	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run(ApplicationArguments args) throws Exception {
	    Thread.sleep(1500);
	    Runtime.getRuntime().exec("cmd /c start chrome --incognito --new-window http://localhost:8080/swagger-ui http://localhost:8080/h2-console");
	}
}