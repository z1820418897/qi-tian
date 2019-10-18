package com.zhc.controller;

import com.zhc.annotation.LogApi;
import com.zhc.model.User;
import com.zhc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.List;

import static org.apache.commons.codec.binary.Hex.encodeHex;

@Slf4j
@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @LogApi("test")
    @RequestMapping(value="/test")
    @ResponseBody
    public String test(HttpServletRequest request,String text,long robotId) throws UnsupportedEncodingException {
//        List<User> friends=userService.findFriendsByUserId(1);
//        log.info(friends.toString());

        String timestamp = request.getHeader("timestamp");
        String accessKey ="AvAGmMXAwICsdSySvySRsqpm";
        String accessSecert ="tlTuaRtrsyRYozQccICjxRma";

        String signatureNonce = request.getHeader("signatureNonce");

        String params = "accessKey=" + accessKey + "&signatureNonce=" + signatureNonce +  "&timestamp=" + timestamp;
        log.info(params);

        MessageDigest md = DigestUtils.getSha256Digest();
        md.update(accessSecert.getBytes("UTF-8"));
        byte[] result = md.digest(params.getBytes("UTF-8"));


        String str="";
        for(byte b:result){
            str+=b;
        }
        log.info("result:"+str);


        String signautre = Hex.encodeHexString(result);

        log.info("signautre:"+signautre);

        return str+"-----"+"----------"+signautre;
    }

}
