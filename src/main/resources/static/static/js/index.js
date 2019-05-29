 $(function(){

  var mainV=new Vue({
   el: '#main',
   data:{
 	 tabPosition: 'left',
 	 //表格数据
 	 tableData: [],
 	 //应用中心
 	 appcenter_f:true,
 	 //用户列表
 	 userList_f:false,
 	 search:''
   },
   methods:{
        //展开导航
        handleOpen(key, keyPath) {
           console.log(key, keyPath);
         },
        //关闭导航
        handleClose(key, keyPath) {
           console.log(key, keyPath);
         },
        //提示消息
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
        },
        handleEdit(index, row) {
               console.log(index, row);
               console.log(row.status);
               row.status=true;
             },
        handleDelete(index, row) {
           console.log(index, row);
               row.status=false;
         },
         //应用中心
         getAppList(keyword){
            console.log(keyword);
            appcenter_f=true;
            userList_f=false;
            var param={};
            if(keyword){
                param={keyword:keyword};
            }
            //获取应用列表
            var res=ajax('POST',appUrl,param);

            if(!res.status){
                open(res.message);
                return false;
            }
            tableData=res.data;
         }




        },
   });

   mainV.tableData=[{                status:false,
                                     remark: '2016-05-02',
                                     name: '王小虎',
                                     url: '上海市普陀区金沙江路 1518 弄'
                                   }];


   mainV.getAppList(null);

 });

