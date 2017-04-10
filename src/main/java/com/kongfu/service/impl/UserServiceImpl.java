package com.kongfu.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongfu.entity.User;
import com.kongfu.entity.UserRepository;
import com.kongfu.service.UserService;
import com.kongfu.util.My_Exception;
import com.kongfu.util.Tools;
import com.kongfu.vo.Result;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	// 实例化Result结果实体类
	Result result = new Result();

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUname(username);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return userRepository.findByUnameAndPwd(username, password);
	}

	@Override
	public Result validate(String author) {
		// 解析身份信息，获取加密部分
		String base64_msg = author.split(" ")[1];
		// 将加密信息还原成明文
		byte[] output = Base64.decodeBase64(base64_msg);
		String msg = null;
		try {
			msg = new String(output, "UTF-8");
		} catch (Exception e) {
			throw new My_Exception("身份验证错误！");
		}
		// 切割用户名：密码
		String name = msg.split(":")[0];
		String password = msg.split(":")[1];
		Result result = validate(name, password);
		return result;
	}

	public Result validate(String username, String password) {
		User user = userRepository.findByUname(username);
		if (user == null) {
			Tools.deal(result, 1, "用户不存在.");
		} else {
			String md5 = Tools.MD5(password);
			if (user.getPwd().equals(md5)) {
				// 绑定Cooick
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("username", user.getUname());
				data.put("userId", user.getId());
				return Tools.deal(result, data, 0, "用户名密码正确.");
			} else {
				Tools.deal(result, 1, "密码错误.");
			}
		}
		return result;
	}

	@Override
	public Result regist(User user) {
		synchronized (this.getClass()) {
			if (user != null) {
				int count = userRepository.findUserCount(user.getUname());
				if (count != 0) {
					return Tools.deal(result, 1, "用户名已存在.");
				} else {
					user.setLastlogintime(Tools.createTime());
					user.setPwd(Tools.MD5(user.getPwd()));
					userRepository.save(user);
					return Tools.deal(result, 0, "用户申请注册成功.");
				}
			} else {
				return Tools.deal(result, 1, "用户注册信息异常.");
			}
		}
	}

	@Override
	public Result getUserMessageById(String userId) {
		User user = this.userRepository.findById(userId);
		System.out.println(user);
		return Tools.deal(result, user, 0, "success");
	}

	public Result messageMordify(String userid) {
		User user = userRepository.findById(userid);
		if (user == null) {
			Tools.deal(result, 1, "用户不存在");
		} else {
			user.setNickname(user.getNickname());
			user.setPwd(user.getPwd());
			user.setPosition(user.getPosition());
			user.setQq(user.getQq());
			user.setStatus(user.getStatus());
			user.setSex(user.getSex());
			user.setPhone(user.getPhone());
			user.setLastlogintime(Tools.createTime());
//			userRepository.messageAdminModify(user);
		}
		return result;
	}
}
