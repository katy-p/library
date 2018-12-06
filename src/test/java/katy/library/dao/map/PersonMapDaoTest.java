package katy.library.dao.map;

import katy.library.dao.PersonDao;
import katy.library.dao.PersonDaoTestTemplate;


class PersonMapDaoTest extends PersonDaoTestTemplate {

    @Override
    protected PersonDao getPersonDao() { return new PersonMapDao();}
}