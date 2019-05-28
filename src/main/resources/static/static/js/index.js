 $(function(){
  var loginV=new Vue({
   el: '#main',
   data:{
     username: '',
 	 password: '',
 	 tabPosition: 'left',
 	 tableData: [{
                 date: '2016-05-02',
                 name: '王小虎',
                 address: '上海市普陀区金沙江路 1518 弄'
               }, {
                 date: '2016-05-04',
                 name: '王小虎',
                 address: '上海市普陀区金沙江路 1517 弄'
               }, {
                 date: '2016-05-01',
                 name: '王小虎',
                 address: '上海市普陀区金沙江路 1519 弄'
               }, {
                 date: '2016-05-03',
                 name: '王小虎',
                 address: '上海市普陀区金沙江路 1516 弄'
               }]
   },
   methods:{
   handleOpen(key, keyPath) {
           console.log(key, keyPath);
         },
         handleClose(key, keyPath) {
           console.log(key, keyPath);
         },
     open(msg) {
                         this.$alert(msg, '提示', {
                       confirmButtonText: '确定',
                       callback: action => {
//                         this.$message({
//                           type: 'info',
//                           message: `action: ${ action }`
//                         });
                       }
                     });
                   }
   }
   });
var loginHtml=window.location.href;
console.log(loginHtml);
var webUrl=getWebUrl(loginHtml);
console.log(webUrl);
 $("#loginBtn").click(

     function(){
     console.log(loginV.username);
     console.log(loginV.password);
     if(!loginV.password||!loginV.username){
//        alert("用户名或密码不能为空");
        loginV.open("用户名或密码不能为空");
        return false;
     }
     var param={username:loginV.username,password:hex_md5(loginV.password+DEFAULT_KEY)};
     var res=ajax("POST",loginUrl,param);
     if(res.status){
        console.log("ok");
        if(webUrl){
            window.location.href=webUrl+'?token='+res.data.token;
        }else{
            window.location.href='../usermanage/index.html';
        }
     }

     }
 );

 });

