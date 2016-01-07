**各种工具类**
---------

###1. **意图工具类<a href="https://github.com/iloveaman/Utils/blob/master/src/utils/utilsIntentHelper.java">IntentHelper.java</a>**
 1. 打开相应意图查看文件
	```
		IntentHelper.openFile(Activity mContext, File file);
	```

 2. 检查某个Intent是否可用(queryIntentActivities)

	```
		IntentHelper.isIntentAvailable(this, Intent.ACTION_VIEW);
	```
 3. 检查某个Intent是否可用(queryIntentActivities)
	
	```	
		IntentHelper.isIntentAvailable(this, Intent.ACTION_VIEW, "video/*")
	```


###2. **软键盘工具类<a href="https://github.com/iloveaman/Utils/blob/master/src/utils/KeyBoardUtils.java">KeyBoardUtils.java</a>**
 	打开关闭软键盘

###3. **计算文件大小工具类<a href="https://github.com/iloveaman/Utils/blob/master/src/utils/FileSizeUtil.java">FileSizeUtil.java</a>**
 

###4. **解绑View工具类<a href="https://github.com/iloveaman/Utils/blob/master/src/utils/ViewUtils.java">ViewUtils.java</a>**
	解绑view,释放内存<br>

###5. **<a href="https://github.com/iloveaman/Utils/tree/master/src/utils/Http">http和webview</a>**<br>
 
