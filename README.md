# StateView


## ChangeLog

### Version: 0.2.1
更新gradle和library版本, 增加一个私有方法showView.

### Version: 0.2.0
修复当使用layout_below的时候addView可能无法正常显示的问题。

### Version: 0.1.0
测试Sample得知：
```xml
        <com.github.nukc.stateview.StateView
             android:id="@+id/stateView"
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:layout_marginTop="100dp"
             tools:visibility="gone" />
```
    使用 android:layout_below="@+id/ll" 的话 , addView有时会无法正常显示，有时却正常。在寻找问题。。

    3个按钮错乱多按几次，有几率会出现不显示的情况。