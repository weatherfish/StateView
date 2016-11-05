# StateView

[ ![Download](https://api.bintray.com/packages/nukc/maven/StateView/images/download.svg) ](https://bintray.com/nukc/maven/StateView/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-StateView-green.svg?style=true)](https://android-arsenal.com/details/1/4255)

StateView一个轻量级的控件, 继承自`View`, 吸收了`ViewStub`的一些特性, 初始状态下是不可见的, 不占布局位置, 占用内存少。
当进行操作显示空/重试/加载视图后, 该视图才会被添加到布局中。所以当用不到显示视图的时候, 占用内存是很少的。
由于目前只有3个视图, 所以至多只会添加3次, 相对于其他具有同样功能的一些控件, StateView更具有优势。

<img src="https://raw.githubusercontent.com/nukc/stateview/master/art/custom.gif">


```groovy
   compile 'com.github.nukc.stateview:library:0.3.5'
```

## 使用方法

直接在代码中使用:

- 注入到Activity
```java
    mStateView = StateView.inject(Activity activity);
```

- 注入到ViewGroup
```java
    mStateView = StateView.inject(ViewGroup parent);

    mStateView = StateView.inject(ViewGroup parent, boolean hasActionBar);
```

```java
    //可用于在Fragment的onCreateView中
    mStateView = StateView.inject(View view);

    mStateView = StateView.inject(View view, boolean hasActionBar);
```

或添加到布局:

```xml

    <com.github.nukc.stateview.StateView
        android:id="@+id/stateView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

```

- 显示空视图: ```mStateView.showEmpty();```
- 显示加载视图: ```mStateView.showLoading();```
- 显示重试视图: ```mStateView.showRetry();```
- 显示内容: ``` mStateView.showContent();```

设置重试点击事件:

```java
    mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
        @Override
        public void onRetryClick() {
            //do something, no need to call showLoading()
            //不需要调用showLoading()方法, StateView自会调用
        }
    });
```

设置自定义视图:

- 全局设置办法:在自己项目的layout下新建, 名字跟StateView默认layout一样即可(也不用代码设置).
默认layout的名字:```base_empty```/```base_retry```/```base_loading```.

- 单页面设置:layout名字不一样, 然后再代码设置.

```java
setEmptyResource(@LayoutRes int emptyResource)

setRetryResource(@LayoutRes int retryResource)

setLoadingResource(@LayoutRes int loadingResource)
```


## Custom Attribute

```xml
<resources>
    <declare-styleable name="StateView">
        <attr name="emptyResource" format="reference" />
        <attr name="retryResource" format="reference" />
        <attr name="loadingResource" format="reference" />
    </declare-styleable>
</resources>
```

## ChangeLog

#### Version 0.3.5
更改inject(activity)方法, 不直接添加到DecorView中, 而加到Content中.
Deprecated几个方法.

#### Version 0.3.3
增加自定义视图的Sample;
修改library的默认layout名字

#### Version 0.3.2
进一步测试, 增加Sample;
删除没必要的方法, 考虑到注入不应该遮挡工具栏, 为此增加inject方法

#### Version 0.3.1
增加静态方法:
inject(View view),该参数view必须是viewGroup,可用于在Fragment中

#### Version 0.3.0
增加静态方法:
inject(Activity activity),用于把StateView添加到DecorView中;

inject(ViewGroup parent),用于添加到ViewGroup中

#### Version: 0.2.4
修复显示LoadingView后还能触摸下层的View

#### Version: 0.2.3
修复 [issues #2](https://github.com/nukc/StateView/issues/2)

#### Version: 0.2.1
更新gradle和library版本, 增加一个私有方法showView。

#### Version: 0.2.0
修复v0.1.0版本中当使用layout_below的时候addView可能无法正常显示的问题。

#### Version: 0.1.0
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