package jvm;

import org.cleverframe.common.utils.DataSizeUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-13 18:58 <br/>
 */
public class JvmTest {
    private final static Logger logger = LoggerFactory.getLogger(JvmTest.class);

    private static long time;

    /**
     * 所有测试开始之前运行
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        time = System.currentTimeMillis();
    }

    /**
     * 所有测试结束之后运行
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.info("测试用时：" + (System.currentTimeMillis() - time));
    }

    /**
     * 每一个测试方法之前运行
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * 每一个测试方法之后运行
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String name : propertyNames) {
            logger.info(name + " - " + properties.getProperty(name));
        }
    }

    @Test
    public void test2() {
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memorymbean.getHeapMemoryUsage();
        System.out.println("INIT HEAP: " + DataSizeUtils.getHumanReadableSize(usage.getInit()));
        System.out.println("MAX HEAP: " + DataSizeUtils.getHumanReadableSize(usage.getMax()));
        System.out.println("USE HEAP: " + DataSizeUtils.getHumanReadableSize(usage.getUsed()));
        System.out.println("\nFull Information:");
        System.out.println("Heap Memory Usage: " + memorymbean.getHeapMemoryUsage());
        System.out.println("Non-Heap Memory Usage: " + memorymbean.getNonHeapMemoryUsage());

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println("===================java options=============== ");
        System.out.println(inputArguments);


        System.out.println("=======================通过java来获取相关系统状态============================ ");
        System.out.println("总的内存量 " + DataSizeUtils.getHumanReadableSize(Runtime.getRuntime().totalMemory()));
        System.out.println("空闲内存量 " + DataSizeUtils.getHumanReadableSize(Runtime.getRuntime().totalMemory()));
        System.out.println("最大内存量 " + DataSizeUtils.getHumanReadableSize(Runtime.getRuntime().maxMemory()));

        System.out.println("=======================OperatingSystemMXBean============================ ");
        OperatingSystemMXBean osm = ManagementFactory.getOperatingSystemMXBean();
//    System.out.println(osm.getFreeSwapSpaceSize()/1024);
//    System.out.println(osm.getFreePhysicalMemorySize()/1024);
//    System.out.println(osm.getTotalPhysicalMemorySize()/1024);
        //获取操作系统相关信息
        System.out.println("osm.getArch() " + osm.getArch());
        System.out.println("osm.getAvailableProcessors() " + osm.getAvailableProcessors());
        //System.out.println("osm.getCommittedVirtualMemorySize() "+osm.getCommittedVirtualMemorySize());
        System.out.println("osm.getName() " + osm.getName());
        //System.out.println("osm.getProcessCpuTime() "+osm.getProcessCpuTime());
        System.out.println("osm.getVersion() " + osm.getVersion());
        //获取整个虚拟机内存使用情况
        System.out.println("=======================MemoryMXBean============================ ");
        MemoryMXBean mm = ManagementFactory.getMemoryMXBean();
        System.out.println("getHeapMemoryUsage " + mm.getHeapMemoryUsage());
        System.out.println("getNonHeapMemoryUsage " + mm.getNonHeapMemoryUsage());
        //获取各个线程的各种状态，CPU 占用情况，以及整个系统中的线程状况
        System.out.println("=======================ThreadMXBean============================ ");
        ThreadMXBean tm = ManagementFactory.getThreadMXBean();
        System.out.println("getThreadCount " + tm.getThreadCount());
        System.out.println("getPeakThreadCount " + tm.getPeakThreadCount());
        System.out.println("getCurrentThreadCpuTime " + tm.getCurrentThreadCpuTime());
        System.out.println("getDaemonThreadCount " + tm.getDaemonThreadCount());
        System.out.println("getCurrentThreadUserTime " + tm.getCurrentThreadUserTime());

        //当前编译器情况
        System.out.println("=======================CompilationMXBean============================ ");
        CompilationMXBean gm = ManagementFactory.getCompilationMXBean();
        System.out.println("getName " + gm.getName());
        System.out.println("getTotalCompilationTime " + gm.getTotalCompilationTime());

        //获取多个内存池的使用情况
        System.out.println("=======================MemoryPoolMXBean============================ ");
        List<MemoryPoolMXBean> mpmList = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mpm : mpmList) {
            System.out.println("getUsage " + mpm.getUsage());
            System.out.println("getMemoryManagerNames " + Arrays.toString(mpm.getMemoryManagerNames()));
        }
        //获取GC的次数以及花费时间之类的信息
        System.out.println("=======================MemoryPoolMXBean============================ ");
        List<GarbageCollectorMXBean> gcmList = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcm : gcmList) {
            System.out.println("getName " + gcm.getName());
            System.out.println("getMemoryPoolNames " + Arrays.toString(gcm.getMemoryPoolNames()));
        }
        //获取运行时信息
        System.out.println("=======================RuntimeMXBean============================ ");
        RuntimeMXBean rmb = ManagementFactory.getRuntimeMXBean();
        System.out.println("getClassPath " + rmb.getClassPath());
        System.out.println("getLibraryPath " + rmb.getLibraryPath());
        System.out.println("getVmVersion " + rmb.getVmVersion());
    }
}
