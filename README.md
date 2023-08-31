[![](https://jitpack.io/v/Kiaorra/Wavy.svg)](https://jitpack.io/#Kiaorra/Wavy)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

wavy
==============
<img src="/sample.gif?raw=true" alt="Wavy" />

🔧 Getting started
--------
#### Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
  
#### Step 2. Add the dependency

```gradle
dependencies {
	implementation 'com.github.Kiaorra:wavy:2.0.0'
}
```

🤔 How do I use wavy?
----------------------------
```xml
    <wavy.EditText
        android:id="@+id/wavy"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

### Additional attributes

- `app:accentLineColor` :  Color of underline when editText is focused.

- `app:defaultLineColor` : Color of underline when editText is not focused.

- `app:duration` : Amount of time (in milliseconds) to display this frame.

- `app:interpolator` : Sets the acceleration curve for the indeterminate animation. Defaults to a linear interpolation.

- `app:underlineWidth` : Stroke width of the underline.

🌊 Wavy in Compose Jetpack
----------------------------
Wavy allows you to display tooltips in Jetpack Compose easily.

Add the dependency below to your **module**'s `build.gradle` file:

```gradle
dependencies {
	implementation 'com.github.Kiaorra:wavy-compose:2.0.0'
}
```

### Wavy Composable

```kotlin

var text by remember { mutableStateOf("") }

val scope = rememberCoroutineScope()

val focusManager = LocalFocusManager.current

val focusRequester by remember { mutableStateOf(FocusRequester()) }

Wavy(
    text = text,
    scope = scope,
    focusRequester = focusRequester,
    modifier = Modifier,
    onTextChanged = { text = it },
    ..
)
```

### Composable attributes
```kotlin
// Wavy Composable Example

Wavy(
    text = text,
    scope = scope,
    focusRequester = focusRequester,
    modifier = Modifier,
    onTextChanged = { text = it },
    animationSpec: AnimationSpec<Float> = tween(1000),
    accentLineColor: Color = Color(0xFFEF00FF) // purple
    accentLineStrokeWidth: Dp = 1.dp,
    accentLineYPositionFromBottom: Dp = 1.dp,
    defaultLineColor: Color = Color(0xFF000000), // black
    defaultLineStrokeWidth: Dp = 1.dp,
    defaultLineYPositionFromBottom: Dp = 1.dp
)
```


- `animationSpec`: Animation Spec of wavy ex) spread speed, spread effect etc..
  
- `accentLineColor` :  Color of underline when editText is focused.

- `accentLineStrokeWidth`: Width of underline when editText is focused.
  
- `accentLineYPositionFromBottom`: Y Position of accentLine

- `defaultLineColor` : Color of underline when editText is not focused.

- `defaultLineStrokeWidth`: Width of underline when editText is not focused.

- `defaultLineYPositionFromBottom`: Y Position of defaultLine

📃 License
-------

    Copyright 2021 Kiaorra.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
