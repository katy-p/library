package katy.library.dao.map;

import katy.library.dao.AuthorDao;
import katy.library.dao.BookDao;
import katy.library.dao.BookDaoTestTemplate;


class BookMapDaoTest extends BookDaoTestTemplate {

    @Override
    protected BookDao getBookDao() {
        return new BookMapDao();
    }

    @Override
    protected AuthorDao getAuthorDao() {
        return new AuthorMapDao();
    }
}