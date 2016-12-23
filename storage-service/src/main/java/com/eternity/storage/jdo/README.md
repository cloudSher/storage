### 操作

* 模型实体类： 根据业务抽象出实体类，这些实体类对应数据库相应的表结构

* jdo配置文件 默认在META-INF/persistence.xml，配置相关类型，以及所需要的驱动

* 采用jdo注解标识实体类， 详细查看[http://www.datanucleus.org:15080/products/accessplatform/jdo/annotations.html] 
   也可以采用xml形式描述元数据，

* mvn install 编译生成class文件

* mvn datanucleus:enhance 对编译生成的class文件进行增强 ，默认插件会在compile的时候，增强class文件

* 可以调用mvn datanucleus:schema-create  对增强的实体类转化生成对应驱动中的schema

* jdo api 操作类