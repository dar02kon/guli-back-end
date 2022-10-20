# 谷粒学苑总结

## 项目介绍

谷粒学院，是一个`B2C`模式的职业技能在线教育系统，分为前台用户系统和后台运营平台。

注：`B2C`是指电子商务的一种模式，也是直接面向消费者销售产品和服务商业的零售模式。

`B2C`电子商务网站由三个基本部分组成：

* 为顾客提供在线购物场所的商场网站；

* 负责为客户所购商品进行配送的配送系统；

* 负责顾客身份的确认及货款结算的银行及认证系统。

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/2%20%E5%95%86%E4%B8%9A%E6%A8%A1%E5%BC%8F.png)

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/3%20%E9%A1%B9%E7%9B%AE%E5%8A%9F%E8%83%BD%E6%A8%A1%E5%9D%97.png)

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/4%20%E9%A1%B9%E7%9B%AE%E6%8A%80%E6%9C%AF%E7%82%B9.png)

## 完成情况

这个项目我是从寒假开始写，在第七天（关于课程发布）之前我都是根据视频一步一步敲过来的，基本上能跟上（每天完成相应的任务），但是跟着视频敲代码效率确实太低了。中间搁置了很长一段时间（大概有5个月了），在这段时间中因为一些原因我需要写一个前后端的项目，当时前端部分的代码都是参考`vue-admin-template-master`然后不断靠搜索引擎去完成的，虽然很痛苦，但是写完后确实很有成就感，甚至有一种渐渐了解前端的感觉，当然会写和真正了解是两码事，现在让我去说一下前端用到一些技术的语法可能我都说不出来，，，虽然当时是我第一次自己动手去使用`Vue`写前端，但我感觉效率还是很可以的，我遇见的一些问题基本可以通过搜索引擎与调试（之前因为写爬虫经常需要调试前端代码）来解决，虽然比“抄作业”要慢，但成就感是真的。

后来到了九月份，我又开始继续去写这个项目（别问，问就是就业所迫），这一次在熟悉前面的内容之后就采取 看文档 + 看笔记 + 看视频的形式去写代码。看文档比看视频要快很多，一天甚至能完成两天的任务，但也给我带来了很多麻烦，待会在下面去详细介绍，最后也是东拼西凑才完成了这个项目（基本所有功能都实现了，能跑但不一定跑的好看，有部分功能未详细测试）。

总得来说这个项目还是很有意义的，确实让我在学习一些技术的同时得到了很多锻炼（各种意义上来说）。

## 问题与解决

### `Nacos`启动报错

**环境：**

本地使用的是在`Windows10`上安装的`Nacos`

通过双击`bin`目录下的`startup.cmd`来启动

**报错信息：**

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/Snipaste_2022-10-19_21-18-16.png)

**原因：**

`Nacos`默认要求是使用集群的方式启动（`nacos-server`使用的是`cluster`集群模式），集群要求至少有三台`Nacos`服务器。

**解决方案：**

启动命令改为单机模式启动 

```shell
startup.cmd -m standalone
```

###  用户信息无法写入cookie

**环境：**

```json
"axios": "^0.19.2",
"element-ui": "^2.13.2",
"js-cookie": "^2.2.1",
"nuxt": "^2.14.1",
"vue-awesome-swiper": "^3.1.3",
"vue-qriously": "^1.1.1"
```

**报错信息：**

请求头cookie中的`guli_ucenter`为`undefined`或者类似`object`这种

**原因：**

如果后端接口没有问题，

* 为`undefined`，可能是数据获取后解析不对如把`response.data.data.userInfo;`写成`response.data.userInfo;`,`response.data.data`中第一个`.data`是`Vue.js`使用`axios`动态获取`response`里的`data`数据操作，第二个`.data`是获取我们提供的参数`data`所对应的数据。
* 为类似于`[object object]`，这个与我们在Java中直接输出某个对象（没有重写`toString()`方法）一样，没有获取到对象的具体值`object`的`prototype`链中没有实现自己的`toString()`，则`object`转换为`String`时就会调用`Object.prototype.toString`,输出的格式是[object 对象的类型]

**解决方案：**

正确读取数据后，使用`JSON.stringify()`将对象转成字符串，需要用户信息时，再使用`JSON.parse()`将字符串转成对象

```js
//登录成功根据token获取用户信息
loginApi.getLoginInfo().then((response) => {
      this.loginInfo = response.data.data.userInfo;
      //将用户信息记录cookie
      cookie.set('guli_ucenter', JSON.stringify(this.loginInfo), { domain: "localhost" });
      //跳转页面
      window.location.href = "/";
 });
```

```js
var jsonStr = cookie.get("guli_ucenter");
if (jsonStr) {
    this.loginInfo = JSON.parse(jsonStr);
}
```

### 前端依赖版本问题

一般引入依赖后突然报一些和源码相关的错误并且很多，那基本和依赖版本不匹配有关

我最有印象是前台页面轮播图`vue-awesome-swiper`与`Nuxt`框架，我之前都是直接`npm install vue-awesome-swiper`，没有指定版本号，

最后导致轮播图无法显示，更有意思的是前台页面代码只能启动一次，第二次就报各种各样的错，，，

**解决办法：**

在`package.json`写好依赖版本，顺便增加`nuxt`端口号的配置

```json
  "dependencies": {
    "axios": "^0.19.2",
    "element-ui": "^2.13.2",
    "js-cookie": "^2.2.1",
    "nuxt": "^2.14.1",
    "vue-awesome-swiper": "^3.1.3",
    "vue-qriously": "^1.1.1"
  },
  "devDependencies": {
    "babel-eslint": "^10.0.1",
    "eslint": "^4.19.1",
    "eslint-friendly-formatter": "^4.0.1",
    "eslint-loader": "^2.1.1",
    "eslint-plugin-vue": "^4.0.0"
  },
  "config": {
     "nuxt": {
     "host": "0.0.0.0",
     "port": 3000
     }
  }
```

删除之前的依赖，然后重新下载

```shell
npm cache clean -force # npm清理缓存
npm install # 下载依赖 
npm run dev # 启动
```

### 使用Spring Security后无法登录

登录后端就报错`Access is denied`

原因可就太杂了，配置和代码可能都有一些问题

**解决方案：**

使用网关`Spring Cloud Gateway`替代`Nginx`后，前端需要修改端口号

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/Snipaste_2022-10-20_15-50-40.png)

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/Snipaste_2022-10-20_15-51-12.png)

如果在网关中配置了跨域，则`controller`层不能再添加注解`@CrossOrigin`

注：后台登录接口是使用`Spring Security`来实现的，`controller`层是找不到这个接口的

![](http://rj5qpfcaf.hn-bkt.clouddn.com/guli/Snipaste_2022-10-20_15-56-29.png)

所以我们还需要正确配置请求拦截

```java
   /**
     * 配置哪些请求不拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
```



**其它一些杂七杂八的BUG我就不记录了（忘了已经），应该都能调试出来，短信可以使用阿里云提供的测试接口意思一下，现在申请好像挺麻烦的**

## 最后的感受

说实话写完之后还是晕晕的，像`Spring Security`以及`Spring Cloud Gateway`之前都没有去深入了解，很多代码也都是CV过来的，大部分都是直接接受了老师的思路，缺乏思考，真正得到锻炼可能还是通过改BUG，，，

但不过怎么说完成这样一个比较大的项目还是很有成就感的，希望接下来的时间能够去详细的了解所用到的技术，并且自己能够从0开始写一个类似的项目。
