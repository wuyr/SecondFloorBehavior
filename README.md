##  只需一个Behavior就能实现“二楼”效果，兼容所有下拉刷新控件。
### 博客详情： 敬请期待。。。


### 使用方式:
#### 添加依赖:
```
implementation 'com.wuyr:secondfloorbehavior:1.0.4'
```

### APIs:
|Method|Description|
|---------|-------------|
|enterSecondFloor()|主动进入二楼|
|leaveSecondFloor()|主动退出二楼|
|getState()|获取当前状态：<br/>**STATE_NORMAL**: 普通状态<br/>**STATE_DRAGGING**: 拖动中<br/>**STATE_PREPARED**: 符合触发进入二楼的条件<br/>**STATE_OPENING**: 正在进入二楼<br/>**STATE_OPENED**: 在二楼<br/>**STATE_CLOSING**: 正在离开二楼|
|setStartInterceptDistance(float distance)|设置开始拦截下拉的滑动距离<br/>即：列表滑动到顶后，往下拉多长距离可以开始触发二楼的下拉？|
|setMinTriggerDistance(float distance)|设置能够进入二楼的滑动距离(从触发上面的二楼下拉后开始计算)<br/>即：拦截下拉后，至少还要继续往下滑动多长距离才能够触发进入二楼？|
|setDampingRatio(float ratio)|设置触发下拉后的滑动距离衰减率<br/>取值范围: **0~1**，0: 无衰减|
|setRollbackDuration(long duration)|设置回退的动画时长 (默认: 200)<br/>回退：即未能触发打开二楼|
|setEnterDuration(long duration)|设置进入二楼的动画时长 (默认: 500)|
|setExitDuration(long duration)|设置退出二楼的动画时长 (默认: 400)|
|setOnBeforeEnterSecondFloorListener(Listener listener)|监听进入二楼之前的事件<br/>在这里可以决定是否同意本次进入二楼，返回：<br/>**true**: 允许进入<br/>**false**: 拒绝进入|
|setOnEnterSecondFloorListener(Listener listener)|监听打开二楼的事件|
|setOnExitSecondFloorListener(Listener listener)|监听退出二楼的事件|
|setOnStateChangeListener(Listener listener)|监听各种状态变化，状态见上：*getState()*|
|setExitAnimationInterpolator(Interpolator interpolator)|设置退出二楼的动画插值器|
|setEnterAnimationInterpolator(Interpolator interpolator)|设置进入二楼的动画插值器|

### Attributes:
|Name|Format|Description|
|----|-----|-----------|
|layout_startInterceptDistance|dimension<br/>默认: HeaderView的高度|开始拦截下拉的滑动距离|
|layout_minTriggerOffset|dimension<br/>默认: HeaderView高度的一半|能够进入二楼的滑动距离|
|layout_dampingRatio|float (默认: 0)|触发下拉后的滑动距离衰减率<br/>取值范围: **0~1**<br/>0: 无衰减<br/>0.5: 衰减一半|
|layout_rollbackDuration|integer (默认: 200)|回退的动画时长|
|layout_enterDuration|integer (默认: 500)|进入二楼的动画时长|
|layout_exitDuration|integer (默认: 400)|退出二楼的动画时长|
|layout_onEnterSecondFloor|string|进入二楼的回调方法<br/>使用方法同**android:onClick**属性|
|layout_onExitSecondFloor|string|退出二楼的回调方法<br/>使用方法同上|

### 布局示例:
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/rootView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!--头部View（可以是任何View）-->
    <TextView
        android:id="@+id/headerView"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:background="@color/colorPrimary"
        android:gravity="center"
        android:text="@string/app_name"
        android:textColor="@android:color/white"
        android:textSize="28sp" />

    <!--二楼View（可以是任何View）-->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/secondFloorView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white"
        app:layout_behavior="com.wuyr.secondfloorbehavior.SecondFloorBehavior" //设置Behavior
        app:layout_dampingRatio="0.5" //设置衰减率为0.5，即：手指滑动到100的时候，View的偏移量是50
        app:layout_enterDuration="1000" //设置进入二楼的动画时长为1秒
        app:layout_exitDuration="1000" //设置退出二楼的动画时长为1秒
        app:layout_minTriggerOffset="150dp" //设置触发进入二楼的滑动距离为150dp
        app:layout_onEnterSecondFloor="onEnterSecondFloor" //当进入二楼时会回调Activity中的onEnterSecondFloor()方法
        app:layout_onExitSecondFloor="onExitSecondFloor" //同上，此为监听退出二楼
        app:layout_rollbackDuration="500" //设置回退的动画时长为0.5秒
        app:layout_startInterceptDistance="200dp" //当下拉刷新控件下拉到200dp时触发二楼的下拉 
        />

    <!--一楼View（可以是任何View）-->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/firstFloorView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
            android:id="@+id/refreshLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recyclerView"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />
        </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

### Demo下载: [app-debug.apk](https://github.com/wuyr/SecondFloorBehavior/raw/master/app-debug.apk)
### 库源码地址: <https://github.com/Ifxcyr/SecondFloorBehavior>

### 效果图:（图片有点大，加载挺慢，可以安装上面的APK来预览）
![preview](https://github.com/wuyr/SecondFloorBehavior/raw/master/previews/preview1.gif) ![preview](https://github.com/wuyr/SecondFloorBehavior/raw/master/previews/preview2.gif)
![preview](https://github.com/wuyr/SecondFloorBehavior/raw/master/previews/preview3.gif) ![preview](https://github.com/wuyr/SecondFloorBehavior/raw/master/previews/preview4.gif)
