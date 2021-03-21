package com.web1.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.web1.service.ServiceImplement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;



@RestController
public class Controller {
    @Autowired
    private ServiceImplement serviceImplement;

    //用户注册：用户id（12位学号），姓名，昵称，邮箱，性别，生日，密码
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestParam Map<String, String> person){ //id,name,nickname,email,gender,birthday,password
        Map<String,Object> map = new HashMap<>();
        int e = serviceImplement.saveUser(person);
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
            case 2:
                map.put("status", "403");
                map.put("msg", "已被注册");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

    //获得用户列表
    @RequestMapping(value = "/user/all", method = RequestMethod.POST)
    public Object getAllUser(){
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findAllUser());
        return JSON.toJSONString(map);
    }

    //获得单个用户：用户id
    @RequestMapping(value = "/user/find",method = RequestMethod.POST)
    public Object getUser(@RequestParam Map<String, String> person){ //id
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findUserById(person.get("id")));
        return JSON.toJSONString(map);
    }

    //获得用户的留言列表：用户id
    @RequestMapping(value = "/user/getMessages", method = RequestMethod.POST)
    public Object getMessagesByUser(@RequestParam Map<String,String> person){//id
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findMessagesByUserId(person.get("id")));
        return JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
    }

    //更新用户信息：用户id，姓名，昵称，邮箱，性别，生日
    @RequestMapping(value = "/user/update/info", method = RequestMethod.POST)
    public Object updateUserInfo(@RequestParam Map<String, String> person){ //id,name,nickname,email,gender,birthday
        int e = serviceImplement.updateUserInfo(person);
        Map<String,Object> map = new HashMap<>();
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

    //更新用户密码：用户id，旧密码，新密码
    @RequestMapping(value = "/user/update/password", method = RequestMethod.POST)
    public Object updateUserPassword(@RequestParam Map<String, String> person){ //id,oldpassword,newpassword
        Map<String,Object> map = new HashMap<>();
        int e = serviceImplement.updateUserPassword(person);
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
            case 2:
                map.put("status", "403");
                map.put("msg", "旧密码错误");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }


    //获取课件列表
    @RequestMapping(value = "/getCourseWare", method = RequestMethod.POST)
    public Object getCourseWare(){
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findAllCourseWare());
        return JSON.toJSONString(map);
    }

    //获取视频列表
    @RequestMapping(value = "/getVideo", method = RequestMethod.POST)
    public Object getVideo(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findAllVideo());
        return JSON.toJSONString(map);
    }


    //新增留言：用户id，发表时间，标题，留言
    @RequestMapping(value = "/message/add", method = RequestMethod.POST)
    public Object addMessage(@RequestParam Map<String, String> message){//id, send_time, title, text
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> msg = new HashMap<>();
        msg.put("user", serviceImplement.findUserByIdhasP(message.get("id")).get());
        msg.put("send_time", message.get("send_time"));
        msg.put("title", message.get("title"));
        msg.put("text", message.get("text"));
        int e = serviceImplement.saveMessage(msg);
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

//    @RequestMapping(value = "/message/update/replycount", method = RequestMethod.POST)
//    public Object updateMsgReplyCount(@RequestParam Map<String, String> message){//id
//        Map<String,Object> map = new HashMap<>();
//        int e = serviceImplement.updateMessageReplyCount(Integer.parseInt(message.get("id")));
//        switch (e){
//            case 0:
//                map.put("status", "500");
//                map.put("msg", "内部错误");
//                map.put("data",e);
//                break;
//            case 1:
//                map.put("status", "200");
//                map.put("msg", "成功");
//                map.put("data",e);
//                break;
//        }
//        return JSON.toJSONString(map);
//    }

    //更新留言点击量：留言
    @RequestMapping(value = "/message/update/clickcount", method = RequestMethod.POST)
    public Object updateMsgClickCount(@RequestParam Map<String, String> message){//id
        Map<String,Object> map = new HashMap<>();
        int e = serviceImplement.updateMessageClickCount(Integer.parseInt(message.get("id")));
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

    //更新留言最新回复时间：留言id，最新时间
    @RequestMapping(value = "/message/update/rtime", method = RequestMethod.POST)
    public Object updateMsgRtime(@RequestParam Map<String, String> message){//id,rtime
        Map<String,Object> map = new HashMap<>();
        int e = serviceImplement.updateMessageLastRtimeandRplCount(
                Integer.parseInt(message.get("id")),message.get("rtime"));
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

    //删除留言：留言id
    @RequestMapping(value = "/message/delete", method = RequestMethod.POST)
    public Object deleteMsg(@RequestParam Map<String, String> message){//id
        Map<String,Object> map = new HashMap<>();
        int e = serviceImplement.deleteMessage(Integer.parseInt(message.get("id")));
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

    //获得单条留言：留言id
    @RequestMapping(value = "/message/find", method = RequestMethod.POST)
    public Object getMsg(@RequestParam Map<String, String> message){//id
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findMessageById(Integer.parseInt(message.get("id"))).get());
        return JSON.toJSONString(map);
    }

    //获取留言列表
    @RequestMapping(value = "/message/find/all", method = RequestMethod.POST)
    public Object getAllMsg(){
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findAllMessage());
        return JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
    }

    //获得留言下的评论：留言id
    @RequestMapping(value = "/message/find/rootReply", method = RequestMethod.POST)
    public Object getRootReplies(@RequestParam Map<String, String> msg){//id
        Map<String,Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "成功");
        map.put("data",serviceImplement.findAllRootReplyByMid(Integer.parseInt(msg.get("id"))));
        return JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
    }


    //新增留言评论: 留言id, 用户id, 时间, 回复
    @RequestMapping(value = "/rootReply/add", method = RequestMethod.POST)
    public Object addRootReply(@RequestParam Map<String, String> rootReply){//mid, uid, time, text
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> rr = new HashMap<>();
        rr.put("user", serviceImplement.findUserByIdhasP(rootReply.get("uid")).get());
        rr.put("mid", Integer.parseInt(rootReply.get("mid")));
        rr.put("time", rootReply.get("time"));
        rr.put("text", rootReply.get("text"));
        int e = serviceImplement.saveRootReply(rr);
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

    //删除评论：评论id
    @RequestMapping(value = "/rootReply/delete", method = RequestMethod.POST)
    public Object deleteRootReply(@RequestParam Map<String, String> rootReply){//id
        Map<String,Object> map = new HashMap<>();
        int e = serviceImplement.deleteRootReply(Integer.parseInt(rootReply.get("id")));
        switch (e){
            case 0:
                map.put("status", "500");
                map.put("msg", "内部错误");
                map.put("data",e);
                break;
            case 1:
                map.put("status", "200");
                map.put("msg", "成功");
                map.put("data",e);
                break;
        }
        return JSON.toJSONString(map);
    }

//    @RequestMapping(value = "/downloadCourseWare", method = RequestMethod.GET)
//    public void downloadCourseWare(HttpServletRequest request, HttpServletResponse response){
//        String fileName = request.getParameter("fileName");
//        System.out.println(fileName);
//        String downloadFilePath = "http://39.105.43.226/web1/courseware/"+fileName;//被下载的文件在服务器中的路径
////        String downloadFilePath = request.getContextPath();
//        System.out.println(downloadFilePath);
//        //下载的文件携带这个名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        //文件下载类型：二进制文件
//        response.setContentType("application/octet-stream");
//        try{
//            InputStream inputStream = new BufferedInputStream(getStreamDownloadOutFile(downloadFilePath));
//            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            int len;
//            while((len=inputStream.read())!=-1){
//                outputStream.write(len);
//                outputStream.flush();
//            }
//            outputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    @RequestMapping(value = "/downloadVideo", method = RequestMethod.GET)
//    public void downloadVideo(HttpServletRequest httpRequest, HttpServletResponse response){
//        String fileName = httpRequest.getParameter("fileName");
//        System.out.println(fileName);
////        String downloadFilePath = "/video/"+fileName;//被下载的文件在服务器中的路径
//        String downloadFilePath = System.getProperty("user.dir")+"/src/main/resources/video/"+fileName;
//        System.out.println(downloadFilePath);
//        //下载的文件携带这个名称
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        //文件下载类型：二进制文件
//        response.setContentType("application/octet-stream");
//        try{
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(fileName)));
//            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            int len;
//            while((len=inputStream.read())!=-1){
//                outputStream.write(len);
//                outputStream.flush();
//            }
//            outputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    public static InputStream getStreamDownloadOutFile(String apiUrl) throws Exception {
//        InputStream is = null;
//        CloseableHttpClient httpClient = HttpClients.createDefault();//创建默认http客户端
//        RequestConfig requestConfig= RequestConfig.DEFAULT;//采用默认请求配置
//        HttpGet request = new HttpGet(apiUrl);//通过get方法下载文件流
//        request.setConfig(requestConfig);//设置请头求配置
//        try {
//            CloseableHttpResponse httpResponse = httpClient.execute(request);//执行请求，接收返回信息
//            int statusCode = httpResponse.getStatusLine().getStatusCode();//获取执行状态
//            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
//                request.abort();
//            } else {
//                HttpEntity entity = httpResponse.getEntity();
//                if (null != entity) {
//                    is = entity.getContent();//获取返回内容
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.abort();
//        }
//        return is;
//    }
}

