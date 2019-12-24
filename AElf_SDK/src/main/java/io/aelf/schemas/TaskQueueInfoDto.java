package io.aelf.schemas;

/**
 * @author linhui linhui@tydic.com
 * @title: TaskQueueInfoDto
 * @description: TODO
 * @date 2019/12/1514:37
 */
public class TaskQueueInfoDto {
    private String Name;
    private int Size;
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public int getSize() {
        return Size;
    }
    public void setSize(int size) {
        this.Size = size;
    }

}
