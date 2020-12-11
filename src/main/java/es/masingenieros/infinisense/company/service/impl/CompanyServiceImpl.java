package es.masingenieros.infinisense.company.service.impl;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.masingenieros.infinisense.company.Company;
import es.masingenieros.infinisense.company.repository.CompanyRepository;
import es.masingenieros.infinisense.company.service.CompanyService;
import es.masingenieros.infinisense.company.service.impl.TenantException.CreateUpdateTenantSchemaException;
import es.masingenieros.infinisense.company.service.impl.TenantException.QueryTenantSchemaException;
import es.masingenieros.infinisense.company.service.impl.TenantException.TenantSchemaAlreadyExistsException;
import es.masingenieros.infinisense.mulitenancy.TenantConnectionProvider;
import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.service.UserService;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private DataSource dataSource;

	private ArrayList<String> listOfFiles = new ArrayList<String>();


	private final List<String> defaultPostgreSchemas = Collections
			.unmodifiableList(Arrays.asList("pg_toast", "pg_temp_1",
					"pg_toast_temp_1", "pg_catalog", "information_schema"));

	//public static final Pattern TENANT_SCHAME_PATTERN = Pattern.compile("/^([^0-9]*)$/");
	private static Logger log = LoggerFactory.getLogger(TenantConnectionProvider.class);





	@Override
	public Company createSchema(Company company, User userDefault) throws CreateUpdateTenantSchemaException, QueryTenantSchemaException,
	TenantSchemaAlreadyExistsException{
		if(listOfFiles.size() == 0) {
			listOfFiles.add("user.sql");
			listOfFiles.add("user_signature.sql");
			listOfFiles.add("company.sql");
			listOfFiles.add("epi.sql");
			listOfFiles.add("sensor_type.sql");
			listOfFiles.add("plant.sql");
			listOfFiles.add("plant_plane.sql");
			listOfFiles.add("plant_coordinate.sql");
			listOfFiles.add("reason.sql");
			listOfFiles.add("visit.sql");
		}

		String tenantSchema = company.getName();
		Assert.notNull(tenantSchema, "Tenant identidier must not be null");

		if (this.checkTenantSchemaExists(tenantSchema)) {
			throw new TenantSchemaAlreadyExistsException("client.errors.tenantSchemaAlreadyExists");
		}

		if (!isValidTenantSchema(tenantSchema)) {
			throw new CreateUpdateTenantSchemaException("Invalid schema name: " + tenantSchema);
		}

		log.info("Create database schema {}", tenantSchema);
		log.debug("Start of method createOrUpdateTenantSchema for tenants : {}", tenantSchema);

		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();

			statement = connection.createStatement();

			log.info("Execute command CREATE SCHEMA IF NOT EXISTS {}", tenantSchema);
			//statement.execute(String.format("SET search_path = \"%s\"", tenantSchema));
			//updateTenantSchema(tenantSchema);
			boolean schemaExec = false;

			/*Resource resource = new ClassPathResource("scripts/create_tables.sql");
	        File file = resource.getFile();
	        String query = IOUtils.toString(new FileReader(file));
	        query = query.replace("schema_name_replace", tenantSchema);
	        connection.setCatalog(tenantSchema);
	        connection.setSchema(tenantSchema);
			statement.execute(query);*/

			for (String fname : listOfFiles) {
				Resource resource = new ClassPathResource("scripts/"+fname);
				File file = resource.getFile();
				//File file = resource.getFile();
				String query = IOUtils.toString(new FileReader(file));
				query = query.replace("schema_name_replace", tenantSchema);
				if(!schemaExec) {
					statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", tenantSchema));
					schemaExec = true;
				}
				connection.setCatalog(tenantSchema);
				connection.setSchema(tenantSchema);
				statement.execute(query);
			}
			/*String currentTenant = TenantContext.getCurrentTenant();
			TenantContext.setCurrentTenant(tenantSchema);
			this.userService.save(userDefault);
			TenantContext.setCurrentTenant(currentTenant);*/
			Date today = new Date();
			userDefault.setPassword(passwordEncoder.encode(userDefault.getPassword()));
			String insertUserSql = String.format("INSERT INTO %s.user (uuid, created_by, created_date, modified_by, modified_date, active, dni, email, firstname, lastname,password, roles, username) "
					+ "VALUES (%s, %s, %s, %s, %s, %s, '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
					tenantSchema, "0", "0", today.getTime(), "0", today.getTime(), 
					userDefault.isActive(), userDefault.getDni(), userDefault.getEmail(),
					userDefault.getFirstname(), userDefault.getLastname(),
					userDefault.getPassword(), userDefault.getRoles(), 
					userDefault.getUsername());
			statement.execute(insertUserSql);
			
			String insertCompanySql = String.format("INSERT INTO %s.company (uuid, created_by, created_date, modified_by, modified_date, name, description, aliro, ergo, active, user_id) " + 
			" VALUES (%s, %s, %s, %s, %s, '%s', '%s', %s, %s, %s, %s);", 
					tenantSchema, "0", "0", today.getTime(), "0", today.getTime(), 
					company.getName(), company.getDescription(), company.getAliro(), company.getErgo(),
					company.getActive(), ("(select uuid from "+tenantSchema+".user where username = '"+userDefault.getUsername()+"')"));
			statement.execute(insertCompanySql);
			

		} catch (SQLException | IllegalArgumentException e) {
			try {
				statement.execute(String.format("DROP DATABASE %s", tenantSchema));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CreateUpdateTenantSchemaException(
					"Error on createOrUpdate  schema  : " + tenantSchema, e);
		} catch (Exception e) {
			try {
				statement.execute(String.format("DROP DATABASE %s", tenantSchema));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new CreateUpdateTenantSchemaException(
					"Error on createOrUpdate schema : " + tenantSchema, e);
		} finally {
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("Error closig statement on createOrUpdateTenantSchema {}.",
							tenantSchema, e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("Error closig connection on createOrUpdateTenantSchema {}.",
							tenantSchema, e);
				}
			}
			log.debug("End of method createOrUpdateTenantSchema for tenants : {}", tenantSchema);
		}

		log.info("Tenant schema {} created successfully", tenantSchema);
		return company;
	}


	private static File[] getResourceFolderFiles (String folder) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(folder);
		String path = url.getPath();
		return new File(path).listFiles();
	}

	/*public void updateTenantSchema(String schema)
			throws CreateUpdateTenantSchemaException, LiquibaseException {

		log.debug("Start of method createOrUpdateTenantSchema for tenant : {}", schema);

		//SpringLiquibase liquibase = null;
		log.info("Execute liquibase update for schema {}", schema);
		//liquibase.afterPropertiesSet();
		log.info("Database schema {} updated successfully", schema);
	}*/

	private boolean checkTenantSchemaExists(String tenantSchema) throws QueryTenantSchemaException {
		List<String> tenantSchemas = getTenantSchemas();
		return tenantSchemas.contains(tenantSchema);
	}


	public List<String> getTenantSchemas() throws QueryTenantSchemaException {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			List<String> schemas = new ArrayList<>();
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			String nonTenantSchemas = defaultPostgreSchemas.stream()
					.map(schema -> String.format("'%s'", schema))
					.collect(Collectors.joining(","));

			result = statement.executeQuery(String.format(
					"SELECT schema_name FROM information_schema.schemata WHERE schema_name not in (%s) order by schema_name asc",
					nonTenantSchemas));

			while (result.next()) {
				schemas.add(result.getString("schema_name"));
			}
			return schemas;
		} catch (SQLException | IllegalArgumentException e) {
			throw new QueryTenantSchemaException("Error on method getTenantSchemas", e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					log.error("Error closing resultset on getTenantSchemas method", e);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("Error closing statement on getTenantSchemas method", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("Error closing connection on getTenantSchemas method", e);
				}
			}
		}
	}

	private boolean isValidTenantSchema(final String schemaName) {
		return true;
		//return schemaName != null && TENANT_SCHAME_PATTERN.matcher(schemaName).matches();
	}


	@Override
	public Company save(Company company) {
		//User user = userService.save(userDefault);
		//company.setUser(user);
		return companyRepository.save(company);
	}

	@Override
	public Company update(String uuid, Company company) {
		Optional<Company> optReason = companyRepository.findById(uuid);
		Company companyInDb = optReason.get();
		companyInDb.setName(company.getName());
		companyInDb.setDescription(company.getDescription());
		companyInDb.setAliro(company.getAliro());
		companyInDb.setErgo(company.getErgo());
		companyInDb.setActive(company.getActive());
		
		// Update data into other schema
		Connection connection = null;
		Statement statement = null;
		String tenantSchema = companyInDb.getName();
		try {
			connection = dataSource.getConnection();

			statement = connection.createStatement();

			log.info("Execute command UPDATE COMPANY IN TENANT {}", companyInDb.getName());

			Date today = new Date();
			
			String updateCompanySql = String.format("UPDATE %s.company SET modified_date = '%s', description = '%s', ergo=%s, aliro=%s WHERE (uuid = '0');", 
					tenantSchema, today.getTime(), company.getDescription(), company.getErgo(), company.getAliro());
			statement.executeUpdate(updateCompanySql);
			

		} catch (SQLException | IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("Error closig statement on createOrUpdateTenantSchema {}.",
							tenantSchema, e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("Error closig connection on createOrUpdateTenantSchema {}.",
							tenantSchema, e);
				}
			}
			companyRepository.save(companyInDb);
		}
		return companyInDb;

		

	}

	@Override
	public void deleteCompanyByUuid(List<String> episUuid) {
		for (String id : episUuid) {
			companyRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<Company> findAll() {
		return companyRepository.findAll();
	}

	@Override
	public Optional<Company> findByUuid(String uuid) {
		return companyRepository.findById(uuid);
	}


}
