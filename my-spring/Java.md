# 												一、JVM调优

---

### 									一、JVM参数类型

- **标配参数**

- **x参数**

- **xx参数**


1. ##### **标配参数：**

   ​	java -version，-help等，稳定的参数

2. ##### **x参数：**

   ​	-Xint：解释执行；

   ​	-Xcomp：第一次使用就编译成本地代码

   ​	-Xmixed：混合模式

3. ##### **xx参数:**

   1. **Boolean类型：**

      1. -XX：+或者-某个属性值

         ​	+表示开启；-表示关闭

      2. 

   2. **K-V设置类型（key-value）**

     

### 									二、



---







# 												二、集合

---

### 									一、List

#### 1.特性

- list允许重复
- 可以插入多个null
  - 有序	

#### 2.常用List实现类

- **ArrayList：**

  ​		基于数组实现，非线程安全，由于数组结构，插入或删除影响后面的数据，慢，查询根据索引所以快。查询快而插入和删除慢

  ​		有索引

  ​		遍历：Iterator，Iterator.hasNext（）、for(T t :list)

  ​		排序：Collections.sort（），复杂List<User>实现Comparable<User>重写compareTo（）

  ```java
          //升序
  		@Override
          public int compareTo(User o) {
  
              return age.compareTo(o.getAge());
          }
  		//删除
  		Iterator<Integer> iterator = s.iterator();
          while (iterator.hasNext()) {
              int i = iterator.next();
              if (5 == i || 7 == i) {
                  iterator.remove();
              }
          }
  ```

- **Vector：**

  ​		基于数组实现

- **LinkedList：**

  ​		底层是双向链表结构，插入或删除只影响位置的前后元素，所以插入快，查询需要从头开始查，慢

  

**迭代器fail-fast属性**：Iterator fail-fast属性检查当前集合结构里的任何改动。如果发现任何改动，它抛出ConcurrentModificationException



### 									二、Set

#### 1.特性

- 无序

- 不允许重复

- 允许null（因为不允许重复所以只有一个null） 


#### 2.常用Set实现类

- **HashSet:**底层hashtable实现
- **TreeSet：**有序

### 									三、Map

#### 1.特性

- 无序
- key不允许重复，value允许重复
- 允许一个null  key

#### 2.常用Map实现类

- **HashMap：**底层数组和链表实现。key的hashcode（）和equals（）
- **HashTable：**不允许null，线程安全，同步
- **TreeMap**：有序

HashMap使用Key对象的hashCode()和equals()方法去决定key-value对的索引。

（1）如果o1.equals(o2)，那么o1.hashCode() == o2.hashCode()总是为true的。

（2）如果o1.hashCode() == o2.hashCode()，并不意味着o1.equals(o2)会为true。





---





# 												三、线程

---

[**线程面试题**](https://blog.csdn.net/cmyperson/article/details/79610870)

### 									一、CAS(compare and swap比较并交换)

##### 什么是CAS

​	比较并交换，是自旋锁和乐观锁的核心操作。

​	CAS 操作包含三个操作数 —— 内存位置/地址（V）、预期原值（A）和新值(B)：如果内存位置的值和预期原值A一致，则内存中的更新成A

​	自身进行循环操作，直到达到所期望的值然后停止循环。

​	AtomicInteger 等这些 并发包下的Atomic*类，底层使用Unsafe类实现。

​	CAS主要是**线程本地变量**和**主内存变量**进行比较，如果是自己期望值，则更新，否则循环直到一致。

##### CAS优缺点

- 循环时间长开销大
- 只能保证一个共享变量的原子操作，多个共享变量必须使用锁
- 引出**ABA**问题（**一个线程将一个共享变量从A改成B,又从B改成A**）可以利用**原子引用类AtomicStampedReference** 增加版本号解决



### 									二、自旋锁

##### 	什么是自旋锁

​	当线程A在获取锁的时候，如果这个锁已经被其他线程获取，那么该线程A则循环等待，判断锁是否能被A获取，直到获取成功才会退出循环。

##### 自旋锁优缺点

- **减少线程的上下文切换**：线程一直处于active状态，不会进入阻塞状态
- 如果长时间获取不到锁，会增加CPU消耗



### 									三、Java内存模型

​	内存分为主内存和本地内存，每个线程有自己的本地内存，主内存中存放共享变量，本地内存存放共享变量的副本。本地内存更新变量副本后需要同步到主内存中。

##### 	三个特性：

- 可见性
- 原子性
- 可排序性



### 									四、乐观锁和悲观锁

##### 	乐观锁：

​			每次拿数据都认为别人不会修改，所以不加锁；但是在更新的时候会进行一个版本的比较，看是否被更改过。适用于多读的应用。

##### 	悲观锁：

​			每次都认为别人会更改数据，所以加锁。传统关系型数据库的行锁，表锁等，读锁，写锁



### 									五、AQS（AbstractQueuedSynchronizer队列同步器）

##### 	1、什么是AQS

​		Java并发用来构建锁和其他同步组件的基础框架

​		提供了一种实现阻塞锁和一系列以来FIFO等待队列的同步器的框架

##### **2、AQS实现方式**

​		AQS的主要使用方式是继承，子类通过**继承同步器并实现它的抽象方法**来管理同步状态。

##### **3、AQS的原理**

​		AQS使用一个int类型的成员（全局）变量state来表示同步状态，当state>0表示获取了锁，=0表示释放锁。提供了三个方法：1、getState（），2、setState（），3、compareAndSetState（）来对state进行操作。AQS确保对state的操作是线程安全的。

​		AQS通过内置的**FIFO**同步队列来完成资源获取线程的排队工作。如果当前线程获取同步锁失败时，AQS则会将**当前线程以及等待状态信息构成一个节点(Node)并将其加入同步队列**，同时会阻塞当前线程，当同步锁释放时，则会把节点中的线程唤醒，使其再次尝试获取锁。



### 									六、Executors框架

Executors框架其内部采用了线程池机制，他在java.util.cocurrent包下，通过该框架来控制线程的启动、执行、关闭，可以简化并发编程的操作。因此，通过Executors来启动线程比使用Thread的start方法更好，而且更容易管理，效率更好，还有关键的一点：有助于避免this溢出。

Executors框架包括：线程池、Executor，Executors，ExecutorService、CompletionServince，Future、Callable。





### 									七、阻塞队列



##### 1、什么是阻塞队列



​	**阻塞队列**是一个支持**两个附加操作**的队列：1、在队列为空时，从队列获取元素时会等待队列变为非空。2、当队列满时，当队列存储元素时会等待队列不满。

常用于**生产者-消费者**模式，**生产者**是往队列存储元素的线程，**消费者**是从队列中获取元素的线程。



##### 2、阻塞队列提供四种处理方法：

| 方法\处理方式 | 抛出异常  | 返回特殊值 | 一直阻塞 | 超时退出           |
| ------------- | --------- | ---------- | -------- | ------------------ |
| 插入方法      | add(e)    | offer(e)   | put(e)   | offer(e,time,unit) |
| 移除方法      | remove()  | poll()     | take()   | poll(time,unit)    |
| 检查方法      | element() | peek()     | 不可用   | 不可用             |

**抛出异常：**当阻塞队列满时，再用add（e）方法插入元素，会抛出IllegalStateException(“Queue full”)异常；当阻塞队列空时，再用remove（）方法移除元素，会抛出NoSuchElementException异常。

**返回特殊值：**offer（e）插入元素返回是否成功true或者false；移除方法poll（）没有返回null

**阻塞：**put（e）无返回值，队列满了继续添加元素线程则会进入阻塞等待，直到添加成功或异常。take（）队列空时线程会进入阻塞等待，直到有元素可删除或异常

**超时退出：**offer(e,time,unit)：成功true，失败false；  poll(time,unit)成功返回元素，失败false



##### 3、Java常用阻塞队列

###### 	1、ArrayBlockingQueue：

​			基于数组结构组成的有界阻塞队列，FIFO先进先出，可以指定队列数

​			默认非公平，可重入



​	**ArrayBlockingQueue在生产者放入数据和消费者获取数据，都是共用同一个锁对象，由此也意味着两者无法真正并行运行，这点尤其不同于LinkedBlockingQueue**



###### 	2、LinkedBlockingQueue：

​			基于链表结构组成的有界阻塞队列，FIFO先进先出，但默认值太大，可以指定队列数

​	**LinkedBlockingQueue之所以能够高效的处理并发数据，因为其对于生产者端和消费者端分别采用了 <u>独立的锁</u> 来控制数据同步**



###### 	3、SynchronousQueue：

###### 			不存储元素阻塞队列。同步队列。		

​	**吞吐量：**SynchronousQueue  >  LinkedBlockingQueue  >  ArrayBlockingQueue

##### 4、阻塞队列实现原理（ArrayBlockingQueue）

​	使用通知者模式实现：当生产者往满的队列里添加元素时会阻塞生产者，当消费者消费了一个队列元素后会通知生产者当前队列可用。

​	ArrayBlockingQueue使用了Lock，和Condition实现：

```java
    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }
```

```

```

### 									八、Callable和Future

##### 1、什么是Callable和Future

​	Callable接口代表一段可以调用并返回结果的代码;Future接口表示异步任务，是还没有完成的任务给出的未来结果。所以说Callable用于产生结果，Future用于获取结果。

​	Callable接口使用泛型去定义它的返回类型。Executors类提供了一些有用的方法在线程池中执行Callable内的任务。由于Callable任务是并行的（并行就是整体看上去是并行的，其实在某个时间点只有一个线程在执行），我们必须等待它返回的结果

##### 2、实现Callsble

```java
    class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return Thread.currentThread().getName();
        }
    }
public class CallableTest{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
		//第一种实现
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        Thread thread = new Thread(futureTask, "AAAA");
        thread.start();
        System.out.println(futureTask.get());

        //第二种实现
        FutureTask<String> futureTask2 = new FutureTask<>(myCallable);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(futureTask2);
        while (true) {
            if (futureTask2.isDone()) {
                executorService.shutdown();
                System.out.println(futureTask2.get());
                break;
            }
        }

        //第三种实现
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        Future<String> future = executorService2.submit(myCallable);

        executorService2.shutdown();
        System.out.println(future.get());

    }
}

```

### 									九、同步容器和并发容器

##### 1、同步容器

​	同步容器包括两个部分，一个是vector和HashTable。同步容器都是线程安全的。

​	同步容器通过对容器的所有状态进行串行访问，从而实现它们的线程安全

​	这样做的代价是削弱了并发性，当多个线程共同竞争容器级的锁时，吞吐量就会降低

##### 2、并发容器

- CopyOnWrite容器：CopyOnWriteArrayList、CopyOnWriteArraySet
- CocurrentMap的实现类：ConcurrentHashMap、ConcurrentSkipListMap 

+ 阻塞队列的实现类（共七种） 
+ 其他：ConcurrentLinkedQueue、ConcurrentLikedDeque、ConcurrentSkipListSe



### 									十、线程上下文切换

​	两个线程A、B。	**A开始执行到t1，CPU记录A线程t1这个状态；然后B开始执行到t2，CPU记录B线程t2这个状态。然后CPU开始重新执行A线程。**这个过程就叫做线程上下文切换。

​	

### 十一、ThreadLocal

​	ThreadLocal是线程局部变量。不想被线程共享的变量可以用ThreadLocal

​	同步机制是为了同步多个线程对相同资源的并发访问，是为了多个线程之间进行通信的有效方式；而ThreadLocal是隔离多个线程的数据共享，从根本上就不在多个线程之间共享资源（变量）

### 十二、ThreadPool（线程池）用法与优势

​	线程池特点：

- **降低资源消耗**：通过复用线程，减少线程的创建和销毁
- **提高相应速度**：当任务到达时，不需要等待线程创建就能执行
- **提高线程的可管理型**：线程池对线程进行统一的分配、调优、监控

```java
第一种：ExecutorService executorService = Executors.newCachedThreadPool();
第二种：ExecutorService executorService1 = new ThreadPoolExecutor(2,5,10,
                                                        TimeUnit.SECONDS,new LinkedBlockingQueue<>(3),
                                                              Executors.defaultThreadFactory(),
                                                              new ThreadPoolExecutor.AbortPolicy());
```



### 十三、synchronized和ReentrantLock的区别

​	synchronized是非公平锁。  ReentrantLock可设置公平、非公平。

​	synchronized是JVM层面的，ReentranLock是JDK层面的。

ReentranLock的实现是一种自旋锁

​	

### 十四、Semaphore、CountDownLatch、CyclicBarrier

##### 1、Semaphore

​	对于Semaphore类而言，就如同一个看门人，限制了可活动的线程数

​	获取资源

- void acquire():从此信号量获取一个许可前线程将一直阻塞。相当于一辆车占了一个车位。           


- void acquire(int n):从此信号量获取给定数目许可，在提供这些许可前一直将线程阻塞。比如n=2，就相当于一辆车占了两个车位。

  释放资源

- void release():释放一个许可，将其返回给信号量。就如同车开走返回一个车位。


- void release(int n):释放n个许可

##### 2、CountDownLatch

​	 允许一个或多个线程等待其他线程完成操作

假如有这样一个需求，当我们需要解析一个Excel里多个sheet的数据时，可以考虑使用多线程，每个线程解析一个sheet里的数据，等到所有的sheet都解析完之后，程序需要提示解析完成

```java
CountDownLatch latch = new CountDownLatch(2);

//thread1

new Thread(()->{
    //dosomething
    latch.countDown();
}).start();


//thread2
new Thread(()->{
    //dosomething
    latch.countDown();
}).start();

//otherthing
latch.await();//await()会阻塞当前线程，直到N变成零
//后续才会执行其他线程的操作
```



##### 3、CyclicBarrier

​	和CountDownLatch相反， 类似于一个栅栏，当所有的线程都到达这个点时，后续的才能进行。



##### 十五、死锁

##### 	1、死锁定义：

​		多个进行相互等待对方资源，在得到所有资源继续运行之前，都不会释放自己已有的资源，这样造成了循环等待的现象，称为死锁。

##### 	2、产生死锁的四个必要条件：

- **资源互斥、资源不共享**

  一个资源只能被一个线程使用

- **占有和等待/请求并保持**

  已得到资源的线程还能继续请求其他资源

- **资源不可剥夺**

  一个资源被一个线程占有后，除非当前线程释放该资源，否则其他线程是不能获取到该资源

- **环路等待**

  线程A持有锁a，线程B持有锁b， A需要锁b，B需要锁a，但是锁 a和b都被占有，A和B相互等待。

- ##### 3、防止死锁:

- ##### - 破坏四个必要条件,

- ##### - 线程的需求资源数小于等于总可用资源数,

- ##### - 判断 系统安全状态 法。其实就是判断线程的需求资源是否大于目前可用资源





## 												四、Spring

### 一、动态代理、静态代理

​	**动态代理**：在程序运行时运用反射机制动态创建而成。一个代理类可以代理各种类型的委托类，一个对多。

​	**jdk实现java动态代理：**是依靠接口实现的，一旦没有接口，java动态代理就没法实现了

​	**静态代理**：在程序运行前**代理类的.class文件**就已经存在了。代理类必须和委托类实现同一个 接口。使用代理类时，必须创建一个代理类并将委托类传入进去。

​	**静态代理缺点：**一个静态代理类只能服务一个委托类。一对一的。

​	**cglib动态代理**：针对类来实现代理的

```java
    public interface Person{
        public String say();
    }
    //委托类
    public class Student implements Person{
        public String say(){
            return "student";
        }
    }



--------------------------------------------------------------------------------------

    //静态代理类
    public class StaticProxy implements Person{
        private Person person;
        public StaticProxy(Person p){
            this.person = p;
        }
        public String say(){
            return person.say();
        }
    }
    //测试类
    public class Test{
        public static void main(String[] args){
            Person proxy = new StaticProxy(new Student());
            proxy.say();
        }
    }
--------------------------------------------------------------------------------------
    //java动态代理类
    public class DynamicInvocationHandler implements InvocationHandler {
        
        private Object target;//委托类
        
        public Object myNewProxyInstance(Object target){
            this.target = target;
            //第一个参数指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器
            
           	//ClassLoader：类加载器， 用于加载代理对象字节码，和被代理对象使用相同的类加载器

        	//第二个参数要实现和目标对象一样的接口，所以只需要拿到目标对象的实现接口  
            //		让代理对象拥有和被代理对象相同的方法。类似于实现相同的接口
        	//第三个参数表明这些被拦截的方法在被拦截时需要执行哪个InvocationHandler的invoke方法 。   提供增强方法，可以匿名内部了实现
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
        }
        
        @Override  
        public Object invoke(Object proxy, Method method, Object[] args)  
            throws Throwable {  
            Object o = null;
            System.out.println("satrt-->>"); 
            before();
            o = method.invoke(target, args)
            after();
            System.out.println("end-->>"); 
            return o;
            
        }
    }
    //测试类
    public class Test{
        public static void main(String[] args){
            Person proxy = (Person)new DynamicInvocationHandler().myNewProxyInstance(new Student());
            proxy.say();
        }
    }
----------------------------------------------------------------------------
    //cglib实现
    public class CglibProxyDemo implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("执行前.......");
            Object ob = methodProxy.invokeSuper(o, objects);
            System.out.println("执行后.......");
            return ob;
        }
    }
    public class HelloImpl implements Hello{
        @Override
        public String sayHello(String string) {
            System.out.println("sayHello-->"+string);
            return null;
        }
        final public String sayHi(String string) {
            System.out.println("sayHi-->"+string);
            return null;
        }
    }
    public class CglibTest {
        public static void main(String[] args) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(HelloImpl.class);
            enhancer.setCallback(new CglibProxyDemo());
            HelloImpl hello = (HelloImpl) enhancer.create();
            hello.sayHello("Hello");
            hello.sayHi("Hi");
        }
    }

```

### 二、Spring默认使用jdk动态代理

​	1.如果目标对象实现了接口，在默认情况下采用jdk的动态代理实现aop

> ​	2.如果目标对象实现了接口，也可以强制使用cglib生成代理实现aop

> ​	3.如果目标对象没有实现接口，那么必须引入cglib，spring会在jdk的动态代理和cglib代理之间切换
>
> **cglib动态代理：被代理目标不是是final修饰的类，因为cglib是基于继承实现的**
>
> ```xml
> <aop:aspectj-autoproxy proxy-target-class="true"/>强制使用cglib代理
> ```

**AspectJ：静态代理**
**AOP：动态代理**



### 三、Spring的Bean单例与线程安全问题

> ​	一个Bean如果是单例的，且在Bean中有全局变量context（全局变量是每个线程共享的资源，会存在并发问题）。
>
> ​	那么在多线程并发的情况下，当A线程进来将context的值设为自己的，这个是线程B也进来将context改成B的，这时候A的context的值也变成B的了。
>
> 解决方法：1、**使用ThreadLocal**，这样每个线程都有自己的本地变量副本conetxt。
>
> ​		  2、避免全局变量

### 四、Spring开启自动配置

```xml
 <context:annotation-config/>
```

**@Autowired:根据name去匹配**

**@Resource:根据类型去匹配，没有找到名称则会根据类型去匹配**

### 五、Spring支持的事务管理类型

- **编程式事务管理**：这意味你通过编程的方式管理事务，给你带来极大的灵活性，但是难维护。**代码手动实现 事务操作(提交，回滚)**
- **声明式事务管理：**这意味着你可以将业务代码和事务管理分离，你只需用注解和XML配置来管理事务。

### 六、BeanFactory和ApplicationContext

##### 一、ApplicationContext常用实现类

1. **ClassPathXmlApplicationContext**

   ​	加载**类路径**下的配置文件。

2. **FileSystemXmlApplicationContext**

   ​	加载**任意路径**下的配置文件

3. **AnnotationConfigApplicationContext**

   ​	读取**注解**创建容器    new AnnotationConfigApplicationContext(XXXconfig.class)将XXXconfig.class标注为一个Spring的配置类等同于加了注解@Configuration 的XXXconfig.class。

   也可以在一个  配置类上添加@Import（XXXconfig.class），XXXconfig.class也被标注成为配置类

   







##### 二、BeanFactory和ApplicationContext区别

​	**1、创建bean的时机：**

​		**BeanFactory：**采用延迟加载来创建Bean，当Bean被用到时才会创建。      项目启动时无法知道是否有Bean配置存在问题	   Bean为多例时比较好，用的时候才创建。

​		**ApplicationContext：**在容器启动时创建所有的Bean。				Bean过多时，容器启动缓慢					Bean为单例时比较好，因为创建一个。







​	BeanFactory和ApplicationContext是Spring的两大核心接口。都可以作为Spring的容器

1. **ApplicationContext实现了BeanFactory。**

2. BeanFactory是Spring最底层的接口。包含：各种Bean的定义、读取Bean配置文档、管理Bean的加载、实例化、控制Bean的生命周期、维护Bean之间的依赖关系。

3. ApplicationContext还实现了：支持国际化、统一的资源文件访问、同时加载多个配置文件、

4. **BeanFactory采用延迟加载来创建Bean，ApplicationContext是在容器启动时一次性创建所有Bean**，所以ApplicationContext会导致Bean过多时，容器启动慢

5. BeanFactory和ApplicationContext都支持BeanPostProcessor、BeanFactoryPostProcessor的使用。BeanFactory需要手动注册，而ApplicationContext则是自动注册

6. BeanFactory通常以编程的方式被创建，ApplicationContext还能以声明的方式创建，如使用ContextLoader

   ##### 

   

### 七、Spring的IOC和DI

##### 	1、IOC（Inversion of Control）：一种设计思想

​		**控制反转**。将对象交给spring容器去创建管理。

##### 	2、DI（Dependency Injection）

​		**依赖注入**。依赖spring容器把组件需要的资源注入。

##### 3、依赖注入

###### **1、依赖注入的方式**

1. **setter（）**：需要有默认的空构造方法

   ```java
   public class Car{
       public String setColor(String color){
           this.color = color;
       }
       public Car(){}
   }
   ```

   ```xml
   <bean id="" class="">
   	<property name="color" value="red"/><!-- 对应set方法 -->
   </bean>
   ```

2. **构造器**:可以保证在Bean实例化时就可以将值设置。

   即使用不到构造函数中的参数也必须在xml中赋值初始化

   **（1）按类型匹配入参**

   ```java
   //带参构造方法
   public Car(int maxSpeed,String brand, double price){
       this.maxSpeed=maxSpeed;
       this.brand=brand;
       this.price=price;
   }
   ```

   ```xml
   <bean id="car1" class="com.spring.model.Car">  
       <constructor-arg type="int" value="300"></constructor-arg>
       <constructor-arg type="java.lang.String" value="宝马"></constructor-arg>
       <constructor-arg type="double" value="300000.00"></constructor-arg>
   </bean>
   ```

   **（2）按索引匹配入参**

   ```xml
   <!-- 构造函数注入(按索引匹配) -->
   <bean id="car2" class="com.spring.model.Car"> 
       <!-- 注意索引从0开始 --> 
       <constructor-arg index="0" value="220"></constructor-arg>
       <constructor-arg index="1" value="宝马"></constructor-arg>
       <constructor-arg index="2" value="300000.00"></constructor-arg>
   </bean>
   ```

   **（3）联合使用类型和索引匹配入参**

   ```java
   public Car(String brand, String corp,double price){
       this.brand=brand;
       this.corp=corp;
       this.price=price;
   }
   
   public Car(String brand, String corp,int maxSpeed){
       this.brand=brand;
       this.corp=corp;
       this.maxSpeed=maxSpeed;
   }
   ```

   ```xml
   <!-- 构造函数注入(通过入参类型和位置索引确定对应关系) -->
   <!-- 对应public Car(String brand, String corp,int maxSpeed)构造函数 -->
   <bean id="car3" class="com.spring.model.Car">  
       <constructor-arg index="0" type="java.lang.String" value="奔驰"></constructor-arg>
       <constructor-arg index="1" type="java.lang.String" value="中国一汽"></constructor-arg>
       <constructor-arg index="2" type="int" value="200"></constructor-arg>
   </bean>
   ```

3. **注解**

   ### 当有多个相同类型的bean，可以**@Qualifier**去指定Bean的id

   开启注解功能：

   ```xml
   <context:annotation-config/>
   ```

   **使用：@Autowired或@Resource**

   ```java
   @Autowired
   private ICommonDao commonDao;
   
   @Resource
   private IXxxxDao xxxxDao;
   ```

   **注：@Autowired：根据对象类型去匹配**，如果一个都没有则报错。

   ​				如果有多个的话会根据Bean的Id为commonDao去匹配;

   ​	**@Resource：根据名称（bean的id）去匹配，没有找到名称则会根据类型去匹配**

###### 2、自动装配

​	**不能自动注入 基本数据类型，String字**

**autowire=" "**

```xml
<bean id="***" class="***" autowire="">
```

1. **不使用autowire设置，用默认值**

   ```xml
   <bean id="customer" class="com.lei.common.Customer">
       <property name="person" ref="person" />
   </bean>
   <bean id="person" class="com.lei.common.Person" />
   ```

2. **使用autowire=*"byName"*:根据属性名person找相同的bean**

   ```xml
   <bean id="customer" class="com.lei.common.Customer" autowire="byName" />
   <bean id="person" class="com.lei.common.Person" />
   ```

3. **使用autowire=*"byType"：根据class类型去找相同bean***

   ```xml
   <bean id="customer" class="com.lei.common.Customer" autowire="byType" />
   <bean id="person" class="com.lei.common.Person" />
   //当有多个Person的bean时会报错UnsatisfiedDependencyException
   <bean id="person2" class="com.lei.common.Person" />
   ```

4. **使用autowire="constructor"：根据构造方法的参数去匹配**，**构造方法中的根据byType去匹配public Customer(Person person)**

   ```xml
   <bean id="customer" class="com.lei.common.Customer" autowire="constructor" />
   <bean id="person" class="com.lei.common.Person" />
   ```

5. **使用autowire="autodetect"**根据情况自动去匹配

6. **default：由上级标签<beans>的default-autowire属性确定**

###### 3、注入集合

```xml
<bean>
	<property>
    	<list></list>
        <map></map>
        <set></set>
    </property>
</bean>
```



### 八、Bean的作用域

##### 	1、**singleton** 

​		单例

​		bean在每个Spring ioc 容器中只有一个实例。

​		在高并发下，如果bean中有全局变量，会存再线程安全问题。用ThreadLocal去解决

##### 	2、**prototype**

​		多例

​		一个bean的定义可以有多个实例

##### 	3、**request**

​		作用于web应用的请求范围

​		每一次http请求都会创建一个bean，仅基于web

##### 	4、**session**

​		作用于web应用的会话范围

​		在一个HTTP Session中，一个bean定义对应一个实例

​		该作用域仅在基于web的Spring **ApplicationContext**情形下有效。

##### 	5、**global-session**

​		作用于集群环境的会话范围（全局会话范围），当不是集群环境时，就是session

​		在一个全局的HTTP Session中，一个bean定义对应一个实例。

​		该作用域仅在基于web的Spring **ApplicationContext**情形下有效

### 九、Spring如何集成Hibernate

​	**用Spring的 SessionFactory 调用 LocalSessionFactory**

1. 配置 Hibernate SessionFactory
2. 写一个Dao继承HibernateDaoSupport（还可以在Dao中使用Hibernate Template）
3. 在AOP支持的事务中配置



### 十、Spring事务

##### 	一、Spring支持的两种事务管理

###### 		1、编程式事务管理：使用TransactionTemplate实现

```xml
        <bean id="transactionManager"
              class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <property name="dataSource" ref="dataSource"/>
        </bean>

        <bean id="transactionTemplate"
              class="org.springframework.transaction.support.TransactionTemplate">
            <property name="transactionManager">
                <ref bean="transactionManager"/>
            </property>
        </bean>
```

```java
        @Autowired
        private TransactionTemplate transactionTemplate;
        PayOrder order = (PayOrder) this.transactionTemplate  
                     .execute(new TransactionCallback() {  
                         @Override  
                         public Object doInTransaction(TransactionStatus status) {  
                             // 查看是否已经存在支付订单，如果已经存在则返回订单主键  
                             PayOrder payOrderTemp = payOrderDAO.findOrder(String  
                                     .valueOf(payOrder.getPayOrderId()));  

                             // 由支付渠道类型(PayChannelType)转换得到交易类型(PayType)  
                             if (payOrder.getPayChannelId().equalsIgnoreCase(PAY_CHNL_ACT_BAL)) {// 账户余额支付  
                                 payOrder.setPayType("3");  
                             } else if (payOrder.getPayChannelId().equalsIgnoreCase(PAY_CHNL_FAST_PAY)) {// 联通快捷支付  
                                 payOrder.setPayType("4");  
                             } else {// 网银网关支付  
                                 payOrder.setPayType("2");  
                             }  

                             // 比对新的支付金额与原订单金额是否一致，如不一致则提示错误  
                             if (payOrderTemp == null) {  
                                 String orderId = payOrderDAO.save(payOrder);  
                                 payOrder.setPayOrderId(orderId);  
                                 return payOrder;  
                             } else {  
                                 return payOrderTemp;  
                             }  
                         }  
                     });  
```



###### 		2、声明式事务管理：通过配置文件中配置tx:advice/      aop:config/ 或者使用@Transactional注解

```xml
		<tx:annotation-driven transaction-manager="transactionManager"/>        
		<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
            <property name="dataSource" ref="dataSource" />  
            <property name="configLocation">  
                <value>classpath:mybatis-config.xml</value>  
            </property>  
        </bean> 

        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
            <property name="dataSource" ref="dataSource" />  
        </bean>         
        <tx:advice id="advice" transaction-manager="transactionManager">  
            <tx:attributes>  
                <tx:method name="update*" propagation="REQUIRED" read-only="false" rollback-for="java.lang.Exception"/>  
                <tx:method name="insert" propagation="REQUIRED" read-only="false"/>  
            </tx:attributes>  
        </tx:advice>  

        <aop:config>  
            <aop:pointcut id="testService" expression="execution (* com.nnngu.service.MyBatisService.*(..))"/>  
            <aop:advisor advice-ref="advice" pointcut-ref="testService"/>  
        </aop:config>  
```



##### 	二、事务传播行为

1. **REQUIRED：**<u>默认值。如果当前存在事务，则加入事务，如果不存在则新建事务</u>。
2. **REQUIRED_NEW：**<u>如果当前存在事务，把当前事务挂起，另起一个新的事务。无则新建事务</u>
3. **SUPPORTS：**<u>如果当前存在事务，则加入事务；若不存在则以非事务方式执行。</u>
4. **NOT_SUPPORTED:**<u>以非事务方式运行，若当前存在事务，将当前事务挂起。</u>
5. **NEVER：**<u>以非事务方式运行，若当前存在事务则抛异常</u>
6. **MANDATORY：**<u>必须有事务。如果当前存在事务，则加入事务。没有事务则抛异常</u>
7. **NESTED：**<u>如果当前存在事务，创建一个新的事务作为当前事务的嵌套事务运行。**没有事务则以REQUIRED方式运行**</u>



##### 	三、事务隔离级别

​	**脏读：**           ------------事务A读取了 事务B尚未提交的数据。

​	**不可重复读：**------------（数据被修改）事务A在执行过程中多次查询结果不一致。并发事务中：在事务A查询过程中，数据被其他并行事务修改了。        **重点是update**

​	**幻读：**           ------------（数据被新增或删除）事务A执行过程中多次查询结果不一致。                                                                                                        **重点是insert和delete**

 									并发事务中：在事务A查询过程中，其他事务对该表insert或delete操作。



1. **DEFAULT：**<u>使用底层数据库的默认隔离级别。大部分数据是：READ_COMMITTED</u>

2. **READ_COMMITTED：**<u>表示一个事务只能读取另一个事务已提交的数据。</u>                                                             **防止脏读。但可能有不可重复读和幻读**

3. **READ_UNCOMMITTED:**<u>表示一个事务可以读取另一个事务已修改但未提交的数据。</u>                                            **产生脏读，不可重复读和幻读**

4. **REPEATABLE_READ（可重复读）：**<u>一个事务在整个过程中可以重复执行某个查询，且每次结果相同。</u>            **防止不可重复读和脏读。有可能幻读**

   ​           						  只能读取提交的数据                                					            

5. **SERIALIZABLE：**<u>所有事务挨个顺序执行，互不影响。</u>级别最高，代价最高                                                          **防止脏读、不可重复读、幻读**

   

### 十一、Spring的AOP

##### 	1、什么是AOP

​	AOP，面向切面编程，通过**预编译方式和运行期动态代理**实现的一种技术。

​	优点：

- 减少重复代码
- 提高开发效率
- 维护方便

​		作为面向对象的一种补充，用于将那些与业务无关，但却对多个对象产生影响的**公共行为和逻辑，抽取并封装为一个可重用的模块**，

​		**这个模块被命名为“切面”（Aspect）**，减少系统中的重复代码，降低了模块间的耦合度，同时提高了系统的可维护性。可用于权限认证、日志、事务处理



##### 	2、AOP的实现

​		AOP的实现关键在于代理模式。AOP分为静态代理和动态代理。AspectJ为静态代理的代表。Spring AOP为动态代理：JDK动态代理和CGLIB动态代理

​		**JDK动态代理：**只提供接口的代理。核心InvocationHandler接口和Proxy类。代理类通过invoke()方法来调用目标类的代码。

​		**CGLIB动态代理：**通过继承的方式做的动态代理。如果某各类被标记为final，是无法实现CGLIB动态代理的。final修饰的方法无法被代理。



### 十二、Spring Bean的生命周期

​	1、单例对象生命周期：当容器启动时，创建bean对象，当容器销毁时，bean对象消亡

​	2、多例对象生命周期：容器启动后，当使用bean对象时，创建bean对象，当对象长时间不用且没有对象引用，有GC回收









### 十三、Spring中的设计模式

1. ​	工厂模式：BeanFactory就是简单工厂模式的体现，用来创建对象的实例；

2. ​	单例模式：Bean默认为单例模式。

3. ​	代理模式：Spring的AOP功能用到了JDK的动态代理和CGLIB字节码生成技术；

4. ​	模板方法：用来解决代码重复的问题。比如. RestTemplate, JmsTemplate, JpaTemplate

5. ​	观察者模式：定义对象键一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都会得到通知被制动更新，

   ​                              如Spring中listener的实现--ApplicationListener。





### 十四、创建Bean的三种方式

##### 	一、默认使用构造函数创建

```xml
		<!-- 当HelloServiceImpl类中没有默认的空构造方法，初始化创建Bean时就会报错 -->
		<bean id="helloService" class="com.zzs.service.impl.HelloServiceImpl" />
```

##### 	二、工厂的普通方法创建

```java
        public class MyFactory{
            public IHelloService getHelloService(){
                return new HelloServiceImpol();
            }
        }
```

```xml
		<bean id="myFactory" class="com.zzs.MyFactory" />	
		<bean id="helloService" factory-bean="myFactory" factory-method="getHelloService"/>
```

##### 	三、工厂的静态方法创建

```java
    	public class MyFactory{
            public static IHelloService getHelloService(){
                return new HelloServiceImpol();
            }
        }
```
```xml
		<!-- 这种方式，方法必须是static的 -->
		<bean id="helloService" class="com.zzs.MyFactory" factory-method="getHelloService/>	
```



https://blog.csdn.net/a745233700/article/details/80959716  spring面试

https://cloud.tencent.com/developer/article/1185885   动态代理





















































