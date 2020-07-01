# MvvmArchitecture



## 中文介绍

##### 项目为以MVVM架构为基础搭建的Android开发框架，可基于此框架快速进行开发。项目中集成了Retrofit2、Rxjava3/AutoDispose2(解决RxJava内存泄漏问题)、Okhttp3、OkhttpUtils、Glide、Gson、EventBus、Lifycycle、DataBinding等框架，并针对网络请求、图片加载、Handler等做了进一步的封装。另外项目中添加了常用的工具类，可直接使用。

#### 项目说明

- 项目基于AndroidStudio进行研发，使用到的gradle编译工具版本为"3.6.3"，gradle的版本为"5.6.4"，编译版本(compileSdkVersion)为29，构建工具版本(buildToolsVersion)为"29.0.3"，最小版本(minSdkVersion)为19，目标版本(targetSdkVersion)为29。使用了AndroidX的依赖库。
- 项目整体采用MVVM架构方式。
- 项目网络框架封装了两个，其一是用Retrofit+Rxjava，可参考RetrofitClient类。其二是用OkHttpUtils，可参考NetUtil类。两者皆有各自的优缺点，可根据项目实际情况与开发者情况选择使用。
- 项目图片框架使用Glide，并且封装了GlideUtil与GlideCacheUtil两个工具类，基本覆盖绝大多数图片加载场景。
- 项目使用Gson框架来进行Json数据的操作与处理。
- 项目使用EventBus框架来进行事件发布和订阅。
- 项目使用Lifecycle框架监听Activity与Fragment的生命周期变化。
- 项目使用ImmersionBar框架处理沉浸式状态栏。
- 项目使用LeakCanary框架以检测内存泄漏。
- 项目使用自封装的CrashHandler用以捕捉全局的崩溃日志并输出到文件。
- 权限相关 - PermissionManeger。
- 加解密相关 - AESUtil。
- 日期工具相关 - DateUtil。
- 文件工具相关- FileUtil。
- 日志工具相关 - LogUtil/LogToFileUtil。
- 网络工具相关 - NetworkUtil。
- SharedPreference工具相关 - PreUtil。
- NTP时间对齐工具相关 - SntpClient。
- 自定义Handler类 - LifecycleHandler。
- 登录接口使用的www.wanandroid.com的，有需要的可以自行去注册。

#### MvpArchitecture项目

Github地址：https://github.com/mythmayor/MvpArchitecture

#### 个人博客

欢迎访问我的博客：https://blog.csdn.net/mythmayor

#### 个人邮箱

有问题请发送至：mythmayor@163.com

#### 致谢

感谢项目中用到的开源框架或资源的提供者，感谢每一位为开源项目作出贡献的人，正是由于大家的努力才形成了可持续发展的开源社区。



## Introduction in English

##### The project is an Android development framework built on the basis of the MVVM architecture, which can be quickly developed based on this framework. The project integrates Retrofit2, Rxjava3/AutoDispose2 (to solve RxJava memory leak problem), Okhttp3, OkhttpUtils, Glide, Gson, EventBus, Lifycycle, DataBinding and other frameworks, and further encapsulates network requests, image loading, Handler and so on. In addition, common tools are added to the project, which can be used directly.

#### Project Instruction

- The project is developed based on AndroidStudio. The gradle compilation tool version used is "3.6.3", the gradle version is "5.6.4", the compiled version (compileSdkVersion) is 29, and the build tool version (buildToolsVersion) is "29.0.3" , The minimum version (minSdkVersion) is 19, and the target version (targetSdkVersion) is 29. The dependency library of AndroidX is used.
- The whole project adopts the MVVM architecture method.
- The project network framework encapsulates two, one is to use Retrofit+Rxjava, refer to RetrofitClient class. The second is to use OkHttpUtils, refer to NetUtil class. Both have their own advantages and disadvantages, and can be used according to the actual situation of the project and the situation of the developer.
- The project picture framework uses Glide, and encapsulates the two tool classes GlideUtil and GlideCacheUtil, which basically covers most image loading scenarios.
- The project uses the Gson framework to operate and process Json data.
- The project uses the EventBus framework for event publishing and subscription.
- The project uses the Lifecycle framework to monitor the life cycle changes of Activity and Fragment.
- The project uses the ImmersionBar framework to handle the immersive status bar.
- The project uses the LeakCanary framework to detect memory leaks.
- The project uses a self-packaged CrashHandler to capture the global crash log and output it to a file.
- Permission related - PermissionManeger.
- Related to encryption and decryption - AESUtil.
- Date tool related - DateUtil.
- File tool related - FileUtil.
- Log tool related - LogUtil/LogToFileUtil.
- Network tools related - NetworkUtil.
- SharedPreference tool related - PreUtil.
- NTP time alignment tool related - SntpClient.
- Custom Handler class - LifecycleHandler.
- The login interface uses www.wanandroid.com, if necessary, you can register yourself.

#### MvpArchitecture Project

Github address: https://github.com/mythmayor/MvpArchitecture

#### Personal Blog

Welcome to my blog: https://blog.csdn.net/mythmayor

#### Personal Mailbox

Please send questions to: mythmayor@163.com

#### Thanks

Thanks to the providers of open source frameworks or resources used in the project, and everyone who contributed to the open source project. It is precisely because of everyone's efforts that a sustainable open source community has been formed.



#### License

```
Copyright 2020 The MvvmArchitecture Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

