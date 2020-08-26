$("#locationSeries p").click(function(){ 
        var ul = $("#locationSeries ul"); 
        if(ul.css("display")=="none"){ 
            ul.slideDown("fast"); 
        }else{ 
            ul.slideUp("fast"); 
        } 
    }); 
$("#locationSeries ul li a").click(function(){ 
    var txt = $(this).text(); 
    $("#locationSeries p").html(txt); 
    $("#locationSeries ul").hide(); 
}); 


$("#timeSeries p").click(function(){ 
    var ul = $("#timeSeries ul"); 
    if(ul.css("display")=="none"){ 
        ul.slideDown("fast"); 
    }else{ 
        ul.slideUp("fast"); 
    } 
}); 
$("#timeSeries").on("click","ul li a",function(){ 
var txt = $(this).text(); 
$("#timeSeries p").html(txt); 
$("#timeSeries ul").hide(); 
}); 
