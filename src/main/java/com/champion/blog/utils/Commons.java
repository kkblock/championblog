package com.champion.blog.utils;

import cn.hutool.core.util.RandomUtil;
import com.champion.blog.constant.WebConst;
import com.champion.blog.dto.Types;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.model.vo.MetaVo;
import com.champion.blog.model.vo.OptionVo;
import com.champion.blog.model.vo.UserVo;
import com.champion.blog.service.MetaService;
import com.champion.blog.service.OptionService;
import com.champion.blog.service.UserService;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主题公共函数
 */
@Component
public final class Commons {

    @Resource
    private OptionService optionService;

    @Resource
    private UserService userService;

    @Resource
    private MetaService metaService;

    private static final String[] colors = new String[]{"red", "orange", "yellow", "olive", "green", "teal", "blue", "violet", "purple", "pink", "brown", "grey", "black"};

    /**
     * 初始化必要信息
     */
    @PostConstruct
    private void initialize() {
        List<OptionVo> voList = optionService.getOptions();
        Map<String, String> options = new HashMap<>();
        voList.forEach((option) -> {
            options.put(option.getName(), option.getValue());
        });
        WebConst.initConfig = options;

        List<UserVo> userAll = userService.findAll();
        WebConst.userAll = userAll;

    }

    public static String THEME = "themes/default";

    /**
     * 判断分页中是否有数据
     *
     * @param paginator mybatis PageInfo
     * @return boolean ,have data？
     */
    public static boolean is_empty(PageInfo paginator) {
        return paginator == null || (paginator.getList() == null) || (paginator.getList().size() == 0);
    }

    public static boolean is_blank(String param) {
        return StringUtils.isBlank(param);
    }

    public static boolean is_map_null(Map map) {
        return !(Objects.nonNull(map) && map.size() > 0);
    }

    public static boolean is_list_null(List map) {
        return !(Objects.nonNull(map) && map.size() > 0);
    }

    /**
     * 网站链接
     *
     * @return 网站链接 url
     */
    public static String site_url() {
        return site_url("");
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return 网站链接下的全址
     */
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }

    public static String site_email() {
        return site_option("mail", "kuangkai_jy@163.com");
    }

    /**
     * 网站标题
     *
     * @return 网站标题
     */
    public static String site_title() {
        return site_option("site_title");
    }

    /**
     * 网站配置项
     *
     * @param key key
     * @return value
     */
    public static String site_option(String key) {
        return site_option(key, "");
    }

    /**
     * 网站配置项
     *
     * @param key          key
     * @param defalutValue 默认值
     * @return value
     */
    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        String str = WebConst.initConfig.get(key);
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return defalutValue;
        }
    }

    /**
     * 截取字符串
     *
     * @param str str
     * @param len len
     * @return substr
     */
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    /**
     * 返回主题URL
     *
     * @return 主题URL
     */
    public static String theme_url() {
        return site_url(Commons.THEME);
    }

    /**
     * 返回主题下的文件路径
     *
     * @param sub sub
     * @return 主题下的文件路径
     */
    public static String theme_url(String sub) {
        return site_url(Commons.THEME + sub);
    }

    /**
     * 返回github头像地址
     *
     * @param email email
     * @return github头像地址
     */
    public static String gravatar(String email) {
        String avatarUrl = "https://github.com/identicons/";
        if (StringUtils.isBlank(email)) {
            email = "user@hanshuai.xin";
        }
        String hash = ChampionUtils.MD5encode(email.trim().toLowerCase());
        return avatarUrl + hash + ".png";
    }

    /**
     * 返回文章链接地址
     *
     * @param contents contentVo
     * @return 文章链接地址
     */
    public static String permalink(ContentVo contents) {
        return permalink(contents.getCid(), contents.getSlug());
    }


    /**
     * 获取随机数
     *
     * @param max max
     * @param str str
     * @return 随机数
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }

    /**
     * 返回文章链接地址
     *
     * @param cid  contentId
     * @param slug slug
     * @return 文章链接地址
     */
    public static String permalink(Integer cid, String slug) {
        return site_url("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime unixTime
     * @return 格式化unix时间戳为日期
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime unixTime
     * @param patten   patten
     * @return 格式化unix时间戳为日期
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }

    /**
     * 显示分类
     *
     * @param categories categories
     * @return 显示分类
     */
    public static String show_categories(String contextPath, String categories) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(categories)) {
            String[] arr = categories.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a href=\"" + contextPath + "/category/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return show_categories(contextPath, "默认分类");
    }

    /**
     * 显示标签
     *
     * @param tags tags
     * @return 显示标签
     */
    public static String show_tags(String contextPath, String tags) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(tags)) {
            String[] arr = tags.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a href=\"" + contextPath + "/tag/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return "";
    }

    /**
     * 截取文章摘要
     *
     * @param value 文章内容
     * @param len   要截取文字的个数
     * @return 截取文章摘要
     */
    public static String intro(String value, int len) {
        int pos = value.indexOf("<!--more-->");
        if (pos != -1) {
            String html = value.substring(0, pos);
            String text = ChampionUtils.htmlToText(ChampionUtils.mdToHtml(html));
            if (text.length() > len) {
                return text.substring(0, len) + "......";
            }
            return text;
        } else {
            String text = ChampionUtils.htmlToText(ChampionUtils.mdToHtml(value));
            if (text.length() > len) {
                return text.substring(0, len) + "......";
            }
            return text;
        }
    }

    /**
     * 显示文章内容，转换markdown为html
     *
     * @param value value
     * @return 文章内容
     */
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return ChampionUtils.mdToHtml(value);
        }
        return "";
    }

    /**
     * 显示文章缩略图，顺序为：文章第一张图 -> 随机获取
     *
     * @return 显示文章缩略图
     */
    public static String show_thumb(ContentVo contents) {
        int cid = contents.getCid();
        int size = cid % 20;
        size = size == 0 ? 1 : size;
        return "/user/img/rand/" + size + ".jpg";
    }


    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     *
     * @param value value
     * @return 种格式的字符转换为emoji表情
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    /**
     * 获取文章第一张图片
     *
     * @return 获取文章第一张图片
     */
    public static String show_thumb(String content) {
        content = ChampionUtils.mdToHtml(content);
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            Matcher m_image = p_image.matcher(content);
            if (m_image.find()) {
                img = img + "," + m_image.group();
                // //匹配src
                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return "";
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    /**
     * 显示文章图标
     *
     * @param cid cid
     * @return 文章图标
     */
    public static String show_icon(int cid) {
        return ICONS[cid % ICONS.length];
    }

    /**
     * 获取社交的链接地址
     *
     * @return
     */
    public static Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
        map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
        map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
        map.put("github", WebConst.initConfig.get(prefix + "github"));
        map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));
        return map;
    }

    /**
     * 根据id获取作者
     *
     * @param uid uid
     * @return username
     */
    public static String getUserName(Integer uid) {
        List<UserVo> userAll = WebConst.userAll;
        AtomicReference<String> targetName = new AtomicReference<>();
        for (UserVo d : userAll) {
            if (d.getUid().equals(uid)) {
                targetName.set(d.getUsername());
                break;
            }
        }
        if ("admin".equalsIgnoreCase(targetName.get()) || "kuangkai".equalsIgnoreCase(targetName.get())) {
            targetName.set("邝凯");
        }
        return targetName.get();
    }

    /**
     * 获取文章标题的指定长度 避免超出边界
     *
     * @param title
     * @param size
     * @return
     */
    public static String getCatalogTileSubSize(String title, int size) {
        if (StringUtils.isBlank(title)) {
            return title;
        }
        if (title.length() >= size) {
            return title.substring(0, size) + "...";
        } else {
            return title;
        }
    }

    /**
     * 获取随机颜色
     *
     * @return semantic 支持的随机颜色名称
     */
    public static String getRandomColor(int i) {
        String basicClass = "ui mini label ";
        int colorLength = colors.length;
        if (i >= colorLength) {
            int randomInt = RandomUtil.randomInt(0, colorLength -1);
            return basicClass + colors[randomInt];
        }
        return basicClass + colors[i];
    }

    /**
     * 获取随机颜色
     *
     * @return semantic 支持的随机颜色名称
     */
    public static String getRandomColor() {
        String basicClass = "ui mini label ";
        int colorLength = colors.length;
        int randomInt = RandomUtil.randomInt(0, colorLength - 1);
        return basicClass + colors[randomInt];
    }

}
