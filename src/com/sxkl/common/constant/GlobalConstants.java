package com.sxkl.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sxkl.menu.model.ParentMenu;

/**
 * 全局常量配置类
 * @author wanguao
 * @date 2015-11-03
 */
public class GlobalConstants {
	
	//考勤记录类型
	public static final Map<String,String> ATTENDENCETIMERECORDTYPE = new HashMap<String,String>();
	public static final String ATTENDENCE_TIME_RECORD_TYPE_AM_ON = "amOnTime";
	public static final String ATTENDENCE_TIME_RECORD_TYPE_AM_OFF = "amOffTime";
	public static final String ATTENDENCE_TIME_RECORD_TYPE_PM_ON = "pmOnTime";
	public static final String ATTENDENCE_TIME_RECORD_TYPE_PM_OFF = "pmOffTime";
	public static final String ATTENDENCE_TIME_RECORD_TYPE_1 = "late";//迟到
	public static final String ATTENDENCE_TIME_RECORD_TYPE_2 = "early";//早退
	public static final String ATTENDENCE_TIME_RECORD_TYPE_3 = "success";//正常
	public static final String ATTENDENCE_TIME_RECORD_TYPE_4 = "week";//周末
	public static final String ATTENDENCE_TIME_RECORD_TYPE_5 = "节日";
	public static final String ATTENDENCE_TIME_RECORD_TYPE_6 = "leave";//请假
	public static final String ATTENDENCE_TIME_RECORD_TYPE_7 = "absence";//缺勤
	public static final Integer LEAVE_RECORD_DEFICIENCY_RANGE = -30;
	public static final String ATTENDENCE_TIME_RECORD_VALUE_1 = "缺勤";
	public static final String[] FISRT_NAME_DATA_ARR= new String[]{
		    "赵","钱","孙","李","周","吴","郑","王","冯","陈",
		    "褚","卫","蒋","沈","韩","杨","朱","秦","尤","许",
		    "何","吕","施","张","孔","曹","严","华","金","魏",
		    "陶","姜","戚","谢","邹","喻","柏","水","窦","章",
		    "云","苏","潘","葛","奚","范","彭","郎","鲁","韦",
		    "昌","马","苗","凤","花","方","俞","任","袁","柳",
		    "酆","鲍","史","唐","费","廉","岑","薛","雷","贺",
		    "倪","汤","滕","殷","罗","毕","郝","邬","安","常",
		    "乐","于","时","傅","皮","卞","齐","康","伍","余",
		    "元","卜","顾","孟","平","黄","和","穆","萧","尹",
		    "姚","邵","湛","汪","祁","毛","禹","狄","米","贝",
		    "明","臧","计","伏","成","戴","谈","宋","茅","庞",
		    "熊","纪","舒","屈","项","祝","董","粱","杜","阮",
		    "蓝","闵","席","季","麻","强","贾","路","娄","危",
		    "江","童","颜","郭","梅","盛","林","刁","钟","徐",
		    "邱","骆","高","夏","蔡","田","樊","胡","凌","霍",
		    "虞","万","支","柯","咎","管","卢","莫","经","房",
		    "裘","缪","干","解","应","宗","宣","丁","贲","邓",
		    "郁","单","杭","洪","包","诸","左","石","崔","吉",
		    "钮","龚","程","嵇","邢","滑","裴","陆","荣","翁",
		    "荀","羊","於","惠","甄","麴","加","封","芮","羿",
		    "储","汲","邴","糜","松","井","段","富","巫","乌",
		    "焦","巴","弓","牧","隗","山","谷","车","侯","宓",
		    "蓬","全","郗","班","仰","秋","仲","伊","宫","宁",
		    "仇","栾","暴","甘","钭","厉","戎","祖","武","符",
		    "刘","景","詹","束","龙","叶","幸","司","韶","郜",
		    "黎","蓟","薄","印","宿","白","怀","蒲","台","从",
		    "鄂","索","咸","籍","赖","卓","蔺","屠","胥","能",
		    "苍","双","闻","莘","党","翟","谭","贡","劳","逄",
		    "姬","申","扶","堵","冉","宰","郦","雍","郤","璩",
		    "桑","桂","濮","牛","寿","通","边","扈","燕","冀",
		    "郏","浦","尚","农","温","别","庄","晏","柴","瞿",
		    "阎","充","慕","连","茹","习","宦","艾","鱼","容",
		    "向","古","易","慎","戈","廖","庚","终","暨","居",
		    "衡","步","都","耿","满","弘","匡","国","文","寇",
		    "广","禄","阙","东","殴","殳","沃","利","蔚","越",
		    "夔","隆","师","巩","厍","聂","晁","勾","敖","融",
		    "冷","訾","辛","阚","那","简","饶","空","曾","毋",
		    "沙","乜","养","鞠","须","丰","巢","关","蒯","相",
		    "查","后","荆","红","游","竺","权","逯","盖","益",
		    "桓","公","万","俟","司","马","上官","欧阳","夏侯","诸葛",
		    "闻人","东方","赫连","皇甫","尉迟","公羊","澹台","公冶","宗政","濮阳",
		    "淳于","仲孙","太叔","申屠","公孙","乐正","轩辕","令狐","钟离","闾丘",
		    "长孙","慕容","鲜于","宇文","司徒","司空","亓官","司寇","仉督","子车",
		    "颛孙","端木","巫马","公西","漆雕","乐正","壤驷","公良","拓拔","夹谷",
		    "宰父","谷粱","晋楚","闫法","汝鄢","涂钦","段干","百里","东郭","南门",
		    "呼延","妫海","羊舌","微生","岳帅","缑亢","况後","有琴","梁丘","左丘",
		    "东门","西门","商牟","佘佴","伯赏","南宫","墨哈","谯笪","年爱","阳佟"
	};
	public final static String CONTENT_TYPE = "text/html;charset=gb2312";  
	public static String FISRT_NAME_DATA_STR = "";
    public static List<ParentMenu> S_PARENT_MENUS = new ArrayList<ParentMenu>(1000);
	
	static{
		//上午上班
		ATTENDENCETIMERECORDTYPE.put("amOnTime", "amOnTime");
		//上午下班
		ATTENDENCETIMERECORDTYPE.put("amOffTime", "amOffTime");
		//下午上班
		ATTENDENCETIMERECORDTYPE.put("pmOnTime", "pmOnTime");
		//下午下班
		ATTENDENCETIMERECORDTYPE.put("pmOffTime", "pmOffTime");
		StringBuffer firstName = new StringBuffer();
		firstName.append("赵钱孙李周吴郑王冯陈")
			     .append("褚卫蒋沈韩杨朱秦尤许")
			     .append("何吕施张孔曹严华金魏")
			     .append("陶姜戚谢邹喻柏水窦章")
			     .append("云苏潘葛奚范彭郎鲁韦")
			     .append("昌马苗凤花方俞任袁柳")
			     .append("酆鲍史唐费廉岑薛雷贺")
			     .append("倪汤滕殷罗毕郝邬安常")
			     .append("乐于时傅皮卞齐康伍余")
			     .append("元卜顾孟平黄和穆萧尹")
			     .append("姚邵湛汪祁毛禹狄米贝")
			     .append("明臧计伏成戴谈宋茅庞")
			     .append("熊纪舒屈项祝董粱杜阮")
			     .append("蓝闵席季麻强贾路娄危")
			     .append("江童颜郭梅盛林刁钟徐")
			     .append("邱骆高夏蔡田樊胡凌霍")
			     .append("虞万支柯咎管卢莫经房")
			     .append("裘缪干解应宗宣丁贲邓")
			     .append("郁单杭洪包诸左石崔吉")
			     .append("钮龚程嵇邢滑裴陆荣翁")
			     .append("荀羊於惠甄麴加封芮羿")
			     .append("储汲邴糜松井段富巫乌")
			     .append("焦巴弓牧隗山谷车侯宓")
			     .append("蓬全郗班仰秋仲伊宫宁")
			     .append("仇栾暴甘钭厉戎祖武符")
			     .append("刘景詹束龙叶幸司韶郜")
			     .append("黎蓟薄印宿白怀蒲台从")
			     .append("鄂索咸籍赖卓蔺屠胥能")
			     .append("苍双闻莘党翟谭贡劳逄")
			     .append("姬申扶堵冉宰郦雍郤璩")
			     .append("桑桂濮牛寿通边扈燕冀")
			     .append("郏浦尚农温别庄晏柴瞿")
			     .append("阎充慕连茹习宦艾鱼容")
			     .append("向古易慎戈廖庚终暨居")
			     .append("衡步都耿满弘匡国文寇")
			     .append("广禄阙东殴殳沃利蔚越")
			     .append("夔隆师巩厍聂晁勾敖融")
			     .append("冷訾辛阚那简饶空曾毋")
			     .append("沙乜养鞠须丰巢关蒯相")
			     .append("查后荆红游竺权逯盖益")
			     .append("桓公万俟司马上官欧阳夏侯诸葛")
			     .append("闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳")
			     .append("淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘")
			     .append("长孙慕容鲜于宇文司徒司空亓官司寇仉督子车")
			     .append("颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷")
			     .append("宰父谷粱晋楚闫法汝鄢涂钦段干百里东郭南门")
			     .append("呼延妫海羊舌微生岳帅缑亢况後有琴梁丘左丘")
			     .append("东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟");
		FISRT_NAME_DATA_STR = firstName.toString();
	}

}
