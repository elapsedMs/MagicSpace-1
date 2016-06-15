package storm.commonlib.common.http;

/**
 * 异常类型
 */

public enum ExceptionTypes {

    UnknownException(0, "未知异常"),

    VerifyCodeInvalid(2000, "验证码错误"),
    VerifyCodeNeedColdToResent(2001, "需要等待冷却时间来重新发送验证码"),
    VerifyCodeExpired(2002, "验证码已失效"),
    InvalidMobileFormat(2003, "手机号码格式错误"),
    MobileAlreadyRegistered(2004, "手机号已注册"),
    InvalidEmailFormat(2005, "邮箱格式不正确"),
    EmailAlreadyRegistered(2006, "邮箱已注册"),
    InvalidInviteCode(2007, "邀请码无效"),
    MobileIsNotPreMarked(2008, "手机号码未标记"),
    AccountIsNotActed(2009, "账号未激活"),
    EmailIsNotAllowed(2010, "不是医学院或医院邮箱"),

    /**
     * WebService请求失败
     */
    WebServiceFail(40000, "WebService请求失败"),
    /**
     * 网络请求失败
     */
    NetworkFail(40001, "网络请求失败"),
    /**
     * 超出限量
     */
    OverBalance(40101, "超出限量"),
    /**
     * 名称已经存在
     */
    HaveSameName(50000, "名称已经存在"),
    /**
     * 审核记录不是草稿状态
     */
    AuditNotDraft(50100, "审核记录不是草稿状态"),
    /**
     * 该内容已经在审核中
     */
    AlreadyHaveAudit(50101, "该内容已经在审核中"),
    /**
     * 该医生已经关联了其他账号
     */
    AlreadyLinkDoctor(50501, "该医生已经关联了其他账号"),
    /**
     * 客户端认证失败
     */
    ClientAuthenticationFails(60001, "客户端认证失败"),
    /**
     * 第三方认证失败
     */
    PlatformAuthenticationFails(60002, "第三方认证失败"),
    /**
     * 第三方类型错误
     */
    PlatformTypeError(60003, "未知的第三方平台类型"),
    /**
     * 访问令牌与当前用户不符
     */
    AccessTokenNotForCurrUser(60010, "访问令牌与当前用户不符"),
    /**
     * 无访问令牌
     */
    NoAccessToken(60011, "无访问令牌"),
    /**
     * 访问令牌没有对应的用户
     */
    NoUserForAccessToken(60012, "访问令牌没有对应的用户"),
    /**
     * 访问令牌无访问权限
     */
    NoRoleForAccessToken(60013, "访问令牌无访问权限"),
    /**
     * 无访问权限
     */
    DisallowAccess(60014, "无访问权限"),
    /**
     * 不允许修改认证医生信息
     */
    DisallowModifyDoctorInfo(60015, "不允许修改认证医生信息"),
    /**
     * 密码错误
     */
    PasswordError(60016, "密码错误"),
    /**
     * 账户已被禁用
     */
    UserDisabled(60017, "账户已被禁用"),
    /**
     * 未找到注册的用户
     */
    NoUser(60021, "未找到注册的用户"),
    /**
     * 未找到分组信息
     */
    NoGroup(60022, "未找到分组信息"),
    /**
     * 未找到患者电话
     */
    NoUserPhone(60023, "未找到患者电话"),
    /**
     * 未找到医生电话
     */
    NoDoctorPhone(60024, "未找到医生电话"),
    /**
     * 医生已经被激活
     */
    DoctorIsActivated(60023, "医生已经被激活"),
    /**
     * 手机号被多个账户注册
     */
    ManyMobileUser(60031, "手机号被多个账户注册"),
    /**
     * 存在多个注册账户
     */
    ManyUser(60032, "存在多个注册账户"),
    /**
     * 用户名已注册
     */
    UserNameRegisted(60050, "用户名已注册"),
    /**
     * 注册失败
     */
    RegisterFailed(60051, "注册失败"),
    /**
     * 激活医生失败
     */
    ActivateDoctorFailed(60052, "激活医生失败"),
    /**
     * 激活手机失败
     */
    ActivateMobileFailed(60053, "激活手机失败"),
    /**
     * 激活的医生与当前账户不符
     */
    ActivateDoctorNotCurrAccount(60054, "激活的医生与当前账户不符"),
    /**
     * 用户ID与用户名不符
     */
    UserIDIsNotThisUserName(60060, "用户ID与用户名不符"),
    /**
     * 验证码无效
     */
    InvalidVerificationCode(80001, "验证码无效"),
    /**
     * 手机已被注册
     */
    ActivitedMobile(80002, "手机已被注册"),
    /**
     * 文件上传失败
     */
    FileUploadFail(80003, "文件上传失败"),
    /**
     * 不允许修改问题状态
     */
    DisallowModifyInquiryState(80101, "修改问题状态被拒绝"),
    /**
     * 已经评价，无法修改结论
     */
    DisallowModifyConclusions(80102, "已经评价，无法修改结论"),
    /**
     * 已经评价
     */
    HaveRate(80103, "已经评价"),
    /**
     * 已经存在咨询预约
     */
    HaveSchedule(80104, "无法重复创建咨询预约时间"),
    /**
     * 未找到回复信息
     */
    NoReply(80111, "未找到指定的资料"),
    /**
     * 未找到回复信息
     */
    DisallowDeleteReply(80112, "删除资料时拒绝，没有权限"),
    /**
     * 未查询到咨询会话
     */
    NoInquiry(80200, "未查询到咨询会话"),
    /**
     * 未找到要购买的服务包
     */
    NoServicePack(80201, "未找到要购买的服务包"),
    /**
     * 扣款账户为空
     */
    DebitAccountIsNull(80202, "未找到扣款账户"),
    /**
     * 扣款账户与咨询会话用户不符
     */
    DebitAccountNotMatch(80203, "扣款账户与咨询会话用户不符"),
    /**
     * 未找到充值订单
     */
    NoPrepaidOrder(80204, "未找到充值订单"),
    /**
     * 账户余额不足
     */
    NotEnoughBalance(80205, "账户余额不足"),
    /**
     * 该咨询已经支付成功
     */
    AlreadyPaidedInquiryOrder(80206, "该咨询已经支付成功"),
    /**
     * 仅咨询中的问题允许转诊
     */
    OnlyPaidedCanReferral(80207, "仅咨询中的问题允许转诊"),
    /**
     * 转诊医生与当前咨询选择的服务包不符
     */
    NoSameServicePack(80208, "转诊医生与当前咨询选择的服务包不符"),
    /**
     * 未付费
     */
    Unpaid(80209, "未付费"),
    /**
     * 没有此咨询的操作权限
     */
    DisallowperateInquiry(80210, "没有此咨询的操作权限"),
    /**
     * 退还费用失败，请联系客服人员
     */
    RefundAmountFail(80211, "退还费用失败，请联系客服人员"),
    /**
     * 扣款失败
     */
    PayError(80212, "扣款失败"),
    /**
     * 账户被锁定
     */
    BalanceLocked(80213, "账户被锁定，请联系客服人员"),
    /**
     * 该活动已结束
     */
    ActivityIsEnd(80250, "该活动已结束"),
    /**
     * 未找到选择的礼券
     */
    NoCouponPack(80251, "未找到选择的礼券"),
    /**
     * 礼券未领取
     */
    CouponNotReceived(80252, "礼券未领取"),
    /**
     * 没有使用礼券的权限
     */
    DisallowUseCoupon(80253, "没有使用礼券的权限"),
    /**
     * 礼券已被使用过
     */
    CouponIsUsed(80254, "礼券已被使用过"),
    /**
     * 礼券已过期
     */
    CouponIsExpired(80255, "礼券已过期"),
    /**
     * 已经领取过礼券，不能重复领取
     */
    ReceivedCoupon(80256, "不能重复领取礼券"),
    /**
     * 该礼券不能被用于当前服务项目
     */
    CouponNotApplicable(80257, "该礼券不能被用于当前服务项目"),
    /**
     * 领取礼券发生错误
     */
    ReceiveCouponError(80258, "领取礼券发生错误"),
    /**
     * 未找到礼券图片
     */
    NoCouponImage(80261, "未找到礼券图片"),
    /**
     * 已经存在该礼券类型的图片
     */
    AlreadyHaveCouponImage(80262, "已经存在该礼券类型的图片"),
    /**
     * 未找到要激活的卡片
     */
    NoCard(80500, "未找到要激活的卡片"),
    /**
     * 卡片被冻结
     */
    CardFreeze(80501, "卡片被冻结"),
    /**
     * 卡片已被激活
     */
    CardActivated(80502, "卡片已被激活"),
    /**
     * 卡片已过期
     */
    CardExpired(80503, "卡片已过期"),
    /**
     * 卡片已作废
     */
    CardObsolete(80504, "卡片已作废"),
    /**
     * 未找到指定的私人会所卡
     */
    NoDoctorClubCard(80505, "未找到指定的私人会所卡"),
    /**
     * 未找到可支付的私人会所卡
     */
    NoDoctorClubCardForPaid(80506, "未找到可支付的私人会所卡"),
    /**
     * 没有找到圈子
     */
    NoCircle(90000, "未找到圈子"),
    /**
     * 没有找到话题
     */
    NoCollection(90001, "未找到话题"),
    /**
     * 圈子账号为空
     */
    CircleIDIsNull(90002, "圈子账号为空"),
    /**
     * 话题账户为空
     */
    CollectionIDIsNull(90003, "话题账户为空"),
    /**
     * 人员账号为空
     */
    UserIDIsNull(90004, "人员账号为空"),
    /**
     * 未找到父帖
     */
    NoParentPost(90005, "未找到父帖"),
    /**
     * 未找到帖子
     */
    NoPost(90006, "未找到帖子"),
    /**
     * 帖子ID为空
     */
    PostIDIsNull(90007, "未找到帖子"),
    /**
     * 没有创建帖子的权限
     */
    DisallowCreatePost(90010, "没有创建帖子的权限"),
    /**
     * 没有加入该圈子的权限
     */
    DisallowJoinCircle(90011, "没有加入该圈子的权限"),
    /**
     * 没有关注该圈子的权限
     */
    DisallowFocusCircle(90012, "没有关注该圈子的权限"),
    /**
     * 已经加入该圈子
     */
    AlreadyJoinCircle(90013, "已经加入该圈子"),
    /**
     * 已经关注该圈子
     */
    AlreadyFocusCircle(90014, "已经关注该圈子"),
    /**
     * 未加入该圈子
     */
    NotJoinCircle(90015, "未加入该圈子"),
    /**
     * 未关注该圈子
     */
    NotFocusCircle(90016, "未关注该圈子"),
    /**
     * 父帖子不是问题贴
     */
    ParentPostNotQuestion(90021, "父帖子不是问题贴"),
    /**
     * 当前用户不是帖子的创建人
     */
    NotPostCreator(90022, "当前用户不是帖子的创建人");

    private int value;
    private String name;

    private ExceptionTypes(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取枚举变量对应的数值
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取枚举变量对应的提示信息
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 通过数值获取枚举对象
     *
     * @param value
     * @return
     */
    public static ExceptionTypes parse(int value) {
        ExceptionTypes type = ExceptionTypes.UnknownException;
        for (ExceptionTypes t : ExceptionTypes.values()) {
            if (value == t.getValue()) {
                type = t;
                break;
            }
        }
        return type;
    }

    /**
     * 是否有异常
     *
     * @return 是否有异常
     */
    public boolean isNoException() {
        if (this == UnknownException) return true;
        return false;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
