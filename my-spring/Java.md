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

- ##### 3、防止死锁:,,- 破坏四个必要条件,- 线程的需求资源数小于等于总可用资源数,- 判断 系统安全状态 法。其实就是判断线程的需求资源是否大于目前可用资源





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
        	//第二个参数要实现和目标对象一样的接口，所以只需要拿到目标对象的实现接口  
        	//第三个参数表明这些被拦截的方法在被拦截时需要执行哪个InvocationHandler的invoke方法 
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

> ### 三、Spring的Bean单例与线程安全问题
>
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

### 五、Spring支持的事务管理类型

- **编程式事务管理**：这意味你通过编程的方式管理事务，给你带来极大的灵活性，但是难维护。**代码手动实现 事务操作(提交，回滚)**
- **声明式事务管理：**这意味着你可以将业务代码和事务管理分离，你只需用注解和XML配置来管理事务。

### 六、BeanFactory和ApplicationContext区别

​	BeanFactory和ApplicationContext是Spring的两大核心接口。都可以作为Spring的容器

1. **ApplicationContext实现了BeanFactory。**
2. BeanFactory是Spring最底层的接口。包含：各种Bean的定义、读取Bean配置文档、管理Bean的加载、实例化、控制Bean的生命周期、维护Bean之间的依赖关系。
3. ApplicationContext还实现了：支持国际化、统一的资源文件访问、同时加载多个配置文件、
4. **BeanFactory采用延迟加载来创建Bean，ApplicationContext是在容器启动时一次性创建所有Bean**，所以ApplicationContext会导致Bean过多时，容器启动慢
5. BeanFactory和ApplicationContext都支持BeanPostProcessor、BeanFactoryPostProcessor的使用。BeanFactory需要手动注册，而ApplicationContext则是自动注册
6. BeanFactory通常以编程的方式被创建，ApplicationContext还能以声明的方式创建，如使用ContextLoader



















https://blog.csdn.net/a745233700/article/details/80959716  spring面试

https://cloud.tencent.com/developer/article/1185885   动态代理





















































