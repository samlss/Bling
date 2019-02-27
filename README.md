![Bling](https://github.com/samlss/Bling/blob/master/screenshots/bling.png)

[![Download](https://api.bintray.com/packages/samlss/maven/bling/images/download.svg?version=1.0.0)](https://bintray.com/samlss/maven/bling/1.0.0/link) [![Api reqeust](https://img.shields.io/badge/API-11+-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11#l11)[![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/Bling/blob/master/LICENSE)  



### Screenshots

#### Circle

![Circle](https://github.com/samlss/Bling/blob/master/screenshots/screenshot1.gif)

<br>

#### Rectangle

![Rectangle](https://github.com/samlss/Bling/blob/master/screenshots/screenshot2.gif)

<br>

#### Triangle
![Triangle](https://github.com/samlss/Bling/blob/master/screenshots/screenshot3.gif)

<br>

#### Star
![Star](https://github.com/samlss/Bling/blob/master/screenshots/screenshot4.gif)

<br>

#### Mixed
![Mixed](https://github.com/samlss/Bling/blob/master/screenshots/screenshot5.gif)

<br>

------
### Dependency

#### Gradle
Add it in your module build.gradle at the end of repositories:
  ```java
  dependencies {
      implementation 'me.samlss:bling:1.0.0'
  }
  ```

#### Maven
```java
<dependency>
  <groupId>me.samlss</groupId>
  <artifactId>bling</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### Sample Usage

```java
//Build a 'Bling'
Bling bling = new Bling.Builder('viewgroup' or 'activity')
    .setDuration(8000) //Set the drop time of the shapes.
    .setShapeCount(66) //Set how many shapes will fall.
    .setRadiusRange(10, 20) //Set the range of the shape radius.
    .setRotationSpeedRange(-3f, 3f) //Set the rotation speed range of falling shapes.
    .setAutoHide(true) //Set whether to hide the shapes when the animation ends
    .setColors(colors) //Set the colors of shapes.
    .setSpeedRange(0.1f, 0.5f) //Set shape drop speed.
    .setRotationRange(90, 150) //Set the rotation range of falling shapes.
    .setInterpolator(new LinearInterpolator()) //Set the drop interpolator of the shapes.
    .setBlingListener(new BlingListener() {
        @Override
        public void onBegin() {
			//begin to fall
        }

        @Override
        public void onEnd() {
			//end to fall
        }
    })
    .build(); 

//The you can call the Bling's method:
bling.show('shapeType'); //The shape type can be: CIRCLE, RECTANGLE, TRIANGLE, STAR, MIXED

bling.dismiss(); //Dismiss the shapes, event though they are doing animation.

bling.release(); //To release Bling & remove the bling view immediately.

```


### License

```
Copyright 2018 samlss

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