# XunFeiTest
讯飞语音合成（即将文字转换为声音）
详细过程：
	一：到科大讯飞开发者中心去登录/注册（http://www.xfyun.cn/）
	二：右上角点击用户名，创建新应用，填写即好，然后开通服务，选择语音合成
	三：点击应用，下载Android平台的SDK。
	四：解压后，将libs里面的armeabi文件夹和Msc.jar放入src/main下面新建的一个叫jniLibs的文件夹中(注：这个文件夹必须要在这个位置，名字也要一样)
	五：在AndroidManifest.xml配置文件中加入权限
		<!--网络-->
		<uses-permission android:name="android.permission.INTERNET"/>
		<!--获取手机录音机使用权限，听写、识别、语义理解需要用到此权限 -->
		<uses-permission android:name="android.permission.RECORD_AUDIO"/>
		<!--读取网络信息状态 -->
		<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
		<!--获取当前wifi状态 -->
		<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
		<!--允许程序改变网络连接状态 -->
		<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
		<!--读取手机信息权限 -->
		<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
		<!--读取联系人权限，上传联系人需要用到此权限 -->
		<uses-permission android:name="android.permission.READ_CONTACTS"/>
	六：在proguard-rules.pro混淆文件中加入下面这句：
		-keep class com.iflytek.**{*;}
	七：在build.gradle中的dependencies下面加入lib的路径
		//讯飞语音
		compile files('src/main/jniLibs/Msc.jar')
	八：在application中加入下面这句，如果没有新建application，则可直接新建一个如MyApp extends Application,在onCreate（）方法中加入下面这句
		当然，也要在配置文件的application中将MyApp加入进去。将网页显示的app_id放在string.xml下面.
		SpeechUtility.createUtility(MyApp.this, "appid=" + getString(R.string.app_id));
	九：在Activity中加入
		
		//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener  
		SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(context, null);  
		//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类  
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人  
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速  
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100  
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端  
		//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”  
		//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限  
		//如果不需要保存合成音频，注释该行代码  
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");  
		//3.开始合成  
		mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);    
		//合成监听器  
		private SynthesizerListener mSynListener = new SynthesizerListener(){  
			//会话结束回调接口，没有错误时，error为null  
			public void onCompleted(SpeechError error) {}  
			//缓冲进度回调  
			//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。  
			public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}  
			//开始播放  
			public void onSpeakBegin() {}  
			//暂停播放  
			public void onSpeakPaused() {}  
			//播放进度回调  
			//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.  
			public void onSpeakProgress(int percent, int beginPos, int endPos) {}  
			//恢复播放回调接口  
			public void onSpeakResumed() {}  
		//会话事件回调接口  
			public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}  
			
	这样就能集成讯飞语音了，步骤不要错，特别是jniLibs文件夹那里，

