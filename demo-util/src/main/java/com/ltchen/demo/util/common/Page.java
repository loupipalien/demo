package com.ltchen.demo.util.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author : ltchen
 * @date : 2017/11/15
 * @desc : 分页工具类
 * 请求方通常会传递页码和页大小, 服务方则先会统计出总数再查询出分页的数据,设置总数时可能会影响到 start, end, number, count 字段.
 * 查询分页数据时可能需要 起始下标, 后取长度, 结束下标 等,分别对应 start, size, end 字段, 构造实例再 get 即可.
 * 最后将查询出数据装载完成
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 5886312444896584218L;

    private static final int DEFAULT_NUMBER = 1;
    private static final int DEFAULT_SIZE = 10;
    private int number;// 页码
    private int size;// 页大小
    private int count; // 页数
    private int start;// 起始
    private int end;// 结束
    private int total;// 总数
    private List<T> data;// 数据

    public Page(int number, int size) {
        // 根据传递的 number 和 size 先修正为非负
        this.number = number > 0 ? number : DEFAULT_NUMBER;
        this.size = size > 0 ? size : DEFAULT_SIZE;
        // 计算出 start 和 end
        this.start = this.number > 0 ? (this.number - 1) * this.size : 0;
        this.end = start + this.size - 1;
    }

    public Page(int number, int size, int total) {
        this(number, size);
        this.setTotal(total);
    }

    public int getNumber() {
        return number;
    }


    public int getSize() {
        return size;
    }

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public List<T> getData() {
        return data;
    }

    public void setTotal(int total) {
        // 根据传递的 total 先修正为非负
        this.total = total > 0 ? total : 0;
        // 根据 total 修正 number, start, end
        while (start > this.total) {
            number = number--;
            start = (number - 1) * size;
            end = start + size - 1;
        }
        // 根据 total 修正 end
        end = this.total > end ? end : this.total;
        // 根据 total 和 size 设置 count
        count = (total % size) == 0 ? total / size : (total / size) + 1;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
