/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.ejb;

import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import musicstore.data.Article;
import musicstore.data.Customer;
import musicstore.data.Invoice;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Ivan
 */
public class ShoppingBeanTest {
    
    public ShoppingBeanTest() {
    }

    private EntityManagerFactory emFactory;
    
    @BeforeClass
    public static void setUpClass() throws Exception {        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        emFactory = Persistence.createEntityManagerFactory("MusicStore");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addItem method, of class ShoppingBean.
     */
    @Test
    public void testAddItem() throws Exception {
        System.out.println("addItem");
        Customer customer = new Customer();
        Article item = new Article();
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ShoppingBeanLocal instance = (ShoppingBeanLocal)container.getContext().lookup("java:global/classes/ShoppingBean");
        instance.addItem(customer, item);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeItem method, of class ShoppingBean.
     */
    @Test
    public void testRemoveItem() throws Exception {
        System.out.println("removeItem");
        Customer customer = null;
        Article item = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ShoppingBeanLocal instance = (ShoppingBeanLocal)container.getContext().lookup("java:global/classes/ShoppingBean");
        instance.removeItem(customer, item);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitOrder method, of class ShoppingBean.
     */
    @Test
    public void testSubmitOrder() throws Exception {
        System.out.println("submitOrder");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ShoppingBeanLocal instance = (ShoppingBeanLocal)container.getContext().lookup("java:global/classes/ShoppingBean");
        
        EntityManager em = emFactory.createEntityManager();
        Invoice inv = null;
        try {
            Article a = em.find(Article.class, 1);
            Customer c = em.find(Customer.class, 2);

            instance.addItem(c, a);
            inv = instance.getOrder(c);
            instance.submitOrder(c);
        } finally {
            if (inv != null) {
                try {
                    em.remove(inv);
                } catch (Exception ex) {
                }
            }
            
            em.close();
            container.close();
        }
    }
}
