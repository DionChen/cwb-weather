package webapp.Service;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import webapp.entities.Item;

@Component
public class DownloadCwbData {
  
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final Logger log =
            LoggerFactory.getLogger(DownloadCwbData.class);
            
        private static final SimpleDateFormat dateFormat = new
                SimpleDateFormat("HH:mm:ss");
        
        @Scheduled(cron = "0 * * * * ?")
        // sec min hour day month 1-7 Year
        @Transactional
        public void cwbSevenDayWeatherOverview() throws ParseException, SQLException, ClientProtocolException, IOException {
            int responseCode;
            int inByte;  
            String pop[]            = new String[7];
            String startTime[]   = new String[7];
            String endTime[]    = new String[7];
            String hTemp[]      = new String[7];
            String lTemp[]       = new String[7];
            String wx[]            = new String[7];
            
            //getdata from cwb restful api
            CloseableHttpClient client = HttpClientBuilder.create().build();
            
            HttpGet request = new HttpGet(
                    //鄉鎮天氣預報-台灣未來1週天氣預報
                      "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-D0047-091?Authorization=CWB-A728380F-57B8-4C6B-B009-F3CEE91D20C5&downloadType=WEB&format=JSON"
                    );
            
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            
            responseCode = response.getStatusLine().getStatusCode();
            
            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);
            InputStream is = entity.getContent();
            //Chinese change to UTF-8
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
           StringBuilder sb = new StringBuilder();         
           // InputStream to StringBuilder
            while (( inByte = isr.read()) != -1) {
               sb.append((char) inByte);    
            }
            // change into JsonObject
            JSONObject object = new JSONObject(sb.toString());
            JSONArray rawData = object.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONObject("locations").getJSONArray("location");
           //Date formate setting
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.TAIWAN);//輸入的被轉化的時間格式
            SimpleDateFormat transforDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.TAIWAN);
            String locationName[]   = new String[rawData.length()];
            //Get data from Json Array
            for (int i = 0; i < rawData.length(); i++) {
                locationName[i] = rawData.getJSONObject(i).getString("locationName");
                
                for (int j=0;j < 6;j++) {
                   
                   String  startTimeCache     = rawData.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(9).getJSONArray("time").getJSONObject(j).getString("startTime"); 
                   String endTimeCache      = rawData.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(9).getJSONArray("time").getJSONObject(j).getString("endTime") ;
                    pop [j]            = rawData.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(9).getJSONArray("time").getJSONObject(j).getJSONObject("elementValue").getString("value");
                    hTemp [j]       = rawData.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(3).getJSONArray("time").getJSONObject(j).getJSONObject("elementValue").getString("value");
                    lTemp [j]        = rawData.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(4).getJSONArray("time").getJSONObject(j).getJSONObject("elementValue").getString("value");
                    wx[j]              = rawData.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(12).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(1).getString("value");

                    //日期格式轉換
                    Date  startTemp           = inputDateFormat.parse(startTimeCache);
                    startTime[j]            = transforDateFormat.format(startTemp);
                    Date endTemp           = inputDateFormat.parse(endTimeCache);
                    endTime[j]           = transforDateFormat.format(endTemp); 
                    // skip time
                    if(startTime[j].contains("12:00:00"))  {
                        System.out.println("skip" + startTime[j] );
                        continue;
                    }
                        
                     entityManager.createNativeQuery("insert ignore into cwbdata (location,maxtemp,mintemp,pop,starttime,endtime,wx) VALUES (?,?,?,?,?,?,?)")
                    .setParameter(1,locationName[i] )
                    .setParameter(2, hTemp[j])
                    .setParameter(3, lTemp[j])
                    .setParameter(4, pop[j])
                    .setParameter(5, startTime[j])
                    .setParameter(6, endTime[j])
                    .setParameter(7, wx[j])
                    .executeUpdate();
           
                    
                    //StringBuilder sb2 = new StringBuilder();
                    //sb2.append("insert ignore into cwbdata (location,maxtemp,mintemp,pop,starttime,endtime) VALUES ");
                    //sb2.append("locationName[i],hTemp[j],lTemp[j],pop[j],startTime[j],endTime[j]");
                    //String sql = sb.toString().substring(0,sb.length()-1);
                    //Query query = entityManager.createNativeQuery(sql);
                    System.out.println("location: " + locationName[i] + " startTime: " + startTime[j] + " endTime: " + endTime[j] +" Temp: " + lTemp[j] + " - " + hTemp[j] +" Pop: " + pop[j] + "%" + "wx: " + wx[j]);
                }
                }
        }
        /*直接使用url 去update 最新的pic， 所以目前暫時不用
        @Scheduled(fixedRate =500000)
        @Transactional
        public void cwbpicurl() throws ClientProtocolException, IOException {
            int responseCode;
            int inByte;  
            String urllist[] = new String[4];
            String pop[]  = {
                    //天氣分析與預測圖-地面天氣圖（6hr）
                    "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0035-001?Authorization=CWB-A728380F-57B8-4C6B-B009-F3CEE91D20C5&downloadType=WEB&format=JSON",
                    //天氣分析與預測圖-定量降水預報(I)(未來0-12小時)(12hr)
                    "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0035-015?Authorization=CWB-A728380F-57B8-4C6B-B009-F3CEE91D20C5&downloadType=WEB&format=JSON",
                    //日累積雨量圖資料-小間距日累積雨量圖資料(30min)
                    "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/O-A0040-002?Authorization=CWB-A728380F-57B8-4C6B-B009-F3CEE91D20C5&downloadType=WEB&format=JSON",
                    //雷達整合回波圖-臺灣(較大範圍)_無地形 (10min)
                    "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/O-A0058-001?Authorization=CWB-A728380F-57B8-4C6B-B009-F3CEE91D20C5&downloadType=WEB&format=JSON"
                    };
            //getdata from cwb restful api
            CloseableHttpClient client = HttpClientBuilder.create().build();
            for(int k=0;k<pop.length;k++) {
            HttpGet request = new HttpGet(pop[k]);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            responseCode = response.getStatusLine().getStatusCode();          
            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);
            
            InputStream is = entity.getContent();
            //Chinese change to UTF-8
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
           StringBuilder sb = new StringBuilder();         
           // InputStream to StringBuilder
            while (( inByte = isr.read()) != -1) {
               sb.append((char) inByte);
            }
            // change into JsonObject
            JSONObject object = new JSONObject(sb.toString());
             urllist[k] = object.getJSONObject("cwbopendata").getJSONObject("dataset").getJSONObject("resource").getString("uri");
        }
            //entityManager.createNativeQuery("insert ignore into cwbdata () VALUES (?,?,?)")
            //.setParameter(1,urllist[0])
            //.setParameter(2, urllist[1])
            //.setParameter(3, urllist[2])
            //.setParameter(4, urllist[3])
            //.executeUpdate();
            System.out.println("get cwb pic urlLength=" + urllist[0] + urllist[1] + urllist[2] + urllist[3]);
    }
    
    
        /*
       public String testSearch(Item item) {
            String location = item.getSelectLocation();
            String time     = item.getSelecttime();
            Query query  = entityManager.createNativeQuery("select * from cwbdata where location = (?) and starttime > (?) limit 3");
            query.setParameter(1, location);
            query.setParameter(2, time);
            System.out.println("query=" + query.getResultList());
                return "test";
        }; */
}
