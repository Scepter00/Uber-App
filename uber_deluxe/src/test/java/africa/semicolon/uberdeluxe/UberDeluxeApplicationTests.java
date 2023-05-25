package africa.semicolon.uberdeluxe;

import jakarta.activation.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UberDeluxeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testDatabaseConnection(){
		DriverManagerDataSource dataSource =
				new DriverManagerDataSource("jdbc:postgres://127.0.0.1:5432");
		try {
			Connection connection = dataSource.getConnection("femi", "Debbie200@");
			System.out.println(connection);
			assertThat(connection).isNotNull();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
