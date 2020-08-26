package webapp.Service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.entities.Item;

public class SearchData {
    
    private  final Logger log =
            LoggerFactory.getLogger(DownloadCwbData.class);
    
    @PersistenceContext
    public EntityManager entityManager;
        
    public  String test(Item item){
       return item.getSelectLocation(); 
       
    };
    
    public List testSearch(Item item) {
     /*   String location = item.getSelectLocation();
        String time = item.getSelecttime();
        Query query = entityManager.createNativeQuery("select * from cwbdata where location = (?) and starttime > now() limit 3");
        query.setParameter(1, location);
        Query query2 = entityManager.createNativeQuery("select * from cwbdata");
        return "success";
         */
        String location = item.getSelectLocation();
        String time = item.getSelecttime();
       Query query = entityManager.createNativeQuery("select maxtemp,mintemp,pop,wx from cwbdata where location = ? and starttime > ? limit 3");
        query.setParameter(1, location);
        query.setParameter(2, time);
     //   query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);      
       List rows  = query.getResultList();
       return rows;
        }
}
