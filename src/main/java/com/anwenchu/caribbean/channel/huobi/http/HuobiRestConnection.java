package com.anwenchu.caribbean.channel.huobi.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anwenchu.caribbean.channel.huobi.config.HuoBiArbitrageConfig;
import com.anwenchu.caribbean.channel.huobi.enums.Result;
import com.anwenchu.caribbean.channel.huobi.exception.SDKException;
import com.anwenchu.caribbean.channel.huobi.signature.ApiSignature;
import com.anwenchu.caribbean.channel.huobi.signature.UrlParamsBuilder;
import com.anwenchu.caribbean.utils.ConnectionFactory;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class HuobiRestConnection {

  @Autowired
  private HuoBiArbitrageConfig options;


  public JSONObject executeGet(String path, UrlParamsBuilder paramsBuilder){

    String url = options.getRestHost() + path + paramsBuilder.buildUrl();

    Request executeRequest = new Request.Builder()
        .url(url)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }
  public JSONObject executeGet(String url){
    Request executeRequest = new Request.Builder()
        .url(url)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }

  public String executeGetString(String url, UrlParamsBuilder paramsBuilder){
    String realUrl = url + paramsBuilder.buildUrl();
    Request executeRequest = new Request.Builder()
        .url(realUrl)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();
    String resp = ConnectionFactory.execute(executeRequest);
    return resp;
  }

  public JSONObject executeGetWithSignature(String path, UrlParamsBuilder paramsBuilder) throws MalformedURLException {

    String requestUrl =  options.getRestHost() + path;
    new ApiSignature().createSignature(options.getApiKey(), options.getSecretKey(), "GET", new URL(this.options.getRestHost()).getHost()
            , path, paramsBuilder);
    requestUrl += paramsBuilder.buildUrl();

    Request executeRequest = new Request.Builder().url(requestUrl)
        .addHeader("Content-Type", "application/x-www-form-urlencoded").build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }


  public JSONObject executePostWithSignature(String path, UrlParamsBuilder paramsBuilder) throws MalformedURLException {

    String requestUrl =  options.getRestHost() + path;
    new ApiSignature().createSignature(options.getApiKey(), options.getSecretKey(), "POST", new URL(this.options.getRestHost()).getHost(),
            path, paramsBuilder);
    requestUrl += paramsBuilder.buildUrl();
    Request executeRequest = new Request.Builder().url(requestUrl).addHeader("accept-language", "zh-CN").post(paramsBuilder.buildPostBody())
        .addHeader("Content-Type", "application/json").build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }


  private JSONObject checkAndGetResponse(String resp) {
    JSONObject json = JSON.parseObject(resp);
    try {
      if (json.containsKey("status")) {
        String status = json.getString("status");
        if ("error".equals(status)) {
          String err_code = json.getString("err-code");
          String err_msg = json.getString("err-msg");
          throw new SDKException(SDKException.EXEC_ERROR, "[Executing] " + err_code + ": " + err_msg);
        } else if (!"ok".equals(status)) {
          throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Response is not expected: " + status);
        }
      } else if (json.containsKey("success")) {
        boolean success = json.getBoolean("success");
        if (!success) {
          String err_code = Result.checkResult(json.getInteger("code"));
          String err_msg = json.getString("message");
          if ("".equals(err_code)) {
            throw new SDKException(SDKException.EXEC_ERROR, "[Executing] " + err_msg);
          } else {
            throw new SDKException(SDKException.EXEC_ERROR, "[Executing] " + err_code + ": " + err_msg);
          }
        }
      } else if (json.containsKey("code")) {
        int code = json.getInteger("code");
        if (code != 200) {
          String message = json.getString("message");
          throw new SDKException(SDKException.EXEC_ERROR,"[Executing]" + message);
        }
      } else {
        throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Status cannot be found in response.");
      }
    } catch (SDKException e) {
      throw e;
    } catch (Exception e) {
      throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
    }

    return json;
  }

}
