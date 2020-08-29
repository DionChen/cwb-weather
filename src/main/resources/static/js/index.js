var now = new Date();
//var now_2 = new Date(now_1.setDate(now_1.getDate()+1));
   $.ajax({
         contentType: "application/json; charset=utf-8",
         type:"get",
         url:"default",
       //  data:jsonString,         
         success:function(data){
  //      	    $("#selecttime").append('<li><a href="#">' + (now.getMonth()+1) + '-' + (now.getDate()-1) + '</a></li>');
   //     	 	$("#selecttime").append('<li><a href="#">' + (now.getMonth()+1) + '-' + (now.getDate()) + '</a></li>');
    //    	 	$("#selecttime").append('<li><a href="#">'  + (now.getMonth()+1) + '-' + (now.getDate()+1) + '</a></li>');
   //			$("#selecttime").append('<li><a href="#" value=' + now.getFullYear() + '-' + (now.getMonth()+1) + '-' + (now.getDate()+1)  + '>'  + (now.getMonth()+1) + '-' + (now.getDate()+1) + '</a></li>');
       	 	$("#selecttime").append('<option value=' + now.getFullYear()  + '-' + (now.getMonth()+1) + '-' + (now.getDate()-1) + '>'  + (now.getMonth()+1) + '/' + (now.getDate()-1) + '</option>');
       	 $("#selecttime").append('<option value=' + now.getFullYear()  + '-' + (now.getMonth()+1) + '-' + (now.getDate()) + '>'  + (now.getMonth()+1) + '/' + (now.getDate()) + '</option>');
       	$("#selecttime").append('<option value=' + now.getFullYear()  + '-' + (now.getMonth()+1) + '-' + (now.getDate()+1) + '>'  + (now.getMonth()+1) + '/' + (now.getDate()+1) + '</option>');
       	 	
              for(var i=0;i<3;i++) {            
              $(".height").get(i).innerHTML = " - " + data[i][0] + "<sup>°</sup>";
              $(".low").get(i).innerHTML = data[i][1]  +"<sup>°</sup>";
              $(".rain").get(i).innerHTML =  data[i][2] + "%";            	
              }
              if(now .getHours() <=12) {
            $(".week-day").get(0).innerHTML = 	  "今日白天";
            $(".week-day").get(1).innerHTML = 	  "今晚明晨";
            $(".week-day").get(2).innerHTML = 	  "明日白天";
             $(".icon img").get(0).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/" +data[0][3] +".svg";
             $(".icon img").get(1).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/" +data[1][3] +".svg";
             $(".icon img").get(2).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/" +data[2][3] +".svg";
              	}else{
                    $(".week-day").get(0).innerHTML = 	  "今晚明晨";
                    $(".week-day").get(1).innerHTML = 	  "明日白天";
                    $(".week-day").get(2).innerHTML = 	  "明日晚上";
              		$(".icon img").get(0).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/" +data[0][3] +".svg";
                    $(".icon img").get(1).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/" +data[1][3] +".svg";
                    $(".icon img").get(2).src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/" +data[2][3] +".svg";
              	}  
              },         
         error:function(e){
               alert("error!!!");
         },
   }
   )
;