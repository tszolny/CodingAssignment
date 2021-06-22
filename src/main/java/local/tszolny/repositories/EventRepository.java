package local.tszolny.repositories;

import local.tszolny.entity.Event;
import local.tszolny.utils.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

public class EventRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepository.class);

    public void save(Event event){
        LOGGER.debug("Storing event {} information.", event.getId());
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            em.persist(event);
            et.commit();
        }
        catch (Exception ex){
            if(et != null){
                et.rollback();
            }
            LOGGER.error("Could not persist object.", ex);
        }
        finally {
            em.close();
        }
    }

    public List<Event> getEvents(){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        String strQuery = "SELECT e FROM Event e";
        TypedQuery<Event> tq = em.createQuery(strQuery, Event.class);
        try{
            return tq.getResultList();
        }
        finally {
            em.close();
        }
    }
}
