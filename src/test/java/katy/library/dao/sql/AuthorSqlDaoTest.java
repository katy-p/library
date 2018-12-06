package katy.library.dao.sql;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import katy.library.dao.AuthorDao;
import katy.library.dao.AuthorDaoTestTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;


class AuthorSqlDaoTest extends AuthorDaoTestTemplate {

    private static EmbeddedPostgreSQL embeddedPostgreSQL;
    private static DataSource source;

    @Override
    protected AuthorDao getAuthorDao() { return new AuthorSqlDao(source);}

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