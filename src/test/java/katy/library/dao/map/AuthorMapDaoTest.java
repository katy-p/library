package katy.library.dao.map;

import katy.library.dao.AuthorDao;
import katy.library.dao.AuthorDaoTestTemplate;

class AuthorMapDaoTest extends AuthorDaoTestTemplate {

    @Override
    protected AuthorDao getAuthorDao() {
        return new AuthorMapDao();
    }
}