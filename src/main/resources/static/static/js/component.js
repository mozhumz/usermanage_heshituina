var appcenter_t={
    template:'<div><template>'+
             '                  <el-table'+
             '                          '+
             '                          style="width: 100%">'+
             '                      <el-table-column'+
             '                              prop="date"'+
             '                              label="日期"'+
             '                              width="180">'+
             '                      </el-table-column>'+
             '                      <el-table-column'+
             '                              prop="name"'+
             '                              label="姓名"'+
             '                              width="180">'+
             '                      </el-table-column>'+
             '                      <el-table-column'+
             '                              prop="address"'+
             '                              label="地址">'+
             '                      </el-table-column>'+
             '                  </el-table>'+
             '              </template></div>'
}

Vue.component('runoob', appcenter_t);