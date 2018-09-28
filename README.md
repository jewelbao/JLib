# **JLib For Android**

[ ![Download](https://api.bintray.com/packages/jewelbao88/ComponentsMaven/JLib/images/download.svg) ](https://bintray.com/jewelbao88/ComponentsMaven/JLib/_latestVersion)

## 介绍
 android、java工具类集合


----------




## 在 build.gradle 中添加依赖
```
implementation 'com.jewel.lib:JLib:1.0.3'
```
 或者
```
implementation('com.jewel.lib:JLib:1.0.3') {
        exclude group: "com.android.support"
}
```

----------


## Android ##

 - [CompatUtil][1]--版本兼容类,处理不同版本差异方法
 - [PermissionUtil][2]--权限工具，6.0后的权限请求封装
 - [SnackbarUtils][3]--Snackbar工具，封装几种常用提示Snackbar
 - [TextViewUtils][4]--TextView相关操作封装
 - [RecyclerViewUtil][5]--RecyclerView线性和网格列表的封装
 - 陆续收录中...

----------


## Java ##

 - [DateUtils][6]--时间转换工具类,String-Long-Date互转
 - [StringFormat][7]--字符串格式化工具类，支持字符串资源文件
 - 陆续收录中...

----------


## License

```
Copyright 2018 jewelbao

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


  [1]: https://github.com/jewelbao/JLib/blob/master/JLib/src/main/java/com/jewel/lib/android/CompatUtil.java
  [2]: https://github.com/jewelbao/JLib/blob/master/JLib/src/main/java/com/jewel/lib/android/PermissionUtil.java
  [3]: https://github.com/jewelbao/JLib/tree/master/JLib/src/main/java/com/jewel/lib/android/SnackbarUtils.java
  [4]: https://github.com/jewelbao/JLib/tree/master/JLib/src/main/java/com/jewel/lib/android/TextViewUtils.java
  [5]: https://github.com/jewelbao/JLib/tree/master/JLib/src/main/java/com/jewel/lib/android/recyclerView/RecyclerViewUtil.java
  [6]: https://github.com/jewelbao/JLib/tree/master/JLib/src/main/java/com/jewel/lib/java/DateUtilser/JLib/src/main/java/com/jewel/lib/android/recyclerView/RecyclerViewUtil.java
  [7]: https://github.com/jewelbao/JLib/tree/master/JLib/src/main/java/com/jewel/lib/java/StringFormat.java