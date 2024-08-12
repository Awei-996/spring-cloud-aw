# OAuth2.1 官方默认配置示例
- 基于OAuth2.1的0.4.5(jdk1.8、springboot2.7.18)版本示例来配置
- 对官方默认示例进行修改，将原来的jdbc存储修改为内存方式
- 用户名：user1 密码：password
- 客户ID：messaging-client
- 客户凭证：secret
- 客户认证方式：client_secret_basic
- 客户授权范围： openid、profile、message.red、message.write
- 回调地址：http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc , http://127.0.0.1:8080/authorized , https://www.baidu.com/sugrec
- 授权请求URL：
  - http://127.0.0.1:9000/oauth2/authorize?client_id=messaging-client&redirect_uri=http://127.0.0.1:8080/authorized&response_type=code&scope=openid%20profile%20message.read%20message.write&state=beff3dfc-bad8-40db-b25f-e5459e3d6ad7
  - ![img.png](img.png)
  - 选择授权对象进行提交，提交后会跳转回调地址
  - http://127.0.0.1:8080/authorized?code=7zsmrHiuaiWnkx12fu5MwO7p82AFPDxW33nkh4ZIZCBiFuY-E2M38-KEQ06q6weCexmFdjJgzeXFoH1G6QXyhFIYhngytNzAzuuTD93Kb46AXvj3joYo-vmEDLzLbTn4&state=beff3dfc-bad8-40db-b25f-e5459e3d6ad7
  -  我们可以得到授权的code state，我们这个code去获取token
  - 两种方式：
    - linux：curl --request POST --url http://127.0.0.1:9000/oauth2/token --header 'Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=' --header 'content-type: multipart/form-data' --form code=你的授权码 --form redirect_uri=http://127.0.0.1:8080/authorized --form grant_type=authorization_code
    -  cmd：curl --request POST --url http://127.0.0.1:9000/oauth2/token --header "Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=" --header "content-type: multipart/form-data" --form "code=PUGF0IVTQGbdrXc4QY39zb6VhThjbPCYEInn7II_W4aJvFG4Fc3tvimIGYQF0nGznt9oeirVY2s9BAXDxHk197dGWCW3FxyVPpFOVAb5kMaFbBWiaCVuCKgHJ0IAif1F" --form "redirect_uri=http://127.0.0.1:8080/authorized" --form "grant_type=authorization_code"
  - 得到的token
    - {
      "access_token": token,
      "refresh_token": 刷新token,
      "scope": "openid message.read",
      "id_token": idToken,
      "token_type": "Bearer",
      "expires_in": 有效时间(默认五分钟)
      }
  - 此时我们用这个token再去访问 http://127.0.0.1:9000 就可以访问了
    - 格式 Authorization: Bearer token
# 修改JWT生成方式 6-29
- 使用自定义的RAS密钥来进行签名验证JWT 
- 修改默认登录页面加载不出来
  - 只需要在authorizationServerSecurityFilterChain 添加异常处理是重定向登录页面就行，在defaultSecurityFilterChain 不需要重复添加异常处理