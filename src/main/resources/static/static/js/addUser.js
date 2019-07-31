 $(function(){
//var userId=getParamValue('id');
//if(userId){
//    userId=parseInt(userId);
//}
//var menuType=getParamValue('menuType');
//if(menuType){
//    menuType=parseInt(menuType);
//}else{
//    menuType=1;
//}
  var mainV=new Vue({
   el: '#main',
   data:{
 	 tabPosition: 'left',
 	 //表格数据
 	 tableData: [],
 	 userList:[],
 	 //用户详情
 	 userDetail_f:true,
 	 //重置密码
 	 pwd_f:false,
 	 lazyBtn_f:false,
 	 search:'',
 	 welcomeUser:'',
 	 keyword:'',
 	 //用户列表-当前页
 	 userPage:1,
 	 //用户列表-总条数
 	 userTotal:0,
 	 roleList:[],
 	 userForm:{username:null,roleIdStr:null,createDate:null,updateDate:null,state:null,realName:null,
 	    phone:null,roleNameStr:null,roleIds:[],gender:null,doUserId:null,empPassword:null},
 	 userBaseInfo:'用户基本信息(姓名，账号，手机)：',
// 	 menuActive: menuType+'',
// 	 userId:userId,
 	 roleList:[],
 	 genderList:[{type:1,name:'男'},{type:2,name:'女'}],
 	 //操作人列表
     doUserList:[],
 	 rules: {
             realName: [
                              { required: true, message: '请输入用户姓名', trigger: 'blur' },
                              { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
                            ],
               username: [
                 { required: true, message: '请输入账号', trigger: 'blur' },
                 { min: 3, max: 5, message: '长度在 3 到 100 个字符', trigger: 'blur' }
               ],
               roleIds: [
                        { required: true, message: '请选择角色', trigger: 'change' }
                      ],
              phone: [
                          { required: true, message: '请输入用户手机', trigger: 'blur' },
                          { min: 8, max: 15, message: '长度在 8 到 15 个字符', trigger: 'blur' }
                        ],
              doUserId: [
                        { required: true, message: '请选择操作人', trigger: 'change' }
                      ],
              empPassword: [
                        { required: true, message: '请输入操作密码', trigger: 'blur' }
                      ],
           }
   },
//   filters:{
//
//   },
   methods:{
   formatDate(time) {
               var date = new Date(time);
               return formatDate(date, 'yyyy-MM-dd hh:mm');
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
         getLoginUser(){
             var res=ajax('GET',loginUrl, null);
             if(res.status){
                setCookie(userCookieName,res.data,120);
                return res.data;
             }else{
                open(res);
                return null;
             }
         },
        changeMenu(menuType){
            switch(menuType){
                case 1:
                    this.pwd_f=false;
                    this.userDetail_f=true;
                    break;
                case 2:
                    this.pwd_f=true;
                    this.userDetail_f=false;
                    break;
                default:
                    this.pwd_f=false;
                    this.userDetail_f=true;
                    break;
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
        getRoleList(){
            var param={};
            var res=ajax('POST',roleListUrl,param);
            if(!res.status)
            this.open(res);

            this.roleList=res.data;
            $('.selectpicker').selectpicker({
                    });
        },
        //添加用户
        addUser(formName){
        this.disableBtn();
            this.$refs[formName].validate((valid) => {
              if (valid) {
                var list=[];
                for(var op of getSelectedOpList($("#select01"))){
                    list.push(parseInt(op.value));
                }
                this.userForm.roleIds=list;
                var param=this.userForm;
                var res=ajax('POST',addUserUrl,param);
                this.enableBtn();
                this.open(res);
              } else {
                this.enableBtn();
                this.open2(paramErr);
                return false;
              }
            });

        },
        resetPwd(type){

            var param={userId:userId,type:type};
            var res=ajax('POST',resetPwdUrl,param);
            this.open(res);
        },
        openMsgBox(type) {
                var msg='';
                if(type==1){
                    //重置登录密码
                    msg='您正在重置用户登录密码, 是否继续?';
                }else{
                    //操作密码
                    msg='您正在重置用户操作密码, 是否继续?';
                }
                this.$confirm(msg, '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning'
                }).then(() => {
                  this.resetPwd(type);
                }).catch(() => {
                  this.$message({
                    type: 'info',
                    message: '已取消'
                  });
                });
        },
         getUserList(roleName){
            var param={roleName:roleName};
            var res=ajax('POST',userListUrl, param);
            if(!res.status){
                this.open(res);
                return null;
            }
            return res.data;
         },
        logOut(){
            window.location.href=logOutUrl;
        }


        },
   });




    //登录用户
    var user=mainV.getLoginUser();
    $("#welcomeUser").html(user.username);
//    mainV.changeMenu(menuType);

    //角色渲染
    mainV.getRoleList();
    for(var p of mainV.roleList){

        $("#select01").append('<option value="'+p.id+'">'+p.name+'</option>');
    }
     $('.selectpicker').selectpicker('refresh');

mainV.doUserList=mainV.getUserList('普通管理员');

 });

