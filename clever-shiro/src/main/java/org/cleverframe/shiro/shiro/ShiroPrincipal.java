//package org.cleverframe.shiro.shiro;
//
//import java.io.Serializable;
//
//import org.cleverframe.modules.sys.entity.Organization;
//import org.cleverframe.modules.sys.entity.User;
//
///**
// * 用户授权信息，使得Subject除了携带用户的登录名外还可以携带更多信息<br>
// * 1.调用SecurityUtils.getSubject().getPrincipal()得到此类对象<br>
// *
// * @author LiZhiWei
// * @version 2015年6月18日 下午8:46:31
// */
//public class ShiroPrincipal implements Serializable
//{
//    private static final long serialVersionUID = 1L;
//
//    /** 当前登录用户 */
//    private User user;
//
//    /** 当前公司 */
//    private Organization homeCompany;
//
//    /** 当前机构 */
//    private Organization homeOrg;
//
//    /**
//     * @param homeCompany 所属公司
//     * @param homeOrg 所属机构
//     * @param user 登入用户
//     */
//    public ShiroPrincipal(Organization homeCompany, Organization homeOrg, User user)
//    {
//        this.homeCompany = homeCompany;
//        this.homeOrg = homeOrg;
//        this.user = user;
//    }
//
//    /** 得到用户登录名称 */
//    public String getLoginName()
//    {
//        return user.getLoginName();
//    }
//
//    /**
//     * 更新当前登录用户信息
//     *
//     * @param user 用户信息
//     * @return 更新成功返回true，若传入的user的loginName与当前不一致则更新失败返回false
//     * */
//    public boolean updateUserInfo(User user)
//    {
//        if (getLoginName().equals(user.getLoginName()))
//        {
//            this.user = user;
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 重载equals,只计算loginName;
//     */
//    @Override
//    public boolean equals(Object obj)
//    {
//        if (this == obj)
//        {
//            return true;
//        }
//        if (obj == null)
//        {
//            return false;
//        }
//        if (getClass() != obj.getClass())
//        {
//            return false;
//        }
//        ShiroPrincipal other = (ShiroPrincipal) obj;
//        if (getLoginName() == null)
//        {
//            if (other.getLoginName() != null)
//            {
//                return false;
//            }
//        }
//        else if (!getLoginName().equals(other.getLoginName()))
//        {
//            return false;
//        }
//        return true;
//    }
//
//    /*--------------------------------------------------------------
//     *          getter
//     * -------------------------------------------------------------*/
//    /** 得到登录用户信息 */
//    public User getUser()
//    {
//        return user;
//    }
//
//    public Organization getHomeCompany()
//    {
//        return homeCompany;
//    }
//
//    public Organization getHomeOrg()
//    {
//        return homeOrg;
//    }
//}
