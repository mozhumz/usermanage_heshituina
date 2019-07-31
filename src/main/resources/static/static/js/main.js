 $(function(){

  var mainV=new Vue({
   el: '#main',
   data () {
        var emailCheck=(rule, value, callback) => {
                    var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
                     if (!this.emailDto.email) {
                       callback(new Error('请输入邮箱'));
                     }else if(!reg.test(this.emailDto.email)){
                        callback(new Error('邮箱格式错误'));
                     }
                     else {
                       callback();
                     }
       };
       var pwdCheck=(rule, value, callback) => {
                    if (!value) {
                      callback(new Error('请输入密码'));
                    }else if(value.length<6||value.length>18){
                       callback(new Error('密码位数必须在6-18之间'));
                    }
                    else {
                      callback();
                    }
      };
    return{
 	 tabPosition: 'left',
 	 //表格数据
 	 tableData: [],
 	 userList:[],
 	 //应用中心
 	 appcenter_f:true,
 	 //用户列表
 	 userList_f:false,
 	 //系统设置
 	 sys_f:false,
 	 //系统邮箱
 	 sys_email_f:false,
 	 //添加邮箱
     sys_emailAdd_f:false,
     //个人中心
     self_f:false,
     //修改信息
     self_info_f:false,
     //密码修改
     self_pwd_f:false,
 	 search:'',
 	 welcomeUser:'',
 	 keyword:'',
 	 //用户列表-当前页
 	 userPage:1,
 	 //用户列表-总条数
 	 userTotal:0,
 	 roleList:[],
 	 isAdmin:false,
 	 userForm:{id:null,username:null,roleIdStr:null,createDate:null,updateDate:null,state:null,realName:null,
      	    phone:null,roleNameStr:null,roleList:[],gender:null,newPwd:null,type:null,code:null,email:null,password2:null},
     genderList:[{type:1,name:'男'},{type:2,name:'女'}],
     //密码类型
     pwdList:[{type:1,name:'登录密码'},{type:2,name:'操作密码'}],
 	 emailDto:{id:null,email:null,pwd:null,remark:null},
 	 emailList:[],
 	 emailQo:{page:null,size:null,keyword:null,state:null},
 	 emailTotal:0,
 	 emailPage:1,
     userId:null,
     lazyBtn_f:false,
     sendCodeBtn_f:false,
     sendCodeTxt:'发送验证码（有效期为5分钟）',
     sendCode_f:true,
     code_time:60,
 	 emailRules:{
 	    email: [
            { required: true, validator: emailCheck, trigger: 'blur' }
          ],
        pwd: [
            { required: true, message: '请输入授权码', trigger: 'blur' }
          ]
 	 },
 	 userRules:{
        type: [
             { required: true, message: '请选择要修改的密码类型', trigger: 'change' }
           ],
         email: [
             { required: true, message: '未设置邮箱，请先到修改信息菜单设置', trigger: 'blur' }
           ],
         code: [
              { required: true, message: '请输入验证码', trigger: 'blur' }
            ],
        newPwd: [
             { required: true, validator: pwdCheck, trigger: 'blur' }
           ],
        password2: [
                { required: true, message: '请再次输入密码', trigger: 'blur' }
              ],
     },
    };
   },
//   filters:{
//
//   },
   methods:{
   formatDate(time) {
               var date = new Date(time);
               return formatDate(date, 'yyyy-MM-dd hh:mm');
             },
        //展开导航
        handleOpen(key, keyPath) {
           console.log(key, keyPath);
         },
        //关闭导航
        handleClose(key, keyPath) {
           console.log(key, keyPath);
         },
        //提示消息
        open(res) {
                 var msg=res.message;
                 var type='error';
                 if(res.status){
                     type='success';
                     msg=ok;
                 }
                 this.$message({
                     message: msg,
                     type: type
                 });
        },
        open2(msg) {
          var type='error';
          this.$message({
              message: msg,
              type: type
          });
       },
        //编辑应用
        handleEdit(index, row) {
               row.status=true;
         },
          disableBtn(type){
             if(!type){
               mainV.lazyBtn_f=true;
             }else{
               mainV.sendCodeBtn_f=true;
             }
           },
           enableBtn(type){
             setTimeout(function(){
                 if(!type){
                   mainV.lazyBtn_f=false;
                 }else{
                   mainV.sendCodeBtn_f=false;
                 }
               },1000);
         },
         //保存应用
        handleSave(index, row) {
           this.disableBtn();
           row.status=false;
           var res=ajax('POST',updateAppUrl,row);
           this.enableBtn();
           this.open(res);
         },
         changeMenu(type,sysType){
            switch(type){
                case 1:
                    this.appcenter_f=true;
                    this.userList_f=false;
                    this.sys_f=false;
                    this.self_f=false;
                    this.getAppList(type);
                    break;
                case 2:
                    this.appcenter_f=false;
                    this.userList_f=true;
                    this.self_f=false;
                    this.sys_f=false;
                    this.getUserList(type);
                    break;

                case 3:
                //系统设置
                    this.appcenter_f=false;
                    this.userList_f=false;
                    this.sys_f=true;
                    this.self_f=false;
                    switch(sysType){
                        case 1:
                        //邮箱列表
                            this.sys_email_f=true;
                            this.sys_emailAdd_f=false;
                            this.getEmails();
                            break;
                        case 2:
                        //添加邮箱
                            this.sys_email_f=false;
                            this.sys_emailAdd_f=true;
                            break;
                        default:
                            this.sys_email_f=true;
                            this.sys_emailAdd_f=false;
                            break;
                    }
                    break;
                case 4:
                //个人中心
                    this.appcenter_f=false;
                    this.userList_f=false;
                    this.sys_f=false;
                    this.self_f=true;
                    switch(sysType){
                        case 1:
                        //信息修改
                            this.self_pwd_f=false;
                            this.self_info_f=true;
                            break;
                         case 2:
                        //密码修改
                            this.self_pwd_f=true;
                            this.self_info_f=false;
                            break;
                        default:
                            this.self_pwd_f=false;
                            this.self_info_f=true;
                            break;
                    }
                    break;
                default:
                    this.appcenter_f=true;
                    this.userList_f=false;
                    this.sys_f=false;
                    this.self_f=false;
                    break;

            }
         },
         //应用中心
         getAppList(type){
            if(type){
                this.keyword='';
            }

            var param={};
            if(this.keyword){
                param={keyword:this.keyword};
            }
            //获取应用列表
            var res=ajax('POST',appUrl,param);

            if(!res.status){
                this.open(res);
                return false;
            }
            this.tableData=res.data;
         },
         getUserList(type,page,size){
            this.userPage=page;
            if(type){
             this.keyword='';
            }

            var param={};
            if(isNum(page,size)){
                param={page:page,size:size};
            }
            if(this.keyword){
                param.keyword=this.keyword;
            }
            //获取应用列表
            var res=ajax('POST',userUrl,param);

            if(!res.status){
                this.open(res);
                return false;
            }
            if(res.data.records.length>0){
                for(let i of res.data.records){
                    i.createDate=this.formatDate(i.createDate);
                    i.gender=getGender(i.gender);
                    if(i.state==1){
                        i.state_f=true;
                        i.state_info='正常';
                    }else{
                        i.state_f=false;
                        i.state_info='冻结';
                    }
                    if(i.is0pwd==1){
                        i.is0pwd="是";
                    }else{
                        i.is0pwd="否";
                    }
                    if(i.is0bpwd==1){
                        i.is0bpwd="是";
                    }else{
                        i.is0bpwd="否";
                    }
                }
            }
            this.userList=res.data.records;
            this.userTotal=res.data.total;
         },
         //添加邮箱
         addEmail(formName){
                this.disableBtn();
            this.$refs[formName].validate((valid) => {
              if (valid) {
                var param=this.emailDto;
                var res=ajax('POST',addEmailUrl,param);
                this.enableBtn();
                this.open(res);
              } else {
                this.enableBtn();
                this.open2(paramErr);
                return false;
              }
            });

         },
         //修改邮箱
        updateEmail(index,row){
            row.status=false;
            if(row.state_f){
                row.state=1;
            }else{
                row.state=2;
            }
            var param={id:row.id,email:row.email,remark:row.remark,pwd:row.pwd,state:row.state};

            var res=ajax('POST',updateEmailUrl,param);
            this.open(res);
          },
          //获取邮箱列表
        getEmails(){
            var param=this.emailQo;
            var res=ajax('POST',emailsUrl,param);
            if(!res.status){
                this.open(res);
                return null;
            }
            for(var i of res.data.records){
                i.status=false;
                i.createDate=this.formatDate(i.createDate);
                if(i.state==1){
                    i.state_f=true;
                }else{
                    i.state_f=false;
                }
            }
            this.emailList=res.data.records;
            this.emailTotal=res.data.total;
        },
         getLoginUser(){
             var res=ajax('GET',loginUrl, null);
             if(res.status){
                setCookie(userCookieName,res.data,120);
                this.userId=res.data.id;
                return res.data;
             }else{
                open(res);
                return null;
             }
         },
         getUserDetail(){
              var param={id:this.userId};
              var res=ajax('POST',userDetailUrl,param);
              if(!res.status)
              this.open(res);
              res.data.createDate=this.formatDate(res.data.createDate);
              this.userForm=res.data;
         },
         //修改用户信息
         saveUser(){
             this.disableBtn();
             var param=this.userForm;
             var res=ajax('POST',saveUserUrl,param);
             this.disableBtn();
             this.open(res);
         },

         //修改密码
          changePwd(formName){
            this.disableBtn();
            this.$refs[formName].validate((valid) => {
              if (valid) {
                this.disableBtn();
                 if(!checkParams(this.userForm.newPwd,this.userForm.password2,this.userForm.code)){
                     this.open2(paramErr);
                     this.enableBtn();
                     return null;
                 }
                 if(this.userForm.newPwd!=this.userForm.password2){
                     this.open2('两次输入的密码不一致');
                     this.enableBtn();
                     return null;
                 }
                 if(this.userForm.newPwd=="123456"){
                     this.open2('密码不能为初始密码：123456');
                     this.enableBtn();
                     return null;
                 }
                 this.userForm.newPwd=hex_md5(this.userForm.newPwd+DEFAULT_KEY);
                 var param=this.userForm;
                 var res=ajax('POST',changePwdUrl,param);
                 this.enableBtn();
                 this.open(res);
              } else {
                this.enableBtn();
                this.open2(paramErr);
                return false;
              }
            });

           },
          //发送验证码（有效期为5分钟）
           sendEmailCode(){

               this.sendCodeBtn_f=true;
               if(this.sendCode_f){
                   var timer = setInterval(function () {

                       if(mainV.code_time == 60 && mainV.sendCode_f){
                           mainV.sendCode_f = false;
                             if(!mainV.userForm.email){
                                   mainV.open2('未设置邮箱，请先到修改信息菜单设置');
                                   mainV.sendCode_f = true;
                                   mainV.code_time = 60;
                                   clearInterval(timer);
                                   mainV.enableBtn(1);
                                   return null;
                           }
                           var param={receiveEmail:mainV.userForm.email};
                           var res=ajax('POST',sendEmailCodeUrl,param);
                           if(!res.status){
                                  mainV.sendCode_f = true;
                                 mainV.code_time = 60;
                                 clearInterval(timer);
                                 mainV.enableBtn(1);
                           }
                           mainV.open(res);
                     }else if(mainV.code_time == 0){
                           mainV.sendCodeTxt='发送验证码（有效期为5分钟）';
                           mainV.enableBtn(1);
                           clearInterval(timer);
                           mainV.code_time = 60;
                           mainV.sendCode_f = true;
                       }else {
                          mainV.sendCodeTxt=mainV.code_time + " s 重新发送";
                           mainV.code_time--;
                       }
                   },1000);
                }

           },
         handleSizeChange(val) {
             console.log(`每页 ${val} 条`);
           },
         handleCurrentChange(val) {
             this.getUserList(null,val,10);
         },
          emailPageChange(val) {
              this.emailQo.page=val;
              this.emailQo.size=10;
              this.getEmails();
          },
         openUserHtml(row){
            open(userHtml+'?'+escape('id='+row.id+'&menuType=1'));
         },
         logOut(){
            window.location.href=logOutUrl;
         },
         //添加用户
         openAddUser(){
            open(openAddUser);
         },
         //消息弹框
         openMsgBox(type,row) {
                 var msg='';
                 var state=row.state;
                 if(type==1){
                     //冻结用户
                     msg='您正在冻结'+row.username+'用户, 是否继续?';
                     row.state=2;
                 }else if(type==2){
                    //解冻用户
                    msg='您正在解冻'+row.username+'用户, 是否继续?';
                    row.state=1;
                 }
                 else{
                    return null;
                 }
                 this.$confirm(msg, '提示', {
                   confirmButtonText: '确定',
                   cancelButtonText: '取消',
                   type: 'warning'
                 }).then(() => {

                   this.userForm.state=row.state;
                   this.userForm.id=row.id;
                   this.userForm.username=row.username;
                   this.userForm.roleList=row.roleList;
                   this.saveUser();
                   this.getUserList();
                 }).catch(() => {
                    row.state=state;
                   this.$message({
                     type: 'info',
                     message: '已取消'
                   });
                 });
         }





        },
   });




   mainV.getAppList();
   var user=mainV.getLoginUser();
   mainV.getUserDetail();
   if(user.role.name=="admin"){
    mainV.isAdmin=true;
   }
//   console.log($("#welcomeUser"));
$("#welcomeUser").html(user.username);
 //回车键绑定
 $("body").keydown(function() {
     if (event.keyCode == "13") {//keyCode=13是回车键
         if(mainV.appcenter_f){
            $('#appBtn').click();//换成按钮的id即可
         }else if(mainV.userList_f){
            $('#userBtn').click();
         }else if(mainV.sys_email_f){
            $('#emailBtn').click();
         }

     }
 });

 });

