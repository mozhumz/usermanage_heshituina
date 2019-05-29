function ajax(method,url,param){
var result=null;
$.ajax({
             type:method,
             dataType:"JSON",
             contentType: 'application/json',
             async: false,
             url:url,
             data:JSON.stringify(param),
             success:function(res){
                 console.log('ajax-res:'+res);
                 console.log('JSON.stringify-res:'+JSON.stringify(res));
//                 window.location.href='http://www.baidu.com';
//                    if(!res.status){
//                        open(res.message);
//                    }
                    result= JSON.stringify(res);
             }

         });
    return result;
}

function getWebUrl(loginHtml){
    try{
        var s=loginHtml.split('?');
        if(s.length>1){
            return s[1].split('=')[1];
        }
    }catch(Error ){

    }
    return null;
}

