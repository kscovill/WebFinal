import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configs.AppConfig;
import configs.DataConfig;
import play.Application;
import play.GlobalSettings;

/**
 * This is our Generic Global Class which calls an onStart Method from the Super
 * Class and tells the app to look for annotation in AppConfig and DataConfig.
 * Also has a getControllerInstance Class that returns the bean in clazz.
 * 
 * @author Kyle
 *
 */
public class Global extends GlobalSettings {
	private ApplicationContext ctx;

	@Override
	public void onStart(Application app) {
		super.onStart(app);
		ctx = new AnnotationConfigApplicationContext(AppConfig.class, DataConfig.class);
	}

	@Override
	public <A> A getControllerInstance(Class<A> clazz) {
		return ctx.getBean(clazz);
	}
}