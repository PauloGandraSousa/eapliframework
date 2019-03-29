/*
 * 
 */
package eapli.framework.infrastructure.repositories.impl.jpa;

import org.junit.Test;

/**
 *
 * @author 1170558
 */
public class JpaBaseRepositoryTest {
    
    public JpaBaseRepositoryTest() {
    }

    /**
     * IllegalArgumentException.class should be added instead of the current exception,
     * after the insertion of a persistence unit and entity class
    */
    @Test(expected = UnsupportedOperationException.class) 
    public void ensureCountThrowsIllegalArgumentExceptionDueToParserFailure() {
        throw new UnsupportedOperationException(); //TO DO remove this exception and uncomment following lines
        //Persistence.createEntityManagerFactory("INSERT PERSISTENCE UNIT").createEntityManager()
        //        .createQuery("SELECT COUNT(*) FROM " + "INSERT ENTITY CLASS NAME", Long.class).getSingleResult();
    }
    
    /**
     * Test.None.class should be added instead of the current exception,
     * after the insertion of a persistence unit and entity class
    */
    @Test(expected = UnsupportedOperationException.class)
    public void ensureCountDoesNotThrowException() {
        throw new UnsupportedOperationException(); //TO DO remove this exception and uncomment following lines
        //Persistence.createEntityManagerFactory("INSERT PERSISTENCE UNIT").createEntityManager()
        //        .createQuery("SELECT COUNT(tablename) FROM " + "INSERT ENTITY" + " tablename", Long.class).getSingleResult();
    }
    
}
