

## Springboot员工管理系统

使用Springboot整合Mybatis实现了简单的CRUD的员工管理系统

### 环境要求

IDEA

MySql5.7

Maven3.5.2

### 1.设计数据库

```mysql
CREATE DATABASE springboot; 

USE springboot;

CREATE TABLE adminuser(
id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(20) NOT NULL,
PASSWORD VARCHAR(30) NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO adminuser VALUES(NULL,'admin','123456');

CREATE TABLE department(
id INT PRIMARY KEY AUTO_INCREMENT,
departmentName VARCHAR(20) NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO department VALUES
(NULL,'研发'),
(NULL,'运维');

CREATE TABLE employee(
eid INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(20) NOT NULL,
email VARCHAR(30) NOT NULL,
gender INT NOT NULL,
e_did INT NOT NULL,
birth DATE NOT NULL,
FOREIGN KEY (e_did) REFERENCES department(did)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO employee VALUES
(NULL,'张三','123456@qq.com',1,1,'2020-07-23 20:00:00'),
(NULL,'李四','123457@qq.com',0,2,'2020-07-23 20:00:00');

```



### 2.新建springboot项目

项目结构

![image-20200729203345561](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200729203345561.png)

#### 2.1添加web，lombok

#### 2.2导入前端页面，资源

导入html页面到templates目录

导入静态资源到static目录

#### 2.3整合Mybatis

添加整合Mybatis，mysql驱动，jdbc连接

```xml
<!--整合Mybatis-->
        <!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
```



application.yml中配置数据源datasource

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

application.yml中配置mybatis

```yaml
mybatis:
  type-aliases-package: com.ls.pojo #别名
  mapper-locations: classpath:mybatis/mapper/*.xml #mapper文件
```

测试数据源是否可用

```java
@Autowired
DataSource dataSource;

@Test
void contextLoads() throws SQLException {

    System.out.println(dataSource);
    System.out.println(dataSource.getConnection());

}
```

### 3.pojo实体类

管理员User

```java
//管理员
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
}
```

部门Department

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private int id;
    private String departmentName;
    
}
```

员工Employee

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private int id;
    private String name;
    private String email;
    private Integer gender; //0 女  1男
    private Department department;
    private Date birth;

}
```

EmployeeDTO

```java
package com.ls.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private int eid;
    private String name;
    private String email;
    private Integer gender; //0 女  1男
    private String departmentName;
    private Date birth;

}
```

### 4.mapper

管理员mapper

```java
@Mapper
@Repository
public interface UserMapper {
     User queryUserByUsernameAndPassword(Map<String,String> map);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ls.mapper.UserMapper">
    <select id="queryUserByUsernameAndPassword" parameterType="map" resultType="user">
        select * from springboot.adminuser where username = #{username} and password = #{password};
    </select>
</mapper>
```

部门mapper

```java
@Mapper
@Repository
public interface DepartmentMapper {

    //查询所有部门id
    List<Department> queryAllDepartment();

    //根据部门id查询部门
    Department queryDepartment(@Param("did") int id);

}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ls.mapper.DepartmentMapper">

    <select id="queryAllDepartment" resultType="Department">
        select * from springboot.department;
    </select>

    <select id="queryDepartment" resultType="Department">
        select * from springboot.department where did = #{did};
    </select>

</mapper>
```

员工mapper

```java
@Mapper
@Repository
public interface EmployeeMapper {

    //查询所有员工
    List<Employee> queryAllEmployee();

    //根据id查询员工
    Employee queryEmployee(@Param("eid") int eid);

    //添加员工
    int addEmployee(Employee employee);

    //删除员工
    int deleteEmployee(@Param("eid") int eid);

    //修改员工
    int updateEmployee(Employee employee);

}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ls.mapper.EmployeeMapper">

    <select id="queryAllEmployee" resultType="Employee">
        select * from springboot.employee;
    </select>

    <select id="queryEmployee" resultType="Employee">
        select * from springboot.employee where eid = #{eid};
    </select>

    <insert id="addEmployee" parameterType="Employee">
        insert into springboot.employee (name, email, gender, e_did, birth) values (#{name},#{email},#{gender},#{did},#{birth});
    </insert>

    <delete id="deleteEmployee">
        delete from springboot.employee where eid = #{eid};
    </delete>

    <update id="updateEmployee" parameterType="Employee">
        update springboot.employee set name = #{name},email = #{email},gender = #{gender},e_did = #{did},birth = #{birth} where eid = #{eid};
    </update>

</mapper>
```

在springboot测试类中注入mapper，测试sql语句，数据

```java
@Autowired
DataSource dataSource;

@Autowired
UserMapper mapper;

@Test
void contextLoads() throws SQLException {

    System.out.println(dataSource);
    System.out.println(dataSource.getConnection());

    Map<String, String> map = new HashMap<>();
    map.put("username","admin");
    map.put("password","123456");

    User user = mapper.queryUserByUsernameAndPassword(map);
    System.out.println(user);

}
```

### 5.实现首页

编写一个Mvc配置类，实现WebMvcConfigurer接口，重写方法

在视图控制器中实现首页映射跳转

```java
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //在浏览器访问/,就会跳转到index.html页面
        registry.addViewController("/").setViewName("index");
        //在浏览器访问/index.html,就会跳转到index.html页面
        registry.addViewController("/index.html").setViewName("index");
    }
}
```

修改html页面中引用静态资源的路径,使用templates模板引擎

```html
<link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
<link th:href="@{/css/signin.css}" rel="stylesheet">
```

设置项目虚拟目录和访问端口

```yaml
server:
  servlet:
    context-path: /ls #设置项目虚拟目录
  port: 80
```

### 6.国际化

在IDEA中设置编码，处理国际化properties乱码问题

![image-20200726144658496](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726144658496.png)

#### 6.1编写国际化配置文件

1.在resources下新建i18n目录，存放国际化配置文件。

2.新建login.properties,login_zh_CN.properties,login_en_US.properties;IDEA自动识别了要做国际化操作，文件夹变了。

![image-20200726145146365](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726145146365.png)

3.编写配置，点击IDEA左下角

![image-20200726145356586](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726145356586.png)

进入这个视图

![image-20200726145440991](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726145440991.png)

添加页面中需要进行国际化处理的信息

4.查看配置文件

login.properties

```properties
login.tip=请登录
login.username=用户名
login.password=密码
login.rememberme=记住我
login.btn=登录
```

login_zh_CN.properties

```properties
login.tip=请登录
login.username=用户名
login.password=密码
login.rememberme=记住我
login.btn=登录
```

login_en_US.properties

```properties
login.tip=Please sign in
login.username=Username
login.password=Password
login.rememberme=Remember me
login.btn=Sign in
```

#### 6.2配置页面国际化值

在页面中获取国际化值，Thymeleaf中，message的取值操作为：#{...}。

![image-20200726145926018](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726145926018.png)

启动项目进行测试，可以看到已经识别为中文了

![image-20200726150026729](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726150026729.png)

#### 6.3配置国际化解析

根据中英文按钮自动切换中英文

修改前端页面跳转链接

```html
<a class="btn btn-sm" th:href="@{/index.html(l=zh_CN)}">中文</a>
<a class="btn btn-sm" th:href="@{/index.html(l=en_US)}">English</a>
```

自定义一个组件`LocaleResolver`，实现根据按钮切换

编写一个区域信息解析类（实现LocaleResolver接口）,处理国际化请求

```java
package com.ls.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//语言解析，请求链接携带语言参数
public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {

        String language = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault(); //没有获取到就使用默认的

        //如果请求链接携带语言参数
        if (!StringUtils.isEmpty(language)){
            //分割请求参数
            String[] split = language.split("_");
            //国家，地区
            locale = new Locale(split[0],split[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
```

将自定义的组件配置到到spring容器中

让区域信息解析类生效，在MyMvcConfig配置类中添加bean

```java
//使区域化信息生效，注入到spring容器
@Bean
public LocaleResolver localeResolver(){
    return new MyLocaleResolver();
}
```

重启项目，可以看到效果

![image-20200726151310795](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726151310795.png)

![image-20200726151258976](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726151258976.png)

### 7.登录+拦截器

#### 7.1实现登录

修改登录页面表单提交地址和方法

![image-20200726162426549](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726162426549.png)

编写UserService

```java
public interface UserService {
    //登录功能
    User UserLogin(String username, String password);
}
```

UserServiceImpl实现UserService，注入UserMapper

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    //登录功能
    public User UserLogin(String username,String password){
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        return mapper.queryUserByUsernameAndPassword(map);
    }

}
```

编写对应地址的controller

```java
@Controller
public class loginController {

    @Autowired
    private UserService service;

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model){
        User user = service.UserLogin(username, password);
        if (user!=null){//登录成功
            //用户信息存到session中
            session.setAttribute("loginUser",username);
            //防止表单重复提交，重定向到main页面
            return "redirect:/main.html";
        } else {//登录失败
            model.addAttribute("msg","用户名或密码错误");
            return "index";
        }
    }
}
```

在index页面请登录下面添加P标签，显示登录失败消息，使用Thymeleaf的工具函数判断消息是否为空

![image-20200726162924485](E:\学习\java\Springboot\Springboot员工管理系统.assets\image-20200726162924485.png)

在MyMvcConfig配置类中添加，实现main和dashboard映射

```java
registry.addViewController("/main.html").setViewName("dashboard");
```

#### 7.2实现拦截器

在不登陆的情况下可以直接访问main.html，所以需要拦截器进行登录拦截，实现没有登录的情况下不能访问

自定义拦截器，实现HandlerInterceptor接口

```java
//登录拦截器
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录后，应该有用户的session
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser==null){//没有登录，返回提示信息，拦截请求
            request.setAttribute("msg","没有权限，请先登录");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        } else {
            return true;//放行
        }
    }
}
```

把自定义的拦截器注册在MyMvcConfig配置类中

```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")	   .excludePathPatterns("/","/index.html","/user/login","/css/**","/img/**","/js**");//拦截所有请求，排除这些请求
}
```

在dashboard页面显示登录的用户名，使用

```html
<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#" th:text="${session.loginUser}">Company name</a>
```

### 8.展示员工列表

提取dashboard，list公共页面

使用`th:fragment="topbar"`提取出公共页面

使用提取出的公共页面`<div th:replace="~{commons/commons::topbar}"></div>`

EmployeeService

```java
public interface EmployeeService {
    //查询所有员工
    List<Employee> queryAllEmployee();
}
```

EmployeeServiceImpl

```java
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public List<Employee> queryAllEmployee() {
        return mapper.queryAllEmployee();
    }
}
```

EmployeeController

```java
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @RequestMapping("/emps")
    public String queryEmp(Model model){
        List<Employee> employees = service.queryAllEmployee();
        model.addAttribute("emps",employees);
        return "emp/list";
    }

}
```

在list页面展示员工信息

### 9.增加员工

增加一个按钮跳转到增加员工页面

```html
<a th:href="@{/emp}" class="btn btn-sm btn-success">添加员工</a>
```

在EmployeeController中添加跳转到添加页面的controller，其中查询出所有部门

```java
//跳转到添加页面
@GetMapping("/emp")
public String toAdd(Model model){
    List<Department> departments = departmentService.queryAllDepartment();
    model.addAttribute("departments",departments);
    return "emp/add";
}
```

添加一个增加员工页面 add.html，在遍历部门信息，部门名字显示到下拉框，部门id传给value表示提交表单的值

```html
<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
   <h2>添加员工</h2>
   <form th:action="@{/emp}" method="post">
      <div class="form-group">
         <label for="name">姓名</label>
         <input type="text" class="form-control" name="name" id="name" placeholder="请输入姓名">
      </div>
      <div class="form-group">
         <label for="email">邮箱</label>
         <input type="text" class="form-control" name="email" id="email" placeholder="请输入邮箱">
      </div>
      <div class="form-group">
         <label>性别</label>
         <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="gender" value="0">
            <lable class="form-check-label">女</lable>
         </div>
         <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="gender" value="1">
            <lable class="form-check-label">男</lable>
         </div>
      </div>
      <div class="form-group">
         <lable>部门</lable>
         <select class="form-control" name="did">
            <option>---请选择部门---</option>
            <option th:each="department :${departments}" th:text="${department.getDepartmentName()}" th:value="${department.getDid()}"></option>
         </select>
      </div>
      <div class="form-group">
         <label for="birth">日期</label>
         <input type="text" class="form-control" name="birth" id="birth" placeholder="请输入日期">
      </div>
      <button type="submit" class="btn btn-success">提交</button>
   </form>
</main>
```

在EmployeeController中添加添加员工的Controller

```java
@PostMapping("/emp")
public String addEmp(Employee employee){
    employeeService.addEmployee(employee);
    return "redirect:emps";
}
```

配置日期

```yml
spring:
    mvc:
        format:
            date: yyyy-MM-dd #时间日期格式化
```

### 10.修改员工信息

list页面给删除按钮添加跳转链接

```html
<a class="btn btn-sm btn-primary" th:href="@{/emp/}+${emp.getId()}">修改</a>
```

编写controller,在跳转controller中查出原来数据和部门信息，把数据传给update页面

```java
@GetMapping("/emp/{id}")
public String toUpdate(@PathVariable("id")int id,Model model){
    //查出原来的数据
    Employee employee = employeeService.queryEmployeeById(id);
    model.addAttribute("emp",employee);
    //查出部门信息
    List<Department> departments = departmentService.queryAllDepartment();
    model.addAttribute("departments",departments);
    return "emp/update";
}

@RequestMapping("/updateEmp")
public String updateEmp(Employee employee){
    System.out.println("debug===>>>"+employee);
    employeeService.updateEmployee(employee);
    return "redirect:/emps";
}
```

添加update页面表单获取选中员工信息和所有部门信息

```html
<form th:action="@{/updateEmp}" method="post">
   <div class="form-group">
      <!-- 隐藏域传递员工id -->
      <input type="hidden" class="form-control" name="eid" th:value="${emp.getEid()}">
      <label for="name">姓名</label>
      <input type="text" class="form-control" name="name" id="name" th:value="${emp.getName()}" placeholder="请输入姓名">
   </div>
   <div class="form-group">
      <label for="email">邮箱</label>
      <input type="text" class="form-control" name="email" id="email" th:value="${emp.getEmail()}" placeholder="请输入邮箱">
   </div>
   <div class="form-group">
      <label>性别</label>
      <div class="form-check form-check-inline">
         <input class="form-check-input" type="radio" name="gender" value="0" th:checked="${emp.getGender()==0}">
         <lable class="form-check-label">女</lable>
      </div>
      <div class="form-check form-check-inline">
         <input class="form-check-input" type="radio" name="gender" value="1" th:checked="${emp.getGender()==1}">
         <lable class="form-check-label">男</lable>
      </div>
   </div>
   <div class="form-group">
      <lable>部门</lable>
      <select class="form-control" name="did">
         <option>---请选择部门---</option>
         <option th:selected="${department.getDid() == emp.getDid()}" th:each="department :${departments}" th:text="${department.getDepartmentName()}" th:value="${department.getDid()}"></option>
      </select>
   </div>
   <div class="form-group">
      <label for="birth">入职日期</label>
      <input type="text" class="form-control" name="birth" th:value="${#dates.format(emp.getBirth(),'yyyy-MM-dd')}" id="birth" placeholder="请输入日期">
   </div>
   <button type="submit" class="btn btn-success">修改</button>
</form>
```

### 11.删除及404页面

list页面删除按钮添加地址

```html
<a class="btn btn-sm btn-danger" th:href="@{/emp/delete/}+${emp.getId()}">删除</a>
```

删除员工的controller，重定向到emps查询所有员工

```java
@GetMapping("/emp/delete/{id}")
public String deleteEmp(@PathVariable("id") int id){
    employeeService.deleteEmployeeById(id);
    return "redirect:/emps";
}
```

### 12.所有的service

UserService

```java
public interface UserService {
    //登录功能
    User UserLogin(String username, String password);
}
```

DepartmentService

```java
public interface DepartmentService {
    //查询所有部门id
    List<Department> queryAllDepartment();
}
```

EmployeeService

```java
public interface EmployeeService {
    //查询所有员工
    List<EmployeeDTO> queryAllEmployee();

    //添加员工
    void addEmployee(Employee employee);

    Employee queryEmployeeById(int id);

    void updateEmployee(Employee employee);

    int deleteEmployeeById(int id);
}
```

UserService

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    //登录功能
    public User UserLogin(String username,String password){
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);

        return mapper.queryUserByUsernameAndPassword(map);
    }

}
```

EmployeeServiceImpl

```java
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public List<EmployeeDTO> queryAllEmployee() {
        return mapper.queryAllEmployee();
    }

    @Override
    public void addEmployee(Employee employee) {
        mapper.addEmployee(employee);
    }

    @Override
    public Employee queryEmployeeById(int id) {
        return mapper.queryEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        mapper.updateEmployee(employee);
    }

    @Override
    public int deleteEmployeeById(int id) {
        return mapper.deleteEmployee(id);
    }
}
```

DepartmentServiceImpl

```java
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper mapper;

    @Override
    public List<Department> queryAllDepartment() {
        return mapper.queryAllDepartment();
    }
}
```