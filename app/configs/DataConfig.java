package configs;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * DataConfig is where out entity manager is created and our entiyManagerFactory to manage all our entities.
 * This also returns the datasource for the database(URL, User, Pass, etc) and makes a jpa TRansaction MAnager.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import play.db.DB;

@Configuration
@EnableTransactionManagement
public class DataConfig {

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(false);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setDatabase(Database.MYSQL);

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPackagesToScan("jpa");
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}

	@Bean
	public DataSource dataSource() {
		// Return the datasource from the play framework.
		return DB.getDataSource();
	}
}