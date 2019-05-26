package cn.itcast.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分类实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    private int cid;//分类id
    private String cname;//分类名称

}
