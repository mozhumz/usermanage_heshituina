 $(function(){
var userId=getParamValue('id');
if(userId){
    userId=parseInt(userId);
}
var menuType=getParamValue('menuType');
if(menuType){
    menuType=parseInt(menuType);
}else{
    menuType=1;
}
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
 	 search:'',
 	 welcomeUser:'',
 	 keyword:'',
 	 //用户列表-当前页
 	 userPage:1,
 	 //用户列表-总条数
 	 userTotal:0,
 	 roleList:[],
 	 userForm:{id:userId,username:null,roleIdStr:null,createDate:null,updateDate:null,state:null,realName:null,
 	    phone:null,roleNameStr:null,roleList:[],gender:null},
 	 userBaseInfo:'用户基本信息(姓名，账号，手机)：',
 	 menuActive: menuType+'',
 	 userId:userId,
 	 roleList:[],
 	 genderList:[{type:1,name:'男'},{type:2,name:'女'}]
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
        //修改用户信息
        saveUser(){
            var list=[];
            for(var op of getSelectedOpList($("#select01"))){
                var r={};
                r.id=parseInt(op.value);
                r.name=op.text;
                list.push(r);
            }
            this.userForm.roleList=list;
            var param=this.userForm;
            var res=ajax('POST',saveUserUrl,param);
            this.open(res);
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
        logOut(){
            window.location.href=logOutUrl;
        }


        },
   });




    //登录用户
    var user=mainV.getLoginUser();
    $("#welcomeUser").html(user.username);
    mainV.changeMenu(menuType);
    //基本信息
    mainV.getUserDetail();
    if(!mainV.userForm.realName){
        mainV.userForm.realName='';
    }
    if(!mainV.userForm.phone){
        mainV.userForm.phone='';
    }
    mainV.userBaseInfo=mainV.userBaseInfo+mainV.userForm.realName+','+mainV.userForm.username+','+mainV.userForm.phone;

    //角色渲染
    mainV.getRoleList();
    console.log($("#select01"));
    for(var p of mainV.roleList){
        var f='';
        for(var i of mainV.userForm.roleList){
            if(p.id==i.id){
                f='selected';
                break;
            }
        }
        $("#select01").append('<option value="'+p.id+'"'+f+'>'+p.name+'</option>');
    }
     $('.selectpicker').selectpicker('refresh');
//     $('.selectpicker').selectpicker('render');


 });

