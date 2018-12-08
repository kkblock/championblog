package com.champion.blog.dto;

import com.champion.blog.model.vo.MetaVo;

/**
 * PO(Persistant Object) 持久对象 用于表示数据库中的一条记录映射成的 java 对象。PO 仅仅用于表示数据，没有任何数据操作。通常遵守 Java Bean 的规范，拥有 getter/setter 方法。
 * BO(Business Object) 业务对象 主要作用是把业务逻辑封装为一个对象。这个对象可以包括一个或多个其它的对象。
 * VO(Value Object) 表现对象 前端界面展示；value object值对象；ViewObject表现层对象；主要对应界面显示的数据对象。
 * DTO(Data Transfer Object) 数据传输对象 前端调用时传输；也可理解成“上层”调用时传输;
 * DAO(Data access object) 数据访问对象
 * POJO(Plain ordinary java object) 简单java对象
 */
public class MetaDto extends MetaVo {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
