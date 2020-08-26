var now = new Date();
$("#submit").click(function ()
{
  var json=$("#formDemo").serializeJSON();
var jsonString = JSON.stringify(json);
var test1 = '{"selectlocation": "' +  $("#locationSeries p").text() + '","selecttime":"' + now .getFullYear() + '-' + $("#timeSeries p").text() + '"}' ;
//{"selectlocation": "臺北市","selecttime":2020-8-22"}
   $.ajax({
         contentType: "application/json; charset=utf-8",
         type:"post",
         url:"submit",
         data: test1,
         //data: "hello1 hello2",
     //    data: {"selectLocation":"bbb","selecttime": "ccc" },
       //  data: {selectLocation:$('#locationSeries p').attr('value') },
         success:function(data){
        	 alert("success!!!");
              for(var i=0;i<3;i++) { 	 
              $(".height").get(i).innerHTML = " - " + data[i][0] + "<sup>°</sup>";
              $(".low").get(i).innerHTML = data[i][1]  +"<sup>°</sup>";
              $(".rain").get(i).innerHTML =  data[i][2] + "%";
              alert("data=" + data[i][0] + data[i][1] + data[i][2]  + data[i][3]);
            	  }
              $(".week-day").get(0).innerHTML = 	  "當天白天";
              $(".week-day").get(1).innerHTML = 	  "當天晚上";
              $(".week-day").get(2).innerHTML = 	  "隔天白天";
             $(".icon img").get(0).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/" +data[0][3] +".svg";
             $(".icon img").get(1).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/" +data[1][3] +".svg";
             $(".icon img").get(2).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/" +data[2][3] +".svg";
         },
         error:function(e){
               alert("error!!!");
         },
   }
   )
});