var pre='http://127.0.0.1:8080/usermanage';
var zuulPre='http://127.0.0.1:8080/hstn';
var logOutUrl=zuulPre+'/api/login/logOut';
var loginHtml=zuulPre+'/index.html';
var ok='操作成功';
//获取登录用户-Get
var loginUrl=pre+'/api/user/login';
//获取应用列表-post
var appUrl=pre+'/api/app/getAppList';
var userUrl=pre+'/api/user/getUserList';
//获取用户列表
var userListUrl=pre+'/api/user/getUserList2';
var userHtml='user.html';
//获取用户详情
var userDetailUrl=pre+'/api/user/getUser';

var roleListUrl=pre+'/api/user/getRoleList';
//保存用户
var saveUserUrl=pre+'/api/user/saveUser';
//重置密码
var resetPwdUrl=pre+'/api/user/resetPwd';
//修改密码
var changePwdUrl=pre+'/api/user/changePwd';
//修改应用
var updateAppUrl=pre+'/api/app/updateApp';

var openAddUser='addUser.html';
//添加用户
var addUserUrl=pre+'/api/user/addUser';
//邮箱列表
var emailsUrl=pre+'/api/sys/getEmails';
//添加邮箱
var addEmailUrl=pre+'/api/sys/addEmail';
//修改邮箱
var updateEmailUrl=pre+'/api/sys/updateEmail';
var sendEmailCodeUrl=pre+'/api/sys/sendCode';

var paramErr='必填项有空值';