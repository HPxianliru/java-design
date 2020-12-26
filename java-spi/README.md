SPI全称Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的接口，它可以用来启用框架扩展和替换组件。 SPI的作用就是为这些被扩展的API寻找服务实现。

SPI和API的使用场景

    API （Application Programming Interface）在大多数情况下，都是实现方制定接口并完成对接口的实现，调用方仅仅依赖接口调用，且无权选择不同实现。 从使用人员上来说，API 直接被应用开发人员使用。
    
    SPI （Service Provider Interface）是调用方来制定接口规范，提供给外部来实现，调用方在调用时则选择自己需要的外部实现。  从使用人员上来说，SPI 被框架扩展人员使用。
    
实现主要有俩种 JDK 自带 

    本示例使用了jdk自带 使用了状态机模式做了封装。根据枚举选择使用对应的实现。



还有dubbo实现 
 
    dubbo对java原生的spi机制作出了一定的扩展，使得其功能更加强大。
    dubbo对services 实现了 k、v 模式加载根据注解方式使用