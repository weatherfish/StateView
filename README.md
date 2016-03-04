# StateView

待添加。

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