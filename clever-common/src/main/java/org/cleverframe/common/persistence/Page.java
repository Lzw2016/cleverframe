package org.cleverframe.common.persistence;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.configuration.BaseConfigNames;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据分页类<br/>
 * 1.存储当前页的数据信息<br/>
 * 2.存储分页信息，如：当前页码数、总页数、总数据数等<br/>
 * 分页信息如下：<br/>
 * 1.每页的数据量：pageSize<br/>
 * 2.当前页面的页码数：pageNo<br/>
 * 3.查询到的数据总数：count<br/>
 * 4.是否是首页：firstPage<br/>
 * 5.是否是尾页：lastPage<br/>
 * 6.总页数：pageCount<br/>
 * 7.当前页第一条数据的位置：firstResult<br/>
 * 8.当前页的数据：list<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-11 23:37 <br/>
 */
public class Page<T> {

    /**
     * 系统配置操作类
     */
    @Autowired
    @Qualifier(SpringBeanNames.Config)
    private IConfig config;

    /**
     * 当前页面的页码数的参数名，Cookie属性名或request请求参数名
     */
    public final static String PAGE_NO = "page";
    /**
     * 每页的数据量的参数名，Cookie属性名或request请求参数名
     */
    public final static String PAGE_SIZE = "rows";
    /**
     * request请求参数名，用于设置让浏览器到Cookie中读取“当前页面的页码数”和“每页的数据量”，起到刷新当前页面的效果
     */
    public final static String RE_PAGE = "repage";

    /**
     * 每页的数据量，需要使用setter赋值（最大500），小于等于0 表示不进行分页(分页无效)
     */
    private int pageSize = Integer.valueOf(config.getConfig(BaseConfigNames.PAGE_PAGESIZE));
    /**
     * 当前页面的页码数  pageNo >= 1
     */
    private int pageNo = 1;
    /**
     * 查询到的数据总数，小于0 表示不查询总数
     */
    private long count = 0;
    /**
     * 当前页的数据
     */
    private List<T> list = new ArrayList<>();

    // ------------------------------------------------------------------------
    // 通过计算出来的属性值
    // ------------------------------------------------------------------------
    /**
     * 是否是第一页
     */
    private boolean firstPage = false;
    /**
     * 是否是最后一页
     */
    private boolean lastPage = false;
    /**
     * 总页数 [ pageCount = this.count / getPageSize() + 1 ] >= 1
     */
    private int pageCount = 1;
    /**
     * 当前页的第一条数据的位置 [ firstResult = (pageNo - 1) * pageSize ] >= 0
     */
    private int firstResult = 0;

    /**
     * 默认的构造<br/>
     * 1.pageNo 当前页面的页码： 默认为1<br/>
     * 2.pageSize 每页的数据量：默认为全局的配置值<br/>
     */
    public Page() {

    }

    /**
     * 手动设置值
     *
     * @param pageNo   当前页面的页码数
     * @param pageSize 每页的数据量
     * @param count    查询到的数据总数
     * @param list     当前页的数据
     */
    public Page(int pageNo, int pageSize, long count, List<T> list) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.setCount(count);
        this.setList(list);
    }

    /**
     * 手动设置值，默认：list = new ArrayList<T>();
     *
     * @param pageNo   当前页面的页码数
     * @param pageSize 每页的数据量
     * @param count    查询到的数据总数
     */
    public Page(int pageNo, int pageSize, long count) {
        this(pageNo, pageSize, count, new ArrayList<T>());
    }

    /**
     * 手动设置值，默认：list = new ArrayList<T>();count = 0
     *
     * @param pageNo   当前页面的页码数
     * @param pageSize 每页的数据量
     */
    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, 0, new ArrayList<T>());
    }

    /**
     * 构造函数，从request和Cookie中获取分页信息<br/>
     * 1.request对象会传递参数{@link #PAGE_NO}（当前页码数）和参数 {@link #PAGE_SIZE}（每页的数据量）或参数{@link #RE_PAGE}（设置刷新当前页面）<br/>
     * 2.当request对象传递参数{@link #RE_PAGE}时，从Cookie中读取分页信息刷新当前页面<br/>
     * 3.当request对象未传递参数{@link #RE_PAGE}时，从参数{@link #PAGE_NO}（当前页码数）和参数 {@link #PAGE_SIZE}（每页的数据量）中读取分页信息<br/>
     * 4.若defaultPageSize参数大于0时，使用defaultPageSize作为“每页的数据量”
     */
    public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize) {
        String no = request.getParameter(Page.PAGE_NO);//当前页码数
        String size = request.getParameter(Page.PAGE_SIZE);//每页的数据量
        String rePage = request.getParameter(Page.RE_PAGE);//设置刷新当前页面

        // 为请求参数设置默认值
        if (StringUtils.isBlank(no)) {
            no = this.getPageNo() + "";
        }
        if (StringUtils.isBlank(size)) {
            size = this.getPageSize() + "";
        }

        // 是否刷新页面
        if ("true".equals(rePage)) {
            no = CookieUtils.getCookie(request, Page.PAGE_NO);
            if (StringUtils.isNumeric(no)) {
                this.setPageNo(Integer.parseInt(no));
            }
            size = CookieUtils.getCookie(request, Page.PAGE_SIZE);
            if (StringUtils.isNumeric(size)) {
                this.setPageSize(Integer.parseInt(size));
            }
        } else {
            CookieUtils.setCookie(response, Page.PAGE_NO, no);
            this.setPageNo(Integer.parseInt(no));
            CookieUtils.setCookie(response, Page.PAGE_SIZE, size);
            this.setPageSize(Integer.parseInt(size));
        }

        if (defaultPageSize > 0) {
            CookieUtils.setCookie(response, Page.PAGE_SIZE, defaultPageSize + "");
            this.setPageSize(defaultPageSize);
        }
    }

    /**
     * 构造函数，从request和Cookie中获取分页信息<br/>
     * 1.request对象会传递参数{@link #PAGE_NO}（当前页码数）和参数 {@link #PAGE_SIZE}（每页的数据量）或参数{@link #RE_PAGE}（设置刷新当前页面）<br/>
     * 2.当request对象传递参数{@link #RE_PAGE}时，从Cookie中读取分页信息刷新当前页面<br/>
     * 3.当request对象未传递参数{@link #RE_PAGE}时，从参数{@link #PAGE_NO}（当前页码数）和参数 {@link #PAGE_SIZE}（每页的数据量）中读取分页信息<br/>
     */
    public Page(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, -2);
    }

    /**
     * 初始化计算出的参数,调用 setList(List<T> list)时会自动调用该方法
     */
    public void init() {
        this.firstPage = this.getPageNo() <= 1;
        this.lastPage = this.getPageNo() >= this.getPageCount();
        this.pageCount = (int) (this.getCount() / this.getPageSize() + 1);
        if (this.pageCount <= 1) {
            this.pageCount = 1;
        }
        this.firstResult = (this.getPageNo() - 1) * this.getPageSize();
        if (this.firstResult < 0) {
            this.firstResult = 0;
        }
    }

    //------------------------------------------------------------------------


    /**
     * 分页是否有效
     *
     * @return this.pageSize <= 0
     */
    public boolean isDisabled() {
        return this.pageSize <= 0;
    }

    /**
     * 分页前是否进行总数统计
     *
     * @return this.count < 0
     */
    public boolean isNotCount() {
        return this.count < 0;
    }

    /**
     * 分页页面大小，需要使用setter赋值（最大500），小于等于0 表示不进行分页(分页无效)
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 分页页面大小，需要使用setter赋值（最大500），小于等于0 表示不进行分页(分页无效)
     */
    private void setPageSize(int pageSize) {
        if (pageSize > 500) {
            this.pageSize = 500;
        } else {
            this.pageSize = pageSize;
        }
    }

    /**
     * 页码数
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 页码数 pageNo >= 1
     */
    private void setPageNo(int pageNo) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    /**
     * 总记录数，设置为"-1"表示不查询总数
     */
    public long getCount() {
        return count;
    }

    /**
     * 总记录数，设置为"-1"表示不查询总数
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 封装查询到的数据
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 封装查询到的数据
     */
    public void setList(List<T> list) {
        this.init();
        this.list = list;
    }

    /**
     * 是否是第一页
     */
    public boolean isFirstPage() {
        this.firstPage = this.getPageNo() <= 1;
        return firstPage;
    }

    /**
     * 是否是最后一页
     */
    public boolean isLastPage() {
        this.lastPage = this.getPageNo() >= this.getPageCount();
        return lastPage;
    }

    /**
     * 总页数
     */
    public int getPageCount() {
        this.pageCount = (int) (this.getCount() / this.getPageSize() + 1);
        if (this.pageCount <= 1) {
            this.pageCount = 1;
        }
        return pageCount;
    }

    /**
     * 当前页的第一条数据的位置
     */
    public int getFirstResult() {
        this.firstResult = (this.getPageNo() - 1) * this.getPageSize();
        if (this.firstResult < 0) {
            this.firstResult = 0;
        }
        return firstResult;
    }
}
