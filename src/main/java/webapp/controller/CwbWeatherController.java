package webapp.controller;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webapp.entities.Weather;
import webapp.repository.PersonRepository;
import webapp.Service.DownloadCwbData;
import webapp.Service.SearchData;
import webapp.entities.Item;
import webapp.Service.SearchData;

//@RestController --> 會顯示你Response 的字串，當要使用html 顯示時，改用Controller
@Controller
public class CwbWeatherController {

       private final PersonRepository personRepository;
       
      @PersistenceContext
      private EntityManager entityManager;
      
       @Autowired
       public CwbWeatherController(PersonRepository personRepository) {
           this.personRepository = personRepository;
       }
        
   //     @RequestMapping("/test")
    //    public  List <Weather> test() {
           // List <Weather> weather = personRepository.findBylocation(location);
          //  System.out.println(weather);
     //       return personRepository.findAll();
      //  }
        @RequestMapping("/index")
        public String Indextest() {
          
            return "index";
        }      
        
        @RequestMapping("/submit")
        @ResponseBody
        public  List AddItem(@RequestBody Item item) {     
            String location = item.getSelectLocation();
            String time = item.getSelecttime();
           Query query = entityManager.createNativeQuery("select maxtemp,mintemp,pop,wx from cwbdata where location = ? and starttime > ? limit 3");
            query.setParameter(1, location);
            query.setParameter(2, time);
         //   query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);      
           List rows  = query.getResultList();
          // for (Object obj : rows) {
           //    Map row = (Map) obj;
               //System.out.println("0=" + cells[0]);
              // System.out.println("1=" + cells[1]);
            //   System.out.println("maxtemp = "  + row.get("maxtemp"));
             //  System.out.println("mintemp = "  + row.get("mintemp"));
              // System.out.println("pop = "  + row.get("pop"));
              // System.out.println(" wx = "  + row.get("wx"));
          // }
            //System.out.println("location=" + location + "time=" + time);
                /*DownloadCwbData test1 = new DownloadCwbData();
                test1.testSearch(item); */
           System.out.println("test=" + location + time );
           return rows;
       } 
         
              
        @RequestMapping("/default")    
        @ResponseBody //當返回非html dom 型態的資料 Ex. json xml .... 須要使用 不然會跳出例外
        
        public  List index() {           
            String location = "臺北市";                           
           Query query = entityManager.createNativeQuery("select  maxtemp,mintemp,pop,wx from cwbdata where location = ? and starttime > now() limit 3");
                   query.setParameter(1, location);
                List index  = query.getResultList();      

                return index ;
               
        }
}
