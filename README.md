# StateView

[ ![Download](https://api.bintray.com/packages/nukc/maven/StateView/images/download.svg) ](https://bintray.com/nukc/maven/StateView/_latestVersion)

StateView一个轻量级的控件, 继承自`View`, 吸收了`ViewStub`的一些特性, 初始状态下是不可见的, 不占布局位置, 占用内存少。
当进行操作显示空/重试/加载视图后, 该视图才会被添加到布局中。所以当用不到显示视图的时候, 占用内存是很少的。
由于目前只有3个视图, 所以至多只会添加3次, 相对于其他具有同样功能的一些控件, StateView更具有优势。


## Usage

```groovy

    dependencies {
        //正在等审核通过
        compile 'com.github.nukc.stateview:library:0.2.1'
    }

```

## ChangeLog

### Version: 0.2.1
更新gradle和library版本, 增加一个私有方法showView。

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



## License

    The MIT License (MIT)

    Copyright (c) 2016 Nukc

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.