package com.example.test.controller;


import com.example.test.model.Subscriber;
import com.example.test.data.SubscriberRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private SubscriberRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/ping")
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/add",
            produces = { "application/json; charset=utf-8" })
    @ResponseBody
    public JSONObject add(@RequestBody JSONObject jsonObject) throws JSONException {
        logger.info("method=add, json="+jsonObject.toString());
        if (!jsonObject.getString("status").equals("add")){
            jsonObject.put("result",false);
            jsonObject.getJSONObject("addition").put("result",false);
            return jsonObject;
        }
        Subscriber subscriber = repository.findById(UUID.fromString(jsonObject.getJSONObject("addition").getString("uuid"))).get();
        if (subscriber == null || !subscriber.isStatus()){
            jsonObject.put("result",false);
            jsonObject.getJSONObject("addition").put("result",false);
            return jsonObject;
        }

        int add = jsonObject.getJSONObject("addition").getInt("add");
        boolean isPossible = true;
        subscriber.setBalance(subscriber.getBalance()+add);

        repository.save(subscriber);

        jsonObject.put("result",isPossible);
        jsonObject.getJSONObject("addition").put("result",isPossible);

        jsonObject.getJSONObject("addition").put("fullName", subscriber.getFullName());
        jsonObject.getJSONObject("addition").put("sum", subscriber.getBalance()-subscriber.getHold());
        jsonObject.getJSONObject("addition").put("isStatus", subscriber.isStatus());

        return jsonObject;
    }

    @RequestMapping(
            value = "/substract",
            produces = { "application/json; charset=utf-8"})
    @ResponseBody
    public JSONObject substract(@RequestBody JSONObject jsonObject) throws JSONException {
        logger.info("method=substract, json="+jsonObject.toString());
        if (!jsonObject.getString("status").equals("substract")){
            jsonObject.put("result",false);
            jsonObject.getJSONObject("addition").put("result",false);
            return jsonObject;
        }
        Subscriber subscriber = repository.findById(UUID.fromString(jsonObject.getJSONObject("addition").getString("uuid"))).get();
        if (subscriber == null || !subscriber.isStatus()){
            jsonObject.put("result",false);
            jsonObject.getJSONObject("addition").put("result",false);
            return jsonObject;
        }

        int substraction = jsonObject.getJSONObject("addition").getInt("substraction");
        boolean isPossible;

        int result = subscriber.getBalance() - subscriber.getHold() - substraction;

        if (result<0){
            isPossible=false;
        }else{
            isPossible=true;
            subscriber.setHold(subscriber.getHold()+substraction);

            repository.save(subscriber);
        }

        jsonObject.put("result",isPossible);
        jsonObject.getJSONObject("addition").put("result",isPossible);

        jsonObject.getJSONObject("addition").put("fullName", subscriber.getFullName());
        jsonObject.getJSONObject("addition").put("sum", subscriber.getBalance()-subscriber.getHold());
        jsonObject.getJSONObject("addition").put("isStatus", subscriber.isStatus());

        return jsonObject;
    }

    @RequestMapping(
            value = "/status",
            produces = { "application/json; charset=utf-8"})
    @ResponseBody
    public JSONObject status(@RequestBody JSONObject jsonObject) throws JSONException {
        logger.info("method=status, json="+jsonObject.toString());
        if (!jsonObject.getString("status").equals("status")){
            jsonObject.put("result",false);
            jsonObject.getJSONObject("addition").put("result",false);
            return jsonObject;
        }
        Subscriber subscriber = repository.findById(UUID.fromString(jsonObject.getJSONObject("addition").getString("uuid"))).get();
        if (subscriber == null){
            jsonObject.put("result",false);
            jsonObject.getJSONObject("addition").put("result",false);
            return jsonObject;
        }

        jsonObject.put("result",true);
        jsonObject.getJSONObject("addition").put("result",true);
        jsonObject.getJSONObject("addition").put("fullName", subscriber.getFullName());
        jsonObject.getJSONObject("addition").put("sum", subscriber.getBalance()-subscriber.getHold());
        jsonObject.getJSONObject("addition").put("isStatus", subscriber.isStatus());

        return jsonObject;
    }
}
