package com.wowfilm.wechatsdk.template;

import com.wowfilm.wechatsdk.common.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.wowfilm.wechatsdk.common.StringTemplate.compileFromClassPath;


public class GroupMessage {

    private static StringTemplate T_CREATE_GROUP = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/group-create.json");
    private static StringTemplate T_SEARCH_GROUP = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/group-search.json");
    private static StringTemplate T_MODIFY_GROUP = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/group-modify.json");
    private static StringTemplate T_MODIFY_USERGROUP = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/group-modify-usergroup.json");

    /**
     * @param groupName 分组名字（30个字符以内）
     */
    public static String wrapCreateGroup(String groupName) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", groupName);
        return T_CREATE_GROUP.replace(params);
    }

    /**
     * @param openid 用户的OpenID
     */
    public static String wrapQueryUserGroup(String openid) {

        Map<String, Object> params = new HashMap<>();
        params.put("openid", openid);
        return T_SEARCH_GROUP.replace(params);
    }

    /**
     * @param groupId   分组id，由微信分配
     * @param groupName 分组名字（30个字符以内）
     */
    public static String wrapModifyGroup(String groupId, String groupName) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", groupId);
        params.put("name", groupName);
        return T_MODIFY_GROUP.replace(params);
    }

    /**
     * @param openid    用户唯一标识符
     * @param toGroupid 分组id
     */
    public static String wrapModifyUserGroup(String openid, String toGroupid) {

        Map<String, Object> params = new HashMap<>();
        params.put("openid", openid);
        params.put("to_groupid", toGroupid);
        return T_MODIFY_USERGROUP.replace(params);
    }

}
