package com.mmall.service.Impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Lenovo
 * 日期: 2018-07-25
 * 时间: 16:58
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        //检查用户名是否存在
        int resultCount = userMapper.checkUsername(username);
        String msg = null;

        if (resultCount == 0) {
            msg = "用户名不存在";
            return ServerResponse.createByErrorMessage(msg);
        }
        //密码登陆MD5,将输入的密码经过MD5处理再与数据库密码进行比对
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            msg = "密码错误";
            System.out.println(username + " + " + password);
            return ServerResponse.createByErrorMessage(msg);
        }

        user.setPassword(StringUtils.EMPTY);
        msg = "登陆成功";
        return ServerResponse.createBySuccess(msg, user);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public ServerResponse<String> register(User user) {
        //分别验证用户名和email
        ServerResponse vaildResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!vaildResponse.isSuccess()) {
            return vaildResponse;
        }
        vaildResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!vaildResponse.isSuccess()) {
            return vaildResponse;
        }

        //设置用户
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //注册信息放入数据库
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }


    /**
     * 校验用户名和邮箱
     *
     * @param str  用户名和邮箱的值
     * @param type 判断是用户名类型还是邮箱类型
     * @return
     */
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                //type为username
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                //type为email
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    /**
     * 通过密保来找回密码
     *
     * @param username 用户名
     * @return
     */
    @Override
    public ServerResponse selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //validResponse.isSuccess()  == checkVaild.isSuccess() ==> 用户不存在
            //checkVaild.isSuccess() 得到的username行数为0
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("密保为空");
    }

    /**
     * 验证密保是否正确
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return
     */
    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //说明问题及答案是这个用户的,并且正确
            String forgetToken = UUID.randomUUID().toString();
            //将本地缓存放进去
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    /**
     * 忘记密码--重置密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken 令牌(密保回答正确生成的限时令牌)
     * @return
     */
    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误,token需要传参");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //validResponse.isSuccess()  == checkVaild.isSuccess() ==> 用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //获得当前的tokeng
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createBySuccessMessage("token无效或者过期");
        }
        //比较forgetToken和token是否相同
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            //更新账号密码
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("tonken错误,请重新获取重置密码的tonken");
        }
        return ServerResponse.createBySuccessMessage("修改密码失败");
    }

    /**
     * 根据旧密码来修改密码
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user        用户
     * @return
     */
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //防止横向越权,需要校验用户的旧密码,一定要指定是这个用户,因为我们会查询一个count(1),如果不指定Id,那么结果就是true  count>0
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        //updateByPrimaryKeySelective  ---  有选择性的更新数据
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }


    /**
     * 修改个人信息
     *
     * @param user 用户
     * @return
     */
    @Override
    public ServerResponse<User> updateInformation(User user) {
        //username不能被更新
        //email也要进行校验,校验新的email是否存在,并且存在的email如果相同的话,不能是我们当前这个用户的
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setQuestion(user.getQuestion());

        int resoultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (resoultCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户Id
     * @return
     */
    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        //将密码置空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    //backend

    /**
     * 校验是否为管理员
     * @param user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
