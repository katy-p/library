package katy.library.dao.sql;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import katy.library.dao.PersonDao;
import katy.library.dao.PersonDaoTestTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;


class PersonSqlDaoTest extends PersonDaoTestTemplate {

    private static EmbeddedPostgreSQL embeddedPostgreSQL;
    private static DataSource source;

    @Override
    protected PersonDao getPersonDao() {
        return new PersonSqlDao(source);
    }

    @BeforeAll
    static void init() throws IOException, SQLException {

        embeddedPostgreSQL = EmbeddedPostgreSQL.start();

        source = embeddedPostgreSQL.getPostgresDatabase();

        SqlDaoTestUtils.createTables(source);
    }

    @AfterAll
    static void teardown() throws IOException {

        embeddedPostgreSQL.close();
    }

    @BeforeEach
    void cleanData() {

        SqlDaoTestUtils.clearTables(source);
    }
}